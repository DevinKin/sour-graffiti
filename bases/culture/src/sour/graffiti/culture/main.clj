(ns sour.graffiti.culture.main
  (:require
   [sour.graffiti.web-server.interface :refer [start]]
   ;; init kit multi-method
   [sour.graffiti.app-state.interface :as app-state]
   [sour.graffiti.database.interface]
   [sour.graffiti.web-server.interface]
   [sour.graffiti.env.interface :refer [defaults]]

   ;; Routes
   [sour.graffiti.culture.api])
  (:gen-class))

(defn -main [& _]
  (start defaults))
