(ns sour.graffiti.web-server.core
  (:require
   [sour.graffiti.app-state.interface :as app-state]
   [sour.graffiti.env.interface :refer [defaults]]
   [clojure.tools.logging :as log]

   ;; Handler
   [sour.graffiti.web-server.handler]

   ;; Edges
   [kit.edge.http.hato]
   [kit.edge.utils.nrepl]
   [kit.edge.server.undertow]))

(defonce system (atom nil))

;; log uncaught exceptions in threads
(Thread/setDefaultUncaughtExceptionHandler
 (reify Thread$UncaughtExceptionHandler
   (uncaughtException [_ thread ex]
     (log/error {:what :uncaught-exception
                 :exception ex
                 :where (str "Uncaught exception on" (.getName thread))}))))

(defn db-query-fn
  []
  (some-> (deref system) :db.sql/query-fn))

(defn stop
  "stop the web server"
  []
  ((or (:stop defaults) (fn [])))
  (some-> (deref system) (app-state/halt-system))
  (shutdown-agents))

(defn start
  "start the web server"
  [& [params]]
  ((or (:start params) (:start defaults) (fn [])))
  (->> (app-state/system-config (or (:opts params) (:opts defaults) {}))
       (app-state/init-system)
       (reset! system))
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop)))
