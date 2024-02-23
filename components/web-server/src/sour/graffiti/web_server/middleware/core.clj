(ns sour.graffiti.web-server.middleware.core
  (:require
   [ring.middleware.defaults :as defaults]
   [sour.graffiti.env.interface :as env]
   [ring.middleware.session.cookie :as cookie]))


(defn wrap-base
  [{:keys [metrics site-defaults-config cookie-secret] :as opts}]
  (let [cookie-store (cookie/cookie-store {:key (.getBytes ^String cookie-secret)})]
    (fn [handler]
      (cond-> ((:middleware env/defaults) handler opts)
        true (defaults/wrap-defaults
              (assoc-in site-defaults-config [:session :store] cookie-store))))))

(defn wrap-authorization [handler]
  (fn [req]
    (if-let [username (get-in req [:session :username])]
      (handler req)
      {:status 401
       :body {:erros {:authorization "Authorization required."}}})))
