(ns sour.graffiti.user.core
  (:require
   [sour.graffiti.user.store :as store]
   [crypto.password.pbkdf2 :as crypto]))

(defn encrypt-password [password]
  (-> password crypto/encrypt str))

(defn regist!
  [{:keys [name email password]}]
  (if-let [_ (store/find-by-email email)]
    [false {:errors {:email "A user exists with given email."}}]
    (if-let [_ (store/find-by-name name)]
      [false {:errors {:name "A user exists with given name."}}]
      (let [user {:name name :email email :password (encrypt-password password)}
            _ (store/add-user! user)]
        (if-let [user (store/find-by-email email)]
          [true user]
          [false {:errors {:other "Cannot add user into db."}}])))))

(defn login
  [{:keys [email password]}]
  (if-let [user (store/find-by-email email)]
    (if (:active user)
      [false {:errors {:password "The user has not yet activated."}}]
      (if (crypto/check password (:password user))
        [true user]
        [false {:errors {:password "Invalid password."}}]))
    [false {:errors {:email "Invalid email."}}]))

(defn active-user!
  [{:keys [email active]}]
  (if-let [user (store/find-by-email email)]
    (if (nil? active)
      (store/update-user! {:name (:name user)
                           :active (:active user)})
      [false {:errors {:active "Invalid active status."}}])
    [false {:errors {:email "Invalid email."}}]))


(defn update-user-password!
  [{:keys [email origin-password new-password]}]
  (if-let [user (store/find-by-email email)]
    (if (and (not (nil? origin-password))
             (not (crypto/check origin-password (:password user))))
      [false "Origin password not match."]
      (let [update-user {:name (:name user)
                         :password new-password}]
        (store/update-user! update-user)))
    [false {:errors {:email "Invalid email."}}]))
