-- :name get-user-by-id :? :1
-- :doc returns a user object by id, or nil if not present
SELECT *
FROM "user" u
WHERE id = :id

-- :name add-user! :! :n
-- :doc add user
insert into "user"(name, email, password)
VALUES (:name, :email, :password)

-- :name update-user! :! :n
-- :doc update user password, active
update "user" SET
/*~
(->> [(when (contains? params :password) "password = :password")
      (when (contains? params :active) "active = :active")]
  (remove nil?)
  (clojure.string/join ", "))
~*/
WHERE name = :name

-- :name find-user :? :1
-- :doc returns a user object by name or email, or nil if not present
SELECT *
FROM "user" u
WHERE 1=1
--~ (if (:name params) " and name = :name")
--~ (if (:email params) " and email = :email")
