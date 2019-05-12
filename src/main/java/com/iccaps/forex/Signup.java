package com.iccaps.forex;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.iccaps.forex.app.PreferenceManager;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Api.KEY_AADHAR;
import static com.iccaps.forex.app.Api.KEY_EMAIL;
import static com.iccaps.forex.app.Api.KEY_PASSPORT;
import static com.iccaps.forex.app.Api.KEY_ROLE;
import static com.iccaps.forex.app.Api.KEY_USERPROFILE;
import static com.iccaps.forex.app.Api.KEY_USER_ROLE;

public class Signup extends AppCompatActivity {
    private static final int REQUEST_READ_SMS = 4;
    String phone, name, email;
    int role;
    private Button mButtonSignUp;
    private Context mContext;
    private Toolbar mToolbar;
    private EditText mEditTextName;
    private TextView mEditTextPhone;
    private EditText mEditTextPass;
    private EditText mEditTextEmail;
    private EditText mEditTextPassport;
    private EditText mEditTextAadhar;
    private ProgressDialog mProgressDialog;
    private PreferenceManager mPreferenceManager;

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initObjects();
        setupToolbar();


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

    public void onClick(View view) {
        if (view == mButtonSignUp) {
            processSignUp();
        }
    }

    private void initObjects() {
        mButtonSignUp = (Button) findViewById(R.id.btn_sign_up);
        mEditTextName = (EditText) findViewById(R.id.input_name);
        mEditTextEmail = (EditText) findViewById(R.id.input_email);
        mEditTextPhone = (TextView) findViewById(R.id.input_phone);
        mEditTextPass = (EditText) findViewById(R.id.input_pass);
        mEditTextAadhar = (EditText)findViewById(R.id.input_aadhar);
        mEditTextPassport = (EditText)findViewById(R.id.input_passport);
        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        mPreferenceManager = new PreferenceManager(this);
        mEditTextPhone.setText(mPreferenceManager.getPhone());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("SignUp");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private void processSignUp() {
        name = mEditTextName.getText().toString().trim();
        String email = mEditTextEmail.getText().toString().trim();
        phone = mEditTextPhone.getText().toString().trim();
        String pass = mEditTextPass.getText().toString().trim();
        String passport = mEditTextPassport.getText().toString().trim();
        String aadhar = mEditTextAadhar.getText().toString().trim();
        if (validateInput(name, email, phone, pass,passport,aadhar)) {
            if (hasReadSmsPermission()) {
                showProgressDialog("Creating account..");

                signUpUser(getSignUpRequestJson(name, email, phone, pass,passport,aadhar));
            } else {
                requestReadSmsPermission();
            }
        }
    }

    private JSONObject getSignUpRequestJson(String name, String email, String phone, String pass,String passport,String aadhar) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.KEY_FIRST_NAME, name);
            jsonObject.put(Api.KEY_EMAIL, email);
            jsonObject.put(Api.KEY_USERNAME, phone);
            jsonObject.put(Api.KEY_PASSWORD, pass);
            JSONObject userprofile = new JSONObject();
            {
                userprofile.put(KEY_PASSPORT, passport);
                userprofile.put(KEY_AADHAR,aadhar);
            }
            jsonObject.put(KEY_USERPROFILE,userprofile);
            Log.d("request", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //TODO: add password regex validation
    private boolean validateInput(String name, String email, String phone, String pass,String passport,String aadhar) {
        if (TextUtils.isEmpty(name)) {
            mEditTextName.requestFocus();
            mEditTextName.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Name"));
            return false;
        } else if (TextUtils.isEmpty(email)) {
            mEditTextEmail.requestFocus();
            mEditTextEmail.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Email"));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEditTextEmail.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_invalid), "Email"));
            mEditTextEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(phone)) {
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
        } else if(passport.length()<8){
            mEditTextPassport.requestFocus();
            mEditTextPassport.setError("Passport number should be equals to 8");
        } else if(aadhar.length()<12){
            mEditTextAadhar.requestFocus();
            mEditTextAadhar.setError("Aadhar Number Should be equal to 12");
        }
        return true;
    }

    private void signUpUser(JSONObject signUpRequestJson) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api.REGISTER_URL,
                signUpRequestJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                try {
                    String name1 = response.getString(Api.KEY_FIRST_NAME);
                    String phone = response.getString(Api.KEY_USERNAME);
                    String email = response.getString(KEY_EMAIL);
                    mEditTextPhone.setText(mPreferenceManager.getPhone());
                    mPreferenceManager.setName(name1);
                    mPreferenceManager.setPhone(phone);
                    mPreferenceManager.setEmail(email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ToastBuilder.build(mContext, "Account created");
                Activity.launch(mContext, OtpActivity.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyErrorHandler.handle(mContext, error);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "sign_up");
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