package com.iccaps.forex;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iccaps.forex.Model.BranchItem;
import com.iccaps.forex.Model.CurrencyItem;
import com.iccaps.forex.Model.PurposeItem;
import com.iccaps.forex.Model.StateItem;
import com.iccaps.forex.Model.TravellersItem;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.Api;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Activity.launchClearStack;
import static com.iccaps.forex.app.Api.KEY_ADDRESS;
import static com.iccaps.forex.app.Api.KEY_BRANCH;
import static com.iccaps.forex.app.Api.KEY_ID;
import static com.iccaps.forex.app.Api.KEY_MOBILE;
import static com.iccaps.forex.app.Api.KEY_NAME;
import static com.iccaps.forex.app.Api.KEY_PHONE;
import static com.iccaps.forex.app.Api.KEY_PURPOSE;
import static com.iccaps.forex.app.Api.KEY_TRAVEL;
import static com.iccaps.forex.app.Api.WELCOME_MSG;
import static com.iccaps.forex.app.Constant.SELECT_BRANCH;
import static com.iccaps.forex.app.Constant.SELECT_PURPOSE;
import static com.iccaps.forex.app.Constant.SELECT_TRAVEL;

public class BuyForexActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_ID = "id";
    private Context mContext;
    private Toolbar mToolbar;
    private EditText mMobileNumber;
    private TextView mInputDate,mAddress;
    private Button mButtonBuy;
    private ImageView mVerified;
    private ImageView mPurposerule,mTravelRules,mDate;
    private List<BranchItem> mBranchItem;
    private List<TravellersItem> mTravelItem;
    private List<PurposeItem> mPurposeItem;
    private List<StateItem> mStateItem;
    private ArrayAdapter<BranchItem> mBranchArrayAdapter;
    private ArrayAdapter<TravellersItem> mTravelArrayAdapter;
    private ArrayAdapter<PurposeItem>mPurposetArrayAdapter;
    private Spinner mTravel, mCity,mPurpose;
    private com.iccaps.forex.app.PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;
    private String dateh;
    private String name;
    private String Address1,Address2,Address3,Address4,Address5,Address6,Address7,Address8,Address9,Address10,Address11,Address12,Address13,Address14,Address15,Address16,Address17,Address18,Address19,Address20,Address21,Address22,Address23,Address24,Address25,Address26,Address27,Address28,Address29,Address30;
    private String Phone1,Phone2,Phone3,Phone4,Phone5,Phone6,Phone7,Phone8,Phone9,Phone10,Phone11,Phone12,Phone13,Phone14,Phone15,Phone16,Phone17,Phone18,Phone19,Phone20,Phone21,Phone22,Phone23,Phone24,Phone25,Phone26,Phone27,Phone28,Phone29,Phone30;
    private String Mobile;
    int Branch,Travel,Purpose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_forex);
        initObjects();
        initCallbacks();
        initSpinner();
        setupToolbar();
        processAddress();
        processSpinners();
        displayAddress();
        checkVerified();
        /*processBundle();*/
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
                handleAddress18(response);
                handleAddress19(response);
                handleAddress20(response);
                handleAddress21(response);
                handleAddress22(response);
                handleAddress23(response);
                handleAddress24(response);
                handleAddress25(response);
                handleAddress26(response);
                handleAddress28(response);
                handleAddress29(response);
                handleAddress30(response);
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

    private void handleAddress18(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(17);

                int id = jsonObject.getInt(KEY_ID);
                Address18 = jsonObject.getString(KEY_ADDRESS);
                Phone18 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleAddress19(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(18);

                int id = jsonObject.getInt(KEY_ID);
                Address19 = jsonObject.getString(KEY_ADDRESS);
                Phone19 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleAddress20(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(19);

                int id = jsonObject.getInt(KEY_ID);
                Address20 = jsonObject.getString(KEY_ADDRESS);
                Phone20 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleAddress21(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(20);

                int id = jsonObject.getInt(KEY_ID);
                Address21 = jsonObject.getString(KEY_ADDRESS);
                Phone21 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleAddress22(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(21);

                int id = jsonObject.getInt(KEY_ID);
                Address22 = jsonObject.getString(KEY_ADDRESS);
                Phone22 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress23(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(22);

                int id = jsonObject.getInt(KEY_ID);
                Address23 = jsonObject.getString(KEY_ADDRESS);
                Phone23 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress24(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(23);

                int id = jsonObject.getInt(KEY_ID);
                Address24 = jsonObject.getString(KEY_ADDRESS);
                Phone24 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleAddress25(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(24);

                int id = jsonObject.getInt(KEY_ID);
                Address25 = jsonObject.getString(KEY_ADDRESS);
                Phone25 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress26(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(25);

                int id = jsonObject.getInt(KEY_ID);
                Address26 = jsonObject.getString(KEY_ADDRESS);
                Phone26 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress27(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(26);

                int id = jsonObject.getInt(KEY_ID);
                Address27 = jsonObject.getString(KEY_ADDRESS);
                Phone27 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress28(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(27);

                int id = jsonObject.getInt(KEY_ID);
                Address28 = jsonObject.getString(KEY_ADDRESS);
                Phone28 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress29(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(28);

                int id = jsonObject.getInt(KEY_ID);
                Address29 = jsonObject.getString(KEY_ADDRESS);
                Phone29 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void handleAddress30(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray(KEY_ADDRESS);
            {
                JSONObject jsonObject = jsonArray.getJSONObject(29);

                int id = jsonObject.getInt(KEY_ID);
                Address30 = jsonObject.getString(KEY_ADDRESS);
                Phone30 = jsonObject.getString(KEY_PHONE);

            } Log.d("resp", response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



















    private void processSpinners() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Api.LIST_SPINNERS_LIST, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleBranchResponse(response);
                /*handleStateResponse(response);*/
                handleTravelResponse(response);
                handlePurposeResponse(response);

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

/*
    private void handleStateResponse(JSONObject response) {
        {
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_STATE);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    int id = jsonObject.getInt(KEY_ID);
                     String name = jsonObject.getString(KEY_NAME);
                        mState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                long selectedItem = parent.getItemIdAtPosition(position);
                                {

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    mStateItem.add(new StateItem(id, name));
                }
                mStateArrayAdapter.notifyDataSetChanged();
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
*/

    private void handleTravelResponse(JSONObject response) {
        {
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_TRAVEL);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    int id = jsonObject.getInt(KEY_ID);
                    String name = jsonObject.getString(KEY_NAME);
                    mTravelItem.add(new TravellersItem(id, name));
                }
                mTravelArrayAdapter.notifyDataSetChanged();
                Log.d("resp", response.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void handlePurposeResponse(JSONObject response) {
        {
            try {
                JSONArray jsonArray = response.getJSONArray(KEY_PURPOSE);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    int id = jsonObject.getInt(KEY_ID);
                    String name = jsonObject.getString(KEY_NAME);
                    mPurposeItem.add(new PurposeItem(id, name));
                }
                mPurposetArrayAdapter.notifyDataSetChanged();
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
        mPreferenceManager = new com.iccaps.forex.app.PreferenceManager(this);
        mPurpose = (Spinner)findViewById(R.id.spinner1);
        mTravel = (Spinner) findViewById(R.id.spinner3);
        mAddress = (TextView) findViewById(R.id.input_branchaddress);
        mCity = (Spinner) findViewById(R.id.city);
        mPurposerule = (ImageView)findViewById(R.id.rule);
        mTravelRules = (ImageView)findViewById(R.id.travel_rules);
        mDate = (ImageView)findViewById(R.id.date);
        mMobileNumber = (EditText) findViewById(R.id.input_number);
        mButtonBuy = (Button) findViewById(R.id.buy);
        mInputDate = (TextView) findViewById(R.id.input_date);
        mInputDate.setText(getCurrentDate());
        mVerified = (ImageView) findViewById(R.id.verified);
        mVerified.setVisibility(View.GONE);
        mBranchItem = new ArrayList<>();
        mStateItem = new ArrayList<>();
        mTravelItem = new ArrayList<>();
        mPurposeItem = new ArrayList<>();
        mTravelArrayAdapter = new ArrayAdapter<>(mContext, R.layout.spinner_item_text, mTravelItem);
        mTravelArrayAdapter.add(new TravellersItem(SELECT_TRAVEL));
        mBranchArrayAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item_text,mBranchItem);
        mBranchArrayAdapter.add(new BranchItem(SELECT_BRANCH));
        mPurposetArrayAdapter = new ArrayAdapter<PurposeItem>(mContext,R.layout.spinner_item_text,mPurposeItem);
        mPurposetArrayAdapter.add(new PurposeItem(SELECT_PURPOSE));


    }

    private void initCallbacks() {
        mButtonBuy.setOnClickListener(this);
        mInputDate.setOnClickListener(this);
        mPurposerule.setOnClickListener(this);
        mTravelRules.setOnClickListener(this);
        mDate.setOnClickListener(this);

    }
    private void initSpinner() {
        mBranchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCity.setAdapter(mBranchArrayAdapter);
        mPurposetArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPurpose.setAdapter(mPurposetArrayAdapter);
        mTravelArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mTravel.setAdapter(mTravelArrayAdapter);
    }




    @Override
    public void onClick(View v) {
        if (v == mButtonBuy) {
            Mobile = mMobileNumber.getText().toString().trim();
            mPreferenceManager.setPhone(Mobile);
            if (validateInput(Mobile,Branch,Travel,Purpose)) {
                processWelcome();
                processBundle();


            }

        } else if (v == mInputDate) {
            promptDatePickerDialog(mInputDate);
        } else if (v == mPurposerule){
            processPurposeRule();
        } else if(v == mTravelRules){
            processTravelDetails();
        } else if(v == mDate){
            processDate();
        }
    }



    private void processDate() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_date, null);
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

    private void processTravelDetails() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_travellers, null);
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

    private void processPurposeRule() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_purpose, null);
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

    private void processWelcome() {
        String Mobile = mMobileNumber.getText().toString().trim();
        String branch = String.valueOf(mBranchItem.get(mCity.getSelectedItemPosition()).getid());
        getOrderRequest(Mobile,branch);

    }


    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Buy Forex");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    private void promptDatePickerDialog(TextView editDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = (calendar.get(Calendar.MONTH));
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        openDateDialog(editDate, year, month + 1, dayOfMonth);
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = (calendar.get(Calendar.MONTH));
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateh = getDate(year, month + 1, dayOfMonth);
        mPreferenceManager.setDate(dateh);
        return getDate(year, month + 1, dayOfMonth);
    }


    private String getDate(int year, int month, int dayOfMonth) {
        return year + "-" + (month < 10 ? "0" + month  : month ) + "-" + (dayOfMonth < 10 ? "0"
                + dayOfMonth : dayOfMonth);
    }

    private void openDateDialog(final TextView editDate, int year, int month,
                                int dayOfMonth) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateh = getDate(year, month + 1, dayOfMonth);
                        editDate.setText(getDate(year, month + 1, dayOfMonth));
                        mPreferenceManager.setDate(dateh);
                        /*ToastBuilder.build(mContext,mPreferenceManager.getDate());
                        Log.d("date",mPreferenceManager.getDate());*/
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private Boolean validateInput(String Mobile,int Branch,int Travel,int Purpose) {
        Branch = mCity.getSelectedItemPosition();
        Travel = mTravel.getSelectedItemPosition();
        Purpose = mPurpose.getSelectedItemPosition();
        if(Branch == 0){
            ToastBuilder.build(mContext,"Select A Branch");
            return false;
        }
        else if(Travel == 0){
            ToastBuilder.build(mContext,"Select Number Of Travellers");
            return false;
        }
        else if(Purpose == 0){
            ToastBuilder.build(mContext,"Select A Purpose");
            return false;
        }
        else if (TextUtils.isEmpty(Mobile)) {
            mMobileNumber.requestFocus();
            mMobileNumber.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_empty),
                            "Contact Number"));
            return false;
        } else if (Mobile.length() < 10) {
            mMobileNumber.requestFocus();
            mMobileNumber.setError(
                    String.format(Locale.getDefault(), getString(R.string.error_length),
                            "Contact Number", 10, "digits"));
            return false;
        }
        return true;
    }
    private void getOrderRequest(String Mobile,String branch) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_MOBILE, Mobile);
            jsonObject.put(KEY_BRANCH,branch);
            /*mPreferenceManager.setPhone(Mobile);*/

        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                WELCOME_MSG, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /*launchClearStack(mContext, CurrencyActivity.class);*/
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyErrorHandler.handle(mContext, error);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "Create_lead");
    }
    private void checkVerified() {
        mMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 10){
                    mVerified.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=10){
                    mVerified.setVisibility(View.GONE);
                }

            }
        });

    }
    private void processBundle() {
        Intent intent = new Intent(getBaseContext(), CurrencyActivity.class);
        String travel = String.valueOf(mTravelItem.get(mTravel.getSelectedItemPosition()).getid());
        String purpose = String.valueOf(mPurposeItem.get(mPurpose.getSelectedItemPosition()).getid());
        String branch = String.valueOf(mBranchItem.get(mCity.getSelectedItemPosition()).getid());
        intent.putExtra("TRAVEL", travel);
        intent.putExtra("PURPOSE", purpose);
        intent.putExtra("BRANCH", branch);
        startActivity(intent);



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
                else if(selectedItem == 18){
                    mAddress.setText(String.valueOf(Address18)+"\n"+"Phone:"+String.valueOf(Phone18));
                }
                else if(selectedItem == 19){
                    mAddress.setText(String.valueOf(Address19)+"\n"+"Phone:"+String.valueOf(Phone19));
                }
                else if(selectedItem == 20){
                    mAddress.setText(String.valueOf(Address20)+"\n"+"Phone:"+String.valueOf(Phone20));
                }
                else if(selectedItem == 21){
                    mAddress.setText(String.valueOf(Address21)+"\n"+"Phone:"+String.valueOf(Phone21));
                }
                else if(selectedItem == 22){
                    mAddress.setText(String.valueOf(Address22)+"\n"+"Phone:"+String.valueOf(Phone22));
                }
                else if(selectedItem == 23){
                    mAddress.setText(String.valueOf(Address23)+"\n"+"Phone:"+String.valueOf(Phone23));
                }
                else if(selectedItem == 24){
                    mAddress.setText(String.valueOf(Address24)+"\n"+"Phone:"+String.valueOf(Phone24));
                }
                else if(selectedItem == 25){
                    mAddress.setText(String.valueOf(Address25)+"\n"+"Phone:"+String.valueOf(Phone25));
                }
                else if(selectedItem == 26){
                    mAddress.setText(String.valueOf(Address26)+"\n"+"Phone:"+String.valueOf(Phone26));
                }
                else if(selectedItem == 27){
                    mAddress.setText(String.valueOf(Address27)+"\n"+"Phone:"+String.valueOf(Phone27));
                }
                else if(selectedItem == 28){
                    mAddress.setText(String.valueOf(Address28)+"\n"+"Phone:"+String.valueOf(Phone28));
                }
                else if(selectedItem == 29){
                    mAddress.setText(String.valueOf(Address29)+"\n"+"Phone:"+String.valueOf(Phone29));
                }
                else if(selectedItem == 30){
                    mAddress.setText(String.valueOf(Address30)+"\n"+"Phone:"+String.valueOf(Phone30));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



}


