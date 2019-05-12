package com.iccaps.forex;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.iccaps.forex.Adapter.CategoryAdapter;
import com.iccaps.forex.Model.CategoryItem;
import com.iccaps.forex.app.Activity;
import com.iccaps.forex.app.Api;
import com.iccaps.forex.app.AppController;
import com.iccaps.forex.app.PreferenceManager;
import com.iccaps.forex.app.ToastBuilder;
import com.iccaps.forex.app.VolleyErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.iccaps.forex.app.Activity.launch;
import static com.iccaps.forex.app.Constant.SUPPORT_PHONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CategoryAdapter.TrackHealthClickListener{
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecyclerView mRecyclerView;
    private PreferenceManager mPreferenceManager;
    private ProgressDialog mProgressDialog;
    private List<CategoryItem> mCategoryItemList;
    private CategoryAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private Context mContext;
    private Toolbar mToolbar;
    boolean backPressed = false;
    boolean backPressedTwice = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initObjects();
        initCallbacks();
        setupToolbar();
        initRecyclerView();
        populateData();
       /* setFooterTextSpan();*/
        setupDrawerToggle();


    }
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initObjects() {
        mPreferenceManager = new PreferenceManager(this);
        mProgressDialog = new ProgressDialog(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mRecyclerView = (RecyclerView) findViewById(R.id.health_category_list);

        mContext = this;
        mCategoryItemList = new ArrayList<>();
        mAdapter = new CategoryAdapter(mCategoryItemList, this);;
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void initCallbacks() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public boolean onNavigationItemSelected( @NonNull MenuItem item) {
        mDrawerLayout.closeDrawers();
        return onNavClick(item.getItemId());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void onBackPressed () {
        if (!backPressed) {
            backPressed = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    if (!backPressedTwice)
                        MainActivity.this.finish(); // go back to previous activity
                }
            }, 500);
        }
        if (backPressed) {
            // it's clicked twice
            backPressedTwice = true; // cancel the handler so it does not finish the activity
            Dialog();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
    }

    private void setupDrawerToggle() {
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }
    private void Dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private boolean onNavClick(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                launch(mContext, MainActivity.class);
                return true;
            case R.id.nav_orders:
                launch(mContext, BuyForexActivity.class);
                return true;
            case R.id.nav_profile:
                launch(mContext,LiveRateActivity.class);
                return true;
            case R.id.nav_share_app:
                shareApp();
                return false;
            case R.id.nav_call_us_now:
                openDialPad(SUPPORT_PHONE);
                return false;
            case R.id.nav_about_us:
                launch(mContext,AboutUs.class);
                return true;
            default:
                return false;
        }
    }

    private void promptLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showProgressDialog("Logging out..");
                /*logoutUser();*/
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

    private void logoutUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.LOGOUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideProgressDialog();
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
        ToastBuilder.build(mContext, "You\'ve been logged out");
        Activity.launchClearStack(this, MainActivity.class);
    }




    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                getString(R.string.app_name) + " https://play.google.com/store/apps/details?id="
                        + getPackageName());
        startActivity(Intent.createChooser(shareIntent, "Share App"));
    }

    private void openDialPad(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }
    private void initRecyclerView() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
    }

    private void populateData() {
        mCategoryItemList.add(
                new CategoryItem(0, R.drawable.ic_loan, "Buy Forex"));
        mCategoryItemList.add(
                new CategoryItem(1, R.drawable.ic_sell,
                        "Sell Forex"));
        mCategoryItemList.add(
                new CategoryItem(2, R.drawable.ic_transfer, "Money Transfer"));
        mCategoryItemList.add(
                new CategoryItem(3, R.drawable.ic_credit_card, "Reload Forex Card"));
        mCategoryItemList.add(new CategoryItem(4,R.drawable.ic_euro,"Live Rates"));
        mCategoryItemList.add(new CategoryItem(5,R.drawable.ic_change,"Currency Convertor"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTrackHealthClick(int id) {
        if (id == 0) {
            launch(mContext, BuyForexActivity.class);
        } else if (id == 1) {
            View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_session, null);
            ImageView imageViewClose = (ImageView) mView.findViewById(R.id.img_close);
            RadioGroup session = (RadioGroup)mView.findViewById(R.id.sessionlayout);
            RadioButton mGuest = (RadioButton) mView.findViewById(R.id.rdo_guest);
            RadioButton mLogin = (RadioButton) mView.findViewById(R.id.rdo_existing_user);
            final Button mContinue = (Button) mView.findViewById(R.id.btn_add1);
            mContinue.setVisibility(View.GONE);
            /*final TextView mRegister = (TextView) mView.findViewById(R.id.txt_register);*/
            final Button mLoginButton = (Button) mView.findViewById(R.id.login);
            mLoginButton.setVisibility(View.GONE);
            session.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if(checkedId == R.id.rdo_existing_user){
                        mContinue.setVisibility(View.GONE);
                    } else if(checkedId == R.id.rdo_guest){
                        mContinue.setVisibility(View.VISIBLE);
                        /*mRegister.setVisibility(View.GONE);*/
                        mLoginButton.setVisibility(View.GONE);

                    }
                }
            });

            /*final EditText mNumber = (EditText) mView.findViewById(R.id.input_mob_number);
            *//*mNumber.setText(mPreferenceManager.getPhone());*/
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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
                    Activity.launch(mContext, SignInSell.class);
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
                    Activity.launch(mContext, SignUpSell.class);
                }
            });
            /*mRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity.launch(mContext, SignUpSell.class);
                }
            });*/
        }

        else if (id == 2) {
            launch(mContext, MoneyTransferActivity.class);
        } else if (id == 3) {
            launch(mContext, ReloadCardActivity.class);
        } else if (id == 4) {
            launch(mContext,LiveRateActivity.class);
        } else if(id == 5) {
            launch(mContext,CurrencyConvertorActivity.class);
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
