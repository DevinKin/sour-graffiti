(ns sour.graffiti.spec.core)

(def username? [:re #"^[a-zA-Z0-9_-]{4,16}$"])

(def email? [:re #"^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$"])

(def non-empty-string [:string {:min 1}])
