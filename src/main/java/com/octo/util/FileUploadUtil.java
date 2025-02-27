package com.octo.util;

import cn.hutool.core.lang.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class FileUploadUtil {
    public static String baseUploadDirectory;

    public static String accessPath;

    @Value("${file.root.directory}")
    public void setBaseUploadDirectory(String baseUploadDirectory) {
        FileUploadUtil.baseUploadDirectory = baseUploadDirectory;
    }

    @Value("${file.access.path}")
    public void setAccessPath(String accessPath) {
        FileUploadUtil.accessPath = accessPath;
    }


    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    public static com.octo.entity.File uploadFile(MultipartFile file, String parentFolder) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID() + fileExtension;

            // 创建完整的目标文件路径，包括自定义父文件夹
            String fullUploadDirectory = FileUploadUtil.baseUploadDirectory + File.separator + parentFolder;
            Path destinationFile = Paths.get(fullUploadDirectory + File.separator + uniqueFileName);

            FileUploadUtil.logger.info(destinationFile.toString());

            // 如果目标目录不存在，则创建它
            if (!Files.exists(destinationFile.getParent())) {
                Files.createDirectories(destinationFile.getParent());
            }

            // 将上传的文件内容写入目标文件
            Files.write(destinationFile, file.getBytes());

            // 返回文件信息
            FileUploadUtil.logger.info("文件上传成功：{}", uniqueFileName);
            com.octo.entity.File record = new com.octo.entity.File();
            record.setUrl(FileUploadUtil.accessPath + File.separator + parentFolder + File.separator + uniqueFileName);
            record.setName(file.getOriginalFilename());
            record.setCreateTime(LocalDateTime.now());
            record.setLength(file.getSize());
            record.setPath(destinationFile.toString());
            return record;
        } catch (IOException e) {
            FileUploadUtil.logger.error("文件上传失败：{}", e.getMessage());
            return null;
        }
    }

}
