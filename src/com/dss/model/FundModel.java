package com.dss.model;

import com.dss.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by daisongsong on 2017/5/16.
 */
public class FundModel {

    public static List<Fund> getFunds(String fundCode) {
        String fundName = null;

        File[] files = new File(".").listFiles();
        File fundFile = null;
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.startsWith("F_" + fundCode)) {
                fundName = fileName.replaceAll("F_" + fundCode + "_", "");
                fundFile = file;
                break;
            }
        }
        if(fundFile == null){
            return new ArrayList<>();
        }

        String s = FileUtil.readFile(fundFile.getAbsolutePath());

        List<Fund> funds = new ArrayList<>();
        String[] strings = s.split("\n");
        for (int i = 0; i < strings.length; i++) {
            Fund fund = parseFundString(strings[i]);
            fund.setCode(fundCode);
            fund.setName(fundName);
            funds.add(fund);
        }

        Collections.sort(funds);

        return funds;
    }

    private static Fund parseFundString(String string) {
        String[] strings = string.split("\t");

        Fund fund = new Fund();
        fund.setDateString(strings[0]);
        fund.setPrice(Float.valueOf(strings[1]));
        return fund;
    }
}
