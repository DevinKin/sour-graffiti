(ns sour.graffiti.web-server.interface
  (:require

   [sour.graffiti.web-server.core :as web-server]
   [sour.graffiti.web-server.middleware.exception :as exception]
   [sour.graffiti.web-server.middleware.formats :as formats]))

(defn stop
  "stop the web server"
  []
  (web-server/stop))

(defn start
  "stop the web server"
  [& [params]]
  (web-server/start params))

(def wrap-exception-mw exception/wrap-exception)

(def format-instance formats/instance)
