package com.octo.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.octo.entity.Dictionary;
import com.octo.enums.ResponseCodeEnums;
import com.octo.exception.CustomException;
import com.octo.mapper.DictionaryMapper;
import com.octo.service.IDictionaryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author zms
 * @since 2025-04-09
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

    @Override
    public List<Dictionary> listDictionaries(String dictionaryName) {
        LambdaQueryWrapper<Dictionary> queryWrapper = Wrappers.lambdaQuery(Dictionary.class)
                .like(StringUtils.isNotBlank(dictionaryName), Dictionary::getDictName, dictionaryName);
        return list(queryWrapper);
    }

    @Override
    public void updateDictionary(Dictionary dictionary) {
        if (dictionary.getId() == null) {
            throw new CustomException(500, "id不能为空");
        }
        LambdaUpdateWrapper<Dictionary> updateWrapper = Wrappers.lambdaUpdate(Dictionary.class)
                .eq(Dictionary::getId, dictionary.getId())
                .set(Dictionary::getDictName, dictionary.getDictName())
                .set(Dictionary::getDictCode, dictionary.getDictCode())
                .set(Dictionary::getComments, dictionary.getComments())
                .set(Dictionary::getSortNumber, dictionary.getSortNumber());
        boolean update = update(updateWrapper);
        if (!update) {
            Dictionary existing = getById(dictionary.getId());
            if (existing != null) {
                throw new CustomException(500, "字典不存在");
            }
            throw new CustomException(ResponseCodeEnums.FAIL);
        }
    }
}
