package com.it.utils;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtil {
    /**
     * 生成32位字符串
     * @return
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }


}
