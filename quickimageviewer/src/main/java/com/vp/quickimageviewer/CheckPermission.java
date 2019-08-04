package com.vp.quickimageviewer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

/**
 * Created by Vinod Patil on 4/8/19
 */
public class CheckPermission {

    public static final int PERMISSION_STORAGE = 100;

    public static boolean hasPermission(String permission, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestPerm(String[] permissions, int requestCode, Activity activity) {
        activity.requestPermissions(permissions, requestCode);
    }
}
