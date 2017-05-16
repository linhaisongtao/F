package com.company.model;

import com.company.util.FileUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by daisongsong on 2017/5/16.
 */
public class FundModel {

    public static List<Fund> getFunds(String fundCode) {
        String s = FileUtil.readFile(fundCode);

        List<Fund> funds = new ArrayList<>();
        String[] strings = s.split("\n");
        for (int i = 0; i < strings.length; i++) {
            Fund fund = parseFundString(strings[i]);
            fund.setCode(fundCode);
            fund.setName(fundCode);
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
