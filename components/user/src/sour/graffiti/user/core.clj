(ns sour.graffiti.user.core
  (:require
   [buddy.sign.jwt :as jwt]
   [tick.core :as t]
   [sour.graffiti.user.store :as store]
   [sour.graffiti.app-state.interface :as app-state]
   [crypto.password.pbkdf2 :as crypto]))

(defn encrypt-password [password]
  (-> password crypto/encrypt str))

(defn- generate-token [email name]
  (let [{:keys [secret alg expire]} (:jwt/signed (app-state/system))
        now (t/now)
        claim {:sub name
               :iss email
               :exp (t/>> now (apply t/new-duration expire))
               :iat now}]
    (jwt/sign claim secret {:alg alg})))

(defn user->visible-user [user token]
  {:user (-> user
             (assoc :token token)
             (dissoc :id :password :active))})

(defn regist!
  [{:keys [name email password]}]
  (if-let [_ (store/find-by-email email)]
    [false {:errors {:email "A user exists with given email."}}]
    (if-let [_ (store/find-by-name name)]
      [false {:errors {:name "A user exists with given name."}}]
      (let [user {:name name :email email :password (encrypt-password password)}
            _ (store/add-user! user)]
        (if-let [user (store/find-by-email email)]
          (let [token (generate-token email (:name user))]
            [true (user->visible-user user token)])
          [false {:errors {:other "Cannot add user into db."}}])))))

(defn login!
  [{:keys [email password]}]
  (if-let [user (store/find-by-email email)]
    (if (:active user)
      (if (crypto/check password (:password user))
        (let [token (generate-token email (:name user))]
          [true (user->visible-user user token)])
        [false {:errors {:password "Invalid password."}}])
      [false {:errors {:active "The user has not yet activated."}}])
    [false {:errors {:email "Invalid email."}}]))

(defn active-user!
  [{:keys [email active]}]
  (if-let [user (store/find-by-email email)]
    [true (store/update-user! {:name (:name user)
                               :active active})]
    [false {:errors {:email "Invalid email."}}]))

(defn update-user-password!
  [{:keys [email origin-password new-password]}]
  (if-let [user (store/find-by-email email)]
    (if (and (not (nil? origin-password))
             (not (crypto/check origin-password (:password user))))
      [false {:errors {:origin-password "Origin password not match."}}]
      (let [update-user {:name (:name user)
                         :password (encrypt-password new-password)}]
        [true (store/update-user! update-user)]))
    [false {:errors {:email "Invalid email."}}]))
