package com.iccaps.forex;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.iccaps.forex.app.Activity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Splash extends AppCompatActivity {

    private Context mContext;
    private TextView mTextViewFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        initObjects();
        setFooterTextSpan();
        startWelcomeActivity();
    }
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private void initObjects() {
        mTextViewFooter = (TextView) findViewById(R.id.txt_footer);

        mContext = this;
    }

    private void setFooterTextSpan() {
        ImageSpan imageSpan = new ImageSpan(mContext, R.drawable.ic_heart,
                DynamicDrawableSpan.ALIGN_BASELINE);
        SpannableString string = new SpannableString("Made with    by India Cements Capital Ltd");
        string.setSpan(imageSpan, 10, 12, 0);
        string.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimaryDark)),
                16, 41, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewFooter.setText(string);
    }

    private void startWelcomeActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Activity.launch(mContext, MainActivity.class);
            }
        }, 2000);
    }
}
