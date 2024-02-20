CREATE TABLE IF NOT EXISTS public.sales_invoices (
	invoice_code varchar(50) NOT NULL, -- 流水号
	sell_date timestamp NULL, -- 日期
	"type" int4 NOT NULL, -- 类型（1：销售，2：退货）
	cashier_name varchar(20) NOT NULL, -- 收银员
	quantity int4 NOT NULL, -- 商品数量
	original_price numeric(10, 2) NOT NULL, -- 商品原价
	received_amount numeric(10, 2) NOT NULL, -- 实收金额
	discount_amount numeric(10, 2) NOT NULL, -- 折让金额
	profit numeric(10, 2) NOT NULL, -- 利润
	taobao_amount numeric(10, 2) NOT NULL, -- 淘宝金额
	pinduoduo_amount numeric(10, 2) NOT NULL, -- 拼多多金额
	molotow_tmall_amount numeric(10, 2) NOT NULL, -- molotow天猫金额
	gs_store_amount numeric(10, 2) NOT NULL, -- GS店金额
	wechat_scan_amount numeric(10, 2) NOT NULL, -- WX扫码收款
	CONSTRAINT sales_invoices_pk PRIMARY KEY (invoice_code)
);
--;;
COMMENT ON TABLE public.sales_invoices IS '销售流水单据表';
--;;
COMMENT ON COLUMN public.sales_invoices.invoice_code IS '流水号';
--;;
COMMENT ON COLUMN public.sales_invoices.sell_date IS '日期';
--;;
COMMENT ON COLUMN public.sales_invoices."type" IS '类型（1：销售，2：退货）';
--;;
COMMENT ON COLUMN public.sales_invoices.cashier_name IS '收银员';
--;;
COMMENT ON COLUMN public.sales_invoices.quantity IS '商品数量';
--;;
COMMENT ON COLUMN public.sales_invoices.original_price IS '商品原价';
--;;
COMMENT ON COLUMN public.sales_invoices.received_amount IS '实收金额';
--;;
COMMENT ON COLUMN public.sales_invoices.discount_amount IS '折让金额';
--;;
COMMENT ON COLUMN public.sales_invoices.profit IS '利润';
--;;
COMMENT ON COLUMN public.sales_invoices.taobao_amount IS '淘宝金额';
--;;
COMMENT ON COLUMN public.sales_invoices.pinduoduo_amount IS '拼多多金额';
--;;
COMMENT ON COLUMN public.sales_invoices.molotow_tmall_amount IS 'molotow天猫金额';
--;;
COMMENT ON COLUMN public.sales_invoices.gs_store_amount IS 'GS店金额';
--;;
COMMENT ON COLUMN public.sales_invoices.wechat_scan_amount IS 'WX扫码收款';
