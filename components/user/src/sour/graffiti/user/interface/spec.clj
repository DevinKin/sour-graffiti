(ns sour.graffiti.user.interface.spec
  (:require
   [sour.graffiti.user.spec :as spec]))

(def register spec/register)

(def login spec/login)

(def authenticated-user spec/authenticated-user)

(def reset-password spec/reset-password)
