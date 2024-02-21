(ns sour.graffiti.culture.controllers.tutorial
  (:require
   [ring.util.http-response :as http-response]
   [sour.graffiti.web-server.interface :refer [route-data]])
  (:import
   [java.util Date]))



(defn healthcheck!
  [request]
  (let [{:keys [query-fn]} (route-data request)]
    (http-response/ok
     {:time     (str (Date. (System/currentTimeMillis)))
      :up-since (str (Date. (.getStartTime (java.lang.management.ManagementFactory/getRuntimeMXBean))))
      :data (query-fn :list-product-info {})
      :app      {:status  "Culture Service and updated"
                 :message ""}})))
