(ns sour.graffiti.minio.interface
  (:require [sour.graffiti.minio.core :as core]))

(defn get-object
  "Get minio object by params
   :bucket bucket-name
   :object object-path"
  [params]
  (core/get-object params))


(defn list-objects
  "List minio objects"
  [params]
  (core/list-objects params))


(defn put-object
  "Put minio object"
  [params]
  (core/put-object params))
