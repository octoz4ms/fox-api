package com.octo.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zms
 */
public class GeneralUtil {
    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);


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

    /**
     * 雪花算法
     *
     * @return
     */
    public static String nextIdStr() {
        return GeneralUtil.snowflake.nextIdStr();
    }
}
