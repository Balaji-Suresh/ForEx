package com.iccaps.forex.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iccaps.forex.Holder.CategoryHolder;
import com.iccaps.forex.Model.CategoryItem;
import com.iccaps.forex.R;

import java.util.List;

/**
 * Created by Admin on 8/21/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    private List<CategoryItem> mHealthCategoryItemList;
    private TrackHealthClickListener mListener;

    public CategoryAdapter(List<CategoryItem> healthCategoryItemList,
                                 TrackHealthClickListener listener) {
        mHealthCategoryItemList = healthCategoryItemList;
        mListener = listener;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,
                        parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        CategoryItem healthCategoryItem = mHealthCategoryItemList.get(position);
        final int id = healthCategoryItem.getId();
        /*int color = healthCategoryItem.getColor();*/
        int icon = healthCategoryItem.getIcon();
        String category = healthCategoryItem.getCategory();

        holder.getImageViewHealthCategory().setImageResource(icon);
        holder.getTextViewHealthCategory().setText(category);
        /*holder.getViewColor().setBackgroundResource(color);*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTrackHealthClick(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHealthCategoryItemList.size();
    }

    public interface TrackHealthClickListener {
        void onTrackHealthClick(int id);
    }
}
