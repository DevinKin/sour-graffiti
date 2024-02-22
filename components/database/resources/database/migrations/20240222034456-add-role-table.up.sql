CREATE TABLE public."role" (
	id serial4 NOT NULL, -- 角色id
	"name" varchar(50) NOT NULL, -- 角色名
	CONSTRAINT role_pk PRIMARY KEY (id)
);
--;;
COMMENT ON TABLE public."role" IS '角色表';
--;;
COMMENT ON COLUMN public."role".id IS '角色id';
--;;
COMMENT ON COLUMN public."role"."name" IS '角色名';
