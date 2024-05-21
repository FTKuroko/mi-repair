package com.mi.repair.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author 李晴
 * @description 状态机状态枚举类
 */
@Getter
public enum RepairOrderStatus{
    /*
       订单部分
    */
    WAITING_FOR_WORKER_ACCEPTANCE("等待工程师接收", 0)

    , WAITING_FOR_USER_CONFIRMATION("等待用户确认", 1)

    , CONFIRMED("用户已确认", 2)

    , CANCEL("用户已取消订单", 3)
    /*
     *  维修部分
     */
    , PASS_WORKER_INSPECTION("通过工程师检测", 11)

    , NOT_PASS_WORKER_INSPECTION("工程师检测不通过", 12)

    , APPLICATION_MATERIALS("申请材料", 13)

    , WAITING_MATERIALS("等待材料", 14)

    , REPAIR("维修", 15)

    , RETEST("复检", 16)
    /*
     *  支付模块
     */
    , WAITING_PAY("等待支付", 21)

    , PAYED("已支付", 22)

    , UNPAID("未支付", 23)

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