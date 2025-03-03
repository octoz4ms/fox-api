package com.octo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class FileStorageService {

    private final Path rootDir;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        rootDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(rootDir);
    }

    /**
     * 上传单个文件
     *
     * @param file   上传的文件对象
     * @param module 模块名称
     * @return 存储的文件名
     */
    public String uploadFile(MultipartFile file, String module) {
        if (file.isEmpty()) {
            throw new FileStorageException("上传文件不能为空");
        }

        try {
            // 生成唯一文件名
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String storedFileName = generateUniqueFilename(originalFileName);

            // 创建模块目录
            Path modulePath = rootDir.resolve(module);
            Files.createDirectories(modulePath);

            // 保存文件
            Path targetPath = modulePath.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return storedFileName;
        } catch (IOException e) {
            throw new FileStorageException("文件上传失败：" + file.getOriginalFilename(), e);
        }
    }

    /**
     * 下载单个文件
     *
     * @param fileName 要下载的文件名
     * @param module   模块名称
     * @return 文件资源
     */
    public Resource downloadFile(String fileName, String module) {
        try {
            Path filePath = rootDir.resolve(module).resolve(fileName).normalize();
            UrlResource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("文件不存在或不可读: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new FileStorageException("无效文件路径：", e);
        }
    }

    /**
     * 上传多个文件
     *
     * @param files  上传的文件对象列表
     * @param module 模块名称
     * @return 存储的文件名列表
     */
    public List<String> uploadFiles(MultipartFile[] files, String module) {
        ArrayList<String> storeFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                storeFileNames.add(uploadFile(file, module));
            }
        }
        return storeFileNames;
    }

    /**
     * 打包下载多个文件
     *
     * @param fileNames 要下载的文件名列表
     * @param module    模块名称
     * @return 文件资源列表
     */
    public Resource downloadFilesAsZip(List<String> fileNames, String module) {
        // 创建临时zip文件
        try {
            Path zipPath = Files.createTempFile("download-", ".zip");
            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
                for (String fileName : fileNames) {
                    Resource resource = downloadFile(module, fileName);
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zos.putNextEntry(zipEntry);
                    StreamUtils.copy(resource.getInputStream(), zos);
                    zos.closeEntry();
                }
            }
            return new UrlResource(zipPath.toUri());
        } catch (IOException e) {
            throw new FileStorageException("zip打包失败：", e);
        }
    }

    /**
     * 生成唯一文件名
     *
     * @param originalFilename 文件原始名称
     * @return 唯一文件名
     */
    private String generateUniqueFilename(String originalFilename) {
        String ext = StringUtils.getFilenameExtension(originalFilename);
        String baseName = UUID.randomUUID().toString().replace("-", "");
        return (ext != null) ? baseName + "." + ext : baseName;
    }

    /**
     * 自定义文件异常
     */
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


    // ==========================文件上传下载V1=======================================

    @Value("${file.upload-dir}")
    private String uploadDir;


    public String uploadFileV1(MultipartFile file, String module) throws FileStorageException {
        if (file.isEmpty()) {
            throw new FileStorageException("Failed to store empty file.");
        }

        try {
            // 创建目标目录
            Path rootPath = Paths.get(uploadDir, module);
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
        }
    }


    /**
     * 下载单个文件
     *
     * @param fileName   要下载的文件名
     * @param sourcePath 文件的存储路径（相对于配置的根目录）
     * @param response   HttpServletResponse对象
     * @throws FileStorageException 文件异常操作
     */
    public void downloadFileV1(String fileName, String sourcePath, HttpServletResponse response) throws FileStorageException {
        Path filePath = Paths.get(uploadDir, sourcePath, fileName);
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
}

