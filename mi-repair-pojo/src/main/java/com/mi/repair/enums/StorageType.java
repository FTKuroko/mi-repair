package com.mi.repair.enums;

import lombok.Getter;

/**
 * @author 罗慧
 */
@Getter
public enum StorageType {
    PHONE("车窗",1),
    HOUSE_APPLIANCE("镜子",2),
    CAR("轮胎",3);
    String name;
    Integer code;
    StorageType(String name,Integer code){
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
