(ns sour.graffiti.user.spec
  (:require
   [sour.graffiti.spec.interface :as spec]
   [malli.util :as mu]
   [malli.generator :as mg]))

(def ^:private user-base
  [:map
   [:name spec/username?]
   [:email spec/email?]
   [:password {:gen/elements ["Aa23456*" "Li123456&"]
               :description "密码正则:最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符"} [:re #"^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$"]]])

(def register user-base)

(def login (-> user-base
               (mu/select-keys [:email :password])))

(def authenticated-user [:map
                         [:user (-> user-base
                                    (mu/select-keys [:username :email])
                                    (mu/assoc :token spec/non-empty-string)
                                    (mu/optional-keys [:token]))]])

(comment
  {:title "名称" :description "用户名称"
   :json-schema/default "lala"}
  {:title "邮箱" :description "用户邮箱"
   :json-schema/default "lala@email.com"}
  {:title "密码" :description "用户密码"
   :json-schema/default "Ab123456*"}
  (mg/generate user-base)
  (mu/update-properties [:map
                         [:name [:re #"^[a-zA-Z0-9_-]{4,16}$"]]
                         [:password {:title "密码" :description "用户密码"
                                     :json-schema/default "Ab123456*"} [:re #"^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$"]]]
                        assoc [:name] {:title "啦啦"
                                       :description "啦啦ming"}))

