package com.example.aarcon.Conditions;

import android.content.SharedPreferences;

import com.example.aarcon.Control;

public class UsedSeldomCondition extends Condition {
    private SharedPreferences preferences;
    private int threshold = 5;

    public UsedSeldomCondition(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public UsedSeldomCondition(Control control, SharedPreferences preferences) {
        super(control);
        this.preferences = preferences;
    }

    public UsedSeldomCondition(SharedPreferences preferences, int threshold) {
        this.preferences = preferences;
        this.threshold = threshold;
    }

    public UsedSeldomCondition(Control control, SharedPreferences preferences, int threshold) {
        super(control);
        this.preferences = preferences;
        this.threshold = threshold;
    }

    @Override
    public boolean check() {
        int appOpened = preferences.getInt("counter", 0);
        return appOpened < threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
