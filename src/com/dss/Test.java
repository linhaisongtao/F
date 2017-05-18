package com.dss;

/**
 * Created by daisongsong on 2017/5/18.
 */
public class Test {
    public static void main(String[] args) {
        r(1);
        r(2);
        r(12);
    }

    private static void r(int n) {
        System.out.println("n = [" + n + "]");
        double q = Math.pow(1 + 0.05, 1.0 / 12);
        double sum = (1 - Math.pow(q, n)) / (1 - q);

        System.out.println("Test.sum" + sum);

        double r = sum / n;
        System.out.println("Test.r " + r);
        System.out.println("");
    }
}
