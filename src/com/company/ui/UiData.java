package com.company.ui;

import com.company.model.Invest;
import com.company.model.Investment;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2017/5/17.
 */
public class UiData {
    private List<TimeSeries> mTimeSeries = new ArrayList<>();

    public static UiData buildFromInvestments(List<Investment> investments, Invest.Condition condition) {
        List<Investment> list = new ArrayList<>();

        for (Investment investment : investments) {
            Invest.Condition c = investment.getCondition();
            if (condition.getCode() != null && condition.getCode().length() != 0) {
                if (!condition.getCode().equals(c.getCode())) {
                    continue;
                }
            }

            if (condition.getBase() >= 0) {
                if (condition.getBase() != c.getBase()) {
                    continue;
                }
            }

            if (condition.getR() >= 0) {
                if (condition.getR() != c.getR()) {
                    continue;
                }
            }

            if (condition.getMax() >= 0) {
                if (condition.getMax() != c.getMax()) {
                    continue;
                }
            }

            if (condition.getMin() >= 0) {
                if (condition.getMin() != c.getMin()) {
                    continue;
                }
            }

            if (condition.getTotal() >= 0) {
                if (condition.getTotal() != c.getTotal()) {
                    continue;
                }
            }
            list.add(investment);
        }

        UiData data = new UiData();
        for (Investment investment : list) {
            TimeSeries series = new TimeSeries(investment.getCondition().toString(), Day.class);
            TimeSeries seriesProfit = new TimeSeries(investment.getCondition().toString() + "_profit", Day.class);
            List<Invest> invests = investment.getInvests();
            for (Invest invest : invests) {
                series.add(new Day(invest.getFund().getDate()), invest.getCost());
                seriesProfit.add(new Day(invest.getFund().getDate()), invest.getProfitRatio());
            }

            data.addTimeSeries(series);
            data.addTimeSeries(seriesProfit);
        }

        return data;
    }

    public void addTimeSeries(TimeSeries series) {
        mTimeSeries.add(series);
    }

    public List<TimeSeries> getTimeSeries() {
        return mTimeSeries;
    }
}
