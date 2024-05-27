package com.mi.repair.service;

import com.mi.repair.dto.MaterialReqPageQueryDTO;
import com.mi.repair.result.PageResult;

public interface MaterialReqService {
    PageResult pageQuery(MaterialReqPageQueryDTO queryDTO);
}
