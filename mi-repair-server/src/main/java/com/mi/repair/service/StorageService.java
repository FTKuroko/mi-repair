package com.mi.repair.service;

import com.mi.repair.Req.Request;
import com.mi.repair.dto.StorageDTO;
import com.mi.repair.vo.StorageVO;

import java.util.List;

public interface StorageService {
    /**
     * 获取库存
     * @param request
     * @return
     */
    List<StorageVO> getStorageList(Request request);

    void addStorage(StorageDTO dto);

    void subStorage(StorageDTO dto);

    void delStorage(StorageDTO dto);
}
