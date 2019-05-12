package com.iccaps.forex.app;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Bobby on 18-02-2017
 */

public class ToastBuilder {
    public static void build(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
