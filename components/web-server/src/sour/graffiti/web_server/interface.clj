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

(defn db-query
  "query db by query-name and params
   :query-name query keyword in resources/*/*.sql
   :params the query parameters"
  [query-name params]
  (when-let [query-fn (web-server/db-query-fn)]
    (query-fn query-name params)))

(def wrap-exception-mw exception/wrap-exception)

(def format-instance formats/instance)

(defn route-data
  [req]
  (get-in req [:reitit.core/match :data]))
