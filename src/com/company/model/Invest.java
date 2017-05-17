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
    private double mCost;

    private double mProfitRatio;

    private Condition mCondition;

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

    public double getCost() {
        return mCost;
    }

    public void setCost(double cost) {
        mCost = cost;
    }

    public double getProfitRatio() {
        return mProfitRatio;
    }

    public void setProfitRatio(double profitRatio) {
        mProfitRatio = profitRatio;
    }

    @Override
    public String toString() {
        return "Invest{" +
                "fund=" + fund +
                ", zhishu=" + zhishu +
                ", ratio=" + ratio +
                ", totalCount=" + totalCount +
                ", currentCount=" + currentCount +
                ", currentMoney=" + currentMoney +
                ", totalMoney=" + totalMoney +
                ", marketMoney=" + marketMoney +
                ", mCost=" + mCost +
                ", mProfitRatio=" + mProfitRatio +
                ", mCondition=" + mCondition +
                '}';
    }

    public Condition getCondition() {
        return mCondition;
    }

    public void setCondition(Condition condition) {
        mCondition = condition;
    }

    public static class Condition {
        private String mCode;
        private int base = 3300;
        private float r = 0.1f;
        private float max = 2f;
        private float min = 0.5f;
        private int total = 12;

        public Condition(String code, int base, float r, float max, float min, int total) {
            mCode = code;
            this.base = base;
            this.r = r;
            this.max = max;
            this.min = min;
            this.total = total;
        }

        public Condition() {
            this("", -1, -1f, -1f, -1f, -1);
        }

        public String getCode() {
            return mCode;
        }

        public int getBase() {
            return base;
        }

        public float getR() {
            return r;
        }

        public float getMax() {
            return max;
        }

        public float getMin() {
            return min;
        }

        public int getTotal() {
            return total;
        }

        @Override
        public String toString() {
            return mCode + "_" + base + "_" + r + "_" + max + "_" + min + "_" + total;
        }
    }
}
