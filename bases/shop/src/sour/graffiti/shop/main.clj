(ns sour.graffiti.shop.main
  (:require
   [sour.graffiti.web-server.interface :refer [start]]
   ;; Routes
   [sour.graffiti.shop.api])

  (:gen-class))

(defn -main [& _]
  (start))
