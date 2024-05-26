package com.mi.repair.enums;

import lombok.Getter;

/**
 * @author 罗慧
 */
@Getter
public enum MaterialReqStatus {
    OUT_OF_STOCK("库存不足",1),
    SUCCESS("申请成功",2);
    String name;
    Integer code;
    MaterialReqStatus(String name,Integer code){
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
