(ns sour.graffiti.auth.spec)

(def register
  [:map
   [:name {:title "名称" :description "用户名称"
           :json-schema/default "lala"} [:re #"^[a-zA-Z0-9_-]{4,16}$"]]
   [:email {:title "邮箱" :description "用户邮箱"
            :json-schema/default "lala@email.com"} [:re #"^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$"]]
   [:password {:title "密码" :description "用户密码"
               :json-schema/default "Ab123456*"} [:re #"^.*(?=.{6,})(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$"]]])
