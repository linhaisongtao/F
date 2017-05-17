package com.company.model;

import java.util.List;

/**
 * Created by daisongsong on 2017/5/17.
 */
public class Investment {
    private List<Invest> mInvests;
    private Invest.Condition mCondition;

    public Investment(List<Invest> invests, Invest.Condition condition) {
        mInvests = invests;
        mCondition = condition;
    }

    public List<Invest> getInvests() {
        return mInvests;
    }

    public void setInvests(List<Invest> invests) {
        mInvests = invests;
    }

    public Invest.Condition getCondition() {
        return mCondition;
    }

    public void setCondition(Invest.Condition condition) {
        mCondition = condition;
    }
}
