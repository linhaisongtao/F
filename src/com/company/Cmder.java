package com.company;

import com.company.model.Invest;
import com.company.model.Investment;
import com.company.ui.Ui;
import com.company.ui.UiData;

import java.io.BufferedReader;
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Cmder please input ratio\n");
        String[] rs = bufferedReader.readLine().split(" ");
        RS = new float[rs.length];
        for (int i = 0; i < rs.length; i++) {
            RS[i] = Float.valueOf(rs[i]);
        }

        while (true) {
            System.out.println("Cmder please input [code base max min total]\n");
            new Cmder().execute(bufferedReader.readLine());
        }
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
