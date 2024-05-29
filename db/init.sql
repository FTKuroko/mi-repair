create database repairsys;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32) NOT NULL COMMENT '用户名',
    password VARCHAR(64) COMMENT '密码',
    phone VARCHAR(11) NOT NULL UNIQUE COMMENT '手机号',
    addr VARCHAR(200) COMMENT '地址'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; -- 使用InnoDB引擎，并设置默认字符集为utf8mb4



CREATE TABLE workers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL COMMENT '工程师姓名',
    password VARCHAR(64) COMMENT '密码',
    phone VARCHAR(11) COMMENT '手机号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE storage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `type` int NOT NULL DEFAULT 3 COMMENT '材料类别：1、手机;2、家电;3、汽车',
    `name` VARCHAR(64) NOT NULL COMMENT '材料名称',
    price decimal(10,2) COMMENT '材料价格',
    amount INT default 0 COMMENT '材料数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE repair_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(32) NOT NULL,
    user_number VARCHAR(11) NOT NULL,
    user_addr VARCHAR(200),
    goods_info VARCHAR(200) NOT NULL COMMENT '商品信息',
    sn VARCHAR(13) NOT NULL,
	`description` VARCHAR(200) NOT NULL COMMENT '故障描述',
    `status` INT NOT NULL DEFAULT 0 COMMENT '0:待(程师)接单;1:待(程师)接单确认;2:维修锁定中(订单无法修改);3:维修完毕',
    create_time DATETIME(6) DEFAULT NULL,
    update_time DATETIME(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE material_req (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    worker_id BIGINT NOT NULL,
    worker_name VARCHAR(64) NOT NULL,
    material_id BIGINT NOT NULL COMMENT '申请的材料，关联到材料库存的外键',
    material_name VARCHAR(64) NOT NULL COMMENT '材料名称',
    material_amount INT NOT NULL COMMENT '材料数量',
    `status` INT NOT NULL DEFAULT 0 COMMENT '0：（仓库）待处理;1：（仓库）处理中;2：（仓库）材料派发完毕;',
    create_time DATETIME(6) DEFAULT NULL,
    update_time DATETIME(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE order_pay (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL, -- 关联到维修订单的外键
    user_id BIGINT NOT NULL, -- 付款方的用户ID
    worker_id BIGINT NOT NULL,
    price decimal(10,2) NOT NULL COMMENT '订单价格', -- 订单价格
    `status` INT NOT NULL DEFAULT 0 COMMENT '0：待支付；1：已支付',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 创建时间，默认为当前时间
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间，默认为当前时间，并在更新时自动更新
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type BIGINT NOT NULL COMMENT '文件类型: 1-图片, 2-视频',
    order_id BIGINT COMMENT '订单ID',
    path VARCHAR(200) NOT NULL COMMENT '文件路径',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT COMMENT '订单ID',
    user_id BIGINT COMMENT '修改人ID',
    `status` INT NOT NULL DEFAULT 0 COMMENT '工单进度状态',
    `type` INT NOT NULL DEFAULT 0 COMMENT '修改人类型，0：普通用户，1：工程师',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;





