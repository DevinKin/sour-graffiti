(ns sour.graffiti.culture.api
  (:require
   [integrant.core :as ig]
   [reitit.coercion.malli :as malli]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.malli :refer [temp-file-part]]
   [reitit.ring.middleware.multipart :as multipart]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [reitit.swagger :as swagger]
   [clojure.tools.logging :as log]
   [sour.graffiti.culture.controllers.tutorial :as tutorial]
   [sour.graffiti.web-server.interface :refer [format-instance mw-wrap-exception]]))


(defn route-data [opts]
  "api route data"
  (merge
   opts
   {:coercion   malli/coercion
    :muuntaja   format-instance
    :swagger    {:id ::api}
    :middleware [;; query-params & form-params
                 parameters/parameters-middleware
                 ;; content-negotiation
                 muuntaja/format-negotiate-middleware
                 ;; encoding response body
                 muuntaja/format-response-middleware
                 ;; exception handling
                 coercion/coerce-exceptions-middleware
                 ;; decoding request body
                 muuntaja/format-request-middleware
                 ;; coercing response bodys
                 coercion/coerce-response-middleware
                 ;; coercing request parameters
                 coercion/coerce-request-middleware
                 ;; exception handling
                 mw-wrap-exception
                 ;; multipart
                 multipart/multipart-middleware]}))

;; Routes
(defn api-routes [_opts]
  [["/swagger.json"
    {:get {:no-doc  true
           :swagger {:info {:title "Culture Service API"}}
           :handler (swagger/create-swagger-handler)}}]
   [""
    {:middleware []}
    ["/tutorial"
     {:get tutorial/healthcheck!}]]])

(derive :reitit.routes/api :reitit/routes)

(defmethod ig/init-key :reitit.routes/api
  [_ {:keys [base-path]
      :or   {base-path ""}
      :as   opts}]
  [base-path (route-data opts) (api-routes opts)])

