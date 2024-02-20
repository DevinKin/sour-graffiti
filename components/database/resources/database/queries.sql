-- Place your queries here. Docs available https://www.hugsql.org/

-- :name list-shipping-costs :? :*
-- :doc selects all available shipping costs
SELECT code, TO_CHAR(date, 'YYYY-MM-DD') AS date, courier_hub, courier_hub_addr, weight, price, type
FROM express_shipping_costs
WHERE 1=1
/*~
(clojure.string/join " "
 [(when (seq (:exclude-codes params)) " and code not in (:v*:exclude-codes)")
 (when (:code params) "and code=:code")
  (when (:type params) "and type=:type")
  (when (and (:start-date params) (:end-date params)) "and date between :start-date and :end-date")
  "LIMIT :page-size OFFSET :page"])
~*/


-- :name count-shipping-costs :? :1
-- :doc get shipping costs count
SELECT COUNT(1) AS cnt
FROM express_shipping_costs
/*~
(clojure.string/join " "
 ["WHERE 1=1"
  (when (seq (:exclude-codes params)) " and code not in (:v*:exclude-codes)")
  (when (:code params) "and code=:code")
  (when (:type params) "and type=:type")
  (when (and (:start-date params) (:end-date params)) "and date between :start-date and :end-date")
  ])
~*/


-- :name get-shipping-cost-by-code :? :1
-- :doc returns a shipping costs by code, or nil if not present
SELECT *
FROM express_shipping_costs
WHERE code = :code

-- :name insert-shipping-costs :! :n
-- :doc Insert multiple shipping costs with :tuple parameter
insert into express_shipping_costs (code, date, courier_hub, courier_hub_addr, weight, price, type)
values :t*:shipping-costs


-- :name update-shipping-cost :! :n
-- :doc Update shipping costs with :tuple parameter
update express_shipping_costs
set date=:date, courier_hub=:courier-hub,courier_hub_addr=:target-hub-addr,weight=:weight,price=:price,type=:type
where code=:code

-- :name list-shipping-costs-code :? :*
-- :doc selects all available shipping costs code
SELECT code FROM express_shipping_costs


-- :name insert-sell-invoices :! :n
-- :doc Insert multiple sell invoices with :tuple parameter
insert into sales_invoices (invoice_code, sell_date, type, cashier_name, quantity, original_price, received_amount, discount_amount,
                            profit, taobao_amount, pinduoduo_amount, molotow_tmall_amount, gs_store_amount, wechat_scan_amount)
values :t*:sell-invoices

-- :name update-shipping-cost :! :n
-- :doc Update sell invoices with :tuple parameter
UPDATE sales_invoices
SET date=:date, type=:type, cashier_name=:cashier_name, quantity=:quantity, original_price=:original_price,
received_amount=:received_amount, discount_amount=:discount_amount, profit=:profit, taobao_amount=:taobao_amount,
pinduoduo_amount=:pinduoduo_amount, molotow_tmall_amount=:molotow_tmall_amount, gs_store_amount=:gs_store_amount,
wechat_scan_amount=:wechat_scan_amount
WHERE invoice_code=:code


-- :name list-sales-invoices-code :? :*
-- :doc selects all available shipping costs code
SELECT invoice_code FROM sales_invoices

-- :name list-sales-invoices :? :*
-- :doc selects all available sales invoices
SELECT * FROM sales_invoices


-- :name list-product-info :? :*
-- :doc selects all available product info
SELECT name, classification FROM product_info

-- :name insert-sales-invoices-detail :! :n
-- :doc Insert multiple sell invoices with :tuple parameter
INSERT INTO sales_invoices_detail
(code, invoice_code, time, type, cashier_name, product_info, product_barcode, quantity, unit_price, original_price,
 received_amount, discount_amount, profit)
VALUES :t*:sales-invoices-detail

-- :name delete-sales-invoices-detail-by-invoice-code :! :n
-- :doc Update sell invoices with :tuple parameter
DELETE FROM sales_invoices_detail
WHERE invoice_code in (:v*:codes);

-- :name list-sales-invoices-detail :? :*
-- :doc selects all available sales invoices detail by condition
SELECT sid.*, si.taobao_amount, si.molotow_tmall_amount, si.gs_store_amount
FROM sales_invoices_detail sid INNER JOIN sales_invoices si ON sid.invoice_code=si.invoice_code
/*~
  (clojure.string/join " "
    ["WHERE 1=1"
    (when (:product-info params) "and LOWER(sid.product_info) LIKE :product-info")
    (when (:type params) "and sid.type=:type")
    (when (:month params) "and TO_CHAR(sid.time, 'YYYY-MM')=:month")
  ])
~*/
