CREATE TABLE IF NOT EXISTS public.sales_invoices_detail (
	code varchar(50) NOT NULL, -- 唯一编号
	invoice_code varchar(50) NOT NULL, -- 流水号
	"time" timestamp NULL, -- 时间
	"type" int4 NOT NULL, -- 类型（1：销售，2：退货）
	cashier_name varchar(20) NOT NULL, -- 收银员
	product_info varchar(500) NOT NULL, -- 商品信息
	product_barcode varchar(50) NOT NULL, -- 商品条码
	quantity int4 NOT NULL, -- 商品数量
	unit_price numeric(10, 2) NOT NULL, -- 商品单价
	original_price numeric(10, 2) NOT NULL, -- 商品原价
	received_amount numeric(10, 2) NOT NULL, -- 实收金额
	discount_amount numeric(10, 2) NOT NULL, -- 折让金额
	profit numeric(10, 2) NOT NULL, -- 利润
	CONSTRAINT sales_invoices_detail_pk PRIMARY KEY (code)
);
--;;
COMMENT ON TABLE public.sales_invoices_detail IS '销售流水单据明细表';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.invoice_code IS '唯一编号';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.invoice_code IS '流水号';
--;;
COMMENT ON COLUMN public.sales_invoices_detail."time" IS '时间';
--;;
COMMENT ON COLUMN public.sales_invoices_detail."type" IS '类型（1：销售，2：退货）';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.cashier_name IS '收银员';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.product_info IS '商品信息';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.product_barcode IS '商品条码';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.quantity IS '商品数量';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.unit_price IS '商品单价';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.original_price IS '商品原价';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.received_amount IS '实收金额';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.discount_amount IS '折让金额';
--;;
COMMENT ON COLUMN public.sales_invoices_detail.profit IS '利润';
