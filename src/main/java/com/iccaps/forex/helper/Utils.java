package com.iccaps.forex.helper;

import android.content.Context;
import android.util.TypedValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bobby on 20-02-2017
 */

public class Utils {
    public static float convertPxToDp(Context context, float px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px,
                context.getResources().getDisplayMetrics());
    }

    public static String getCreatedDate(String myDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.getDefault());
        try {
            Date date = dateFormat.parse(myDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM HH:mm aa",
                    Locale.getDefault());
            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }


    public static String parseCreatedDate(String date) {
        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    Locale.getDefault()).parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a, dd MMM",
                    Locale.getDefault());
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String parseBookLaterDate(String date) {
        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.getDefault()).parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a, dd MMM",
                    Locale.getDefault());
            return outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
