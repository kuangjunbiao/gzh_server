package com.gaoan.forever.utils.character;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

/**
 * Created by one on 2017/4/14.
 */
public class WheatfieldStringUtils {

    public static void main(String[] args) {
//        for (int i=0; i<10; i++) {
//            System.out.println(getRandomString(32, true, true));
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        System.out.println(sdf.format(date));
//
//        long time = date.getTime();
//        Date date1 = new Date(time);
//        System.out.println(sdf.format(date1));

    }

    public static boolean isNum(String num) {
        String regex = "^[0-9]+(.[0-9]+)?$";
        boolean bool = num.matches(regex);
        return bool;
    }


    /**
     * 生成随机字符串
     * @param length 长度
     * @param letter 含字母
     * @param capital 字母可大写
     * @return String
     */
    public static String getRandomString(int length, boolean letter, boolean capital) {
        String val = "";
        Random random = new Random();

        //不含字母
        if(!letter) {
            for (int i = 0; i < length; i++) {
                val += String.valueOf(random.nextInt(10));
            }
            return val;
        }

        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "letter" : "num"; // 输出字母还是数字

            if ("letter".equalsIgnoreCase(charOrNum)) { // 字符串
                int choice = 97;
                //取得大写字母还是小写字母
                if(capital) {
                    choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                }
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }

    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
