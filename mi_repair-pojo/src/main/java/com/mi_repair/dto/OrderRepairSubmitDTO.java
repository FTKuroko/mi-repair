package com.mi_repair.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 14:58
 */
@Data
@ApiModel(description = "创建维修单时传入的数据类型")
public class OrderRepairSubmitDTO {
    @ApiModelProperty("维修单id，非必须")
    private Long id;

    @ApiModelProperty("用户id，非必须")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("手机号")
    private String userPhone;

    @ApiModelProperty("用户地址")
    private String userAddr;

    @ApiModelProperty("设备信息")
    private String goodsInfo;

    @ApiModelProperty("sn")
    private String sn;

    @ApiModelProperty("设备信息")
    private String desc;

}
