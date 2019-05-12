package com.iccaps.forex;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.iccaps.forex.app.PreferenceManager;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;
import com.iccaps.forex.helper.MacAddress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Api.KEY_EMAIL;
import static com.iccaps.forex.app.Api.KEY_FIRST_NAME;
import static com.iccaps.forex.app.Api.KEY_USERNAME;

public class SignInSell extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Toolbar mToolbar;
    private EditText mEditTextPhone, mEditTextPass;
    private TextView mTextViewForgotPass;
    private Button mButtonSignIn;
    private PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sell);
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

    @Override
    public void onClick(View v) {
        if (v == mTextViewForgotPass) {
            Activity.launch(mContext, ResetPasswordSell.class);
        } else if (v == mButtonSignIn) {
            processSignIn();
        }
    }

    private void initObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEditTextPhone = (EditText) findViewById(R.id.input_phone);
        mEditTextPass = (EditText) findViewById(R.id.input_pass);
        mTextViewForgotPass = (TextView) findViewById(R.id.txt_forgot_pass);
        mButtonSignIn = (Button) findViewById(R.id.btn_sign_in);

        mContext = this;
        mPreferenceManager = new PreferenceManager(this);
        /*mEditTextPhone.setText(mPreferenceManager.getPhone());*/
        mProgressDialog = new ProgressDialog(this);
    }

    private void initCallbacks() {
        mTextViewForgotPass.setOnClickListener(this);
        mButtonSignIn.setOnClickListener(this);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Sign In");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private void processSignIn() {
        String phone = mEditTextPhone.getText().toString().trim();
        String pass = mEditTextPass.getText().toString().trim();
        if (validateInput(phone, pass)) {
            showProgressDialog("Signing in..");
            signInUser(getSignInRequestJson(phone, pass));
        }
    }

    private JSONObject getSignInRequestJson(String phone, String pass) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_USERNAME, phone);
            jsonObject.put(Api.KEY_PASSWORD, pass);
            jsonObject.put(Api.KEY_CLIENT, MacAddress.getMacAddress());
            jsonObject.put(Api.KEY_TOKEN, mPreferenceManager.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //TODO: add password regex validation
    private boolean validateInput(String phone, String pass) {
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

    private void signInUser(JSONObject signInRequestJson) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api.LOGIN_URL,
                signInRequestJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                handleSignInResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                VolleyErrorHandler.handle(mContext, error);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "sign_in");
    }

    private void handleSignInResponse(JSONObject response) {
        try {
            int userId = response.getInt(Api.KEY_USERID);
            String name = response.getString(KEY_FIRST_NAME);
            String token = response.getString(Api.KEY_TOKEN);
            String pickPhone = response.getString(KEY_USERNAME);
            String email = response.getString(KEY_EMAIL);
            mPreferenceManager.setName(name);
            mPreferenceManager.setToken(token);
            mPreferenceManager.setPhone(pickPhone);
            mPreferenceManager.setEmail(email);

            ToastBuilder.build(mContext, "Welcome");

            Activity.launchClearStack(mContext, SellForexActivity.class);
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

