package com.show.seckill.utils;

import java.util.UUID;

/**
 * @Auther: 涂成
 * @Date: 2019/6/21 15:46
 * @Description:
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
