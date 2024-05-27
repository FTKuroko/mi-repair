-- 2024.5.13
alter table repair_order change `desc` description varchar(200) not null comment '故障描述';
-- 2024.5.14
alter table storage modify type int default 3 not null comment '材料类别：1、车窗 2. 镜子  3. 轮胎';
-- 2024.5.24
alter table repair_order
	add worker_id bigint null comment '工程师id';
-- 2024.5.27
alter table material_req
	add price_sum decimal(10,2) null comment '花费';
