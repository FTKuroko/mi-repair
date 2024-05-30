package com.mi.repair.utils;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Kuroko
 * @description 文件压缩工具类
 * @date 2024/5/30 16:12
 */
public class FileCompressionUtil {
    public static File compressFile(File file, long maxSizeInBytes) throws IOException {
        if (file.length() <= maxSizeInBytes) {
            return file;
        }

        File compressedFile = new File(file.getParent(), "compressed_" + file.getName() + ".gz");

        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(compressedFile);
             GzipCompressorOutputStream gzos = new GzipCompressorOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                gzos.write(buffer, 0, len);
            }
        }

        return compressedFile;
    }
}
