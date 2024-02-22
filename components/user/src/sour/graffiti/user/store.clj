(ns sour.graffiti.user.store
  (:require [sour.graffiti.database.interface :refer [db-execute]]))

(defn get-user-by-id
  [id]
  (db-execute :get-user-by-id {:id id}))

(defn add-user!
  [{:keys [name email password]}]
  (db-execute :add-user! {:name name
                          :email email
                          :password password}))

(defn update-user!
  [{:keys [name password active]}]
  (db-execute :update-user! (cond-> {:name name}
                              (not (nil? password)) (assoc :password password)
                              (not (nil? active)) (assoc :active active))))
