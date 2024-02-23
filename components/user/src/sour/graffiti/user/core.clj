(ns sour.graffiti.user.core
  (:require
   [sour.graffiti.user.store :as store]
   [crypto.password.pbkdf2 :as crypto]))

(defn encrypt-password [password]
  (-> password crypto/encrypt str))


(defn user->visible-user [user token]
  {:user (-> user
             (assoc :token token)
             (dissoc :password))})

(defn register!
  [{:keys [name email password]}]
  (if-let [_ (store/find-by-email email)]
    [false {:errors {:email "A user exists with given email."}}]
    (if-let [_ (store/find-by-name name)]
      [false {:errors {:name "A user exists with given name."}}]
      (let [user {:name name :email email :password (encrypt-password password)}
            _ (store/add-user! user)]
        (if-let [user (store/find-by-email email)]
          [true (user->visible-user user nil)]
          [false {:errors {:other "Cannot add user into db."}}])))))


