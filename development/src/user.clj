(ns user
  "Userspace functions you can run by default in your local REPL."
  (:require
   [clojure.pprint]
   [clojure.string :as string]
   [clojure.spec.alpha :as s]
   [clojure.tools.namespace.repl :as repl]
   ;[criterium.core :as c]                                  ;; benchmarking
   [expound.alpha :as expound]
   [integrant.core :as ig]
   [integrant.repl :refer [clear go halt prep init reset reset-all]]
   [integrant.repl.state :as state]
   [lambdaisland.classpath.watch-deps :as watch-deps]
   [sour.graffiti.app-state.interface :as app-state]
   [sour.graffiti.database.interface]
   [sour.graffiti.web-server.interface]
   [sour.graffiti.culture.main]
   [polylith.clj.core.api.interface :refer [workspace]]))

(watch-deps/start! {:aliases [:dev :test]})

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(add-tap (bound-fn* clojure.pprint/pprint))

(defn dev-prep!
  []
  (integrant.repl/set-prep! (fn []
                              (-> (app-state/system-config {:profile :dev})
                                  (ig/prep)))))

(dev-prep!)

(apply repl/set-refresh-dirs (filter (fn [path]
                                       (and
                                        (not= path "development/src")
                                        (string/includes? path "/src"))) (workspace "stable" "paths" "existing")))

(def refresh repl/refresh)

(comment
  (filter (fn [path]
            (and (not= path "development/src")
                 (string/includes? path "/src"))) (workspace "stable" "paths" "existing"))
  (go)
  (reset))
