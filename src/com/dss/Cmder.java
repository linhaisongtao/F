package com.dss;

import com.dss.model.Invest;
import com.dss.model.Investment;
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
            System.out.println("Cmder please input [code base max min total]\n");
            new Cmder().execute(filterInfo(bufferedReader.readLine()));
        }
    }

    private static String filterInfo(String cmd) {
        Log.ENABLE_LOG = cmd.contains("-i");
        return cmd.replaceAll("-i", "");
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

        List<Investment> investments = new ArrayList<>();
        for (float r : RS) {
            investments.add(compute(code, base, r, max, min, total));
        }

        Invest.Condition condition = new Invest.Condition();
        UiData data = UiData.buildFromInvestments(investments, condition, code + "_" + base + "_" + max + "_" + min + "_" + total);
        Ui ui = new Ui(data);
        ui.showChart();
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
