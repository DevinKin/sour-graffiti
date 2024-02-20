(ns sour.graffiti.app-state.interface
  (:require [kit.config :as config]
            [integrant.core :as ig]))

(def ^:const system-filename "app-state/system.edn")

(defn system-config
  "Return your application system, fully configured"
  [options]
  (config/read-config system-filename options))

(defn init-system
  "Return your application system by system config"
  [config]
  (->> config
       (ig/prep)
       (ig/init)))

(defn halt-system
  "Halt provided system"
  [system]
  (ig/halt! system))

(defn resume-system
  "resume provided system"
  [system config]
  (ig/resume system config))
