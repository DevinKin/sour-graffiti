(ns sour.graffiti.user.core-test
  (:require
   [clojure.test :refer [deftest are]]
   [sour.graffiti.user.core :as core]
   [sour.graffiti.user.store :as store]
   [sour.graffiti.app-state.interface :as app-state]
   [sour.graffiti.database.interface :as database]))

(def exist-user {:name "lala" :email "lala@163.com" :password "a123456"})
(def user {:name "damao" :email "damao@163.com" :password "a123456"})

(deftest regist-email-exists-result
  (let [[ok? res] (core/regist! (assoc exist-user :name "juju"))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:email "A user exists with given email."}})))

(deftest regist-name-exists-result
  (let [[ok? res] (core/regist! (assoc exist-user :email "juju@163.com"))]
    (are [x y] (= x y)
      ok? false
      res {:errors {:name "A user exists with given name."}})))

(deftest regist-correct-result
  (let [[ok? res] (core/regist! user)]
    (are [x y] (= x y)
      ok? true
      res user)))

(defn test-ns-hook
  []
  ;; setup test system
  (-> {:profile :test}
      (app-state/system-config)
      (select-keys [:db.sql/connection :db.sql/query-fn :db.sql/migrations])
      (app-state/init-system)
      (app-state/setup-system!))

  ;; add an user
  (store/add-user! exist-user)

  ;; unit test in order
  (regist-email-exists-result)
  (regist-name-exists-result)

  ;; reset migrations
  (database/reset (app-state/system))

  ;; halt test system
  (app-state/halt-system))
