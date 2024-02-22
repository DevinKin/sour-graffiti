(ns sour.graffiti.user.store-test
  (:require [clojure.test :as test :refer :all]
            [sour.graffiti.user.store :as user]
            [sour.graffiti.app-state.interface :as app-state]
            [sour.graffiti.database.interface :as database]))

(defn prepare-for-tests
  [f]
  ;; setup test system
  (-> {:profile :test}
      (app-state/system-config)
      (select-keys [:db.sql/connection :db.sql/query-fn :db.sql/migrations])
      (app-state/init-system)
      (app-state/setup-system!))

  ;; reset migrations
  (database/reset (app-state/system))
  (f)

  ;; halt test system
  (app-state/halt-system))

(use-fixtures :once prepare-for-tests)

(deftest add-user-test
  (let [result (user/add-user! {:name "lala" :email "lala@163.com" :password "a123456"})]
    (is (= 1 result))))

(deftest find-user-by-id-test
  (let [user (user/get-user-by-id 1)]
    (prn user)
    (is (= 1 (:id user)))))

