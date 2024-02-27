(ns sour.graffiti.env.interface
  (:require
   [clojure.tools.logging :as log]))

(def profile (or (System/getenv "PROFILE") "dev"))

(defn startup-log [profile]
  (log/info "\n-=[service started successfully using the development " profile "]=-"))

(defn shutdown-log []
  (log/info "\n-=[service started successfully using the prod profile]=-"))

(defn wrap-dev [handler _opts]
  (-> handler))

(def defaults
  (case profile
    "test" {:start     (partial startup-log "test")
            :stop      shutdown-log
            :opts {:profile :test}}
    "prod" {:start      (partial startup-log "prod")
            :stop       shutdown-log
            :middleware (fn [handler _] handler)
            :opts {:profile :prod}}
    "dev" {:start      (partial startup-log "dev")
           :stop       shutdown-log
           :middleware wrap-dev
           :opts {:profile :dev}}))
