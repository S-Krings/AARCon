package com.example.aarcon.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class SystemInfoHelper {

    public static int getBatteryPercent(Context context) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, intentFilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float share = level / (float) scale;
        return (int) (share * 100);
    }

    public float getFontScale(Context context) {
        float scale = context.getResources().getConfiguration().fontScale;
        return scale;
    }
}
