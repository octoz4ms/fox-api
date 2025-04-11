package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.dto.response.PageResult;
import com.octo.entity.DictionaryData;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author zms
 * @since 2025-04-09
 */
public interface IDictionaryDataService extends IService<DictionaryData> {

    PageResult<DictionaryData> pageDictionaryData(DictionaryData dictionaryData, String sortField, String sortOrder, int pageNum, int pageSize);

    List<DictionaryData> listDictionaryData(String dictCode);

    void updateDictionaryData(DictionaryData dictionaryData);

    void batchDeleteDictionaryData(List<Long> ids);

}
