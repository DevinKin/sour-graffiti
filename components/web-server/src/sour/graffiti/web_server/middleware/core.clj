(ns sour.graffiti.web-server.middleware.core
  (:require
   [ring.middleware.defaults :as defaults]
   [ring.middleware.session.cookie :as cookie]))

(defn default-handler [handler _opts]
  (-> handler))

(defn wrap-base
  [{:keys [metrics site-defaults-config cookie-secret] :as opts}]
  (let [cookie-store (cookie/cookie-store {:key (.getBytes ^String cookie-secret)})]
    (fn [handler]
      (defaults/wrap-defaults
       (default-handler handler opts)
       (assoc-in site-defaults-config [:session :store] cookie-store)))))
