package com.iccaps.forex.app;

/**
 * Created by Admin on 8/28/2017.
 */

public class Api {
    public static final String KEY_NON_FIELD_ERRORS = "non_field_errors";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_DETAIL = "data";
    public static final String KEY_USER_ROLE = "userrole";
    public static final String KEY_ROLE = "role";
    public static final String KEY_MOBILE = "mobile_number";
    public static final String KEY_PHONE_NUMBER = "mobile";
    public static final String KEY_OTP = "otp";
    public static final String KEY_CLIENT = "client";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_USERID = "id";
    public static final String KEY_ID = "id";
    public static final String KEY_PAYMENT_METHOD = "payment_method";
    public static final String KEY_PAYMENT_PLACE = "payment_place";
    public static final String KEY_NAME = "name";
    public static final String KEY_BRANCH = "branch";
    public static final String KEY_STATE = "currency";
    public static final String KEY_PURPOSE = "purpose";
    public static final String KEY_TRAVEL = "travellers_count";
    public static final String KEY_TRAVEL_COUNT = "travellers";
    public static final String KEY_PRODUCT = "product";
    public static final String KEY_CASH = "cash";
    public static final String KEY_CARD = "card";
    public static final String KEY_CHEQUE = "cheque";
    public static final String KEY_ADDRESS_DELIVERY = "delivery_address";
    public static final String KEY_US = "value";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone1";
    public static final String KEY_FXAMOUNT = "foreign_amount";
    public static final String KEY_INRAMOUNT = "inr_amount";
    public static final String KEY_DATE = "travel_date";
    public static final String KEY_PASSPORT = "passport_no";
    public static final String KEY_AADHAR = "aadhaar_no";
    public static final String KEY_FULL_INR = "full_amount";
    public static final String KEY_USERPROFILE = "userprofile";
    public static final String KEY_CURRENCY_VALUE = "current_rate";
    public static final String KEY_ORDER_ID = "order_id";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_COLOR = "color";
    public static final String KEY_THEME = "theme";
    public static final String KEY_PREFILL = "prefill";
    public static final String KEY_RAZORPAY_ORDER_ID = "razorpay_order_id";
    public static final String KEY_RAZORPAY_PAYMENT_ID = "razorpay_payment_id";
    public static final String KEY_RAZORPAY_SIGNATURE = "razorpay_signature";


    /* Api Urls*/

    public static final String URL = "http://139.59.58.93/";
    public static final String REGISTER_URL = URL + "django_auth/register/";
    public static final String FORGOT_PASS = URL + "django_auth/forgot-password/";
    public static final String LIST_SPINNERS_LIST = URL + "myapp/list/details/";
    public static final String LIST_SPINNERS_LIST1 = URL + "myapp/list/details/";
    public static final String ACTIVATE_URL = URL + "django_auth/activate/account/";
    public static final String LOGIN_URL = URL + "django_auth/login/user/";
    public static final String RESET_PASS = URL + "django_auth/reset-password/";
    public static final String WELCOME_MSG = URL + "myapp/send/new/customer/";
    public static final String LOGOUT_URL = URL + "django_auth/logout/";
    public static final String LIST_CURRENCY = URL + "myapp/list/currency/";
    public static final String LIST_CARD_CURRENCY = URL + "myapp/list/currency/product/";
    public static final String LIST_CARD_CURRENCY_SELL = URL + "myapp/list/currency/product/sell/";
    public static final String LIST_ADDRESS_PHONE = URL + "myapp/list/branch/";
    public static final String ADD_BUY_FOREX = URL + "myapp/create/forex/";
    public static final String LIST_CURRENCY_SELL = URL + "myapp/list/details/sell/";
    public static final String ADD_SELL_FOREX = URL + "myapp/create/forex/sell/";
    public static final String MONEY_TRANSFER_ACTIVITY = URL + "myapp/create/money/transfer/";
    public static final String RELOAD_CARD_ACTIVITY = URL + "myapp/create/reload/forex/";
    public static final String PAYMENT_GATEWAY = URL + "myapp/create/forex/payment/";
    public static final String PAYMENT_SUCCESS = URL + "myapp/forex/payment/success/";


}
