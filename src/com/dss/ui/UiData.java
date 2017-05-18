package com.dss.ui;

import com.dss.Config;
import com.dss.model.Invest;
import com.dss.model.Investment;
import com.dss.util.RUtil;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daisongsong on 2017/5/17.
 */
public class UiData {
    private List<TimeSeries> mTimeSeries = new ArrayList<>();

    private String mChartTitle;

    public static UiData buildFromInvestments(List<Investment> investments, Invest.Condition condition, String chartTitle) {
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
        TimeSeries priceSeries = new TimeSeries("price", Day.class);
        TimeSeries zhishuSeries = new TimeSeries("指数", Day.class);
        TimeSeries ratioInYear = new TimeSeries("RatioInYear_" + Config.sRatioInYear, Day.class);
        TimeSeries ratioInYear2 = new TimeSeries("RatioInYear_" + Config.sRatioInYear2, Day.class);
        for (int i = 0; i < list.size(); i++) {
            Investment investment = list.get(i);

            TimeSeries series = new TimeSeries(investment.getCondition().toString(), Day.class);
            TimeSeries seriesProfit = new TimeSeries(investment.getCondition().toString() + "_profit", Day.class);
            List<Invest> invests = investment.getInvests();
            for (int j = 0; j < invests.size(); j++) {
                Invest invest = invests.get(j);

                series.add(new Day(invest.getFund().getDate()), invest.getCost());
                seriesProfit.add(new Day(invest.getFund().getDate()), invest.getProfitRatio());
                if (i == 0) {
                    priceSeries.add(new Day(invest.getFund().getDate()), invest.getFund().getPrice());
                    zhishuSeries.add(new Day(invest.getFund().getDate()), invest.getZhishu().getValue() / 1000);
                    if (Config.sRatioInYear > 0) {
                        ratioInYear.add(new Day(invest.getFund().getDate()), RUtil.r(Config.sRatioInYear, j + 1) - 1);
                    }
                    if (Config.sRatioInYear2 > 0) {
                        ratioInYear2.add(new Day(invest.getFund().getDate()), RUtil.r(Config.sRatioInYear2, j + 1) - 1);
                    }
                }
            }

            if (Config.sShowPrice) {
                data.addTimeSeries(series);
            }
            data.addTimeSeries(seriesProfit);

        }
        if (Config.sShowPrice) {
            data.addTimeSeries(priceSeries);
        }
        if (Config.sShowZhishu) {
            data.addTimeSeries(zhishuSeries);
        }
        if (Config.sRatioInYear > 0) {
            data.addTimeSeries(ratioInYear);
        }
        if (Config.sRatioInYear2 > 0) {
            data.addTimeSeries(ratioInYear2);
        }

        data.setChartTitle(chartTitle);
        return data;
    }

    public void addTimeSeries(TimeSeries series) {
        mTimeSeries.add(series);
    }

    public List<TimeSeries> getTimeSeries() {
        return mTimeSeries;
    }

    public String getChartTitle() {
        return mChartTitle;
    }

    public void setChartTitle(String chartTitle) {
        mChartTitle = chartTitle;
    }
}
