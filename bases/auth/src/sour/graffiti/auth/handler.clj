(ns sour.graffiti.auth.handler
  (:require
   [clojure.tools.logging :as log]
   [ring.util.http-response :as http-response]
   [sour.graffiti.user.interface :as user]))

(defn regist
  [{{{:keys [name email password]} :body} :parameters :as request}]
  (let [user {:name name :email email :password password}
        [ok? res] (user/regist! user)]
    (if ok?
      (http-response/ok res)
      (http-response/bad-request res))))

(defn login
  [{{{:keys [email password]} :body} :parameters :as request}]
  (let [user {:email email :password password}
        [ok? res] (user/login! user)]
    (if ok?
      (http-response/ok res)
      (http-response/bad-request res))))

(defn user-reset-password
  [{{{:keys [email origin-password new-password]} :body} :parameters :as request}]
  (let [user {:email email :origin-password origin-password :new-password new-password}
        [ok? res] (user/update-user-password! user)]
    (if ok?
      (http-response/ok res)
      (http-response/bad-request res))))

