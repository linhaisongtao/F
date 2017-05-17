package com.dss.model;

import com.dss.util.FileUtil;

import java.util.*;

/**
 * Created by daisongsong on 2017/5/16.
 */
public class ZhishuModel {

    public static Map<String, Zhishu> getZhishuMap() {
        Map<String, Zhishu> zhishuMap = new HashMap<>();
        List<Zhishu> zhishus = getZhishus();
        for (int i = 0; i < zhishus.size(); i++) {
            zhishuMap.put(zhishus.get(i).getDateString(), zhishus.get(i));
        }
        return zhishuMap;
    }

    public static List<Zhishu> getZhishus() {
        String szzs = FileUtil.readFile("szzs");
        String[] strings = szzs.split("\n");
        List<Zhishu> zhishus = new ArrayList<>();

        for (int i = 1; i < strings.length; ++i) {
            zhishus.add(parseZhishuString(strings[i]));
        }
        Collections.sort(zhishus);

        return zhishus;
    }

    private static Zhishu parseZhishuString(String string) {
        Zhishu zhishu = new Zhishu();
        String[] strings = string.split("\t");
        zhishu.setDateString(strings[0]);
        zhishu.setName(strings[1]);
        zhishu.setValue(Float.valueOf(strings[2]));
        return zhishu;
    }

    public static Zhishu findZhishu(List<Zhishu> zhishus, Date date) {
        for (int i = 0; i < zhishus.size(); i++) {
            if (!zhishus.get(i).getDate().before(date)) {
                return zhishus.get(i);
            }
        }
        return null;
    }
}
