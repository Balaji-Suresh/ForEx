package com.iccaps.forex.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iccaps.forex.R;

/**
 * Created by Admin on 8/21/2017.
 */

public class CategoryHolder extends RecyclerView.ViewHolder {

    private ImageView mImageViewHealthCategory;
    private TextView mTextViewHealthCategory;
   /* private View mViewColor;*/

    public CategoryHolder(View itemView) {
        super(itemView);
        mImageViewHealthCategory = (ImageView) itemView.findViewById(R.id.img_health_category);
        mTextViewHealthCategory = (TextView) itemView.findViewById(R.id.txt_health_category);
        /*mViewColor = itemView.findViewById(R.id.color);*/
    }

    public ImageView getImageViewHealthCategory() {
        return mImageViewHealthCategory;
    }

    public TextView getTextViewHealthCategory() {
        return mTextViewHealthCategory;
    }

    /*public View getViewColor() {
        return mViewColor;
    }*/
}