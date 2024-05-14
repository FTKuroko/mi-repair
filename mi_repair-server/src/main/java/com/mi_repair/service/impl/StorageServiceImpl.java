package com.mi_repair.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mi_repair.Req.Request;
import com.mi_repair.dto.StorageDTO;
import com.mi_repair.entity.Storage;
import com.mi_repair.enums.StorageType;
import com.mi_repair.mapper.StorageMapper;
import com.mi_repair.result.Result;
import com.mi_repair.service.StorageService;
import com.mi_repair.vo.StorageVO;
import lombok.extern.slf4j.Slf4j;
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
    public List<StorageVO> getStorageList(Request request) {
        PageHelper.startPage(request.getPage(),request.getCount());
        List<Storage> list = storageMapper.getStorageList(request);
        List<Storage> listPage = new PageInfo<Storage>(list).getList();
        List<StorageVO> result = new ArrayList<>();
        for (Storage storage : listPage) {
            StorageVO storageVO = new StorageVO();
            storageVO.setAmount(storage.getAmount());
            storageVO.setName(storage.getName());
            storageVO.setPrice(storage.getPrice());
            storageVO.setId(storage.getId());
            for (StorageType value : StorageType.values()) {
                if(value.getCode() == storage.getType()){
                    storageVO.setStatus(value.getName());
                }
            }
            result.add(storageVO);
        }
        return result;
    }

    @Override
    public void addStorage(StorageDTO dto) {
        storageMapper.addStorage(dto);
    }

    @Override
    public void subStorage(StorageDTO dto) {
        storageMapper.subStorage(dto);
    }
}
