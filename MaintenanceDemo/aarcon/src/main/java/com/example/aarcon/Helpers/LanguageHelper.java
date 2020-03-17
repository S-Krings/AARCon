package com.example.aarcon.Helpers;

import android.app.Activity;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageHelper {
    public void setLanguage(Activity activity, String language, String country){
        Locale locale;
        if (country != null) {
            locale = new Locale(language,country);
        }
        else{
            locale = new Locale(language);
        }
        Locale.setDefault(locale);
        Configuration config = activity.getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
    }

    public void setLanguage(Activity activity, Locale locale){
        Locale.setDefault(locale);
        Configuration config = activity.getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
    }

    public String getAppLanguage(Activity activity){
        return activity.getResources().getConfiguration().getLocales().get(0).getLanguage();
    }

    public Locale getAppLanguageLocale(Activity activity){
        return activity.getResources().getConfiguration().getLocales().get(0);
    }

    public String getSysLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public Locale getSysLanguageLocale() {
        return Locale.getDefault();
    }
}
