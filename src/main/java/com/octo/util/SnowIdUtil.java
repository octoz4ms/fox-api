package com.octo.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author zms
 */
public class SnowIdUtil {
    private static Snowflake snowflake = IdUtil.createSnowflake(1,1);

    public static String nextIdStr(){
        return snowflake.nextIdStr();
    }
}
