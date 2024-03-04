(ns sour.graffiti.app-state.core
  (:require
   [integrant.core :as ig]
   [integrant.repl.state :as state]
   [kit.config :as config]
   [clojure.tools.logging :as log])
  (:import [io.minio MinioClient]))

(def ^:const system-filename "app-state/system.edn")

(defn system-config
  [options]
  (config/read-config system-filename options))

(defonce system (atom nil))

(defn setup-system!
  [cus-system]
  (reset! system cus-system))

(defn get-system
  []
  (or @system state/system))

(defmethod ig/init-key :jwt/signed
  [_ jws]
  jws)

(defmethod ig/init-key :minio/client
  [_ {:keys [enable? endpoint access-key secret-key]}]
  (if enable?
    (-> (MinioClient/builder)
        (doto
         (.endpoint endpoint)
          (.credentials access-key secret-key))
        (.build))))
