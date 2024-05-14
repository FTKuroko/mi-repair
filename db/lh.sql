-- 2024.5.13
alter table repair_order change `desc` description varchar(200) not null comment '故障描述';
-- 2024.5.14
alter table storage modify type int default 3 not null comment '材料类别：1、车窗 2. 镜子  3. 轮胎';