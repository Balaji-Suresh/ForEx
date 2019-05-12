package com.iccaps.forex;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutUsFragment extends Fragment {

    private static final String EXTRA_DATA = "data";
    private View mRootView;
    private TextView mTextViewAboutUs;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static AboutUsFragment newInstance(String data) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_DATA, data);

        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        aboutUsFragment.setArguments(bundle);
        return aboutUsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        initObjects();
        setContent();
        return mRootView;
    }

    private void initObjects() {
        mTextViewAboutUs = (TextView) mRootView.findViewById(R.id.txt_about_us1);
    }

    private void setContent() {
        mTextViewAboutUs.setText(getArguments().getString(EXTRA_DATA));
    }
}

