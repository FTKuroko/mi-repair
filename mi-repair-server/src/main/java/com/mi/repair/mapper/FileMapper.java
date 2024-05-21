package com.mi.repair.mapper;

import com.mi.repair.dto.FileDTO;
import com.mi.repair.entity.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 罗慧
 */
@Mapper
public interface FileMapper {

    /**
     * 保存文件
     * @param file
     */
    public void saveFileInfos(File file) ;

    /**
     * 获取文件
     * @param dto
     * @return
     */
    List<File> getFiles(FileDTO dto);
}
