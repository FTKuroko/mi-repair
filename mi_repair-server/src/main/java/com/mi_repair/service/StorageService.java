package com.mi_repair.service;

import com.mi_repair.Req.Request;
import com.mi_repair.dto.StorageDTO;
import com.mi_repair.result.Result;
import com.mi_repair.vo.StorageVO;

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
}
