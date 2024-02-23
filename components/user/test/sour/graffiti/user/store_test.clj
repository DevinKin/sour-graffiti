(ns sour.graffiti.user.store-test
  (:require [clojure.test :refer [deftest is are]]
            [sour.graffiti.user.store :as user]
            [sour.graffiti.app-state.interface :as app-state]
            [sour.graffiti.database.interface :as database]))

(def user {:name "lala" :email "lala@163.com" :password "a123456"})

(deftest add-user-test
  (let [result (user/add-user! user)]
    (is (= 1 result))))

(deftest find-user-by-id-test
  (let [user1 (user/get-user-by-id 1)]
    (are [x y] (= x y)
      1 (:id user1)
      "lala" (:name user1))))

(deftest update-user-password-test
  (let [update-user (assoc user :password "a1234")
        result (user/update-user! update-user)
        db-user (user/get-user-by-id 1)]
    (are [x y] (= x y)
      1 result
      (:password update-user) (:password db-user))))

(deftest update-user-active-test
  (let [new-user (assoc user :active true)
        result (user/update-user! new-user)
        db-user (user/get-user-by-id 1)]
    (are [x y] (= x y)
      1 result
      (:active new-user) (:active db-user))))

(deftest update-user-password-and-active-test
  (let [new-user (assoc user :password "a123456" :active false)
        result (user/update-user! new-user)
        db-user (user/get-user-by-id 1)]
    (are [x y] (= x y)
      1 result
      (:password new-user) (:password db-user)
      (:active new-user) (:active db-user))))

(deftest find-user-test
  (let [name "lala"
        email "lala@163.com"
        find-by-name (user/find-by-name name)
        find-by-email (user/find-by-email email)]
    (are [x y] (= x y)
      name (:name find-by-name)
      email (:email find-by-email))))

(defn test-ns-hook
  []
  ;; setup test system
  (-> {:profile :test}
      (app-state/system-config)
      (select-keys [:db.sql/connection :db.sql/query-fn :db.sql/migrations])
      (app-state/init-system)
      (app-state/setup-system!))

  ;; unit test in order
  (add-user-test)
  (find-user-by-id-test)
  (update-user-password-test)
  (update-user-active-test)
  (update-user-password-and-active-test)
  (find-user-test)

  ;; reset migrations
  (database/reset (app-state/system))

  ;; halt test system
  (app-state/halt-system))
