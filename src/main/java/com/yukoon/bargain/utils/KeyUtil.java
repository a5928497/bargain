package com.yukoon.bargain.utils;

import java.util.Random;

public class KeyUtil {
    private static final String chars = "1234567890";

    public static String getKey(int length) {
        Random random = new Random();
        StringBuffer str = new StringBuffer();
        for (int i = 0;i<length;i++) {
            int index = random.nextInt(chars.length());
            str.append(chars.substring(index,index+1));
        }
        return str.toString();
    }

    public static void main(String[] args) {
        System.out.println(KeyUtil.getKey(4));
    }
}
