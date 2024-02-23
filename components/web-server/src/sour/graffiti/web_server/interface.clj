(ns sour.graffiti.web-server.interface
  (:require
   [sour.graffiti.web-server.core :as web-server]
   [sour.graffiti.web-server.middleware.exception :as exception]
   [sour.graffiti.web-server.middleware.core :as core]
   [sour.graffiti.web-server.middleware.formats :as formats]))

(defn stop
  "stop the web server"
  []
  (web-server/stop))

(defn start
  "stop the web server"
  [& [params]]
  (web-server/start params))


(def mw-wrap-exception exception/wrap-exception)

(def mw-wrap-authorization core/wrap-authorization)

(def format-instance formats/instance)

(defn route-data
  [req]
  (get-in req [:reitit.core/match :data]))
