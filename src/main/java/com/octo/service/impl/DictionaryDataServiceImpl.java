package com.octo.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.dto.response.PageResult;
import com.octo.entity.Dictionary;
import com.octo.entity.DictionaryData;
import com.octo.enums.ResponseCodeEnums;
import com.octo.exception.CustomException;
import com.octo.mapper.DictionaryDataMapper;
import com.octo.service.IDictionaryDataService;
import com.octo.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2025-04-09
 */
@Service
public class DictionaryDataServiceImpl extends ServiceImpl<DictionaryDataMapper, DictionaryData> implements IDictionaryDataService {

    @Autowired
    private IDictionaryService dictionaryService;

    @Override
    public PageResult<DictionaryData> pageDictionaryData(DictionaryData dictionaryData, String sortField, String sortOrder, int pageNum, int pageSize) {
        // 分页参数
        Page<DictionaryData> dictionaryDataPage = new Page<>(pageNum, pageSize);

        // 条件构建
        LambdaQueryWrapper<DictionaryData> queryWrapper = new LambdaQueryWrapper<>(DictionaryData.class)
                .eq(DictionaryData::getDictId, dictionaryData.getDictId())
                .like(StringUtils.isNotBlank(dictionaryData.getDictDataName()), DictionaryData::getDictDataName, dictionaryData.getDictDataName())
                .like(StringUtils.isNotBlank(dictionaryData.getDictDataCode()), DictionaryData::getDictDataCode, dictionaryData.getDictDataCode());

        // 动态排序
        HashMap<String, SFunction<DictionaryData, ?>> allowedSortFields = new HashMap<>();
        allowedSortFields.put("dictDataName", DictionaryData::getDictDataName);
        allowedSortFields.put("dictDataCode", DictionaryData::getDictDataCode);
        allowedSortFields.put("sortNumber", DictionaryData::getSortNumber);
        allowedSortFields.put("createTime", DictionaryData::getCreateTime);
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sortOrder)) {
            SFunction<DictionaryData, ?> field = allowedSortFields.get(sortField);
            if ("asc".equalsIgnoreCase(sortOrder)) {
                queryWrapper.orderByAsc(field);
            } else if ("desc".equalsIgnoreCase(sortOrder)) {
                queryWrapper.orderByDesc(field);
            }
        } else {
            queryWrapper.orderByAsc(DictionaryData::getSortNumber);
        }

        Page<DictionaryData> dataPage = page(dictionaryDataPage, queryWrapper);
        return new PageResult<>(dataPage);
    }

    @Override
    public List<DictionaryData> listDictionaryData(String dictCode) {
        Dictionary dictionary = dictionaryService.getOne(new LambdaQueryWrapper<>(Dictionary.class).eq(Dictionary::getDictCode, dictCode));
        if (dictionary == null) {
            return List.of();
        }
        LambdaQueryWrapper<DictionaryData> queryWrapper = new LambdaQueryWrapper<DictionaryData>()
                .eq(DictionaryData::getDictId, dictionary.getId())
                .orderByAsc(DictionaryData::getSortNumber);
        return list(queryWrapper);
    }

    @Override
    public void updateDictionaryData(DictionaryData dictionaryData) {
        if (dictionaryData.getId() == null) {
            throw new CustomException(500, "id不能为空");
        }
        boolean updated = updateById(dictionaryData);
        if (!updated) {
            DictionaryData existing = getById(dictionaryData.getId());
            if (existing == null) {
                throw new CustomException(500, "字典数据不存在");
            }
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
    }

    @Override
    @Transactional
    public void batchDeleteDictionaryData(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CustomException(500, "请至少选择一条数据");
        }
        removeByIds(ids);
    }
}
