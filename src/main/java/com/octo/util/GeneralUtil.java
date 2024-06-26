package com.octo.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zms
 */
public class GeneralUtil {
    /**
     * 封装分页数据
     *
     * @param page
     * @return
     */
    public static Map<String, Object> resultPage(IPage page) {
        HashMap<String, Object> result = new HashMap<>(2);
        result.put("count", page.getTotal());
        result.put("list", page.getRecords());
        return result;
    }
}
