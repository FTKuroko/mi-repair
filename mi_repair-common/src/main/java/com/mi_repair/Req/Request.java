package com.mi_repair.Req;


import lombok.Data;

/**
 * @author 罗慧
 */
@Data
public class Request {
    /**
     * 第几页
     */
    private Integer page;
    /**
     * 一页几个数据
     */
    private Integer count;
    /**
     * 数据id
     */
    private Long id;
    /**
     * 数据名称
     */
    private String name;
    /**
     * 数据类型
     */
    private String type;

}
