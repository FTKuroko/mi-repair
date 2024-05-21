package com.mi.repair.mapper;

import com.mi.repair.entity.MaterialReq;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/16 12:58
 */
@Mapper
public interface MaterialReqMapper {

    long submit(MaterialReq materialReq);
}
