(ns sour.graffiti.shop.main
  (:require
   [sour.graffiti.web-server.interface :refer [start]]
   [sour.graffit.env.interface :refer]
   ;; Routes
   [sour.graffiti.shop.api])

  (:gen-class))

(defn -main [& _]
  (start))
