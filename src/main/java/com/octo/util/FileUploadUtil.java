package com.octo.util;

import cn.hutool.core.lang.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {
    @Value("${file.path}")
    private static String baseUploadDirectory;
    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    public static String uploadFile(MultipartFile file, String customParentFolder) {
        if (file.isEmpty()) {
            return null;
        }

        try {
            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID() + fileExtension;

            // 创建完整的目标文件路径，包括自定义父文件夹
            String fullUploadDirectory = FileUploadUtil.baseUploadDirectory + File.separator + customParentFolder;
            Path destinationFile = Paths.get(fullUploadDirectory + File.separator + uniqueFileName);

            // 如果目标目录不存在，则创建它
            if (!Files.exists(destinationFile.getParent())) {
                Files.createDirectories(destinationFile.getParent());
            }

            // 将上传的文件内容写入目标文件
            Files.write(destinationFile, file.getBytes());

            FileUploadUtil.logger.info("文件上传成功：{}", uniqueFileName);
            return uniqueFileName;
        } catch (IOException e) {
            FileUploadUtil.logger.error("文件上传失败：{}", e.getMessage());
            return null;
        }
    }
}
