CREATE TABLE public."user" (
	id serial4 NOT NULL, -- 自增键
	"name" varchar(50) NOT NULL, -- 用户名
	email varchar(100) NOT NULL, -- 用户邮箱
	active bool NOT NULL, -- 激活状态（0：未激活，1：已激活）
	"password" varchar(100) NOT NULL, -- 密码
	CONSTRAINT user_pk PRIMARY KEY (id)
);
--;;
COMMENT ON TABLE public."user" IS '用户表';
--;;
COMMENT ON COLUMN public."user".id IS '自增键';
--;;
COMMENT ON COLUMN public."user"."name" IS '用户名';
--;;
COMMENT ON COLUMN public."user".email IS '用户邮箱';
--;;
COMMENT ON COLUMN public."user".active IS '激活状态（0：未激活，1：已激活）';
--;;
COMMENT ON COLUMN public."user"."password" IS '密码';
