-- :name get-user-by-id :? :1
-- :doc returns a user object by id, or nil if not present
SELECT *
FROM "user" u
WHERE id = :id
