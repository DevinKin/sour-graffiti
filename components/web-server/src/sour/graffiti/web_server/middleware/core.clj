(ns sour.graffiti.web-server.middleware.core
  (:require
   [ring.middleware.defaults :as defaults]
   [sour.graffiti.env.interface :as env]
   [sour.graffiti.app-state.interface :as app-state]

   [clojure.tools.logging :as log]
   ;; buddy signed jwt
   [buddy.auth.backends :as backends]
   [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
   [buddy.auth :refer [authenticated? throw-unauthorized]]))

(defn wrap-base
  [{:keys [site-defaults-config jws] :as opts}]
  (let [{:keys [secret alg]} jws
        backend (backends/jws {:secret secret :options {:alg alg}})]
    (fn [handler]
      (cond-> ((:middleware env/defaults) handler opts)
        true (wrap-authorization backend)
        true (wrap-authentication backend)
        true (defaults/wrap-defaults site-defaults-config)))))

(defn wrap-user-authorization [handler]
  (fn [request]
    (log/info (:identity request))
    (when (not (authenticated? request))
      (throw-unauthorized {:errors {:authorization "Authorization required."}}))
    (handler request)))

(comment)
