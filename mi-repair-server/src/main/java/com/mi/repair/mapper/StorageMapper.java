package com.mi.repair.mapper;

import com.github.pagehelper.Page;
import com.mi.repair.dto.StorageDTO;
import com.mi.repair.dto.WorkerStoragePageQueryDTO;
import com.mi.repair.entity.Storage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StorageMapper {
    Page<Storage> getStorageList(WorkerStoragePageQueryDTO queryDTO);

    void addStorage(StorageDTO dto);

    void subStorage(StorageDTO dto);

    void delStorage(StorageDTO dto);

    Storage getStorageByName(String name);

    Storage getStorage(Long id);
}
