package com.iccaps.forex;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.Api;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.PreferenceManager;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.attr.id;
import static com.iccaps.forex.app.Activity.launchClearStack;
import static com.iccaps.forex.app.Api.ADD_BUY_FOREX;
import static com.iccaps.forex.app.Api.KEY_ADDRESS;
import static com.iccaps.forex.app.Api.KEY_ADDRESS_DELIVERY;
import static com.iccaps.forex.app.Api.KEY_AMOUNT;
import static com.iccaps.forex.app.Api.KEY_BRANCH;
import static com.iccaps.forex.app.Api.KEY_COLOR;
import static com.iccaps.forex.app.Api.KEY_CURRENCY;
import static com.iccaps.forex.app.Api.KEY_CURRENCY_VALUE;
import static com.iccaps.forex.app.Api.KEY_DATE;
import static com.iccaps.forex.app.Api.KEY_DESCRIPTION;
import static com.iccaps.forex.app.Api.KEY_EMAIL;
import static com.iccaps.forex.app.Api.KEY_FULL_INR;
import static com.iccaps.forex.app.Api.KEY_FXAMOUNT;
import static com.iccaps.forex.app.Api.KEY_ID;
import static com.iccaps.forex.app.Api.KEY_INRAMOUNT;
import static com.iccaps.forex.app.Api.KEY_NAME;
import static com.iccaps.forex.app.Api.KEY_ORDER_ID;
import static com.iccaps.forex.app.Api.KEY_PAYMENT_METHOD;
import static com.iccaps.forex.app.Api.KEY_PAYMENT_PLACE;
import static com.iccaps.forex.app.Api.KEY_PHONE;
import static com.iccaps.forex.app.Api.KEY_PREFILL;
import static com.iccaps.forex.app.Api.KEY_PRODUCT;
import static com.iccaps.forex.app.Api.KEY_PURPOSE;
import static com.iccaps.forex.app.Api.KEY_RAZORPAY_ORDER_ID;
import static com.iccaps.forex.app.Api.KEY_RAZORPAY_PAYMENT_ID;
import static com.iccaps.forex.app.Api.KEY_RAZORPAY_SIGNATURE;
import static com.iccaps.forex.app.Api.KEY_STATE;
import static com.iccaps.forex.app.Api.KEY_THEME;
import static com.iccaps.forex.app.Api.KEY_TRAVEL_COUNT;
import static com.iccaps.forex.app.Api.KEY_USERNAME;
import static com.iccaps.forex.app.Api.PAYMENT_GATEWAY;
import static com.iccaps.forex.app.Api.PAYMENT_SUCCESS;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener,PaymentResultWithDataListener {
    private Context mContext;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private TextInputLayout mLayoutName, mLayoutPhone, mLayoutEmail, mLayoutAddress, mLayoutCity,
            mLayoutState, mLayoutPinCode, mLayoutCountry;
    private TextInputEditText mEditTextName, mEditTextPhone, mEditTextEmail, mEditTextAddress,
            mEditTextCity, mEditTextState, mEditTextPinCode, mEditTextCountry, mEditTextLandmark;
    private LinearLayout mLayoutCheckout, mLayoutPayNow, mLayoutOops;
    private RadioGroup mGroupPaymentType,mGroupDeliveryType;
    private TextView mTextViewTotal;
    private TextView mInputDate;
    private Button mButtonPayNow, mButtonRetry;
    private PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;
    private RadioButton mFullPayment, mAdvancePayment, mHome, mBranch;
    private String dateh;
    private String number, name, Address, Inramount, FxAmount,INRAmount, currency, branch, travellers, purpose,product,Liverate;
    Double InrAmount,FXAmount,LiveRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initObjects();
        initCallbacks();
        initToolbar();

    }

    @Override
    public void onClick(View v) {
        if (v == mButtonPayNow) {
            processPayNow();
        }else if (v == mInputDate) {
            promptDatePickerDialog(mInputDate);
        }
    }




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void initObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLayoutName = (TextInputLayout) findViewById(R.id.name);
        mLayoutPhone = (TextInputLayout) findViewById(R.id.phone);
        mLayoutEmail = (TextInputLayout) findViewById(R.id.email);
        mLayoutAddress = (TextInputLayout) findViewById(R.id.address);
        mEditTextName = (TextInputEditText) findViewById(R.id.input_name);
        mEditTextPhone = (TextInputEditText) findViewById(R.id.input_phone);
        mEditTextEmail = (TextInputEditText) findViewById(R.id.input_email);
        mEditTextAddress = (TextInputEditText) findViewById(R.id.input_address);
        mLayoutCheckout = (LinearLayout) findViewById(R.id.checkout);
        mLayoutPayNow = (LinearLayout) findViewById(R.id.pay_now);
        /*mInputDate = (TextView) findViewById(R.id.input_date);
        mInputDate.setText(getCurrentDate());
        */mGroupPaymentType = (RadioGroup) findViewById(R.id.grp_payment_type);
        mTextViewTotal = (TextView) findViewById(R.id.txt_total);
        mButtonPayNow = (Button) findViewById(R.id.btn_pay_now);
        mContext = this;
        mHome = (RadioButton) findViewById(R.id.radio_card);
        mBranch = (RadioButton) findViewById(R.id.radio_cod);
        mPreferenceManager = new PreferenceManager(mContext);
        mEditTextName.setText(mPreferenceManager.getName());
        mEditTextPhone.setText(mPreferenceManager.getPhone());
        FXAmount = Double.valueOf(mPreferenceManager.getForeignCurrency());
        InrAmount = Double.valueOf(mPreferenceManager.getINRCurrency());
        /*LiveRate = Double.valueOf(mPreferenceManager.getCurrencyValue());*/
        currency = mPreferenceManager.getCurrencyId();
        branch = mPreferenceManager.getBranchId();
        travellers = mPreferenceManager.getTravellersId();
        purpose =mPreferenceManager.getPurposeId();
        product = mPreferenceManager.getProductId();
        mEditTextEmail.setText(String.valueOf(mPreferenceManager.getEmail()));
        mProgressDialog = new ProgressDialog(mContext);
        mGroupDeliveryType = (RadioGroup)findViewById(R.id.grp_delivery_type);
        mGroupDeliveryType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.radio_home){
                    mBranch.setVisibility(View.GONE);
                } else if(checkedId == R.id.radio_branch){
                    mHome.setVisibility(View.VISIBLE);
                    mBranch.setVisibility(View.VISIBLE);
                }
            }
        });
        mGroupPaymentType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radio_cod){
                    Double v1 = (InrAmount*10/100);
                    mTextViewTotal.setText(String.valueOf(v1));
                   /* if(InrAmount <= 25000){
                        Double v1 = (250 + InrAmount);
                        Double v2 = v1*10/100;
                        mTextViewTotal.setText(String.valueOf(v2));
                    } else if(InrAmount > 25000 && InrAmount <= 100000){
                        Double v1 = (InrAmount*1/100);
                        Double v2 = v1 * 18/100;
                        mTextViewTotal.setText(String.valueOf(v2));
                    } else if(InrAmount > 100000 && InrAmount <= 1000000){
                        Double v1 = (1000+((InrAmount-100000)*(0.5/100)))*18/100;
                        mTextViewTotal.setText(String.valueOf(v1));
                    }
*/
                } else if (checkedId == R.id.radio_card) {
                     if(InrAmount <= 25000){
                         Double v1 = (250 + InrAmount);
                         mTextViewTotal.setText(String.valueOf(v1));
                     } else if(InrAmount > 25000 && InrAmount <= 100000){
                         Double v1 = (InrAmount*1/100);
                         Double v2 = v1 * 18/100;
                         Double v3 = InrAmount + v2;
                         mTextViewTotal.setText(String.valueOf(v3));
                     } else if(InrAmount > 100000 && InrAmount <= 1000000){
                         Double v1 = (1000+((InrAmount-100000)*(0.5/100)))*18/100;
                         Double v2 = InrAmount+v1;
                         mTextViewTotal.setText(String.valueOf(v2));
                     }
              }
            }
        });


    }

    private void initCallbacks() {
        /*mButtonRetry.setOnClickListener(this);*/
        mButtonPayNow.setOnClickListener(this);
    }

    private void initToolbar() {
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("CheckOut");
            mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
            mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }




    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        String orderId = paymentData.getOrderId();
        String paymentId = paymentData.getPaymentId();
        String signature = paymentData.getSignature();
        showProgressDialog("Processing payment..");
        paymentSuccess(orderId, paymentId, signature);
    }


    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        ToastBuilder.build(mContext, s);
    }
    private void processPayNow() {
        Address = mEditTextAddress.getText().toString().trim();
        FxAmount = mPreferenceManager.getForeignCurrency();
        Inramount = mTextViewTotal.getText().toString().trim();
        INRAmount = mPreferenceManager.getINRCurrency();
        Liverate = mPreferenceManager.getCurrencyValue();
        int paymentMode = getPaymentMode();
        int deliveryMode = getDeliveryMode();
        if (validateInput(Address)) {
            showProgressDialog("Processing..");
            payNow(Address,FxAmount,Inramount,currency,branch,travellers,purpose,product,
                    paymentMode,deliveryMode,INRAmount,Liverate);
        }
    }


    private void promptDatePickerDialog(TextView editDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        openDateDialog(editDate, year, month, dayOfMonth);
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateh = getDate(year, month, dayOfMonth);
        return getDate(year, month + 1, dayOfMonth);
    }


    private String getDate(int year, int month, int dayOfMonth) {
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (dayOfMonth < 10 ? "0"
                + dayOfMonth : dayOfMonth);
    }

    private void openDateDialog(final TextView editDate, int year, int month,
                                int dayOfMonth) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateh = getDate(year, month, dayOfMonth);
                        editDate.setText(getDate(year, month + 1, dayOfMonth));
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private int getPaymentMode() {
        int id = mGroupPaymentType.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radio_cod:
                return 0;
            case R.id.radio_card:
                return 1;
            default:
                return 2;
        }
    }
    private int getDeliveryMode(){
        int id = mGroupDeliveryType.getCheckedRadioButtonId();
        switch (id){
            case R.id.radio_home:
                return 0;
            case R.id.radio_branch:
                return 1;
            default:
                return 2;
        }

    }
    private boolean validateInput(String address
    ) {
         if (address.isEmpty()) {
            mLayoutAddress.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty), "Address"));
            return false;
        }
        return true;
    }
    private void payNow(String Address, String FxAmount, String InrAmount, String currency,String branch,String travellers,String purpose,String product,
                        int paymentMode,int deliveryMode,String INRAmount,String LiveRate) {
        JSONObject jsonObject = new JSONObject();
        try {
            String dateT = mPreferenceManager.getDate();
            jsonObject.put(KEY_ADDRESS_DELIVERY, Address);
            jsonObject.put(KEY_FXAMOUNT, FxAmount);
            jsonObject.put(KEY_STATE, currency);
            jsonObject.put(KEY_BRANCH,branch);
            jsonObject.put(KEY_PAYMENT_METHOD, paymentMode);
            jsonObject.put(KEY_PAYMENT_PLACE, deliveryMode);
            jsonObject.put(KEY_TRAVEL_COUNT,travellers);
            jsonObject.put(KEY_INRAMOUNT, InrAmount);
            jsonObject.put(KEY_FULL_INR,INRAmount);
            jsonObject.put(KEY_PURPOSE, purpose);
            jsonObject.put(KEY_PRODUCT,product);
            jsonObject.put(KEY_DATE,dateT);
            jsonObject.put(KEY_CURRENCY_VALUE,LiveRate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ADD_BUY_FOREX, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showProgressDialog("Processing..");
                try {
                    int id = response.getInt(KEY_ID);
                    paymentGateway(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*dialog();*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyErrorHandler.handle(mContext, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token " + mPreferenceManager.getToken());
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "pay_now");
    }

    private void paymentGateway(int id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(PAYMENT_GATEWAY + id + "/",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                handlePaymentGateway(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyErrorHandler.handle(mContext, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token " + mPreferenceManager.getToken());
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "payment_gateway");
    }

    private void handlePaymentGateway(JSONObject response) {
        try {
            int amount = response.getInt(KEY_AMOUNT);
            String orderId = response.getString(KEY_ORDER_ID);
            processPayment(amount, orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processPayment(int amount, String orderId) {
        Checkout checkout = new Checkout();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_NAME, getString(R.string.app_name));
            jsonObject.put(KEY_DESCRIPTION, "Payment Gateway");
            jsonObject.put(KEY_CURRENCY, "INR");
            jsonObject.put(KEY_AMOUNT, amount);

            JSONObject themeObject = new JSONObject();
            themeObject.put(KEY_COLOR, "#0156a9");
            jsonObject.put(KEY_THEME, themeObject);

            JSONObject prefillObject = new JSONObject();
            prefillObject.put(KEY_NAME, mPreferenceManager.getName());
            prefillObject.put(KEY_USERNAME, mPreferenceManager.getPhone());
            prefillObject.put(KEY_EMAIL, mPreferenceManager.getEmail());
            jsonObject.put(KEY_PREFILL, prefillObject);

            jsonObject.put(KEY_ORDER_ID, orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        checkout.open(this, jsonObject);
    }
    private void paymentSuccess(String orderId, String paymentId, String signature) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_RAZORPAY_ORDER_ID, orderId);
            jsonObject.put(KEY_RAZORPAY_PAYMENT_ID, paymentId);
            jsonObject.put(KEY_RAZORPAY_SIGNATURE, signature);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                PAYMENT_SUCCESS,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                ToastBuilder.build(mContext, "Order placed successfully");
                logoutUser();
                launchClearStack(mContext,MainActivity.class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyErrorHandler.handle(mContext, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token " + mPreferenceManager.getToken());
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "payment_success");
    }


    private void dialog() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.layout_success, null);
        Button mHome = (Button)mView.findViewById(R.id.btn_home);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.launch(mContext,MainActivity.class);
            }
        });
    }
    private void logoutUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.LOGOUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleLogoutResponse();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyErrorHandler.handle(mContext, error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token " + mPreferenceManager.getToken());
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, "logout");
    }

    private void handleLogoutResponse() {
        mPreferenceManager.clearUser();
    }

    private void showProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
