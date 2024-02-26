(ns sour.graffiti.auth.handler
  (:require
   [clojure.tools.logging :as log]
   [ring.util.http-response :as http-response]
   [sour.graffiti.user.interface :as user]))

(defn regist
  [{{{:keys [name email password]} :body} :parameters :as request}]
  (let [session (:session request)
        user {:name name :email email :password password}
        [ok? res] (user/regist! user)]
    (if ok?
      (-> res
          (http-response/ok)
          (assoc :session (assoc session :name (:name user))))
      (-> res
          (http-response/bad-request)))))

(defn login
  [request]
  (http-response/ok {:message "login success"}))

(defn user-active
  [request])

(defn user-reset-password
  [request]
  (http-response/ok {:message "reset-password"}))
