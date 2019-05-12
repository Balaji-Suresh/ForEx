package com.iccaps.forex;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iccaps.forex.Model.CurrencyItem;
import com.iccaps.forex.Model.ProductItem;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.Api;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.VolleyErrorHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Api.KEY_CARD;
import static com.iccaps.forex.app.Api.KEY_CASH;
import static com.iccaps.forex.app.Api.KEY_CHEQUE;
import static com.iccaps.forex.app.Api.KEY_ID;
import static com.iccaps.forex.app.Api.KEY_NAME;
import static com.iccaps.forex.app.Api.KEY_PRODUCT;
import static com.iccaps.forex.app.Api.KEY_STATE;
import static com.iccaps.forex.app.Api.KEY_US;
import static com.iccaps.forex.app.Constant.SELECT_CURRENCY;
import static java.lang.String.valueOf;

public class CurrencyConvertorActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private Context mContext;
    private ImageView mProductRules, mCurrencyRules;
    private List<ProductItem> mProductItem;
    private List<CurrencyItem> mCurrencyItem;
    private ArrayAdapter<CurrencyItem> mCurrencyArrayAdapter;
    private ArrayAdapter<ProductItem> mProductArrayAdapter;
    private EditText mFXamount;
    private TextView mInramount;
    private TextView mRate, mCityTextView, mStateTextView;
    private Spinner mProduct, mCurrency;
    float USdollar,Ukpound,euro,australia,canada,uae,singapore,japan,malaysia,hongKong,saudi;
    float USdollar1,Ukpound1,euro1,australia1,canada1,uae1,singapore1,japan1,malaysia1,hongKong1,saudi1;
    float USdollar2,Ukpound2,euro2,australia2,canada2,uae2,singapore2,japan2,malaysia2,hongKong2,saudi2;
    private String UsDollar,Pound,Euroo,AustraliaDolar,CanadaDollar,UAEDIR,SingaporeDollar,JapaneseYen,MalaysiaRing,Hongkong,SaudiRid;
    private Double USDollar,UkPound,Euro,Australia,Canada,UAE,Singapore,Japan,Malaysia,HongKong,Saudi;
    private Double USDollar1,UkPound1,Euro1,Australia1,Canada1,UAE1,Singapore1,Japan1,Malaysia1,HongKong1,Saudi1;
    private Double USDollar2,UkPound2,Euro2,Australia2,Canada2,UAE2,Singapore2,Japan2,Malaysia2,HongKong2,Saudi2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_convertor);
        initObjects();
        initCallbacks();
        initSpinner();
        setupToolbar();
        getProduct();
        getCurrency();
        populateRates();
        populateCurrency();


    }



    private void getProduct() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Api.LIST_SPINNERS_LIST, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /*handleProductResponse(response);*/
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
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Currency Convertor");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private void initSpinner() {
      /*  mProductArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProduct.setAdapter(mProductArrayAdapter);
      */  mCurrencyArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mCurrency.setAdapter(mCurrencyArrayAdapter);

    }


    private void initObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContext = this;
        mProductRules = (ImageView) findViewById(R.id.type_rule);
        mCurrencyRules = (ImageView) findViewById(R.id.amount_rule);
        mFXamount = (EditText) findViewById(R.id.fx_amount);
        mInramount = (TextView) findViewById(R.id.indrupees);
        mRate = (TextView)findViewById(R.id.dailyrate);
        mCurrency = (Spinner) findViewById(R.id.currency);
        mProduct = (Spinner) findViewById(R.id.type);
        /*mButtonBuy = (Button) findViewById(R.id.buy);*/
        mProductItem = new ArrayList<>();
        mProductArrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item_text, mProductItem);
        mCurrencyItem = new ArrayList<>();
        mCurrencyArrayAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item_text,mCurrencyItem);
        mCurrencyArrayAdapter.add(new CurrencyItem(SELECT_CURRENCY));


    }

    private void initCallbacks() {
        mCurrencyRules.setOnClickListener(this);
        mProductRules.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      if (v == mCurrencyRules) {
            processCurrencyRules();
        } else if (v == mProductRules) {
            processProductRules();
        }

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

   /* private void promptBookingDialog() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_session, null);
        ImageView imageViewClose = (ImageView) mView.findViewById(R.id.img_close);
        RadioButton mGuest = (RadioButton) mView.findViewById(R.id.rdo_guest);
        RadioButton mLogin = (RadioButton) mView.findViewById(R.id.rdo_existing_user);
        final Button mContinue = (Button) mView.findViewById(R.id.btn_add1);
        final TextView mRegister = (TextView) mView.findViewById(R.id.txt_register);
        mRegister.setVisibility(View.GONE);
        final Button mLoginButton = (Button) mView.findViewById(R.id.login);
        mLoginButton.setVisibility(View.GONE);
        final EditText mNumber = (EditText) mView.findViewById(R.id.input_mob_number);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginButton.setVisibility(View.VISIBLE);
                mRegister.setVisibility(View.VISIBLE);
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
                Activity.launch(mContext, TravelDetailActivity.class);
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.launch(mContext, Signup.class);
            }
        });
    }
*/



    private void handleCurrencyResponse(JSONObject response) {
        {
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_STATE);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    int id = jsonObject.getInt(KEY_ID);
                    String name = jsonObject.getString(KEY_NAME);
                    mCurrencyItem.add(new CurrencyItem(id, name));
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


    private void populateCurrency() {


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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                USdollar = (float) (v1 * (Double.valueOf(USDollar)));
                                                mInramount.setText(valueOf(USdollar));
                                                /*mPreferenceManager.setINRCurrency(String.valueOf(USdollar));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
*/
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
                                               /* mPreferenceManager.setINRCurrency(String.valueOf(USdollar));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
*/
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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                USdollar = (float) (v1 * (Double.valueOf(USDollar)));
                                                mInramount.setText(valueOf(USdollar));
                                               /* mPreferenceManager.setINRCurrency(String.valueOf(USdollar));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
*/
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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                float value = (float) (v1 * (Double.valueOf(UkPound)));
                                                mInramount.setText(valueOf(value));
                                                /*mPreferenceManager.setINRCurrency(String.valueOf(value));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
*/
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
                                        mFXamount.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                float value = (float) (v1 * (Double.valueOf(UkPound1)));
                                                mInramount.setText(valueOf(value));
  /*                                              mPreferenceManager.setINRCurrency(String.valueOf(value));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
*/
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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                float value = (float) (v1 * (Double.valueOf(UkPound)));
                                                mInramount.setText(valueOf(value));
  /*                                              mPreferenceManager.setINRCurrency(String.valueOf(value));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
*/
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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                euro = (float) (v1 * (Double.valueOf(Euro)));
                                                mInramount.setText(valueOf(euro));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                euro = (float) (v1 * (Double.valueOf(Euro1)));
                                                mInramount.setText(valueOf(euro));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                euro = (float) (v1 * (Double.valueOf(Euro)));
                                                mInramount.setText(valueOf(euro));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                australia = (float) (v1 * (Double.valueOf(Australia)));
                                                mInramount.setText(valueOf(australia));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                australia = (float) (v1 * (Double.valueOf(Australia1)));
                                                mInramount.setText(valueOf(australia));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                australia = (float) (v1 * (Double.valueOf(Australia)));
                                                mInramount.setText(valueOf(australia));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                canada = (float) (v1 * (Double.valueOf(Canada)));
                                                mInramount.setText(valueOf(canada));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                canada = (float) (v1 * (Double.valueOf(Canada1)));
                                                mInramount.setText(valueOf(canada));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                canada = (float) (v1 * (Double.valueOf(Canada)));
                                                mInramount.setText(valueOf(canada));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                uae = (float) (v1 * (Double.valueOf(UAE)));
                                                mInramount.setText(valueOf(uae));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                uae = (float) (v1 * (Double.valueOf(UAE1)));
                                                mInramount.setText(valueOf(uae));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                uae = (float) (v1 * (Double.valueOf(UAE)));
                                                mInramount.setText(valueOf(uae));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                singapore = (float) (v1 * (Double.valueOf(Singapore)));
                                                mInramount.setText(valueOf(singapore));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                singapore = (float) (v1 * (Double.valueOf(Singapore1)));
                                                mInramount.setText(valueOf(singapore));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                singapore = (float) (v1 * (Double.valueOf(Singapore)));
                                                mInramount.setText(valueOf(singapore));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                japan = (float) (v1 * (Double.valueOf(Japan)));
                                                mInramount.setText(valueOf(japan));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                japan = (float) (v1 * (Double.valueOf(Japan1)));
                                                mInramount.setText(valueOf(japan));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                japan = (float) (v1 * (Double.valueOf(Japan)));
                                                mInramount.setText(valueOf(japan));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                malaysia = (float) (v1 * (Double.valueOf(Malaysia)));
                                                mInramount.setText(valueOf(malaysia));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                malaysia = (float) (v1 * (Double.valueOf(Malaysia1)));
                                                mInramount.setText(valueOf(malaysia));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                malaysia = (float) (v1 * (Double.valueOf(Malaysia)));
                                                mInramount.setText(valueOf(malaysia));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                hongKong = (float) (v1 * (Double.valueOf(HongKong)));
                                                mInramount.setText(valueOf(hongKong));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                hongKong = (float) (v1 * (Double.valueOf(HongKong1)));
                                                mInramount.setText(valueOf(hongKong));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                hongKong = (float) (v1 * (Double.valueOf(HongKong)));
                                                mInramount.setText(valueOf(hongKong));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                saudi = (float) (v1 * (Double.valueOf(Saudi)));
                                                mInramount.setText(valueOf(saudi));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                saudi = (float) (v1 * (Double.valueOf(Saudi1)));
                                                mInramount.setText(valueOf(saudi));

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
                                                float v1 = (float) Double.parseDouble(!mFXamount.getText().toString().isEmpty() ?
                                                        mFXamount.getText().toString() : "0");
                                                saudi = (float) (v1 * (Double.valueOf(Saudi)));
                                                mInramount.setText(valueOf(saudi));

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

    }}
