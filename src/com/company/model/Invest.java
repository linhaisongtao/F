package com.company.model;

/**
 * Created by daisongsong on 2017/5/16.
 */
public class Invest {
    private Fund fund;
    private Zhishu zhishu;
    private float ratio;

    private double totalCount;
    private double currentCount;

    private double currentMoney;
    private double totalMoney;

    private double marketMoney;

    public double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(double totalCount) {
        this.totalCount = totalCount;
    }

    public double getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(double currentCount) {
        this.currentCount = currentCount;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public Zhishu getZhishu() {
        return zhishu;
    }

    public void setZhishu(Zhishu zhishu) {
        this.zhishu = zhishu;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getMarketMoney() {
        return marketMoney;
    }

    public void setMarketMoney(double marketMoney) {
        this.marketMoney = marketMoney;
    }

    @Override
    public String toString() {
        return "Invest{" +
                "fund=" + fund +
                ", zhishu=" + zhishu +
                ", ratio=" + ratio +
                '}';
    }
}
