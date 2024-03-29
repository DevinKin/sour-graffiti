(ns sour.graffiti.user.core-test
  (:require
   [crypto.password.pbkdf2 :as crypto]
   [clojure.test :refer [deftest is are]]
   [malli.core :as m]
   [malli.generator :as mg]
   [sour.graffiti.user.spec :as spec]
   [sour.graffiti.user.core :as core]
   [sour.graffiti.user.store :as store]
   [sour.graffiti.app-state.interface :as app-state]
   [sour.graffiti.database.interface :as database]))

(def exist-user (mg/generate spec/register))
(def user (mg/generate spec/register))
(def invalid-email "juju@163.com")
(def new-password "Ab123456&")

(deftest encrypt-password-result
  (let [password "a123456"
        encrypt-password (core/encrypt-password password)]
    (is (crypto/check password encrypt-password))))


(deftest regist-email-exists-result
  (let [[ok? res] (core/regist! (assoc exist-user :name "juju"))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:email "A user exists with given email."}})))

(deftest regist-name-exists-result
  (let [[ok? res] (core/regist! (assoc exist-user :email invalid-email))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:name "A user exists with given name."}})))

(deftest regist-correct-result
  (let [[ok? res] (core/regist! user)]
    (are [x y] (= x y)
      ok? true
      (select-keys (:user res) [:name :email]) (select-keys user [:name :email])
      (m/validate spec/authenticated-user res) true)))

(deftest user-active-invalid-email-result
  (let [[ok? res] (core/active-user! (assoc user :email invalid-email))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:email "Invalid email."}})))

(deftest user-active-correct-result
  (let [[ok? res] (core/active-user! (assoc user :active true))]
    (are [x y] (= x y)
      ok? true
      res 1)))

(deftest login-user-invalid-email-result
  (let [[ok? res] (core/login! (assoc user :email invalid-email))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:email "Invalid email."}})))

(deftest login-user-invalid-password-result
  (let [[ok? res] (core/login! (assoc user :password "lala123"))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:password "Invalid password."}})))

(deftest login-user-not-active-result
  (let [[ok? res] (core/login! user)]
    (are [x y] (= x y)
      ok? false
      res {:errors {:active "The user has not yet activated."}})))

(deftest login-correct-result
  (let [[ok? res] (core/login! user)]
    (are [x y] (= x y)
      ok? true
      (select-keys (:user res) [:name :email]) (select-keys user [:name :email])
      (m/validate spec/authenticated-user res) true)))

(deftest update-password-invalid-email-result
  (let [[ok? res] (core/update-user-password! (assoc user
                                                     :email "lalanew@163.com"
                                                     :origin-password (:password user)
                                                     :new-password new-password))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:email "Invalid email."}})))

(deftest update-password-not-match-result
  (let [[ok? res] (core/update-user-password! (assoc user
                                                     :origin-password (str (:password user) "error")
                                                     :new-password new-password))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:origin-password "Origin password not match."}})))

(deftest update-password-correct-result
  (let [[ok? res] (core/update-user-password! (assoc user
                                                     :origin-password (:password user)
                                                     :new-password new-password))
        {:keys [password]} (store/find-by-email (:email user))]
    (are [x y] (= x y)
      ok? true
      res 1
      (crypto/check new-password password) true)))

(defn test-ns-hook
  []
  ;; setup test system
  (-> {:profile :test}
      (app-state/system-config)
      (select-keys [:db.sql/connection :db.sql/query-fn :db.sql/migrations :jwt/signed])
      (app-state/init-system)
      (app-state/setup-system!))

  ;; add an user
  (store/add-user! exist-user)

  ;; unit test in order
  (encrypt-password-result)
  (regist-email-exists-result)
  (regist-name-exists-result)
  (regist-correct-result)
  ;;(user-active-invalid-email-result)
  ;;(login-user-not-active-result)
  ;;(user-active-correct-result)
  (login-user-invalid-email-result)
  (login-user-invalid-password-result)
  (login-correct-result)
  (update-password-invalid-email-result)
  (update-password-not-match-result)
  (update-password-correct-result)

  ;; reset migrations
  (database/reset (app-state/system))

  ;; halt test system
  (app-state/halt-system))

