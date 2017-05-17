package com.dss.model;

/**
 * Created by daisongsong on 2017/5/16.
 */
public class InvestModel {

    public static float invest(Zhishu zhishu, int base, float ratio, float max, float min) {
        float nowValue = zhishu.getValue();

        int level = (int) ((nowValue - base) / 100);

        float r = 1 - level * ratio;
//        float r = 1f;
//        if (level >= 0) {
//            r = (float) Math.pow(1 - ratio, level);
//        } else {
//            r = (float) Math.pow(1 + ratio, -level);
//        }

        return Math.max(min, Math.min(max, r));
    }
}
