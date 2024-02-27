(ns sour.graffiti.auth.api
  (:require
   [integrant.core :as ig]
   [reitit.coercion.malli :as malli]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.middleware.multipart :as multipart]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [reitit.swagger :as swagger]
   [malli.util :as mu]
   [malli.generator :as mg]
   [sour.graffiti.user.interface.spec :as spec]
   [sour.graffiti.spec.interface :refer [username? email?]]
   [sour.graffiti.auth.handler :as handler]
   [sour.graffiti.web-server.interface :refer [format-instance mw-wrap-exception wrap-user-authorization] :as web-server]))

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
           :swagger {:info {:title "Auth Service API"
                            :description "Swagger doc Auth Service API"
                            :version "v1.0.0"}
                     ;; used in /secure APIs below
                     :securityDefinitions {"auth" {:type :apiKey
                                                   :in :header
                                                   :name "Authorization: Token"}}
                     :tags [{:name "user", :description "user api"}]}
           :handler (swagger/create-swagger-handler)}}]
   ["/user"
    {:tags #{"user"}}
    ["/register"
     {:post {:summary "user register"
             :parameters {:body spec/register}
             :handler handler/regist
             :responses {200 {:body spec/authenticated-user}}}}]
    ["/login"
     {:post {:summary "user login"
             :parameters {:body spec/login}
             :handler handler/login
             :responses {200 {:body spec/authenticated-user}}}}]
    [""
     {:openapi {:security [{"auth" []}]}
      :swagger {:security [{"auth" []}]}
      :middleware [wrap-user-authorization]}
     ["/reset-password"
      {:post {:summary "user reset password"
              :parameters {:body spec/reset-password}
              :handler handler/user-reset-password
              :responses {200 {:body {:message [:string {:default "success"}]}}}}}]]]])

(derive :reitit.routes/api :reitit/routes)

(defmethod ig/init-key :reitit.routes/api
  [_ {:keys [base-path]
      :or   {base-path ""}
      :as   opts}]
  [base-path (route-data opts) (api-routes opts)])

