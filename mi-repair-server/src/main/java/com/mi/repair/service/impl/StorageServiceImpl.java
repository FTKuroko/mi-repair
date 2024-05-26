package com.mi.repair.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mi.repair.dto.WorkerStoragePageQueryDTO;
import com.mi.repair.mapper.StorageMapper;
import com.mi.repair.result.PageResult;
import com.mi.repair.service.StorageService;
import com.mi.repair.Req.Request;
import com.mi.repair.dto.StorageDTO;
import com.mi.repair.entity.Storage;
import com.mi.repair.enums.StorageType;
import com.mi.repair.vo.StorageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 罗慧
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;

    @Override
    public PageResult getStorageList(WorkerStoragePageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<Storage> page = storageMapper.getStorageList(queryDTO);
        List<Storage> result = page.getResult();
        List<StorageVO> pageResult = new ArrayList<>(result.size());
        Long total = page.getTotal();
        for (Storage storage : result) {
            StorageVO storageVO = new StorageVO();
            BeanUtils.copyProperties(storage, storageVO);
            for (StorageType value : StorageType.values()) {
                if(value.getCode() == storageVO.getType()){
                    storageVO.setTypeInfo(value.getName());
                }
            }
            pageResult.add(storageVO);
        }
        return new PageResult(total, pageResult);
    }

    @Override
    public void addStorage(StorageDTO dto) {
        storageMapper.addStorage(dto);
    }

    @Override
    public void subStorage(StorageDTO dto) {
        storageMapper.subStorage(dto);
    }

    @Override
    public void delStorage(StorageDTO dto) {
        storageMapper.delStorage(dto);
    }
}
