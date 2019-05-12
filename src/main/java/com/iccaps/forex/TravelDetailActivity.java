/*
package com.iccaps.forex;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Activity.launchClearStack;
import static com.iccaps.forex.app.Api.ADD_BUY_FOREX;
import static com.iccaps.forex.app.Api.KEY_ADDRESS_DELIVERY;
import static com.iccaps.forex.app.Api.KEY_BRANCH;
import static com.iccaps.forex.app.Api.KEY_DATE;
import static com.iccaps.forex.app.Api.KEY_FXAMOUNT;
import static com.iccaps.forex.app.Api.KEY_INRAMOUNT;
import static com.iccaps.forex.app.Api.KEY_NAME;
import static com.iccaps.forex.app.Api.KEY_PAYMENT_METHOD;
import static com.iccaps.forex.app.Api.KEY_PAYMENT_PLACE;
import static com.iccaps.forex.app.Api.KEY_PURPOSE;
import static com.iccaps.forex.app.Api.KEY_STATE;
import static com.iccaps.forex.app.Api.KEY_TRAVEL;
import static com.iccaps.forex.app.Api.KEY_TRAVEL_COUNT;

public class TravelDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Toolbar mToolbar;
    private TextInputLayout mLayoutName;
    private TextInputEditText mName;
    private EditText mInputAddress;
    private TextView mInputDateJourney;
    private Button mButtonPay;
    private ImageView mVerified;
    private Spinner mTravel, mState, mCity;
    private com.iccaps.forex.app.PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;
    private String dateh;
    private String number, name, Address, Inramount, FxAmount, currency, branch, travellers, purpose,Method,Place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
        initObjects();
        initCallbacks();
        setupToolbar();

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
        mContext = this;
        mLayoutName = (TextInputLayout)findViewById(R.id.pick_name);
        mName = (TextInputEditText) findViewById(R.id.input_pick_name);
        mInputAddress = (EditText) findViewById(R.id.input_description);
        mInputDateJourney = (TextView) findViewById(R.id.input_date_journey);
        mInputDateJourney.setText(getCurrentDate());
        mButtonPay = (Button) findViewById(R.id.btn_pay_now);
        mProgressDialog = new ProgressDialog(mContext);
        mPreferenceManager = new com.iccaps.forex.app.PreferenceManager(this);
        mName.setText(String.valueOf(mPreferenceManager.getName()));

    }

    private void initCallbacks() {
        mButtonPay.setOnClickListener(this);
        mInputDateJourney.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == mInputDateJourney) {
            promptDatePickerDialog(mInputDateJourney);
        } else if (v == mButtonPay) {
            processPay();
        }

    }


    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Travel Details");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
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

    private void processPay() {
        name = mName.getText().toString().trim();
        Address = mInputAddress.getText().toString().trim();
        FxAmount = mPreferenceManager.getForeignCurrency();
        Inramount = mPreferenceManager.getINRCurrency();
        currency = "1";
        branch = "2";
        travellers = "1";
        purpose = "1";
        Method = "0";
        Place = "0";
        if (validateInput(name, Address)) {
            promptBookingDialog();

        }
    }

    private Boolean validateInput(String name, String Address) {
        if (TextUtils.isEmpty(name)) {
            mName.requestFocus();
            mLayoutName.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Contact Name"));
            return false;
        } else if (TextUtils.isEmpty(Address)) {
            mInputAddress.requestFocus();
            mInputAddress.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Enter Your Address"));
            return false;


        }
        return true;
    }
    private void promptBookingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showProgressDialog("Booking...");
                getOrderRequest(name, Address, FxAmount, Inramount, currency,branch,travellers,purpose,Place,Method);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getOrderRequest(String name, String Address, String FxAmount, String InrAmount, String currency,String branch,String travellers,String purpose,String Place,String Method) {
        JSONObject jsonObject = new JSONObject();
        try {
            String dateT = dateh + "";
           */
/* jsonObject.put(KEY_NAME, name);*//*

            jsonObject.put(KEY_ADDRESS_DELIVERY, Address);
            mPreferenceManager.setAddress(Address);
            jsonObject.put(KEY_FXAMOUNT, FxAmount);
            jsonObject.put(KEY_STATE, currency);
            jsonObject.put(KEY_BRANCH,branch);
            jsonObject.put(KEY_PAYMENT_PLACE,Place);
            jsonObject.put(KEY_PAYMENT_METHOD,Method);
            jsonObject.put(KEY_TRAVEL_COUNT,travellers);
            jsonObject.put(KEY_INRAMOUNT, InrAmount);
            jsonObject.put(KEY_PURPOSE, purpose);
            jsonObject.put(KEY_DATE, dateT);


        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ADD_BUY_FOREX, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                handlePaymentResponse(response);
                launchClearStack(mContext, PaymentActivity.class);
            }
        }, new com.android.volley.Response.ErrorListener() {
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

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "Create_lead");
    }

    private void handlePaymentResponse(JSONObject response) {

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
*/
