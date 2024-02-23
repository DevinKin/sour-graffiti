(ns sour.graffiti.culture.controllers.tutorial
  (:require
   [ring.util.http-response :as http-response]
   [sour.graffiti.web-server.interface :refer [route-data]])
  (:import
   [java.util Date]))


(defn login
  [request]
  (let [{:keys [query-fn]} (route-data request)
        session (:session request)]
    (-> (http-response/ok
         {:message "Login success"})
        (assoc :session (assoc session :username "devin")))))

(defn healthcheck!
  [request]
  (let [{:keys [query-fn]} (route-data request)
        session (:session request)]
    (-> (http-response/ok
         {:time     (str (Date. (System/currentTimeMillis)))
          :up-since (str (Date. (.getStartTime (java.lang.management.ManagementFactory/getRuntimeMXBean))))
          :user (:user session)
          :password (:password session)
          :app      {:status  "Culture Service and updated"
                     :message ""}})
        (assoc :session (assoc session :user "devin" :password "lala")))))
