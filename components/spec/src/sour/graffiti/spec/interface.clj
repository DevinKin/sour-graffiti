(ns sour.graffiti.spec.interface
  (:require [sour.graffiti.spec.core :as core]))

(def username? core/username?)

(def email? core/email?)

(def non-empty-string core/non-empty-string)
