package com.mi.repair.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mi.repair.mapper.StorageMapper;
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
    public List<StorageVO> getStorageList(Request request) {
        PageHelper.startPage(request.getPage(),request.getCount());
        List<Storage> list = storageMapper.getStorageList(request);
        List<Storage> listPage = new PageInfo<Storage>(list).getList();
        List<StorageVO> result = new ArrayList<>();
        for (Storage storage : listPage) {
            StorageVO storageVO = new StorageVO();
            BeanUtils.copyProperties(storage, storageVO);
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

    @Override
    public void delStorage(StorageDTO dto) {
        storageMapper.delStorage(dto);
    }
}
