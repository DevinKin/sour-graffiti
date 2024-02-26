(ns user
  "Userspace functions you can run by default in your local REPL."
  (:require
   [clojure.tools.logging :as log]
   [clojure.pprint]
   [clojure.string :as string]
   [clojure.spec.alpha :as s]
   [clojure.tools.namespace.repl :as repl]
   ;[criterium.core :as c]                                  ;; benchmarking
   [expound.alpha :as expound]
   [integrant.core :as ig]
   [integrant.repl :refer [go reset reset-all clear halt prep init] :as ig-repl]
   [integrant.repl.state :as state]
   [lambdaisland.classpath.watch-deps :as watch-deps]
   ;[sour.graffiti.culture.main]
   ;[sour.graffiti.shop.main]
   [sour.graffiti.app-state.interface :as app-state]
   [sour.graffiti.database.interface :as database]
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

(defn set-refresh-project
  "set-refresh-dirs base on project and its config"
  [project-name]
  (let [project (workspace "stable" "projects" project-name)
        base-name (-> project (get-in [:base-names :src]) first)
        require-path (symbol (str "sour.graffiti." base-name ".main"))
        source-paths (-> project (get-in [:paths :src]))]
    (require require-path)
    (log/info "refresh-source-paths: " source-paths)
    (apply repl/set-refresh-dirs (filter #(string/includes? % "/src") source-paths))))

(set-refresh-project "g-auth")

(def refresh repl/refresh)

(defn db-execute
  [sql-name params]
  ((:db.sql/query-fn state/system) sql-name params))

(comment
  (go)
  (reset))
