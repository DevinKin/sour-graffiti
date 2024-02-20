(ns sour.graffiti.database.interface
  (:require
   [sour.graffiti.database.core]

   ;; Edge
   [kit.edge.db.sql.migratus]
   [kit.edge.db.postgres]
   [kit.edge.db.sql.conman]
   [migratus.core :as migratus]))


(defn create [system name]
  (migratus/create (:db.sql/migrations system) name))

(defn migrate [system]
  (migratus/migrate (:db.sql/migrations system)))

(defn rollback [system]
  (migratus/rollback (:db.sql/migrations system)))

(defn rollback-until-just-after [system migration-id]
  (migratus/rollback-until-just-after (:db.sql/migrations system) migration-id))

(defn up [system & ids]
  (migratus/up (:db.sql/migrations system) ids))

(defn down [system & ids]
  (migratus/down (:db.sql/migrations system) ids))

(defn reset [system]
  (migratus/reset (:db.sql/migrations system)))

(defn pending-list [system]
  (migratus/pending-list (:db.sql/migrations system)))

(defn migrate-until-just-before [system migration-id]
  (migratus/migrate-until-just-before (:db.sql/migrations system) migration-id))
