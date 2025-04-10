package com.octo.controller;


import com.octo.dto.response.PageResult;
import com.octo.entity.DictionaryData;
import com.octo.service.IDictionaryDataService;
import com.octo.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2025-04-09
 */
@RestController
@RequestMapping("/system/dictionary-data")
public class DictionaryDataController {

    @Autowired
    private IDictionaryDataService dictionaryDataService;

    @GetMapping("/page")
    public ApiResponse<PageResult<DictionaryData>> pageDictionaryData(DictionaryData dictionaryData,
                                                                      @RequestParam(name = "sort", required = false) String sortField,
                                                                      @RequestParam(name = "order", required = false) String sortOrder,
                                                                      @RequestParam(name = "page", required = false, defaultValue = "1") int pageNum,
                                                                      @RequestParam(name = "limit", required = false, defaultValue = "10") int pageSize) {
        PageResult<DictionaryData> data = dictionaryDataService.pageDictionaryData(dictionaryData, sortField, sortOrder, pageNum, pageSize);
        return ApiResponse.success(data);
    }

    @PostMapping
    public ApiResponse<DictionaryData> addDictionaryData(@RequestBody DictionaryData dictionaryData) {
        dictionaryDataService.save(dictionaryData);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse<DictionaryData> updateDictionaryData(@RequestBody DictionaryData dictionaryData) {
        dictionaryDataService.updateDictionaryData(dictionaryData);
        return ApiResponse.success(dictionaryData);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteDictionaryData(@PathVariable Long id) {
        dictionaryDataService.removeById(id);
        return ApiResponse.success();
    }

    @DeleteMapping("/batch")
    public ApiResponse<Boolean> batchDeleteDictionaryData(@RequestBody List<Long> ids) {
        dictionaryDataService.batchDeleteDictionaryData(ids);
        return ApiResponse.success();
    }
}
