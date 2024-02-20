(ns sour.graffiti.database.test-setup
  (:require
   [sour.graffiti.app-state.interface :as app-state]))

(defonce test-system (atom nil))

(defn setup
  [project-name]
  (let [config (app-state/system-config {:profile :test})]
    (->> config
         (app-state/init-system)
         (reset! test-system)))
  (println (str "--- test setup for " project-name " component database ---")))

(defn teardown
  [project-name]
  (some-> (deref test-system) (app-state/halt-system))
  (println (str "--- test teardown for " project-name " component database ---")))

(comment
  (setup "test")
  (prn @test-system)
  (teardown "test"))
