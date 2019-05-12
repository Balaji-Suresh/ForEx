package com.iccaps.forex;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.iccaps.forex.Model.BranchItem;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.Api;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
import static com.iccaps.forex.app.Api.KEY_PHONE_NUMBER;
import static com.iccaps.forex.app.Api.MONEY_TRANSFER_ACTIVITY;
import static com.iccaps.forex.app.Api.RELOAD_CARD_ACTIVITY;
import static com.iccaps.forex.app.Api.WELCOME_MSG;
import static com.iccaps.forex.app.Constant.SELECT_BRANCH;

public class ReloadCardActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Toolbar mToolbar;
    private EditText mMobileNumber;
    private Button mButtonBuy;
    private ImageView mVerified;
    private TextView mInputDate,mAddress;
    private com.iccaps.forex.app.PreferenceManager mPreferenceManager;
    private List<BranchItem> mBranchItem;
    private ArrayAdapter<BranchItem> mBranchArrayAdapter;
    private Spinner mTravel, mCity,mPurpose;
    private LinearLayout mSuccess;
    private TextInputEditText mName;
    private ProgressDialog mProgressDialog;
    private String Address1,Address2,Address3,Address4,Address5,Address6,Address7,Address8,Address9,Address10,Address11,Address12,Address13,Address14,Address15,Address16,Address17;
    private String Phone1,Phone2,Phone3,Phone4,Phone5,Phone6,Phone7,Phone8,Phone9,Phone10,Phone11,Phone12,Phone13,Phone14,Phone15,Phone16,Phone17;
    private String Mobile,Name;
    int Branch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload_card);
        initObjects();
        initCallbacks();
        setupToolbar();
        checkVerified();
        initSpinners();
        displayAddress();
        processAddress();
        processSpinners();
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
    private void initObjects(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mContext = this;
        mMobileNumber = (EditText)findViewById(R.id.input_number);
        mButtonBuy = (Button)findViewById(R.id.buy);
        mCity = (Spinner) findViewById(R.id.city);
        mVerified = (ImageView) findViewById(R.id.verified);
        mAddress = (TextView) findViewById(R.id.input_branchaddress);
        mName = (TextInputEditText)findViewById(R.id.input_name);
        mVerified.setVisibility(View.GONE);
        mBranchItem = new ArrayList<>();
        mBranchArrayAdapter = new ArrayAdapter<>(mContext,R.layout.spinner_item_text,mBranchItem);
        mBranchArrayAdapter.add(new BranchItem(SELECT_BRANCH));


    }
    private void initCallbacks(){
        mButtonBuy.setOnClickListener(this);

    }
    private void initSpinners() {
        mBranchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCity.setAdapter(mBranchArrayAdapter);

    }
    private void processSpinners() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Api.LIST_SPINNERS_LIST, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleBranchResponse(response);
                /*handleStateResponse(response);*/

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



    @Override
    public void onClick(View v) {
        if(v == mButtonBuy){
            processPay();
        }

    }

    private void processPay() {
        Name = mName.getText().toString().trim();
        Mobile = mMobileNumber.getText().toString().trim();
        Branch = mBranchItem.get(mCity.getSelectedItemPosition()).getid();
        if (validateInput(Mobile,Branch,Name)) {
            getOrderRequest(Mobile,Branch,Name);
        }

    }
    private void getOrderRequest(String Mobile, int Branch,String Name) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_PHONE_NUMBER
                    , Mobile);
            jsonObject.put(KEY_BRANCH, Branch);
            jsonObject.put(KEY_NAME,Name);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                RELOAD_CARD_ACTIVITY, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ToastBuilder.build(mContext, "Order placed successfully");
                launchClearStack(mContext,MainActivity.class);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyErrorHandler.handle(mContext, error);
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "Create_lead");
    }

    private Boolean validateInput(String Mobile,int Branch,String Name) {
        Branch = mCity.getSelectedItemPosition();
        if(Branch == 0){
            ToastBuilder.build(mContext,"Select A Branch");
            return false;
        }
        if (TextUtils.isEmpty(Mobile)) {
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
        } else if(TextUtils.isEmpty(Name)){
            mName.requestFocus();
            mName.setError("Name Cannot be Empty");
            return false;
        }
        return true;
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Reload ForexCard");
        mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
        mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
    }
    private void promptBookingDialog() {
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
