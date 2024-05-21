package com.mi.repair.enums;

public enum RepairOrderEvent {
    USER_CANCEL_ORDER("用户取消订单")

    ,WORKER_ACCEPT_ORDER("工程师接收")

    ,USER_CONFIRM_ORDER("用户确认")

    ,WORKER_INSPECTION_SUCCESS("工程师检测成功")

    ,WORKER_INSPECTION_FAILED("工程师检测失败")

    ,APPLICATION_MATERIALS_SUCCESS("申请材料成功")

    ,APPLICATION_MATERIALS_FAILED("申请材料失败")

    ,REPAIR_SUCCESS("维修成功")

    ,REPAIR_FAILED("维修失败")

    ,RETEST_SUCCESS ("复检成功")

    ,RETEST_FAILED ("复检失败")

    ,RETEST_NUMBER_OF_EXCEEDANCES("复检超出次数")

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
