package com.mi.repair.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mi.repair.dto.MaterialReqPageQueryDTO;
import com.mi.repair.entity.MaterialReq;
import com.mi.repair.enums.MaterialReqStatus;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.mapper.MaterialReqMapper;
import com.mi.repair.result.PageResult;
import com.mi.repair.service.MaterialReqService;
import com.mi.repair.vo.MaterialReqVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 罗慧
 */
@Service
public class MaterialReqServiceImpl implements MaterialReqService {
    @Autowired
    MaterialReqMapper materialReqMapper;
    @Override
    public PageResult pageQuery(MaterialReqPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<MaterialReq> page = materialReqMapper.pageQuery(queryDTO);
        long total = page.getTotal();
        List<MaterialReqVO> pageInfo = new ArrayList<>(page.size());
        List<MaterialReq> result = page.getResult();
        for (MaterialReq entity : result) {
            MaterialReqVO vo = new MaterialReqVO();
            BeanUtils.copyProperties(entity, vo);
            for(MaterialReqStatus status : MaterialReqStatus.values()){
                if(entity.getStatus().equals(status.getCode())){
                    vo.setStatusInfo(status.getName());
                }
                pageInfo.add(vo);
            }
        }
        return new PageResult(total, pageInfo);
    }
}
