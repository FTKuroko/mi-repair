-- 2024.5.13
alter table repair_order change `desc` description varchar(200) not null comment '故障描述';