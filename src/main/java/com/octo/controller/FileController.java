package com.octo.controller;


import com.octo.service.IFileService;
import com.octo.service.IUserService;
import com.octo.util.ApiResponse;
import com.octo.util.FileStorageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 文件管理 前端控制器
 * </p>
 *
 * @author zms
 * @since 2024-09-13
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private IFileService fileService;

    @Resource
    private IUserService userService;

    @Resource
    private FileStorageUtil fileStorageUtil;


    @GetMapping("page")
    public ApiResponse<Map<String, Object>> getFilesByPage(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Map<String, Object> data = fileService.findFilesByPage(page, limit);
        return ApiResponse.success(data);
    }

//    @PostMapping("/upload")
//    public ApiResponse<Map<String, String>> uploadFile(@RequestParam(value = "folder", defaultValue = "") String folder, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
//        File record = FileUploadUtil.uploadFile(file, folder);
//        if (record == null) {
//            return ApiResponse.fail("上传文件失败!");
//        }
//        // 生成文件记录
//        String username = JwtUtil.getUsername(request);
//        User user = userService.getUserByUsername(username);
//        record.setCreator(user.getUserNo());
//        record.setCreatorName(user.getNickname());
//        boolean save = fileService.addFile(record);
//        if (!save) {
//            return ApiResponse.fail("上传文件失败!");
//        }
//        HashMap<String, String> map = new HashMap<>();
//        map.put("file", record.getUrl());
//        return ApiResponse.success(map);
//    }

    @DeleteMapping("/remove/{id}")
    public ApiResponse<Boolean> deleteFile(@PathVariable(value = "id") String id) {
        boolean result = fileService.removeById(id);
        return ApiResponse.success(result);
    }

    @PostMapping("/upload")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "path", defaultValue = "") String path) {
        String fileName = fileStorageUtil.uploadFile(file, path);
        return ApiResponse.success(fileName);
    }


    @GetMapping("/download/{fileName}")
    public void downloadFile(@PathVariable(value = "fileName") String fileName, @RequestParam(value = "path", defaultValue = "") String path, HttpServletResponse response) {
        fileStorageUtil.downloadFile(fileName, path, response);
    }
}
