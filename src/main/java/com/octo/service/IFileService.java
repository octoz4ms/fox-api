package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.entity.File;

import java.util.Map;

/**
 * <p>
 * 文件管理 服务类
 * </p>
 *
 * @author zms
 * @since 2024-09-13
 */
public interface IFileService extends IService<File> {

    Map<String, Object> findFilesByPage(int page, int limit);

    boolean addFile(File file);
}
