(ns user
  "Userspace functions you can run by default in your local REPL."
  (:require
   [clojure.pprint]
   [clojure.spec.alpha :as s]
   [clojure.tools.namespace.repl :as repl]
   ;[criterium.core :as c]                                  ;; benchmarking
   [expound.alpha :as expound]
   [integrant.core :as ig]
   [integrant.repl :refer [clear go halt prep init reset reset-all]]
   [integrant.repl.state :as state]
   [lambdaisland.classpath.watch-deps :as watch-deps]
   [sour.graffiti.app-state.interface :as app-state]))

(watch-deps/start! {:aliases [:dev :test]})

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(add-tap (bound-fn* clojure.pprint/pprint))

(defn dev-prep!
  []
  (integrant.repl/set-prep! (fn []
                              (-> (app-state/system-config {:profile :dev})
                                  (ig/prep)))))

(dev-prep!)

(repl/set-refresh-dirs "components/app-state/src")

(def refresh repl/refresh)

(comment
  (go)
  (reset))
