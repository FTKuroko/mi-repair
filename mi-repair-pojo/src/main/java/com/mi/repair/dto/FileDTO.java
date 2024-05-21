package com.mi.repair.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 罗慧
 */
@Data
@ApiModel(description = "文件")
public class FileDTO {

    private Long id;

    private Integer type;

    private Long orderId;

    private String path;
}
