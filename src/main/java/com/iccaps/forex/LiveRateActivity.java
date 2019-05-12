package com.iccaps.forex;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iccaps.forex.Model.BranchItem;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.PreferenceManager;
import com.iccaps.forex.app.VolleyErrorHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Api.KEY_BRANCH;
import static com.iccaps.forex.app.Api.KEY_ID;
import static com.iccaps.forex.app.Api.KEY_NAME;
import static com.iccaps.forex.app.Api.KEY_US;
import static com.iccaps.forex.app.Api.LIST_CURRENCY;

public class LiveRateActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView mDollar,mPound,mNewzelandDollar,mUAE,mSaudi,mSingapore,mEuro,mCanada,mAustralia,mJapan,mHongKong;
    private PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_rate);
        initObjects();
        setupToolbar();
        processRates();

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
        mProgressDialog = new ProgressDialog(mContext);
        mAustralia = (TextView)findViewById(R.id.aus_dollar);
        mDollar = (TextView)findViewById(R.id.us_rate);
        mNewzelandDollar = (TextView)findViewById(R.id.newzeland);
        mUAE = (TextView)findViewById(R.id.uae);
        mCanada = (TextView)findViewById(R.id.can_dollar);
        mSingapore = (TextView)findViewById(R.id.singapore_dollar);
        mPound = (TextView)findViewById(R.id.uk_pound);
        mJapan = (TextView)findViewById(R.id.jap_yen);
        mSaudi = (TextView)findViewById(R.id.saudi);
        mEuro = (TextView)findViewById(R.id.swizz);
        mHongKong = (TextView)findViewById(R.id.hongkong);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Live Rates");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private void processRates() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(LIST_CURRENCY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleUs(response);
                        handleUk(response);
                        handleSwizz(response);
                        handleSingapore(response);
                        handleHongKong(response);
                        handleAustralia(response);
                        handleCanada(response);
                        handleUAE(response);
                        handleJapan(response);
                        handleMalayasia(response);
                        handleSaudi(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyErrorHandler.handle(mContext, error);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest, "dashboard");
    }



    private void handleUs(JSONArray response) {
        {
            double USDollar = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(0);
                for (int j = 0; j < jsonObject.length(); j++) {
                    USDollar = jsonObject.getDouble(KEY_US);
                }
                mDollar.setText(String.valueOf(USDollar));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleUk(JSONArray response) {
        {
            double UkPound = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(1);
                for (int j = 0; j < jsonObject.length(); j++) {
                    UkPound = jsonObject.getDouble(KEY_US);
                }
                mPound.setText(String.valueOf(UkPound));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleSwizz(JSONArray response) {
        {
            double Swizz = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(2);
                for (int j = 0; j < jsonObject.length(); j++) {
                    Swizz = jsonObject.getDouble(KEY_US);
                }
                mEuro.setText(String.valueOf(Swizz));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleAustralia(JSONArray response) {
        {
            double Australia = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(3);
                for (int j = 0; j < jsonObject.length(); j++) {
                    Australia = jsonObject.getDouble(KEY_US);
                }
                mAustralia.setText(String.valueOf(Australia));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleCanada(JSONArray response) {
        {
            double Canada = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(4);
                for (int j = 0; j < jsonObject.length(); j++) {
                    Canada = jsonObject.getDouble(KEY_US);
                }
                mCanada.setText(String.valueOf(Canada));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleUAE(JSONArray response) {
        {
            double UAE = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(5);
                for (int j = 0; j < jsonObject.length(); j++) {
                    UAE = jsonObject.getDouble(KEY_US);
                }
                mUAE.setText(String.valueOf(UAE));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    private void handleSingapore(JSONArray response) {
        {
            double Singapore = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(6);
                for (int j = 0; j < jsonObject.length(); j++) {
                    Singapore = jsonObject.getDouble(KEY_US);
                }
                mSingapore.setText(String.valueOf(Singapore));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleJapan(JSONArray response) {
        {
            double Japan = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(7);
                for (int j = 0; j < jsonObject.length(); j++) {
                    Japan = jsonObject.getDouble(KEY_US);
                }
                mJapan.setText(String.valueOf(Japan));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMalayasia(JSONArray response) {
        double Malaysia = 0;
        try {
            JSONObject jsonObject = response.getJSONObject(8);
            for (int j = 0; j < jsonObject.length(); j++) {
                Malaysia = jsonObject.getDouble(KEY_US);
            }
            mNewzelandDollar.setText(String.valueOf(Malaysia));
            Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleHongKong(JSONArray response) {
        {
            double Newzeland = 0;
            try {
                JSONObject jsonObject = response.getJSONObject(9);
                for (int j = 0; j < jsonObject.length(); j++) {
                    Newzeland = jsonObject.getDouble(KEY_US);
                }
                mHongKong.setText(String.valueOf(Newzeland));
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleSaudi(JSONArray response){
        double Saudi = 0;
        try {
            JSONObject jsonObject = response.getJSONObject(10);
            for (int j = 0; j < jsonObject.length(); j++) {
                Saudi = jsonObject.getDouble(KEY_US);
            }
            mSaudi.setText(String.valueOf(Saudi));
            Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }














    @Override
    public void onClick(View v) {

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
