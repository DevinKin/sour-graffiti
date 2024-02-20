(ns sour.graffiti.app-state.interface
  (:require [kit.config :as config]))

(def ^:const system-filename "app-state/system.edn")

(defn system-config
  "Return your application state, fully configured"
  [options]
  (config/read-config system-filename options))
