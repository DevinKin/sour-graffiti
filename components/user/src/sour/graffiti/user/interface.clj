(ns sour.graffiti.user.interface
  (:require [sour.graffiti.user.core :as core]))

(defn regist!
  [user]
  (core/regist! user))

(defn login
  [user]
  (core/login user))

(defn active-user!
  [user]
  (core/active-user! user))

(defn update-user-password!
  [user]
  (core/update-user-password! user))
