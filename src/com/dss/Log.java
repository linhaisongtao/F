package com.dss;

/**
 * Created by daisongsong on 2017/5/17.
 */
public class Log {
    public static boolean ENABLE_LOG = false;

    public static void i(String msg) {
        if (ENABLE_LOG) {
            System.out.println(msg);
        }
    }
}
