(ns sour.graffiti.shop.main
  (:require
   [sour.graffiti.web-server.interface :refer [start]]
   ;; init kit multi-method
   [sour.graffiti.app-state.interface :as app-state]
   [sour.graffiti.database.interface]
   [sour.graffiti.web-server.interface]
   [sour.graffiti.env.interface :refer [defaults]]
   ;; Routes
   [sour.graffiti.shop.api])

  (:gen-class))

(defn -main [& _]
  (start defaults))
