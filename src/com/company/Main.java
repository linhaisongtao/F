package com.company;

import com.company.model.*;
import com.company.ui.Ui;
import com.company.ui.UiData;
import com.company.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private static final Map<Invest.Condition, List<Invest>> sInvestMap = new HashMap<>();
    private static final List<Investment> sInvestments = new ArrayList<>();
    private static Set<String> sMonth = new HashSet<>();
    private static int base = 3300;
    private static float r = 0.1f;
    private static float max = 2f;
    private static float min = 0.5f;
    private static int S_TOTAL = 12;
    private static String FUND_CODE = "210004";

    public static void main(String[] args) throws IOException {
        // write your code here
        System.out.println("please base max min \n");
        String cmd = new BufferedReader(new InputStreamReader(System.in)).readLine();
        System.out.println(cmd);
        String[] split = cmd.split(" ");
        base = Integer.valueOf(split[0]);
        max = Float.valueOf(split[1]);
        min = Float.valueOf(split[2]);

        sInvestMap.clear();
        sInvestments.clear();

        File[] files = new File(".").listFiles();
        for (File file : files) {
            String fileName = file.getName();
            if (fileName.startsWith("F_")) {
                FUND_CODE = fileName.replaceFirst("F_", "");
                runWithDiffentR();
            }
        }

        while (true){
            System.out.println("please code total\n");
            cmd = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.println(cmd);
            split = cmd.split(" ");
            String code = split[0];
            int total = Integer.valueOf(split[1]);
            Invest.Condition condition = new Invest.Condition(code, base, -1f, max, min, total);
            System.out.println("condition=" + condition);

            UiData data = UiData.buildFromInvestments(sInvestments, condition);
            Ui ui = new Ui(data);
            ui.showChart();
        }

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

//        System.out.println("Main.main " + invests.size());

        int needCount = S_TOTAL;
        List<Invest> investList = new ArrayList<>();
        int startIndex = invests.size() - needCount;
        startIndex = startIndex < 0 ? 0 : startIndex;
        for (; startIndex < invests.size(); ++startIndex) {
            investList.add(invests.get(startIndex));
        }


        computeBenifit(investList);
//        System.out.println("Main.main " + investList.size());
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
            invest.setCost(invest.getTotalMoney() / invest.getTotalCount());
            invest.setProfitRatio(invest.getMarketMoney() / invest.getTotalMoney() - 1);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("base[" + base + "],ratio[" + r + "],max[" + max + "],min[" + min + "]").append("\n");
        String title = "编号\t日期\t指数\t净值\t钱(当期)\t份额(当期)\t钱(总)\t份额(总)\t成本(总)\t市值\t收益率";
//        System.out.println(title);
        stringBuilder.append(title).append("\n");
        for (int i = 0; i < invests.size(); i++) {
            Invest invest = invests.get(i);
            String s = String.format("%d\t%s\t%.2f\t%.4f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f%%\t",
                    i + 1,
                    invest.getFund().getDateString(),
                    invest.getZhishu().getValue(),
                    invest.getFund().getPrice(),
                    invest.getCurrentMoney(),
                    invest.getCurrentCount(),
                    invest.getTotalMoney(),
                    invest.getTotalCount(),
                    invest.getMarketMoney(),
                    invest.getCost(),
                    invest.getProfitRatio() * 100);

//            System.out.println(s);
            stringBuilder.append(s).append("\n");
        }
//        System.out.println();

        FileUtil.writeFile("fund/" + invests.get(0).getFund().getCode() + "_" + invests.size() + "_" + base + "_" + r + "_" + max + "_" + min + ".txt", stringBuilder.toString());
        Invest.Condition condition = new Invest.Condition(invests.get(0).getFund().getCode(), base, r, max, min, invests.size());
        sInvestMap.put(condition, invests);
        sInvestments.add(new Investment(invests, condition));
    }

    private static boolean canBuy(Zhishu zhishu, Fund fund) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String m = simpleDateFormat.format(zhishu.getDate());
        boolean contains = sMonth.contains(m);
        sMonth.add(m);
        return !contains;
    }

}
