(ns sour.graffiti.web-server.core
  (:require
   [sour.graffiti.app-state.interface :as app-state]
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

(defn stop
  "stop the web server"
  []
  (log/info "\n-=[culture service has shut down successfully]=-")
  (some-> (deref system) (app-state/halt-system))
  (shutdown-agents))

(defn start
  "start the web server"
  [& [params]]
  (log/info "\n-=[culture service started successfully using the development or test profile]=-")
  (->> (app-state/system-config (or (:opts params) {:profile :dev}))
       (app-state/init-system)
       (reset! system))
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop)))
