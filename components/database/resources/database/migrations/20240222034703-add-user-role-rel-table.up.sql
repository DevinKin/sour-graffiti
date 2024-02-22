CREATE TABLE public.user_role_rel (
	user_id int4 NOT NULL, -- 用户id
	role_id int4 NOT NULL -- 角色id
);
--;;
COMMENT ON TABLE public.user_role_rel IS '用户角色关系表';
--;;
COMMENT ON COLUMN public.user_role_rel.user_id IS '用户id';
--;;
COMMENT ON COLUMN public.user_role_rel.role_id IS '角色id';
--;;
ALTER TABLE public.user_role_rel ADD CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES public."role"(id);
--;;
ALTER TABLE public.user_role_rel ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public."user"(id);
