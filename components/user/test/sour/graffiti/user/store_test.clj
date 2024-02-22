(ns sour.graffiti.user.store-test
  (:require [clojure.test :as test :refer :all]
            [sour.graffiti.user.store :as user]))

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

(deftest dummy-test
  (is (= 1 1)))
