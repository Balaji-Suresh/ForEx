package com.iccaps.forex.Model;

/**
 * Created by Admin on 8/31/2017.
 */

public class CurrencyItem {
    private int mid;
    private String mname;

    public CurrencyItem(int id, String name) {
        mid = id;
        mname = name;
    }

    public CurrencyItem(String selectCurrency) {
        mname = selectCurrency;
    }

    public int getid() {


        return mid;
    }

    public void setmid(int mid) {
        this.mid = mid;
    }

    public String getmname() {
        return mname;
    }

    public void setmname(String mname) {
        this.mname = mname;
    }

    @Override
    public String toString() {
        return mname;
    }
}

