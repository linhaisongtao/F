package com.company;

import com.company.model.*;
import com.company.util.FileUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static Set<String> sMonth = new HashSet<>();
    private static int base = 3300;
    private static float r = 0.1f;
    private static float max = 2f;
    private static float min = 0.5f;
    private static int S_TOTAL = 12;
    private static String FUND_CODE = "210004";

    public static void main(String[] args) {
        // write your code here
        FUND_CODE = "210004";
        runWithDiffentR();

        FUND_CODE = "160212";
        runWithDiffentR();

        FUND_CODE = "519975";
        runWithDiffentR();

        FUND_CODE = "310318";
        runWithDiffentR();

        FUND_CODE = "000478";
        runWithDiffentR();

    }

    private static void runWithDiffentR() {
        r = 0f;
        runOneFundWithDiffentTotal();
        r = 0.05f;
        runOneFundWithDiffentTotal();
        r = 0.1f;
        runOneFundWithDiffentTotal();
    }

    private static void runOneFundWithDiffentTotal() {
        S_TOTAL = 12;
        run();
        S_TOTAL = 24;
        run();
        S_TOTAL = 36;
        run();
        S_TOTAL = 60;
        run();
    }

    private static void run() {
        sMonth.clear();

        List<Zhishu> zhishus = ZhishuModel.getZhishus();
        List<Fund> funds = FundModel.getFunds(FUND_CODE);


        List<Invest> invests = new ArrayList<>();
        for (int i = 0; i < funds.size(); i++) {
            Fund fund = funds.get(i);

            Zhishu zhishu = ZhishuModel.findZhishu(zhishus, fund.getDate());
            if (canBuy(zhishu, fund)) {
                Invest invest = new Invest();
                invest.setFund(fund);
                invest.setZhishu(zhishu);
                invest.setRatio(InvestModel.invest(zhishu, base, r, max, min));
                invests.add(invest);
            }
        }

        System.out.println("Main.main " + invests.size());

        int needCount = S_TOTAL;
        List<Invest> investList = new ArrayList<>();
        int startIndex = invests.size() - needCount;
        startIndex = startIndex < 0 ? 0 : startIndex;
        for (; startIndex < invests.size(); ++startIndex) {
            investList.add(invests.get(startIndex));
        }


        computeBenifit(investList);
        System.out.println("Main.main " + investList.size());
    }

    private static void computeBenifit(List<Invest> invests) {
        for (int i = 0; i < invests.size(); i++) {
            Invest invest = invests.get(i);
            invest.setCurrentMoney(1000 * invest.getRatio());
            invest.setCurrentCount(invest.getCurrentMoney() / invest.getFund().getPrice());
            if (i > 0) {
                invest.setTotalCount(invest.getCurrentCount() + invests.get(i - 1).getTotalCount());
                invest.setTotalMoney(invest.getCurrentMoney() + invests.get(i - 1).getTotalMoney());
            } else {
                invest.setTotalCount(invest.getCurrentCount());
                invest.setTotalMoney(invest.getCurrentMoney());
            }
            invest.setMarketMoney(invest.getTotalCount() * invest.getFund().getPrice());

//            String format = String.format("%d date[%s],currentMoney[%.0f],totalMoney[%.0f],currentCount[%.2f],totalCount[%.2f],marketMoney[%.2f],benifitRatio[%.2f%%]",
//                    i + 1,
//                    invest.getFund().getDateString(),
//                    invest.getCurrentMoney(),
//                    invest.getTotalMoney(),
//                    invest.getCurrentCount(),
//                    invest.getTotalCount(),
//                    invest.getMarketMoney(),
//                    (invest.getMarketMoney() / invest.getTotalMoney() - 1) * 100);
//
//            System.out.println(format);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("base[" + base + "],ratio[" + r + "],max[" + max + "],min[" + min + "]").append("\n");
        String title = "编号\t日期\t指数\t净值\t钱(当期)\t份额(当期)\t钱(总)\t份额(总)\t市值\t收益率";
        System.out.println(title);
        stringBuilder.append(title).append("\n");
        for (int i = 0; i < invests.size(); i++) {
            Invest invest = invests.get(i);
            String s = String.format("%d\t%s\t%.2f\t%.4f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f%%\t",
                    i + 1,
                    invest.getFund().getDateString(),
                    invest.getZhishu().getValue(),
                    invest.getFund().getPrice(),
                    invest.getCurrentMoney(),
                    invest.getCurrentCount(),
                    invest.getTotalMoney(),
                    invest.getTotalCount(),
                    invest.getMarketMoney(),
                    (invest.getMarketMoney() / invest.getTotalMoney() - 1) * 100);

            System.out.println(s);
            stringBuilder.append(s).append("\n");
        }

        FileUtil.writeFile("fund/" + invests.get(0).getFund().getCode() + "_" + invests.size() + "_" + base + "_" + r + "_" + max + "_" + min + ".txt", stringBuilder.toString());
    }

    private static boolean canBuy(Zhishu zhishu, Fund fund) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String m = simpleDateFormat.format(zhishu.getDate());
        boolean contains = sMonth.contains(m);
        sMonth.add(m);
        return !contains;
    }

}
