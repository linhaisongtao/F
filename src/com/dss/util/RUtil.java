package com.dss.util;

/**
 * Created by daisongsong on 2017/5/18.
 */
public class RUtil {

    public static double r(float rInYear, int n) {
        double q = Math.pow(1 + rInYear, 1.0 / 12);
        double sum = (1 - Math.pow(q, n)) / (1 - q);
        double r = sum / n;
        return r;
    }
}
