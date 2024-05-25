package com.mi.repair.mapper;

import com.mi.repair.Req.Request;
import com.mi.repair.dto.StorageDTO;
import com.mi.repair.entity.Storage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface StorageMapper {
    List<Storage> getStorageList(Request request);

    void addStorage(StorageDTO dto);

    void subStorage(StorageDTO dto);

    void delStorage(StorageDTO dto);

    Storage getStorageByName(String name);

    Storage getStorage(Storage storage);
}
