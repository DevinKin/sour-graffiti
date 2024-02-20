(ns sour.graffiti.database.interface-test
  (:require [clojure.test :as test :refer [deftest is use-fixtures]]
            [sour.graffiti.app-state.interface :as app-state]
            [sour.graffiti.database.interface :as database]))

(defonce db (atom nil))

(defn prepare-for-tests
  [f]
  (let [config (app-state/system-config {:profile :test})
        test-db (->> config
                     (app-state/init-system))]
    (database/reset test-db)
    (reset! db test-db)
    (f)
    (some-> (deref db) (app-state/halt-system))))

(use-fixtures :each prepare-for-tests)

(deftest query-test
  (let [query-fn (:db.sql/query-fn @db)]
    (is (= (:col (query-fn :select-test {})) 1))))


