package com.gaoan.forever.utils.encryption;

import java.util.UUID;

/**
 * UUID工具类
 * Created by three on 2017/4/12.
 */
public class UUIDUtils {

    /**
     * 返回32位UUID
     * @return 32位UUID
     */
    public static String quer32UUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
