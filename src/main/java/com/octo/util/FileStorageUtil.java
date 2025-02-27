package com.octo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileStorageUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;


    /**
     * 上传文件到服务器
     *
     * @param file       文件对象
     * @param targetPath 目标存储路径（相对于配置的根目录）
     * @return 存储的文件名
     * @throws FileStorageException 文件操作异常
     */
    public String uploadFile(MultipartFile file, String targetPath) throws FileStorageException {
        if (file.isEmpty()) {
            throw new FileStorageException("Failed to store empty file.");
        }

        try {
            // 创建目标目录
            Path rootPath = Paths.get(uploadDir, targetPath);
            if (!Files.exists(rootPath)) {
                Files.createDirectories(rootPath);
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtention = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtention = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String storedFileName = UUID.randomUUID().toString() + fileExtention;

            // 保存文件
            Path targetLocation = rootPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetLocation);
            return storedFileName;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file: " + e.getMessage(), e);
        } finally {

        }
    }

    /**
     * 下载文件
     *
     * @param fileName   要下载的文件名
     * @param sourcePath 文件的存储路径（相对于配置的根目录）
     * @param response   HttpServletResponse对象
     * @throws FileStorageException 文件异常操作
     */
    public void downloadFile(String fileName, String sourcePath, HttpServletResponse response) throws FileStorageException {
        Path filePath = Paths.get(uploadDir, sourcePath, fileName);
        System.out.println(filePath.toString());
        if (!Files.exists(filePath)) {
            throw new FileStorageException("File not found: " + fileName);
        }

        try (InputStream is = Files.newInputStream(filePath);
             ServletOutputStream os = response.getOutputStream()) {
            // 设置响应头
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            // 流式传输文件
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (IOException e) {
            throw new FileStorageException("Failed to download file: " + e.getMessage(), e);
        }
    }


    // 自定义异常类
    public static class FileStorageException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = -6227235707417667135L;

        public FileStorageException(String message) {
            super(message);
        }

        public FileStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
