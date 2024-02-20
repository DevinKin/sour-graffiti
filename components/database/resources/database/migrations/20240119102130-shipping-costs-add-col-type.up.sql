ALTER TABLE public.express_shipping_costs ADD "type" int NULL;
--;;
COMMENT ON COLUMN public.express_shipping_costs."type" IS '运单类型(1:超1公斤,2:超3公斤)';
