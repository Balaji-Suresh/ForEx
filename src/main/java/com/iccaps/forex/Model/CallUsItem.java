package com.iccaps.forex.Model;

/**
 * Created by Admin on 10/13/2017.
 */

public class CallUsItem {

    private String mname;
    private String mAddress;
    private String mPhone;


    public CallUsItem(String Name, String Date, String Status, String Desc, String budget, String source, String type, String Phone) {
        mname = Name;
        mAddress = Date;
        mPhone = Phone;

    }

    public String getmName() {


        return mname;
    }

    public void setmName(String mname) {
        this.mname = mname;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mdate) {
        this.mAddress = mdate;
    }


    public String getPhone() {
        return mPhone;
    }



}
