(ns sour.graffiti.database.core
  (:require [clojure.tools.logging :as log]
            [clojure.string :as str]
            [hugsql.adapter :refer [execute query]]
            [hugsql.core :refer [hugsql-command-fn]]))

(defn log-sqlvec [sqlvec]
  (let [sql (->> sqlvec
                 (map #(str/replace (or % "") #"\n" " "))
                 (str/join " ; "))]
    (when (< (count sql) 200)
      (log/info sql))))

(defn log-command-fn [this db sqlvec options]
  (log-sqlvec sqlvec)
  (condp contains? (:command options)
    #{:!} (execute this db sqlvec options)
    #{:? :<!} (query this db sqlvec options)))

(defmethod hugsql-command-fn :! [_sym] `log-command-fn)
(defmethod hugsql-command-fn :<! [_sym] `log-command-fn)
(defmethod hugsql-command-fn :? [_sym] `log-command-fn)

