package com.dss;

import com.dss.model.Invest;
import com.dss.model.InvestModel;
import com.dss.model.Investment;
import com.dss.model.Zhishu;
import com.dss.ui.Ui;
import com.dss.ui.UiData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2017/5/17.
 */
public class Cmder {
    private static boolean sShowGraph = true;
    private static float[] RS;

    public static void main(String[] args) throws IOException {
        System.out.println(new File("").getAbsolutePath());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Cmder please input ratio\n");
        String[] rs = filterInfo(bufferedReader.readLine()).split(" ");
        RS = new float[rs.length];
        for (int i = 0; i < rs.length; i++) {
            String r = rs[i];
            if (r != null && r.length() > 0) {
                RS[i] = Float.valueOf(r);
            }
        }

        while (true) {
            System.out.println("Cmder please input [code base max min total (ratio in year)]\n");
            new Cmder().execute(filterInfo(bufferedReader.readLine()));
        }
    }

    private static String filterInfo(String cmd) {
        Log.ENABLE_LOG = cmd.contains("-i");
        sShowGraph = !cmd.contains("-no-graph");
        Config.sShowPrice = cmd.contains("-price");
        Config.sShowZhishu = cmd.contains("-zhishu");
        return cmd.replaceAll("-i", "").replace("-no-graph", "").replace("-price", "").replace("-zhishu", "");
    }

    /**
     * @param cmd code base max min total
     */
    public void execute(String cmd) {

        String[] cmds = cmd.split(" ");
        String code = cmds[0];
        int base = Integer.valueOf(cmds[1]);
        float max = Float.valueOf(cmds[2]);
        float min = Float.valueOf(cmds[3]);
        int total = Integer.valueOf(cmds[4]);
        try {
            Config.sRatioInYear = Float.valueOf(cmds[5]);
            Config.sRatioInYear2 = Float.valueOf(cmds[6]);
        } catch (Exception e) {
//            e.printStackTrace();
            Config.sRatioInYear = -1f;
            Config.sRatioInYear2 = -1f;
        }

        List<Investment> investments = new ArrayList<>();
        for (float r : RS) {
            investments.add(compute(code, base, r, max, min, total));
        }

        System.out.println("");
        for (float r : RS) {
            System.out.println("ratio=" + r);
            System.out.println("指数\t金额");
            for (int zhishu = 6000; zhishu >= 2000; zhishu -= 100) {
                Zhishu z = new Zhishu();
                z.setValue(zhishu);
                System.out.println(zhishu + "\t" + 1000 * InvestModel.invest(z, base, r, max, min));
            }
            System.out.println();
        }

        if (sShowGraph) {
            Invest.Condition condition = new Invest.Condition();
            String fundName = null;
            try {
                fundName = investments.get(0).getInvests().get(0).getFund().getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
            UiData data = UiData.buildFromInvestments(investments, condition, fundName + "[" + code + "]_" + base + "_" + max + "_" + min + "_" + total);
            Ui ui = new Ui(data);
            ui.showChart();
        }
    }

    private Investment compute(String code, int base, float r, float max, float min, int total) {
        Main.FUND_CODE = code;
        Main.base = base;
        Main.r = r;
        Main.max = max;
        Main.min = min;
        Main.S_TOTAL = total;
        Investment investment = Main.run();
        return investment;
    }
}
