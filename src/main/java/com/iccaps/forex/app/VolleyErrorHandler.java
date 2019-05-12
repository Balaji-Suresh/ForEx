package com.iccaps.forex.app;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.iccaps.forex.R;
import com.iccaps.forex.Splash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Admin on 6/15/2017.
 */


public class VolleyErrorHandler {


    public static void handle(Context context, VolleyError volleyError) {
        if (volleyError instanceof NoConnectionError) {
            ToastBuilder.build(context, context.getString(R.string.error_no_internet));
        }

        PreferenceManager preferenceManager = new PreferenceManager(context);

        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null) {
            int statusCode = networkResponse.statusCode;
            String error = new String(networkResponse.data);
            Log.e("error", error);
            String message = null;
            try {
                JSONObject jsonObject = new JSONObject(error);
                if (jsonObject.has(Api.KEY_NON_FIELD_ERRORS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.KEY_NON_FIELD_ERRORS);
                    message = jsonArray.getString(0);
                } else if (jsonObject.has(Api.KEY_DETAIL)) {
                    message = jsonObject.getString(Api.KEY_DETAIL);
                } else {
                    for (int i = 0; i < jsonObject.length(); i++) {
                        JSONArray keyArray = new JSONArray(
                                jsonObject.getString(jsonObject.names().getString(i)));
                        message = keyArray.getString(0);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            switch (statusCode) {
                case 400:
                    if (message != null) {
                        ToastBuilder.build(context, message);
                    }
                    break;
                case 401:
                    if (message != null) {
                        ToastBuilder.build(context, message);
                    }
                    preferenceManager.clearUser();
                    Activity.launchClearStack(context, Splash.class);
                    break;
                case 403:
                    if (message != null) {
                        ToastBuilder.build(context, message);
                    }
                    preferenceManager.clearUser();
                    Activity.launchClearStack(context, Splash.class);
                    break;
                case 404:
                    if (message != null) {
                        ToastBuilder.build(context, message);
                    }
                    break;
                default:
                    ToastBuilder.build(context, context.getString(R.string.error_unexpected));
                    break;
            }
        }
    }
}
