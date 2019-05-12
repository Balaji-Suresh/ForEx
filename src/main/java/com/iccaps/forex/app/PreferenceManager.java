package com.iccaps.forex.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.TextView;

/**
 * Created by Bobby on 18-02-2017
 */

public class PreferenceManager {

    private static final String PREF_USER = "user";
    private static final String PREF_LOCATION = "location";
    private static final String PREF_EXTRAS = "extras";
    private static final String PURPOSE_ID = "purpose_id";
    private static final String BRANCH_ID = "branch_id";
    private static final String PRODUCT_ID = "product";
    private static final String TRAVELLERS_ID = "travellers_id";
    private static final String CURRENCY_ID = "currency_id";
    private static final String CURRENCY_VALUE = "currency_value";
    private static final String NAME = "name";
    private static final String TOKEN = "token";
    private static final String PHONE = "phone";
    private static final String INR_AMOUNT = "inr_amount";
    private static final String FOREIGN_AMOUNT = "fx_amount";
    private static final String ABOUT_US = "email";
    private static final String BOOKING_OPTIONS = "address";
    private static final String DATE = "date";

    private static final String FCM_TOKEN = "device_token";
    private static final String TOKEN_UPLOADED = "token_uploaded";
    private SharedPreferences mPreferencesUser, mPreferencesLocation, mPreferencesExtras;

    public PreferenceManager(Context context) {
        mPreferencesUser = context.getSharedPreferences(PREF_USER, Context.MODE_PRIVATE);
        mPreferencesLocation = context.getSharedPreferences(PREF_LOCATION, Context.MODE_PRIVATE);
        mPreferencesExtras = context.getSharedPreferences(PREF_EXTRAS, Context.MODE_PRIVATE);
    }

    public String getPurposeId() {
        return mPreferencesUser.getString(encode(PURPOSE_ID), null);
    }

    public void setPurposeId(String purposeid) {
        mPreferencesUser.edit().putString(encode(PURPOSE_ID), purposeid).apply();
    }
    public String getBranchId() {
        return mPreferencesUser.getString(encode(BRANCH_ID), null);
    }

    public void setBranchId(String branchid) {
        mPreferencesUser.edit().putString(encode(BRANCH_ID), branchid).apply();
    }
    public String getTravellersId() {
        return mPreferencesUser.getString(encode(TRAVELLERS_ID), null);
    }

    public void setTravellersId(String travellersid) {
        mPreferencesUser.edit().putString(encode(TRAVELLERS_ID), travellersid).apply();
    }
    public String getCurrencyId() {

        return mPreferencesUser.getString(encode(CURRENCY_ID), null);
    }

    public void setCurrencyId(String currencyid) {
        mPreferencesUser.edit().putString(encode(CURRENCY_ID), currencyid).apply();
    }
    public String getProductId() {

        return mPreferencesUser.getString(encode(PRODUCT_ID), null);
    }

    public void setProductId(String currencyid) {
        mPreferencesUser.edit().putString(encode(PRODUCT_ID), currencyid).apply();
    }




    public String getName() {
        return mPreferencesUser.getString(encode(NAME), null);
    }

    public void setName(String name) {
        mPreferencesUser.edit().putString(encode(NAME), name).apply();
    }


    public String getToken() {
        return mPreferencesUser.getString(encode(TOKEN), null);
    }

    public void setToken(String token) {
        mPreferencesUser.edit().putString(encode(TOKEN), token).apply();
    }

    public String getPhone() {
        return mPreferencesUser.getString(encode(PHONE), null);
    }

    public void setPhone(String phone) {
        mPreferencesUser.edit().putString(encode(PHONE), phone).apply();
    }

    public String getINRCurrency() {
        return mPreferencesUser.getString(encode(INR_AMOUNT),null);
    }

    public void setINRCurrency(String inrcurrency) {
        mPreferencesUser.edit().putString(encode(INR_AMOUNT), inrcurrency).apply();
    }

    public String getForeignCurrency() {
        return mPreferencesUser.getString(encode(FOREIGN_AMOUNT),null);
    }

    public void setForeignCurrency(String fxcurrency) {
        mPreferencesUser.edit().putString(encode(FOREIGN_AMOUNT), fxcurrency).apply();
    }

    public String getEmail() {
        return mPreferencesExtras.getString(encode(ABOUT_US), null);
    }

    public void setEmail(String aboutUs) {
        mPreferencesExtras.edit().putString(encode(ABOUT_US), aboutUs).apply();
    }

    public String getAddress() {
        return mPreferencesExtras.getString(encode(BOOKING_OPTIONS), null);
    }

    public void setAddress(String bookingOptions) {
        mPreferencesExtras.edit().putString(encode(BOOKING_OPTIONS), bookingOptions).apply();
    }

    public String getDate() {
        return mPreferencesExtras.getString(encode(DATE), null);
    }

    public void setDate(String booking) {
        mPreferencesExtras.edit().putString(encode(DATE), booking).apply();
    }
    public String getCurrencyValue() {
        return mPreferencesExtras.getString(encode(CURRENCY_VALUE), null);
    }

    public void setCurrencyValue(String currencyValue) {
        mPreferencesExtras.edit().putString(encode(CURRENCY_VALUE), currencyValue).apply();
    }


    public boolean isTokenUploaded() {
        return mPreferencesExtras.getBoolean(encode(TOKEN_UPLOADED), false);
    }

    public void setTokenUploaded(boolean tokenUploaded) {
        mPreferencesExtras.edit().putBoolean(encode(TOKEN_UPLOADED), tokenUploaded).apply();
    }

    public String getFcmToken() {
        return mPreferencesExtras.getString(encode(FCM_TOKEN), null);
    }

    void setFcmToken(String fcmToken) {
        mPreferencesExtras.edit().putString(encode(FCM_TOKEN), fcmToken).apply();
    }

    public void clearUser() {
        setTokenUploaded(false);
        mPreferencesUser.edit().clear().apply();
    }
    public void clearinr(){
        setINRCurrency(String.valueOf(false));
        mPreferencesUser.edit().clear().apply();
    }

    private String encode(String data) {
        return Base64.encodeToString(data.getBytes(), Base64.NO_WRAP);
    }

    public void setDate(TextView editDate) {
    }
}