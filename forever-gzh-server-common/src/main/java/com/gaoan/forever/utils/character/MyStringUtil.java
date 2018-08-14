package com.gaoan.forever.utils.character;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by longshengtang on 2017/4/9.
 */
public class MyStringUtil {
    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}