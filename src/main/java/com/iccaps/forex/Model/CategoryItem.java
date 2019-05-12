package com.iccaps.forex.Model;

/**
 * Created by Admin on 8/21/2017.
 */

public class CategoryItem {
    private int mId /*mColor*/, mIcon;
    private String mCategory;

    public CategoryItem(int id/*, int color*/, int icon, String category) {
        mId = id;
        /*mColor = color;*/
        mIcon = icon;
        mCategory = category;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

   /* public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
*/
    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

}
