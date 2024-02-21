(ns sour.graffiti.culture.main
  (:require
   [sour.graffiti.web-server.interface :refer [start]]
   ;; Routes
   [sour.graffiti.culture.api])

   
  (:gen-class))

(defn -main [& _]
  (start))
