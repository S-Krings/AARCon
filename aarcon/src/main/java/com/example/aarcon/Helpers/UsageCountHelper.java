package com.example.aarcon.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UsageCountHelper {
    private Activity activity;

    public int updateUsageCount(Activity activity){
        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int appOpened = preferences.getInt("counter", 0);
        appOpened++;
        editor.putInt("counter", appOpened);
        editor.commit();
        return appOpened;
    }

    public SharedPreferences getPreferences(Activity activity){
        return activity.getPreferences(Context.MODE_PRIVATE);
    }
}
