package com.iccaps.forex.Model;

/**
 * Created by Admin on 8/29/2017.
 */

public class PurposeItem {
    private int mid;
    private String mname;

    public PurposeItem(int id, String name) {
        mid = id;
        mname = name;
    }

    public PurposeItem(String selectPurpose) {
        mname = selectPurpose;
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



