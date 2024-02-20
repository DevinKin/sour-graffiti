CREATE TABLE IF NOT EXISTS public.product_info (
	code varchar(50) NOT NULL, -- 唯一标识
	"name" varchar(255) NOT NULL, -- 产品名称
	classification varchar(50) NOT NULL, -- 分类
	CONSTRAINT product_info_pk PRIMARY KEY (code),
	CONSTRAINT product_info_un UNIQUE (code)
);
--;;
COMMENT ON TABLE public.product_info IS '产品信息表';
--;;
COMMENT ON COLUMN public.product_info.code IS '唯一标识';
--;;
COMMENT ON COLUMN public.product_info."name" IS '产品名称';
--;;
COMMENT ON COLUMN public.product_info.classification IS '分类';
