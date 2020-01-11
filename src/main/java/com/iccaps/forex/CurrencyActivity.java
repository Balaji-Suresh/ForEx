package com.iccaps.forex;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iccaps.forex.Model.BranchItem;
import com.iccaps.forex.Model.CurrencyItem;
import com.iccaps.forex.Model.ProductItem;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.Api;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.PreferenceManager;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Api.KEY_BRANCH;
import static com.iccaps.forex.app.Api.KEY_CARD;
import static com.iccaps.forex.app.Api.KEY_CASH;
import static com.iccaps.forex.app.Api.KEY_CHEQUE;
import static com.iccaps.forex.app.Api.KEY_ID;
import static com.iccaps.forex.app.Api.KEY_NAME;
import static com.iccaps.forex.app.Api.KEY_PRODUCT;
import static com.iccaps.forex.app.Api.KEY_PURPOSE;
import static com.iccaps.forex.app.Api.KEY_STATE;
import static com.iccaps.forex.app.Api.KEY_US;
import static com.iccaps.forex.app.Api.LIST_CURRENCY;
import static com.iccaps.forex.app.Constant.SELECT_CURRENCY;
import static java.lang.String.*;

public class CurrencyActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private Context mContext;
    private Button mButtonBuy;
    private ImageView mProductRules, mCurrencyRules;
    private List<ProductItem> mProductItem;
    private List<CurrencyItem> mCurrencyItem;
    private ArrayAdapter<CurrencyItem> mCurrencyArrayAdapter;
    private ArrayAdapter<ProductItem> mProductArrayAdapter;
    private RadioGroup mLayout;
    private RadioButton mFullPayment, mAdvancePayment, mHome, mBranch;
    private EditText mFXamount;
    private TextView mInramount;
    private PreferenceManager mPreferenceManager;
    private TextView mRate, mCityTextView, mStateTextView;
    private Spinner mProduct, mCurrency;
    float USdollar,Ukpound,euro,australia,canada,uae,singapore,japan,malaysia,hongKong,saudi;
    float USdollar1,Ukpound1,euro1,australia1,canada1,uae1,singapore1,japan1,malaysia1,hongKong1,saudi1;
    float USdollar2,Ukpound2,euro2,australia2,canada2,uae2,singapore2,japan2,malaysia2,hongKong2,saudi2;
    String Currency;
    int Purpose;
    float v1;
    private String UsDollar,Pound,Euroo,AustraliaDolar,CanadaDollar,UAEDIR,SingaporeDollar,JapaneseYen,MalaysiaRing,Hongkong,SaudiRid;
    private Double USDollar,UkPound,Euro,Australia,Canada,UAE,Singapore,Japan,Malaysia,HongKong,Saudi;
    private Double USDollar1,UkPound1,Euro1,Australia1,Canada1,UAE1,Singapore1,Japan1,Malaysia1,HongKong1,Saudi1;
    private Double USDollar2,UkPound2,Euro2,Australia2,Canada2,UAE2,Singapore2,Japan2,Malaysia2,HongKong2,Saudi2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        initObjects();
        initCallbacks();
        initSpinner();
        setupToolbar();
        getProduct();
        getCurrency();
        populateRates();
        /*populateCurrencies();*/



    }



    private void getProduct() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Api.LIST_SPINNERS_LIST, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               /* handleProductResponse(response);*/
                handleCurrencyResponse(response);


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyErrorHandler.handle(mContext, error);

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "Dropdown details");
    }

    private void handleProductResponse(JSONObject response) {
        {
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_PRODUCT);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    int id = jsonObject.getInt(KEY_ID);
                    String name = jsonObject.getString(KEY_NAME);
                    mProductItem.add(new ProductItem(id, name));
                }
                mProductArrayAdapter.notifyDataSetChanged();
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Buy Forex");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private void initSpinner() {
        /*mProductArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProduct.setAdapter(mProductArrayAdapter);
        */mCurrencyArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mCurrency.setAdapter(mCurrencyArrayAdapter);

    }


    private void initObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContext = this;
       /* mLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rdo_fullpayment){
                    mHome.setVisibility(View.VISIBLE);
                    mAdvancePayment.setVisibility(View.VISIBLE);
                } else {
                    mHome.setVisibility(View.GONE);
                    mAdvancePayment.setVisibility(View.VISIBLE);
                }
            }
        });

       */ mRate = (TextView) findViewById(R.id.dailyrate);
        mProductRules = (ImageView) findViewById(R.id.type_rule);
        mCurrencyRules = (ImageView) findViewById(R.id.amount_rule);
        mFXamount = (EditText) findViewById(R.id.fx_amount);

        mInramount = (TextView) findViewById(R.id.indrupees);
        mCurrency = (Spinner) findViewById(R.id.currency);
        mProduct = (Spinner) findViewById(R.id.type);
        mButtonBuy = (Button) findViewById(R.id.buy);
        mProductItem = new ArrayList<>();
        mProductArrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item_text, mProductItem);
        mCurrencyItem = new ArrayList<>();
        mPreferenceManager = new PreferenceManager(this);
        mCurrencyArrayAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item_text,mCurrencyItem);
        mCurrencyArrayAdapter.add(new CurrencyItem(SELECT_CURRENCY));
        Purpose = Integer.parseInt(getIntent().getExtras().getString("PURPOSE"));

    }

    private void initCallbacks() {
        mButtonBuy.setOnClickListener(this);
        mCurrencyRules.setOnClickListener(this);
        mProductRules.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonBuy) {
            processValidation();
        } else if (v == mCurrencyRules) {
            processCurrencyRules();
        } else if (v == mProductRules) {
            processProductRules();
        }

    }


    private void processValidation() {
        String FXAmount = mFXamount.getText().toString().trim();
        int Type = mProduct.getSelectedItemPosition();
        int Currency = mCurrency.getSelectedItemPosition();
        if(validateInput(FXAmount,Type,Currency)){
            processBundle();
            Activity.launch(mContext, PaymentActivity.class);
            //promptBookingDialog();
        }
    }
    private boolean validateInput(String FxAmount, int Type,int Currency) {
        Type = mProduct.getSelectedItemPosition();
        Currency = mCurrency.getSelectedItemPosition();
        if (TextUtils.isEmpty(FxAmount)) {
            mFXamount.requestFocus();
            mFXamount.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Fx Amount Cannot be null"));
            return false;
        } else if (FxAmount.length() > 5) {
            mFXamount.requestFocus();
            mFXamount.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_length),
                            "Enter FxAmount less than", 5, "digits"));
            return false;
        } else if(Type == 0){
            ToastBuilder.build(mContext,"Please Select a Type");
            return false;
        }
        else if(Currency == 0){
            ToastBuilder.build(mContext,"Please Select a Currency");
            return false;
        } else if(mFXamount.length()<3){
            mFXamount.requestFocus();
            mFXamount.setError("Enter the value in 3 digits");
            return false;
        } else if(v1>0&&v1<100){
            mFXamount.requestFocus();
            mFXamount.setError("Fx Amount should be Minimum of 100");
            return false;
        }
        return true;
    }

    private void processProductRules() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_product, null);
        ImageView imageViewClose = (ImageView) mView.findViewById(R.id.img_close);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void processCurrencyRules() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_currency, null);
        ImageView imageViewClose = (ImageView) mView.findViewById(R.id.img_close);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void promptBookingDialog() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_session, null);
        ImageView imageViewClose = (ImageView) mView.findViewById(R.id.img_close);
        RadioGroup session = (RadioGroup)mView.findViewById(R.id.sessionlayout);
        RadioButton mGuest = (RadioButton) mView.findViewById(R.id.rdo_guest);
        RadioButton mLogin = (RadioButton) mView.findViewById(R.id.rdo_existing_user);
        final Button mContinue = (Button) mView.findViewById(R.id.btn_add1);
        mContinue.setVisibility(View.GONE);
       /* final TextView mRegister = (TextView) mView.findViewById(R.id.txt_register);*/
        final Button mLoginButton = (Button) mView.findViewById(R.id.login);
        mLoginButton.setVisibility(View.GONE);
        session.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.rdo_existing_user){
                    mContinue.setVisibility(View.GONE);
                } else if(checkedId == R.id.rdo_guest){
                    mContinue.setVisibility(View.VISIBLE);
                   /* mRegister.setVisibility(View.GONE);*/
                    mLoginButton.setVisibility(View.GONE);

                }
            }
        });

        /*final EditText mNumber = (EditText) mView.findViewById(R.id.input_mob_number);
        mNumber.setText(mPreferenceManager.getPhone());
        */AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginButton.setVisibility(View.VISIBLE);
                /*mRegister.setVisibility(View.VISIBLE);*/

            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.launch(mContext, Sigin.class);
            }
        });
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.launch(mContext, Signup.class);
            }
        });
       /* mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.launch(mContext, Signup.class);
            }
        });*/
    }




    private void handleCurrencyResponse(JSONObject response) {
        {
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_STATE);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    int id = jsonObject.getInt(KEY_ID);
                    String name = jsonObject.getString(KEY_NAME);
                    mCurrencyItem.add(new CurrencyItem(id, name));
                    /*mPreferenceManager.setCurrencyId(id);*/
                }
                mCurrencyArrayAdapter.notifyDataSetChanged();
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void getCurrency() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Api.LIST_CARD_CURRENCY, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyErrorHandler.handle(mContext, error);

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "Dropdown details");
    }
    private void handleUs(JSONObject response) {
        {
            USDollar1 = 0.0;
            USDollar = 0.0;
            USDollar2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.getInt(KEY_ID);
                    USDollar = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/


                } Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_CASH);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                int id = jsonObject.getInt(KEY_ID);
                USDollar1 = jsonObject.getDouble(KEY_US);


            }Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                int id = jsonObject.getInt(KEY_ID);
                USDollar2 = jsonObject.getDouble(KEY_US);
            }Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void handleUk(JSONObject response) {
        {
            UkPound = 0.0;
            UkPound1 = 0.0;
            UkPound2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(1);

                    UkPound = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(1);

                    UkPound1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(1);

                    UkPound2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleSwizz(JSONObject response) {
        {
            Euro = 0.0;
            Euro1 = 0.0;
            Euro2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(2);

                    Euro = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(2);

                    Euro1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(2);

                    Euro2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleAustralia(JSONObject response) {
        {
            Australia = 0.0;
            Australia1 = 0.0;
            Australia2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(3);

                    Australia = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(3);

                    Australia1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(3);

                    Australia2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleCanada(JSONObject response) {
        {
            Canada = 0.0;
            Canada1 = 0.0;
            Canada2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(4);

                    Canada = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(4);

                    Canada1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(4);

                    Canada2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleUAE(JSONObject response) {
        {
            UAE =0.0;
            UAE1 = 0.0;
            UAE2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(5);

                    UAE = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(5);

                    UAE1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(5);

                    UAE2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleSingapore(JSONObject response) {
        {
            Singapore = 0.0;
            Singapore1 = 0.0;
            Singapore2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(6);

                    Singapore = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(6);

                    Singapore1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(6);

                    Singapore2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleJapan(JSONObject response) {
        {
            Japan = 0.0;
            Japan1 = 0.0;
            Japan2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(7);

                    Japan = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(7);

                    Japan1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(7);

                    Japan2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMalayasia(JSONObject response) {
        {
            Malaysia = 0.0;
            Malaysia1 = 0.0;
            Malaysia2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(8);

                    Malaysia = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(8);

                    Malaysia1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(8);

                    Malaysia2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleHongKong(JSONObject response) {
        {
            HongKong = 0.0;
            HongKong1 = 0.0;
            HongKong2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(9);

                    HongKong = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(9);

                    HongKong1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(9);

                    HongKong2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleSaudi(JSONObject response){

        {
            Saudi = 0.0;
            Saudi1 = 0.0;
            Saudi2 = 0.0;
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CARD);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(10);

                    Saudi = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CASH);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(10);

                    Saudi1 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_CHEQUE);
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(10);

                    Saudi2 = jsonObject.getDouble(KEY_US);
                    /*UsDollar = jsonObject.getString(KEY_CODE);*/
                }Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void processBundle() {
        Currency = String.valueOf(mCurrencyItem.get(mCurrency.getSelectedItemPosition()).getid());
        String product = String.valueOf(mProduct.getSelectedItemPosition());
        String travel = getIntent().getExtras().getString("TRAVEL");
        Purpose = Integer.parseInt(getIntent().getExtras().getString("PURPOSE"));
        String branch = getIntent().getExtras().getString("BRANCH");
        mPreferenceManager.setBranchId(branch);
        mPreferenceManager.setPurposeId(String.valueOf(Purpose));
        mPreferenceManager.setTravellersId(travel);
        mPreferenceManager.setCurrencyId(Currency);
        mPreferenceManager.setProductId(product);


    }
    private void populateRates() {
        mCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long selectedItem = parent.getItemIdAtPosition(position);
                {
                    if (selectedItem == 1) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mInramount.setText("");
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }
                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mInramount.setText("");
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                USdollar = (float) (v1 * (Double.valueOf(USDollar)));
                                                mInramount.setText(valueOf(USdollar));
                                                mPreferenceManager.setINRCurrency(String.valueOf(USdollar));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(USDollar));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    if(v1>10000){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    } else if(v1>0&&v1<=10000){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    if(v1>25000){
                                                        mFXamount.setError("Please enter the amount equivalent to 250000 USD");
                                                    }
                                                } else if(USdollar == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mInramount.setText("");
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                 v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                USdollar = (float) (v1 * (Double.valueOf(USDollar1)));
                                                mInramount.setText(valueOf(USdollar));
                                                mPreferenceManager.setINRCurrency(String.valueOf(USdollar));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(USDollar));
                                                mFXamount.requestFocus();
                                                if(v1>3000){
                                                    mFXamount.setError("Enter the amount equivalent to 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                } else if(v1>0&&v1<=3000){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(USdollar == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mFXamount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                USdollar = (float) (v1 * (Double.valueOf(USDollar)));
                                                mInramount.setText(valueOf(USdollar));
                                                mPreferenceManager.setINRCurrency(String.valueOf(USdollar));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(USDollar));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    if(v1>10000){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    } else if(v1>0&&v1<=10000){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    if(v1>25000){
                                                        mFXamount.setError("Please enter the amount equivalent to 250000 USD");
                                                    }
                                                } else if(USdollar == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mFXamount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 2) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }
                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                Ukpound = (float) (v1 * (Double.valueOf(UkPound)));
                                                mInramount.setText(valueOf(Ukpound));
                                                mPreferenceManager.setINRCurrency(String.valueOf(Ukpound));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(UkPound));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(Ukpound>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&Ukpound<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(Ukpound>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&Ukpound<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Ukpound == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mFXamount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mInramount.clearFocus();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                float value = (float) (v1 * (Double.valueOf(UkPound1)));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                mInramount.setText(valueOf(value));
                                                mPreferenceManager.setINRCurrency(String.valueOf(value));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(UkPound1));
                                                if(value>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&value<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(value == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }



                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mFXamount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                float value = (float) (v1 * (Double.valueOf(UkPound)));
                                                mInramount.setText(valueOf(value));
                                                mPreferenceManager.setINRCurrency(String.valueOf(value));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(UkPound));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(value>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&value<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(value>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&value<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(value == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mFXamount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 3) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }

                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                euro = (float) (v1 * (Double.valueOf(Euro)));
                                                mInramount.setText(valueOf(euro));
                                                mPreferenceManager.setINRCurrency(String.valueOf(euro));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Euro));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(euro>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&euro<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(euro>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&euro<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(euro == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                euro = (float) (v1 * (Double.valueOf(Euro1)));
                                                mInramount.setText(valueOf(euro));
                                                mPreferenceManager.setINRCurrency(String.valueOf(euro));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Euro1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(euro>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&euro<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                }
                                                 else if(euro == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                euro = (float) (v1 * (Double.valueOf(Euro)));
                                                mInramount.setText(valueOf(euro));
                                                mPreferenceManager.setINRCurrency(String.valueOf(euro));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Euro));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(euro>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&euro<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(euro>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&euro<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(euro == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 4) {
                        mFXamount.getText().clear();
                        mProduct.setSelection(0);
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }

                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                australia = (float) (v1 * (Double.valueOf(Australia)));
                                                mInramount.setText(valueOf(australia));
                                                mPreferenceManager.setINRCurrency(String.valueOf(australia));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Australia));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(australia>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&australia<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(australia>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&australia<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(australia == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                australia = (float) (v1 * (Double.valueOf(Australia1)));
                                                mInramount.setText(valueOf(australia));
                                                mPreferenceManager.setINRCurrency(String.valueOf(australia));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Australia1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(australia>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&australia<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(australia == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                australia = (float) (v1 * (Double.valueOf(Australia)));
                                                mInramount.setText(valueOf(australia));
                                                mPreferenceManager.setINRCurrency(String.valueOf(australia));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Australia));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(australia>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&australia<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(australia>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&australia<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(australia == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 5) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }

                                    if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                canada = (float) (v1 * (Double.valueOf(Canada)));
                                                mInramount.setText(valueOf(canada));
                                                mPreferenceManager.setINRCurrency(String.valueOf(canada));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Canada));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(canada>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&canada<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(canada>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&canada<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(canada == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                canada = (float) (v1 * (Double.valueOf(Canada1)));
                                                mInramount.setText(valueOf(canada));
                                                mPreferenceManager.setINRCurrency(String.valueOf(canada));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Canada1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(canada>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&canada<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(canada == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                canada = (float) (v1 * (Double.valueOf(Canada)));
                                                mInramount.setText(valueOf(canada));
                                                mPreferenceManager.setINRCurrency(String.valueOf(canada));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Canada));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(canada>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&canada<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(canada>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&canada<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(canada == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 6) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }

                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                uae = (float) (v1 * (Double.valueOf(UAE)));
                                                mInramount.setText(valueOf(uae));
                                                mPreferenceManager.setINRCurrency(String.valueOf(uae));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(UAE));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(uae>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&uae<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(uae>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&uae<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(uae == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                uae = (float) (v1 * (Double.valueOf(UAE1)));
                                                mInramount.setText(valueOf(uae));
                                                mPreferenceManager.setINRCurrency(String.valueOf(uae));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(UAE1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(uae>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&uae<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(uae == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                uae = (float) (v1 * (Double.valueOf(UAE)));
                                                mInramount.setText(valueOf(uae));
                                                mPreferenceManager.setINRCurrency(String.valueOf(uae));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(UAE));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(uae>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&uae<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(uae>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&uae<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(uae == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 7) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }
                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                singapore = (float) (v1 * (Double.valueOf(Singapore)));
                                                mInramount.setText(valueOf(singapore));
                                                mPreferenceManager.setINRCurrency(String.valueOf(singapore));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Singapore));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(singapore>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&singapore<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(singapore>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&singapore<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(singapore == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                singapore = (float) (v1 * (Double.valueOf(Singapore1)));
                                                mInramount.setText(valueOf(singapore));
                                                mPreferenceManager.setINRCurrency(String.valueOf(singapore));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Singapore1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(singapore>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&singapore<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(singapore == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                singapore = (float) (v1 * (Double.valueOf(Singapore)));
                                                mInramount.setText(valueOf(singapore));
                                                mPreferenceManager.setINRCurrency(String.valueOf(singapore));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Singapore));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(singapore>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&singapore<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(singapore>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&singapore<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(singapore == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 8) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }

                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                japan = (float) (v1 * (Double.valueOf(Japan)));
                                                mInramount.setText(valueOf(japan));
                                                mPreferenceManager.setINRCurrency(String.valueOf(japan));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Japan));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(japan>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&japan<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(japan>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&japan<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(japan == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                japan = (float) (v1 * (Double.valueOf(Japan1)));
                                                mInramount.setText(valueOf(japan));
                                                mPreferenceManager.setINRCurrency(String.valueOf(japan));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Japan1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(japan>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&japan<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(japan == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                japan = (float) (v1 * (Double.valueOf(Japan)));
                                                mInramount.setText(valueOf(japan));
                                                mPreferenceManager.setINRCurrency(String.valueOf(japan));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Japan));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(japan>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&japan<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(japan>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&japan<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(japan == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 9) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }

                                    if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                malaysia = (float) (v1 * (Double.valueOf(Malaysia)));
                                                mInramount.setText(valueOf(malaysia));
                                                mPreferenceManager.setINRCurrency(String.valueOf(malaysia));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Malaysia));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(malaysia>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&malaysia<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(malaysia>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&malaysia<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(malaysia == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                malaysia = (float) (v1 * (Double.valueOf(Malaysia1)));
                                                mInramount.setText(valueOf(malaysia));
                                                mPreferenceManager.setINRCurrency(String.valueOf(malaysia));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Malaysia1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(malaysia>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&malaysia<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(malaysia == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                malaysia = (float) (v1 * (Double.valueOf(Malaysia)));
                                                mInramount.setText(valueOf(malaysia));
                                                mPreferenceManager.setINRCurrency(String.valueOf(malaysia));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Malaysia));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(malaysia>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&malaysia<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(malaysia>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&malaysia<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(malaysia == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 10) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }

                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                hongKong = (float) (v1 * (Double.valueOf(HongKong)));
                                                mInramount.setText(valueOf(hongKong));
                                                mPreferenceManager.setINRCurrency(String.valueOf(hongKong));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(HongKong));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(hongKong>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&hongKong<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(hongKong>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&hongKong<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(hongKong == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                hongKong = (float) (v1 * (Double.valueOf(HongKong1)));
                                                mInramount.setText(valueOf(hongKong));
                                                mPreferenceManager.setINRCurrency(String.valueOf(hongKong));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(HongKong1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(hongKong>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&hongKong<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(hongKong == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                hongKong = (float) (v1 * (Double.valueOf(HongKong)));
                                                mInramount.setText(valueOf(hongKong));
                                                mPreferenceManager.setINRCurrency(String.valueOf(hongKong));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(HongKong));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(hongKong>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&hongKong<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(hongKong>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&hongKong<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(hongKong == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    } else if (selectedItem == 11) {
                        mProduct.setSelection(0);
                        mFXamount.getText().clear();
                        mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);{
                                    if(selectedItem == 0){
                                        mFXamount.setEnabled(false);
                                    }

                                    else if(selectedItem == 1){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                saudi = (float) (v1 * (Double.valueOf(Saudi)));
                                                mInramount.setText(valueOf(saudi));
                                                mPreferenceManager.setINRCurrency(String.valueOf(saudi));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Saudi));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(saudi>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&saudi<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(saudi>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&saudi<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(saudi == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });

                                    } else if(selectedItem == 2){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                saudi = (float) (v1 * (Double.valueOf(Saudi1)));
                                                mInramount.setText(valueOf(saudi));
                                                mPreferenceManager.setINRCurrency(String.valueOf(saudi));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Saudi1));
                                                float v3 = (float)(3000*(Double.valueOf(USDollar1)));
                                                if(saudi>v3){
                                                    mFXamount.setError("Enter the equivalent value of 3000 USD");
                                                    mButtonBuy.setEnabled(false);
                                                }else if(v1>0&&saudi<=v3){
                                                    mFXamount.setError(null);
                                                    mButtonBuy.setEnabled(true);
                                                } else if(saudi == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }


                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });

                                    } else if(selectedItem == 3){
                                        mFXamount.setEnabled(true);
                                        mFXamount.getText().clear();
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                saudi = (float) (v1 * (Double.valueOf(Saudi)));
                                                mInramount.setText(valueOf(saudi));
                                                mPreferenceManager.setINRCurrency(String.valueOf(saudi));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                                mPreferenceManager.setCurrencyValue(String.valueOf(Saudi));
                                                if(Purpose == 1){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 10000*USDollar;
                                                    if(saudi>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 10000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&saudi<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(Purpose == 3){
                                                    mFXamount.requestFocus();
                                                    Double v3 = 25000*USDollar;
                                                    if(saudi>v3){
                                                        mFXamount.setError("Please enter the amount equivalent to 25000 USD");
                                                        mButtonBuy.setEnabled(false);
                                                    }else if(v1>0&&saudi<=v3){
                                                        mFXamount.setError(null);
                                                        mButtonBuy.setEnabled(true);
                                                    }
                                                } else if(saudi == 0.0){
                                                    mButtonBuy.setEnabled(false);
                                                }

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });

                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });













        /*mCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long selectedItem = parent.getItemIdAtPosition(position);
                if(selectedItem == 0) {
                    mRate.setText("1USD = " + (valueOf(USDollar1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            USdollar = (float) (v1 * (Double.valueOf(USDollar1)));
                            mInramount.setText(valueOf(USdollar));
                            mPreferenceManager.setINRCurrency(String.valueOf(USdollar));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 1) {
                    mRate.setText("1GBP = " + (valueOf(UkPound1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            Ukpound = (float) (v1 * (Double.valueOf(UkPound1)));
                            mInramount.setText(valueOf(Ukpound));
                            mPreferenceManager.setINRCurrency(String.valueOf(Ukpound));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 2) {
                    mRate.setText("1 EUR= " + (valueOf(Euro1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            euro = (float) (v1 * (Double.valueOf(Euro1)));
                            mInramount.setText(valueOf(euro));
                            mPreferenceManager.setINRCurrency(String.valueOf(euro));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 3) {
                    mRate.setText("1 AUD= " + (valueOf(Australia1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            australia = (float) (v1 * (Double.valueOf(Australia1)));
                            mInramount.setText(valueOf(australia));
                            mPreferenceManager.setINRCurrency(String.valueOf(australia));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 4) {
                    mRate.setText("1 CAD= " + (valueOf(Canada1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            canada = (float) (v1 * (Double.valueOf(Canada1)));
                            mInramount.setText(valueOf(canada));
                            mPreferenceManager.setINRCurrency(String.valueOf(canada));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 5) {
                    mRate.setText("1 UAE= " + (valueOf(UAE1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            uae = (float) (v1 * (Double.valueOf(UAE1)));
                            mInramount.setText(valueOf(uae));
                            mPreferenceManager.setINRCurrency(String.valueOf(uae));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 6) {
                    mRate.setText("1 SGD= " + (valueOf(Singapore1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            singapore = (float) (v1 * (Double.valueOf(Singapore1)));
                            mInramount.setText(valueOf(singapore));
                            mPreferenceManager.setINRCurrency(String.valueOf(singapore));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 7) {
                    mRate.setText("1 JPY= " + (valueOf(Japan1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            japan = (float) (v1 * (Double.valueOf(Japan1)));
                            mInramount.setText(valueOf(japan));
                            mPreferenceManager.setINRCurrency(String.valueOf(japan));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 8) {
                    mRate.setText("1 MYR= " + (valueOf(Malaysia1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            malaysia = (float) (v1 * (Double.valueOf(Malaysia1)));
                            mInramount.setText(valueOf(malaysia));
                            mPreferenceManager.setINRCurrency(String.valueOf(malaysia));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 9) {
                    mRate.setText("1 HKD= " + (valueOf(HongKong1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            hongKong = (float) (v1 * (Double.valueOf(HongKong1)));
                            mInramount.setText(valueOf(hongKong));
                            mPreferenceManager.setINRCurrency(String.valueOf(hongKong));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                } else if(selectedItem == 10) {
                    mRate.setText("1 SAR= " + (valueOf(Saudi1)));
                    mFXamount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                    mFXamount.getText().toString() : "0");
                            saudi = (float) (v1 * (Double.valueOf(Saudi1)));
                            mInramount.setText(valueOf(saudi));
                            mPreferenceManager.setINRCurrency(String.valueOf(saudi));
                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s.length() == 0) {
                                mInramount.clearComposingText();
                            }

                        }
                    });
                }

                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            {
                                if (selectedItem == 1) {
                                    *//*mInramount.setText(valueOf(USDollar));*//*

                                } else if (selectedItem == 2) {
                                   *//* mInramount.setText(valueOf(USDollar1));*//*
                                    mFXamount.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                    mFXamount.getText().toString() : "0");
                                            USdollar1 = (float) (v1 * Double.valueOf(USDollar1));
                                            mInramount.setText(valueOf(USdollar1));
                                            mPreferenceManager.setINRCurrency(String.valueOf(USdollar1));
                                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                            if (s.length() > 4) {
                                                mFXamount.requestFocus();
                                                mFXamount.setError(
                                                        "Enter Value Less than 5 digit for processing Cash");

                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {
                                            if (s.length() == 0) {
                                                mInramount.clearComposingText();
                                            }

                                        }
                                    });

                                } else if (selectedItem == 3) {
                                    *//*mInramount.setText(valueOf(USDollar2));*//*
                                    mFXamount.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                    mFXamount.getText().toString() : "0");
                                            USdollar2 = (float) (v1 * Double.valueOf(USDollar2));
                                            mInramount.setText(valueOf(USdollar2));
                                            mPreferenceManager.setINRCurrency(String.valueOf(USdollar2));
                                            mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {
                                            if (s.length() == 0) {
                                                mInramount.clearComposingText();
                                            }

                                        }
                                    });

                                }
                            }
                        }

                        @Override
                        public void onNothingSelected (AdapterView < ? > parent){

                        }

                    });
                } else if(selectedItem == 1){
                    mRate.setText("1 GBP = "+(valueOf(UkPound)));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){

                                mInramount.setText(valueOf(UkPound));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        Ukpound = (float) (v1 * (Double.valueOf(UkPound)));
                                        mInramount.setText(valueOf(Ukpound));
                                        mPreferenceManager.setINRCurrency(String.valueOf(Ukpound));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                mInramount.setText(valueOf(UkPound1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        Ukpound1 = (float) (v1 * Double.valueOf(UkPound1));
                                        mInramount.setText(valueOf(Ukpound1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(Ukpound1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                        *//*mRate.setText("1 GBP = "+(valueOf(UkPound1)));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                mInramount.setText(valueOf(UkPound2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        Ukpound2 = (float) (v1 * Double.valueOf(UkPound2));
                                        mInramount.setText(valueOf(Ukpound2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(Ukpound2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                        *//*mRate.setText("1 GBP = "+(valueOf(UkPound2)));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if(selectedItem == 2) {
                    mRate.setText("1 EUR = "+ valueOf(Euro));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){

                                mInramount.setText(valueOf(Euro));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        euro = (float) (v1 * (Double.valueOf(Euro)));
                                        mInramount.setText(valueOf(euro));
                                        mPreferenceManager.setINRCurrency(String.valueOf(euro));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                mInramount.setText(valueOf(Euro1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        euro1 = (float) (v1 * Double.valueOf(Euro1));
                                        mInramount.setText(valueOf(euro1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(euro1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                        *//*mRate.setText("1 EUR = "+ valueOf(Euro1));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                mInramount.setText(valueOf(Euro2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        euro2 = (float) (v1 * Double.valueOf(Euro2));
                                        mInramount.setText(valueOf(euro2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(euro2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));
                                        *//*mRate.setText("1 EUR = "+ valueOf(Euro2));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else if(selectedItem == 3){
                    mRate.setText("1 AUD ="+valueOf(Australia));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){

                                mInramount.setText(valueOf(Australia));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        australia = (float) (v1 * (Double.valueOf(Australia)));
                                        mInramount.setText(valueOf(australia));
                                        mPreferenceManager.setINRCurrency(String.valueOf(australia));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                mInramount.setText(valueOf(Australia1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        australia1 = (float) (v1 * Double.valueOf(Australia1));
                                        mInramount.setText(valueOf(australia1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(australia1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        *//*mRate.setText("1 AUD ="+valueOf(Australia1));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                mInramount.setText(valueOf(Australia2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        australia2 = (float) (v1 * Double.valueOf(Australia2));
                                        mInramount.setText(valueOf(australia2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(australia2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        *//*mRate.setText("1 AUD ="+valueOf(Australia2));*//*
                                    }


                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if(selectedItem == 4){
                    mRate.setText("1 CAD ="+valueOf(Canada));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){

                                mInramount.setText(valueOf(Canada));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        canada = (float) (v1 * (Double.valueOf(Canada)));
                                        mInramount.setText(valueOf(canada));
                                        mPreferenceManager.setINRCurrency(String.valueOf(canada));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                mInramount.setText(valueOf(Canada1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        canada1 = (float) (v1 * Double.valueOf(Canada1));
                                        mInramount.setText(valueOf(canada1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(canada1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        *//*mRate.setText("1 CAD ="+valueOf(Canada1));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                mInramount.setText(valueOf(Canada2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        canada2 = (float) (v1 * Double.valueOf(Canada2));
                                        mInramount.setText(valueOf(canada2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(canada2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                       *//* mRate.setText("1 CAD ="+valueOf(Canada2));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if(selectedItem == 5){
                    mRate.setText("1 UAE = "+ valueOf(UAE));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){
                                mInramount.setText(valueOf(UAE));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        uae = (float) (v1 * (Double.valueOf(UAE)));
                                        mInramount.setText(valueOf(uae));
                                        mPreferenceManager.setINRCurrency(String.valueOf(uae));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                mInramount.setText(valueOf(UAE1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        uae1 = (float) (v1 * Double.valueOf(UAE1));
                                        mInramount.setText(valueOf(uae1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(uae1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        mRate.setText("1 UAE = "+ valueOf(UAE1));
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                mInramount.setText(valueOf(UAE2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        uae2 = (float) (v1 * Double.valueOf(UAE2));
                                        mInramount.setText(valueOf(uae2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(uae2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        *//*mRate.setText("1 UAE = "+ valueOf(UAE2));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else if(selectedItem == 6){
                    mRate.setText("1 SGD = "+valueOf(Singapore));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){
                                mInramount.setText(valueOf(Singapore));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        singapore = (float) (v1 * (Double.valueOf(Singapore)));
                                        mInramount.setText(valueOf(singapore));
                                        mPreferenceManager.setINRCurrency(String.valueOf(singapore));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                mInramount.setText(valueOf(Singapore1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        singapore1 = (float) (v1 * Double.valueOf(Singapore1));
                                        mInramount.setText(valueOf(singapore1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(singapore1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        *//*mRate.setText("1 SGD = "+valueOf(Singapore1));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                mInramount.setText(valueOf(Singapore2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        singapore2 = (float) (v1 * Double.valueOf(Singapore2));
                                        mInramount.setText(valueOf(singapore2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(singapore2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        *//*mRate.setText("1 SGD = "+valueOf(Singapore2));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }else if(selectedItem == 7){
                    mRate.setText("1JPY = "+valueOf(Japan));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){
                                mInramount.setText(valueOf(Japan));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                         japan = (float) (v1 * (Double.valueOf(Japan)));
                                        mInramount.setText(valueOf(japan));
                                        mPreferenceManager.setINRCurrency(String.valueOf(japan));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                mInramount.setText(valueOf(Japan1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        japan1 = (float) (v1 * Double.valueOf(Japan1));
                                        mInramount.setText(valueOf(japan1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(japan1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        *//*mRate.setText("1JPY = "+valueOf(Japan1));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                mInramount.setText(valueOf(Japan2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        japan2 = (float) (v1 * Double.valueOf(Japan2));
                                        mInramount.setText(valueOf(japan2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(japan2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                       *//* mRate.setText("1JPY = "+valueOf(Japan2));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if(selectedItem == 8){
                    mRate.setText("1 MYR = "+valueOf(Malaysia));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){
                                mInramount.setText(valueOf(Malaysia));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        malaysia = (float) (v1 * (Double.valueOf(Malaysia)));
                                        mInramount.setText(valueOf(malaysia));
                                        mPreferenceManager.setINRCurrency(String.valueOf(malaysia));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                mInramount.setText(valueOf(Malaysia1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        malaysia1 = (float) (v1 * Double.valueOf(Malaysia1));
                                        mInramount.setText(valueOf(malaysia1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(malaysia1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                       *//* mRate.setText("1 MYR = "+valueOf(Malaysia1));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                mInramount.setText(valueOf(Malaysia2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        malaysia2 = (float) (v1 * Double.valueOf(Malaysia2));
                                        mInramount.setText(valueOf(malaysia2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(malaysia2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                       *//* mRate.setText("1 MYR = "+valueOf(Malaysia2));*//*
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if(selectedItem == 9){
                    mRate.setText("1 HKD = "+valueOf(HongKong));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){
                                mInramount.setText(valueOf(HongKong));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        hongKong = (float) (v1 * (Double.valueOf(HongKong)));
                                        mInramount.setText(valueOf(hongKong));
                                        mPreferenceManager.setINRCurrency(String.valueOf(hongKong));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                *//*mRate.setText("1 HKD = "+valueOf(HongKong1));*//*
                                mInramount.setText(valueOf(HongKong1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        hongKong1 = (float) (v1 * Double.valueOf(HongKong1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(hongKong1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        mInramount.setText(valueOf(hongKong1));

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                *//*mRate.setText("1 HKD = "+valueOf(HongKong2));*//*
                                mInramount.setText(valueOf(HongKong2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        hongKong2 = (float) (v1 * Double.valueOf(HongKong2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(hongKong2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        mInramount.setText(valueOf(hongKong2));

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if(selectedItem == 10){
                    mRate.setText("1 SAR = "+valueOf(Saudi));
                    mProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            long selectedItem = parent.getItemIdAtPosition(position);
                            if(selectedItem == 1){

                                mInramount.setText(valueOf(Saudi));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        saudi = (float) (v1 * (Double.valueOf(Saudi)));
                                        mPreferenceManager.setINRCurrency(String.valueOf(saudi));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        mInramount.setText(valueOf(saudi));

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 2){
                                *//*mRate.setText("1 SAR = "+valueOf(Saudi1));*//*
                                mInramount.setText(valueOf(Saudi1));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                        saudi1 = (float) (v1 * Double.valueOf(Saudi1));
                                        mPreferenceManager.setINRCurrency(String.valueOf(saudi1));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        mInramount.setText(valueOf(saudi1));

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            } else if(selectedItem == 3){
                                *//*mRate.setText("1 SAR = "+valueOf(Saudi2));*//*
                                mInramount.setText(valueOf(Saudi2));
                                mFXamount.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                mFXamount.getText().toString():"0");
                                         saudi2 = (float) (v1 * Double.valueOf(Saudi2));
                                        mPreferenceManager.setINRCurrency(String.valueOf(saudi2));
                                        mPreferenceManager.setForeignCurrency(String.valueOf(v1));

                                        mInramount.setText(valueOf(saudi2));

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        if(s.length()== 0){
                                            mInramount.clearComposingText();
                                        }

                                    }
                                });

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


*/
    }

}





