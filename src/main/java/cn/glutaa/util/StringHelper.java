package cn.glutaa.util;

import java.util.Random;

public class StringHelper {

    public static boolean isNullOrEmpty(String str){
        return str == null || str.equals("");
    }

    public static boolean isNullOrWhitespace(String str){
        return str == null || str.trim().equals("");
    }

    public static boolean isPhoneNumber(String number){
        // 首先判斷是否爲11位數字
        if (number.length() != 11 || !number.matches("\\d+")) {
            return false;
        }
        // 判斷是否以1開頭
        if (!number.startsWith("1")) {
            return false;
        }
        // 判斷第二位是否爲3、4、5、6、7、8、9中的一個
        char second = number.charAt(1);
        return second == '3' || second == '4' || second == '5' || second == '6' || second == '7' || second == '8' || second == '9';
    }

    //字符串常量池
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
