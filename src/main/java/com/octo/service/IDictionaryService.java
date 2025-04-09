package com.octo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.octo.entity.Dictionary;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author zms
 * @since 2025-04-09
 */
public interface IDictionaryService extends IService<Dictionary> {

    List<Dictionary> listDictionaries(String dictionaryName);

    void updateDictionary(Dictionary dictionary);
}
