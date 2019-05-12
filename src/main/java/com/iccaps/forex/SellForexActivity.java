package com.iccaps.forex;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.iccaps.forex.Model.BranchItem;
import com.iccaps.forex.Model.CurrencyItem;
import com.iccaps.forex.Model.ProductItem;
import com.iccaps.forex.Model.PurposeItem;
import com.iccaps.forex.Model.StateItem;
import com.iccaps.forex.Model.TravellersItem;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Activity.launchClearStack;
import static com.iccaps.forex.app.Api.ADD_BUY_FOREX;
import static com.iccaps.forex.app.Api.ADD_SELL_FOREX;
import static com.iccaps.forex.app.Api.KEY_ADDRESS;
import static com.iccaps.forex.app.Api.KEY_ADDRESS_DELIVERY;
import static com.iccaps.forex.app.Api.KEY_BRANCH;
import static com.iccaps.forex.app.Api.KEY_CARD;
import static com.iccaps.forex.app.Api.KEY_CASH;
import static com.iccaps.forex.app.Api.KEY_CHEQUE;
import static com.iccaps.forex.app.Api.KEY_DATE;
import static com.iccaps.forex.app.Api.KEY_FXAMOUNT;
import static com.iccaps.forex.app.Api.KEY_ID;
import static com.iccaps.forex.app.Api.KEY_INRAMOUNT;
import static com.iccaps.forex.app.Api.KEY_NAME;
import static com.iccaps.forex.app.Api.KEY_PAYMENT_METHOD;
import static com.iccaps.forex.app.Api.KEY_PAYMENT_PLACE;
import static com.iccaps.forex.app.Api.KEY_PHONE;
import static com.iccaps.forex.app.Api.KEY_PRODUCT;
import static com.iccaps.forex.app.Api.KEY_PURPOSE;
import static com.iccaps.forex.app.Api.KEY_STATE;
import static com.iccaps.forex.app.Api.KEY_TRAVEL_COUNT;
import static com.iccaps.forex.app.Api.KEY_US;
import static com.iccaps.forex.app.Constant.SELECT_BRANCH;
import static com.iccaps.forex.app.Constant.SELECT_CURRENCY;
import static java.lang.String.valueOf;

