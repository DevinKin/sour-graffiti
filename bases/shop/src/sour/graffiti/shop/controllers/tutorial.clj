(ns sour.graffiti.shop.controllers.tutorial
  (:require
   [ring.util.http-response :as http-response])
  (:import
   [java.util Date]))



(defn healthcheck!
  [req]
  (http-response/ok
   {:time     (str (Date. (System/currentTimeMillis)))
    :up-since (str (Date. (.getStartTime (java.lang.management.ManagementFactory/getRuntimeMXBean))))
    :app      {:status  "Shop Service up and updated"
               :message ""}}))
