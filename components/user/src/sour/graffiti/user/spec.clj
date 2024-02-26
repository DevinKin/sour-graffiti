(ns sour.graffiti.user.spec
  (:require
   [sour.graffiti.spec.interface :as spec]
   [malli.util :as mu]))

(def ^:private password-prop {:title "密码" :description "密码正则:最少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符"
                              :json-schema/default "Ab123456*"
                              :gen/elements ["Aa23456*" "Li123456&"]})

(def ^:private email-prop {:title "邮箱" :description "用户邮箱"
                           :json-schema/default "lala@163.com"})
(def ^:private user-base
  [:map
   [:name {:title "名称" :description "用户名称"
           :json-schema/default "lala"} spec/username?]
   [:email email-prop spec/email?]
   [:password password-prop spec/password?]])

(def register user-base)

(def login (-> user-base
               (mu/select-keys [:email :password])))

(def authenticated-user [:map
                         [:user (-> user-base
                                    (mu/select-keys [:name :email])
                                    (mu/assoc :token spec/non-empty-string)
                                    (mu/optional-keys [:token]))]])

(def reset-password
  [:map
   [:email email-prop spec/email?]
   [:origin-password password-prop spec/password?]
   [:new-password password-prop spec/password?]])


