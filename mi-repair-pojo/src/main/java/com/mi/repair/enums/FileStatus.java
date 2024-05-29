package com.mi.repair.enums;

import lombok.Getter;

/**
 * @author 罗慧
 */
public enum FileStatus {
    IMAGE("图片",1),
    VIDEO("视频",2);

    String desc;
    int code;
    FileStatus(String desc,Integer code){
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
