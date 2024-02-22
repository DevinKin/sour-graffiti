-- :name get-user-by-id :? :1
-- :doc returns a user object by id, or nil if not present
SELECT *
FROM "user" u
WHERE id = :id

-- :name add-user! :! :n
-- :doc add user
insert into "user"(name, email, password)
values :t*:user

-- :name update-user-password! :! :n
-- :doc update user password
update "user"
SET password = :password
WHERE name = :name

-- :name update-user-active! :! :n
-- :doc update user active status
update "user"
SET active = :active
WHERE name = :name
