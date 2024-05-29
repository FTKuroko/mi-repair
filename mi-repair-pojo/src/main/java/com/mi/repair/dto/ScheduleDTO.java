package com.mi.repair.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 罗慧
 */
@Data
@ApiModel(description = "进度")
public class ScheduleDTO implements Serializable {
    private Long orderId;
    private Long userId;
    private Integer type;
}
