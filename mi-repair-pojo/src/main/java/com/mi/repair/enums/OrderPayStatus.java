package com.mi.repair.enums;

import lombok.Getter;

/**
 * @author 罗慧
 */
@Getter
public enum OrderPayStatus {
    NO("待支付",0),
    YES("已支付",1);
    String name;
    Integer code;
    OrderPayStatus(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
