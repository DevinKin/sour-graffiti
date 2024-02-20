(ns sour.graffiti.app-state.interface-test
  (:require [clojure.test :as test :refer :all]
            [sour.graffiti.app-state.interface :as app-state]))

#_(deftest system-config-read-test
    (is (= (:system/env (app-state/system-config {:profile :dev})) :dev)))

