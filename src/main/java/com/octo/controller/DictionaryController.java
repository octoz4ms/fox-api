package com.octo.controller;


import com.octo.entity.Dictionary;
import com.octo.service.IDictionaryService;
import com.octo.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author zms
 * @since 2025-04-09
 */
@RestController
@RequestMapping("/system/dictionary")
public class DictionaryController {

    @Autowired
    private IDictionaryService dictionaryService;

    @GetMapping
    public ApiResponse<List<Dictionary>> listDictionaries(String dictionaryName) {
        List<Dictionary> dictionaries = dictionaryService.listDictionaries(dictionaryName);
        return ApiResponse.success(dictionaries);
    }

    @PostMapping
    public ApiResponse<Dictionary> createDictionary(@RequestBody Dictionary dictionary) {
        dictionaryService.save(dictionary);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse<Dictionary> updateDictionary(@RequestBody Dictionary dictionary) {
        dictionaryService.updateDictionary(dictionary);
        return ApiResponse.success();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Dictionary> deleteDictionary(@PathVariable Long id) {
        dictionaryService.removeById(id);
        return ApiResponse.success();
    }
}
