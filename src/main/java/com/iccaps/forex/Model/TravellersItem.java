package com.iccaps.forex.Model;

/**
 * Created by Admin on 8/29/2017.
 */

public class TravellersItem {
    private int mid;
    private String mname;

    public TravellersItem(int id, String name) {
        mid = id;
        mname = name;
    }

    public TravellersItem(String selectTravel) {
        mname = selectTravel;
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