public class SellForexActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private Context mContext;
    private Button mButtonSell;
    private TextView mAddress;
    private ImageView mProductRules, mCurrencyRules;
    private List<BranchItem> mBranchItem;
    private List<ProductItem> mProductItem;
    private List<CurrencyItem> mCurrencyItem;
    private ArrayAdapter<CurrencyItem> mCurrencyArrayAdapter;
    private ArrayAdapter<ProductItem> mProductArrayAdapter;
    private com.iccaps.forex.app.PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;
    private String dateh;
    private String number;
    private String Address1,Address2,Address3,Address4,Address5,Address6,Address7,Address8,Address9,Address10,Address11,Address12,Address13,Address14,Address15,Address16,Address17;
    private String Phone1,Phone2,Phone3,Phone4,Phone5,Phone6,Phone7,Phone8,Phone9,Phone10,Phone11,Phone12,Phone13,Phone14,Phone15,Phone16,Phone17;
    private String FxAmount,InrAmount,branch,currency;
    int product,Currency,Branch;
    float USdollar,Ukpound,euro,australia,canada,uae,singapore,japan,malaysia,hongKong,saudi;
    float USdollar1,Ukpound1,euro1,australia1,canada1,uae1,singapore1,japan1,malaysia1,hongKong1,saudi1;
    float USdollar2,Ukpound2,euro2,australia2,canada2,uae2,singapore2,japan2,malaysia2,hongKong2,saudi2;
    private TextView mRate;
    private Spinner mProduct, mCurrency,mCity;
    private EditText mFXamount;
    private TextView mInramount;
    private EditText mMobileNumber;
    private ArrayAdapter<BranchItem> mBranchArrayAdapter;
    private Double USDollar,UkPound,Euro,Australia,Canada,UAE,Singapore,Japan,Malaysia,HongKong,Saudi;
    private Double USDollar1,UkPound1,Euro1,Australia1,Canada1,UAE1,Singapore1,Japan1,Malaysia1,HongKong1,Saudi1;
    private Double USDollar2,UkPound2,Euro2,Australia2,Canada2,UAE2,Singapore2,Japan2,Malaysia2,HongKong2,Saudi2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_forex);
        initObjects();
        initCallbacks();
        setupToolbar();
        initSpinner();
        processAddress();
        getCurrency();
        displayAddress();
        processSpinners();
        getProduct();
        populateRates();


    }
    private void processSpinners() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Api.LIST_SPINNERS_LIST, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleBranchResponse(response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyErrorHandler.handle(mContext, error);

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "Dropdown details");
    }
    private void handleBranchResponse(JSONObject response) {
        {
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_BRANCH);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    int id = jsonObject.getInt(KEY_ID);
                    String name = jsonObject.getString(KEY_NAME);
                    mBranchItem.add(new BranchItem(id, name));
                }

                mBranchArrayAdapter.notifyDataSetChanged();
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getProduct() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Api.LIST_CURRENCY_SELL, null, new com.android.volley.Response.Listener<JSONObject>() {
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
    private void initObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContext = this;
        mAddress = (TextView) findViewById(R.id.input_branchaddress);
        mCity = (Spinner) findViewById(R.id.city);
        mButtonSell = (Button)findViewById(R.id.btn_continue);
        mButtonSell.setEnabled(true);
        mMobileNumber = (EditText) findViewById(R.id.input_number);
        mBranchItem = new ArrayList<>();
        mRate = (TextView) findViewById(R.id.dailyrate);
        mProductRules = (ImageView) findViewById(R.id.type_rule);
        mCurrencyRules = (ImageView) findViewById(R.id.amount_rule);
        mFXamount = (EditText) findViewById(R.id.fx_amount);
        mInramount = (TextView) findViewById(R.id.indrupees);
        mCurrency = (Spinner) findViewById(R.id.currency);
        mProduct = (Spinner) findViewById(R.id.type);
        mProductItem = new ArrayList<>();
        mProductArrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item_text, mProductItem);
        mCurrencyItem = new ArrayList<>();
        mProgressDialog = new ProgressDialog(mContext);
        mPreferenceManager = new PreferenceManager(this);
        mCurrencyArrayAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item_text,mCurrencyItem);
        mCurrencyArrayAdapter.add(new CurrencyItem(SELECT_CURRENCY));
        mBranchArrayAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item_text,mBranchItem);
        mBranchArrayAdapter.add(new BranchItem(SELECT_BRANCH));



    }
    private void initCallbacks(){
        mButtonSell.setOnClickListener(this);
        mCurrencyRules.setOnClickListener(this);
        mProductRules.setOnClickListener(this);

    }
    private void initSpinner() {
        /*mProductArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProduct.setAdapter(mProductArrayAdapter);
        */mCurrencyArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mCurrency.setAdapter(mCurrencyArrayAdapter);
        mBranchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCity.setAdapter(mBranchArrayAdapter);

    }


    @Override
    public void onClick(View v) {
        if (v == mButtonSell) {
            processPay();
        } else if (v == mCurrencyRules) {
            processCurrencyRules();
        } else if (v == mProductRules) {
            processProductRules();
        }

        }
    private void processPay() {
        FxAmount = mFXamount.getText().toString().trim();
        InrAmount = mInramount.getText().toString().trim();
        currency = String.valueOf(mCurrencyItem.get(mCurrency.getSelectedItemPosition()).getid());
        branch = String.valueOf(mBranchItem.get(mCity.getSelectedItemPosition()).getid());
        product = mProduct.getSelectedItemPosition();
        if (validateInput(FxAmount,product,Currency,Branch)) {
            promptBookingDialog();

        }
    }


    private Boolean validateInput(String FxAmount, int product,int Currency,int Branch) {
        product = mProduct.getSelectedItemPosition();
        Currency = mCurrency.getSelectedItemPosition();
        Branch = mCity.getSelectedItemPosition();
        if (TextUtils.isEmpty(FxAmount)) {
            mFXamount.requestFocus();
            mFXamount.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Fx Amount cannot be empty"));
            return false;
        } else if (FxAmount.length() > 5) {
            mFXamount.requestFocus();
            mFXamount.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_length),
                            "Enter FxAmount less than", 5, "digits"));
            return false;
        } else if(product == 0){
            ToastBuilder.build(mContext,"Please Select a Type");
            return false;
        } else if(Currency == 0){
            ToastBuilder.build(mContext,"Please Select a Currency");
            return false;
        } else if(Branch == 0){
            ToastBuilder.build(mContext,"Please Select a Branch city");
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
                getOrderRequest(FxAmount, InrAmount, currency,branch,product);
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
    private void getOrderRequest(String FxAmount, String InrAmount, String currency, String branch, int product) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_FXAMOUNT, FxAmount);
            jsonObject.put(KEY_STATE, currency);
            jsonObject.put(KEY_BRANCH,branch);
            jsonObject.put(KEY_INRAMOUNT, InrAmount);
            jsonObject.put(KEY_PRODUCT,product);


        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ADD_SELL_FOREX, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                ToastBuilder.build(mContext, "Order placed successfully");
                logoutUser();
                launchClearStack(mContext,MainActivity.class);
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


    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Sell Forex");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }
    private void processAddress() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Api.LIST_ADDRESS_PHONE, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleAddress1(response);
                handleAddress2(response);
                handleAddress3(response);
                handleAddress4(response);
                handleAddress5(response);
                handleAddress6(response);
                handleAddress7(response);
                handleAddress8(response);
                handleAddress9(response);
                handleAddress10(response);
                handleAddress11(response);
                handleAddress12(response);
                handleAddress13(response);
                handleAddress14(response);
                handleAddress15(response);
                handleAddress16(response);
                handleAddress17(response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyErrorHandler.handle(mContext, error);

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "Dropdown details");

    }

    private void handleAddress1(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                int id = jsonObject.getInt(KEY_ID);
                Address1 = jsonObject.getString(KEY_ADDRESS);
                Phone1 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress2(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(1);

                int id = jsonObject.getInt(KEY_ID);
                Address2 = jsonObject.getString(KEY_ADDRESS);
                Phone2 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress3(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(2);

                int id = jsonObject.getInt(KEY_ID);
                Address3 = jsonObject.getString(KEY_ADDRESS);
                Phone3 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress4(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(3);

                int id = jsonObject.getInt(KEY_ID);
                Address4 = jsonObject.getString(KEY_ADDRESS);
                Phone4 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress5(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(4);

                int id = jsonObject.getInt(KEY_ID);
                Address5 = jsonObject.getString(KEY_ADDRESS);
                Phone5 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress6(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(5);

                int id = jsonObject.getInt(KEY_ID);
                Address6 = jsonObject.getString(KEY_ADDRESS);
                Phone6 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress7(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(6);

                int id = jsonObject.getInt(KEY_ID);
                Address7 = jsonObject.getString(KEY_ADDRESS);
                Phone7 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress8(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(7);

                int id = jsonObject.getInt(KEY_ID);
                Address8 = jsonObject.getString(KEY_ADDRESS);
                Phone8 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress9(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(8);

                int id = jsonObject.getInt(KEY_ID);
                Address9 = jsonObject.getString(KEY_ADDRESS);
                Phone9 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress10(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(9);

                int id = jsonObject.getInt(KEY_ID);
                Address10 = jsonObject.getString(KEY_ADDRESS);
                Phone10 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress11(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(10);

                int id = jsonObject.getInt(KEY_ID);
                Address11 = jsonObject.getString(KEY_ADDRESS);
                Phone11 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress12(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(11);

                int id = jsonObject.getInt(KEY_ID);
                Address12 = jsonObject.getString(KEY_ADDRESS);
                Phone12 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress13(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(12);

                int id = jsonObject.getInt(KEY_ID);
                Address13 = jsonObject.getString(KEY_ADDRESS);
                Phone13 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress14(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(13);

                int id = jsonObject.getInt(KEY_ID);
                Address14 = jsonObject.getString(KEY_ADDRESS);
                Phone14 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress15(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(14);

                int id = jsonObject.getInt(KEY_ID);
                Address15 = jsonObject.getString(KEY_ADDRESS);
                Phone15 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress16(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(15);

                int id = jsonObject.getInt(KEY_ID);
                Address16 = jsonObject.getString(KEY_ADDRESS);
                Phone16 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress17(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(16);

                int id = jsonObject.getInt(KEY_ID);
                Address17 = jsonObject.getString(KEY_ADDRESS);
                Phone17 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void displayAddress() {
        mCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                long selectedItem = parent.getItemIdAtPosition(position);
                if(selectedItem == 0){
                    mAddress.setText("Select Your Branch");
                }

               else if(selectedItem == 1){
                    mAddress.setText(String.valueOf(Address1 +"\n"+"Phone:"+String.valueOf(Phone1)));
                } else if(selectedItem == 2){
                    mAddress.setText(String.valueOf(Address2)+"\n"+"Phone:"+String.valueOf(Phone2));
                } else if(selectedItem == 3){
                    mAddress.setText(String.valueOf(Address3)+"\n"+"Phone:"+String.valueOf(Phone3));
                } else if(selectedItem == 4){
                    mAddress.setText(String.valueOf(Address4)+"\n"+"Phone:"+String.valueOf(Phone4));
                } else if(selectedItem == 5){
                    mAddress.setText(String.valueOf(Address5)+"\n"+"Phone:"+String.valueOf(Phone5));
                } else if(selectedItem == 6){
                    mAddress.setText(String.valueOf(Address6)+"\n"+"Phone:"+String.valueOf(Phone6));
                } else if(selectedItem == 7){
                    mAddress.setText(String.valueOf(Address7)+"\n"+"Phone:"+String.valueOf(Phone7));
                } else if(selectedItem == 8){
                    mAddress.setText(String.valueOf(Address8)+"\n"+"Phone:"+String.valueOf(Phone8));
                } else if(selectedItem == 9){
                    mAddress.setText(String.valueOf(Address9)+"\n"+"Phone:"+String.valueOf(Phone9));
                } else if(selectedItem == 10){
                    mAddress.setText(String.valueOf(Address10)+"\n"+"Phone:"+String.valueOf(Phone10));
                } else if(selectedItem == 11){
                    mAddress.setText(String.valueOf(Address11)+"\n"+"Phone:"+String.valueOf(Phone11));
                } else if(selectedItem == 12){
                    mAddress.setText(String.valueOf(Address12)+"\n"+"Phone:"+String.valueOf(Phone12));
                } else if(selectedItem == 13){
                    mAddress.setText(String.valueOf(Address13)+"\n"+"Phone:"+String.valueOf(Phone13));
                } else if(selectedItem == 14){
                    mAddress.setText(String.valueOf(Address14)+"\n"+"Phone:"+String.valueOf(Phone14));
                } else if(selectedItem == 15){
                    mAddress.setText(String.valueOf(Address15)+"\n"+"Phone:"+String.valueOf(Phone15));
                } else if(selectedItem == 16){
                    mAddress.setText(String.valueOf(Address16)+"\n"+"Phone:"+String.valueOf(Phone16));
                } else if(selectedItem == 17){
                    mAddress.setText(String.valueOf(Address17)+"\n"+"Phone:"+String.valueOf(Phone17));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                Api.LIST_CARD_CURRENCY_SELL, null, new com.android.volley.Response.Listener<JSONObject>() {
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
                                                USdollar = (float) (v1 * (Double.valueOf(USDollar1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                } else if(USdollar == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                USdollar = (float) (v1 * (Double.valueOf(USDollar)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                } else if(USdollar == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                    } /*else if(selectedItem == 3){
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
                                               *//* mPreferenceManager.setINRCurrency(String.valueOf(USdollar));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
*//*
                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mFXamount.clearComposingText();
                                                }

                                            }
                                        });
                                    }*/
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
                                                float value = (float) (v1 * (Double.valueOf(UkPound1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                } else if(value == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                float value = (float) (v1 * (Double.valueOf(UkPound)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(value == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                    } /*else if(selectedItem == 3){
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
  *//*                                              mPreferenceManager.setINRCurrency(String.valueOf(value));
                                                mPreferenceManager.setForeignCurrency(String.valueOf(v1));
*//*
                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mFXamount.clearComposingText();
                                                }

                                            }
                                        });
                                    }*/
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
                                                euro = (float) (v1 * (Double.valueOf(Euro1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(euro == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                euro = (float) (v1 * (Double.valueOf(Euro)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(euro == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(euro));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } /*else if(selectedItem == 3){
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
                                    }*/
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
                                                australia = (float) (v1 * (Double.valueOf(Australia1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(australia == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                australia = (float) (v1 * (Double.valueOf(Australia)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(australia == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(australia));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } /*else if(selectedItem == 3){
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
                                    }*/
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
                                                canada = (float) (v1 * (Double.valueOf(Canada1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(canada == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                canada = (float) (v1 * (Double.valueOf(Canada)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(canada == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(canada));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } /*else if(selectedItem == 3){
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
                                    }*/
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
                                                uae = (float) (v1 * (Double.valueOf(UAE1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(uae == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                uae = (float) (v1 * (Double.valueOf(UAE)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(uae == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(uae));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } /*else if(selectedItem == 3){
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
                                    }*/
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
                                                singapore = (float) (v1 * (Double.valueOf(Singapore1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(singapore == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                singapore = (float) (v1 * (Double.valueOf(Singapore)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(singapore == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(singapore));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } /*else if(selectedItem == 3){
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
                                    }*/
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
                                                japan = (float) (v1 * (Double.valueOf(Japan1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(japan == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                japan = (float) (v1 * (Double.valueOf(Japan)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(japan == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(japan));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } /*else if(selectedItem == 3){
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
                                    }*/
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
                                                malaysia = (float) (v1 * (Double.valueOf(Malaysia1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(malaysia == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                malaysia = (float) (v1 * (Double.valueOf(Malaysia)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(malaysia == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(malaysia));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } /*else if(selectedItem == 3){
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
                                    }*/
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
                                                hongKong = (float) (v1 * (Double.valueOf(HongKong1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(hongKong == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                hongKong = (float) (v1 * (Double.valueOf(HongKong)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(hongKong == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(hongKong));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });
                                    } /*else if(selectedItem == 3){
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
                                    }*/
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
                                                saudi = (float) (v1 * (Double.valueOf(Saudi1)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(saudi == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
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
                                                saudi = (float) (v1 * (Double.valueOf(Saudi)));
                                                if(mFXamount.length()>5){
                                                    mFXamount.requestFocus();
                                                    mFXamount.setError("Enter the Amount less than 5 digits");
                                                }else if(saudi == 0){
                                                    mButtonSell.setEnabled(false);
                                                }
                                                mInramount.setText(valueOf(saudi));

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (s.length() == 0) {
                                                    mInramount.clearComposingText();
                                                }

                                            }
                                        });

                                    } /*else if(selectedItem == 3){
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

                                    }*/
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




}
