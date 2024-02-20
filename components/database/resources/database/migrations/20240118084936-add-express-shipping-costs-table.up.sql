CREATE TABLE public.express_shipping_costs (
	code varchar(50) NOT NULL, -- 运单编号
	"date" date NOT NULL, -- 时间日期
	courier_hub varchar(20) NOT NULL, -- 网点
	courier_hub_addr varchar(500) NOT NULL, -- 目的地网点名称
	weight numeric(10, 4) NOT NULL, -- 重量
	price numeric(10, 4) NOT NULL, -- 价格
	CONSTRAINT express_shipping_costs_pk PRIMARY KEY (code)
);
--;;
COMMENT ON COLUMN public.express_shipping_costs.code IS '运单编号';
--;;
COMMENT ON COLUMN public.express_shipping_costs."date" IS '时间日期';
--;;
COMMENT ON COLUMN public.express_shipping_costs.courier_hub IS '网点';
--;;
COMMENT ON COLUMN public.express_shipping_costs.courier_hub_addr IS '目的地网点名称';
--;;
COMMENT ON COLUMN public.express_shipping_costs.weight IS '重量';
--;;
COMMENT ON COLUMN public.express_shipping_costs.price IS '价格';
