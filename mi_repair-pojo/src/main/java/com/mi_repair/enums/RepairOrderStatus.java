package com.mi_repair.enums;

public enum RepairOrderStatus {
    /*
       订单部分
    */
    WAITING_FOR_WORKER_ACCEPTANCE("等待工程师接收", 1)

    , WAITING_FOR_USER_CONFIRMATION("等待用户确认", 2)

    , CONFIRMED("用户已确认", 3)

    , CANCEL("用户已取消订单", 4)
    /*
     *  维修部分
     */
    , APPLICATION_MATERIALS("申请材料", 13)

    , WAITING_MATERIALS("等待材料", 14)

    , REPAIR("维修", 15)

    , RETEST("复检", 16)

    , REPAIR_FAILED("维修失败",17)
    /*
     *  支付模块
     */
    , WAITING_PAY("等待支付", 21)

    , PAYED("已支付", 22)

    , DONE("已完成", 24);

    String description;
    int code;

    RepairOrderStatus(String description, int code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}