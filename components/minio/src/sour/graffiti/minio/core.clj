(ns sour.graffiti.minio.core
  (:require
   [sour.graffiti.app-state.interface :as app-state]
   [clojure.java.io :as io]
   [clojure.string :as str])
  (:import [io.minio GetObjectArgs ListObjectsArgs PutObjectArgs RemoveObjectsArgs]
           [java.util LinkedList]
           [java.io InputStream]
           [io.minio.messages Item DeleteObject]
           [io.minio.errors ErrorResponseException]))

(defn- minio-client
  "get minio client from system state"
  []
  (:minio/client (app-state/system)))

(defn- objectItem->map
  "Helper function for datatye conversion."
  [^Item item]
  {:etag (.etag item)
   :last-modified (.lastModified item)
   :obj-name (.objectName item)
   :owner (.owner item)
   :size (.size item)
   :storage-class (.storageClass item)
   :user-metadata (.userMetadata item)
   :version-id (.versionId item)})

(defn- result->map
  [result]
  (-> result
      (.get)
      (objectItem->map)))

(defn get-object
  [{:keys [bucket object]}]
  (let [get-obj-args (.build
                      (doto (GetObjectArgs/builder)
                        (.bucket bucket)
                        (.object object)))]
    (-> (minio-client)
        (.getObject get-obj-args))))

(defn list-objects
  [{:keys [bucket recursive? prefix] :or {recursive? false}}]
  (let [list-obj-args (.build
                       (doto (ListObjectsArgs/builder)
                         (.bucket bucket)
                         (.recursive recursive?)
                         (when-not (str/blank? prefix)
                           (.prefix prefix))))]
    (-> (minio-client)
        (.listObjects list-obj-args))))

(defn put-object
  [{:keys [bucket obj-name size content-type ^InputStream stream]}]
  (let [put-obj-args (.build
                      (doto (PutObjectArgs/builder)
                        (.bucket bucket)
                        (.object obj-name)
                        (.stream stream size -1)
                        (when-not (str/blank? content-type)
                          (.contentType content-type))))]
    (-> (minio-client)
        (.putObject put-obj-args))))

(defn remove-objects!
  [{:keys [bucket obj-names]}]
  (let [del-objs (new LinkedList)
        _ (doseq [obj-name obj-names]
            (.add del-objs (new DeleteObject obj-name)))
        rm-obj-args (.build
                     (doto (RemoveObjectsArgs/builder)
                       (.bucket bucket)
                       (.objects del-objs)))]
    (-> (minio-client)
        (.removeObjects rm-obj-args))))

(comment
  (remove-objects! {:bucket "sour" :obj-names ["banner/guagua1.jpg"
                                               "banner/guagua2.jpg"
                                               "banner/guagua3.jpg"]})
  (put-object {:bucket "sour" :obj-name "banner/guagua3.jpg"
               :size (.length (io/file "guagua1.jpg"))
               :stream (io/input-stream "guagua1.jpg")})
  (map #(result->map %) (list-objects {:bucket "sour" :recursive? true}))
  (io/copy (get-object {:bucket "sour" :object "banner/guagua1.jpg"}) (io/file "guagua1.jpg"))
  (get-object {:bucket "sour" :object "banner/guagua1.jpg"}))
