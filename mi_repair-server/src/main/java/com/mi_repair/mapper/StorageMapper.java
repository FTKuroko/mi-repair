package com.mi_repair.mapper;

import com.mi_repair.Req.Request;
import com.mi_repair.dto.StorageDTO;
import com.mi_repair.entity.Storage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface StorageMapper {
    List<Storage> getStorageList(Request request);

    void addStorage(StorageDTO dto);

    void subStorage(StorageDTO dto);
}
