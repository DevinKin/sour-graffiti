(ns sour.graffiti.database.core
  (:require
   ;; Edge
   [kit.edge.db.sql.migratus]
   [kit.edge.db.postgres]
   [kit.edge.db.sql.conman]
   [migratus.core :as migratus]
   [sour.graffiti.app-state.interface :as app-state]))

(defn migrate []
  (migratus/migrate {} #_(:db.sql/migrations state/system)))
