package com.mi_repair.enums;


/**
 * @author 李晴
 * @description 状态机事件枚举类
 */
public enum RepairOrderEvent {
    USER_CANCEL_ORDER("用户取消订单")

    ,WORKER_ACCEPT_ORDER("工程师接收")

    ,USER_CONFIRM_ORDER("用户确认")

    ,WORKER_INSPECTION("工程师检测")

    ,APPLICATION_MATERIALS_SUCCESS("申请材料成功")

    ,APPLICATION_MATERIALS_FAILED("申请材料失败")

    ,REPAIR_SUCCESS("维修成功")

    ,REPAIR_FAILED("维修失败")

    ,RETEST_SUCCESS ("复检成功")

    ,RETEST_FAILED ("复检失败")

    ,USER_PAY("用户支付")

    ,DEVICE_RETURN("设备归还");

    String description;

    RepairOrderEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
