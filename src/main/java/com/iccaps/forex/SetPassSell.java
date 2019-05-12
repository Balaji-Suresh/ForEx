package com.iccaps.forex;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.Api;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.Constant;
import com.iccaps.forex.app.PreferenceManager;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;
import com.iccaps.forex.helper.MacAddress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SetPassSell extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Toolbar mToolbar;
    private EditText mEditTextOtp, mEditTextPass;
    private TextView mTextViewResendOtp;
    private Button mButtonSetPass;
    private BroadcastReceiver mReceiverSms;
    private PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        initObjects();
        initCallbacks();
        setupToolbar();
        setupBroadcastReceiver();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiverSms, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiverSms);
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

    @Override
    public void onClick(View v) {
        if (v == mTextViewResendOtp) {
            forgotPass();
        } else if (v == mButtonSetPass) {
            processOtp();
        }
    }

    private void initObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEditTextOtp = (EditText) findViewById(R.id.input_otp);
        mEditTextPass = (EditText) findViewById(R.id.input_pass);
        mTextViewResendOtp = (TextView) findViewById(R.id.txt_resend_otp);
        mButtonSetPass = (Button) findViewById(R.id.btn_set_pass);

        mContext = this;
        mPreferenceManager = new PreferenceManager(this);
        mProgressDialog = new ProgressDialog(this);
    }

    private void initCallbacks() {
        mTextViewResendOtp.setOnClickListener(this);
        mButtonSetPass.setOnClickListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Set Password");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private void setupBroadcastReceiver() {
        mReceiverSms = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processSms(intent);
            }
        };
    }

    private void processSms(Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            final Object[] objPdus = (Object[]) bundle.get(Constant.EXTRA_PDUS);
            if (objPdus != null) {
                SmsMessage[] smsMsg = new SmsMessage[objPdus.length];
                for (int i = 0; i < objPdus.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString(Constant.EXTRA_FORMAT);
                        smsMsg[i] = SmsMessage.createFromPdu((byte[]) objPdus[i], format);
                    } else {
                        smsMsg[i] = SmsMessage.createFromPdu((byte[]) objPdus[i]);
                    }
                    String phoneNumber = smsMsg[i].getDisplayOriginatingAddress();
                    String msg = smsMsg[i].getDisplayMessageBody();
                    if (phoneNumber.contains(Constant.OTP_SENDER_ID)) {
                        String otp = msg.replaceAll("[^0-9]", "");
                        receiveSms(otp);
                    }
                }
            }
        }
    }

    private void receiveSms(String otp) {
        mEditTextOtp.getText().clear();
        mEditTextOtp.setText(otp);
        mEditTextOtp.setSelection(otp.length());
    }

    private void processOtp() {
        String otp = mEditTextOtp.getText().toString().trim();
        String pass = mEditTextPass.getText().toString().trim();
        if (validateInput(otp, pass)) {
            showProgressDialog("Resetting password..");
            resetPass(getOtpRequestJson(otp, pass));
        }
    }

    private JSONObject getOtpRequestJson(String otp, String pass) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.KEY_MOBILE, mPreferenceManager.getPhone());
            jsonObject.put(Api.KEY_OTP, otp);
            jsonObject.put(Api.KEY_PASSWORD, pass);
            jsonObject.put(Api.KEY_CLIENT, MacAddress.getMacAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //TODO: add password regex validation
    private boolean validateInput(String otp, String pass) {
        if (TextUtils.isEmpty(otp)) {
            mEditTextOtp.requestFocus();
            mEditTextOtp.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "OTP Code"));
            return false;
        } else if (otp.length() < 6) {
            mEditTextOtp.requestFocus();
            mEditTextOtp.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_length),
                            "OTP Code", 6, "digits"));
            return false;
        } else if (TextUtils.isEmpty(pass)) {
            mEditTextPass.requestFocus();
            mEditTextPass.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Password"));
            return false;
        } else if (pass.length() < 6) {
            mEditTextPass.requestFocus();
            mEditTextPass.setError(getString(R.string.error_pass_length));
            return false;
        }
        return true;
    }

    private void resetPass(JSONObject resetPassRequestJson) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api.RESET_PASS,
                resetPassRequestJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                handleResetPassResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyErrorHandler.handle(mContext, error);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "activate");
    }

    private void handleResetPassResponse(JSONObject response) {
        try {
            String token = response.getString(Api.KEY_TOKEN);

            mPreferenceManager.setToken(token);

            Activity.launchClearStack(mContext, SellForexActivity.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getForgotPassRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.KEY_MOBILE, mPreferenceManager.getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void forgotPass() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Api.FORGOT_PASS, getForgotPassRequestJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                handleForgotPassResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyErrorHandler.handle(mContext, error);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "forgot_pass");
    }

    private void handleForgotPassResponse(JSONObject response) {
        try {
            String detail = response.getString(Api.KEY_DETAIL);
            ToastBuilder.build(mContext, detail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
