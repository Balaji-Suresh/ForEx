package com.iccaps.forex;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.Api;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.PreferenceManager;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResetPasswordActivity extends AppCompatActivity  implements View.OnClickListener {

    private static final int REQUEST_READ_SMS = 4;
    private Context mContext;
    private Toolbar mToolbar;
    private EditText mEditTextPhone;
    private Button mButtonResetPass;
    private PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initObjects();
        initCallbacks();

        setupToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    processForgotPass();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
        if (v == mButtonResetPass) {
            processForgotPass();
        }
    }

    private void initObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEditTextPhone = (EditText) findViewById(R.id.input_phone);
        mButtonResetPass = (Button) findViewById(R.id.btn_reset_pass);

        mContext = this;
        mPreferenceManager = new PreferenceManager(this);
        mProgressDialog = new ProgressDialog(this);
    }

    private void initCallbacks() {
        mButtonResetPass.setOnClickListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Reset Password");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private void processForgotPass() {
        String phone = mEditTextPhone.getText().toString().trim();
        if (validateInput(phone)) {
            if (hasReadSmsPermission()) {
                showProgressDialog("Verifying phone..");
                mPreferenceManager.setPhone(phone);
                forgotPass(getForgotPassRequestJson(phone));
            } else {
                requestReadSmsPermission();
            }
        }
    }

    private JSONObject getForgotPassRequestJson(String phone) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.KEY_MOBILE, phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private boolean validateInput(String phone) {
        if (TextUtils.isEmpty(phone)) {
            mEditTextPhone.requestFocus();
            mEditTextPhone.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Phone Number"));
            return false;
        } else if (phone.length() < 10) {
            mEditTextPhone.requestFocus();
            mEditTextPhone.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_length),
                            "Phone Number", 10, "digits"));
            return false;
        }
        return true;
    }

    private void forgotPass(JSONObject forgotPassRequestJson) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                Api.FORGOT_PASS, forgotPassRequestJson, new Response.Listener<JSONObject>() {
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

            Activity.launch(mContext, SetPasswordActivity.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},
                REQUEST_READ_SMS);
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



