(ns sour.graffiti.app-state.core
  (:require
   [integrant.core :as ig]
   [integrant.repl.state :as state]
   [kit.config :as config]))

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
