package com.octo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.File;
import com.octo.mapper.FileMapper;
import com.octo.service.IFileService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 文件管理 服务实现类
 * </p>
 *
 * @author zms
 * @since 2024-09-13
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Override
    public Map<String, Object> findFilesByPage(int page, int limit) {
        Page<File> filePage = page(new Page<>(page, limit));
        HashMap<String, Object> result = new HashMap<>();
        result.put("count", filePage.getTotal());
        result.put("list", filePage.getRecords());
        return result;
    }

    @Override
    public boolean addFile(File file) {
        return save(file);
    }
}
