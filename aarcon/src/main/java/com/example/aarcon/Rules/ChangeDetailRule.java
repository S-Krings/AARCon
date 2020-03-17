package com.example.aarcon.Rules;

import android.app.Activity;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.LanguageHelper;
import com.example.aarcon.Helpers.TextReloadHelper;

public class ChangeDetailRule extends Rule {
    //TODO have to reload renderable for changes to take effect

    private Activity activity;
    private TextReloadHelper textReloadHelper;

    public ChangeDetailRule(Activity activity) {
        this.activity = activity;
    }

    public ChangeDetailRule(Control control, Activity activity) {
        super(control);
        this.activity = activity;
    }

    @Override
    public void execute() {
        LanguageHelper languageHelper = new LanguageHelper();
        String appLanguage = languageHelper.getAppLanguage(activity);
        languageHelper.setLanguage(activity,appLanguage,"XX"); //multiple string resources not supported, so work around with country code XX
        if(textReloadHelper != null){
            textReloadHelper.reload();
        }
    }

    @Override
    public void unexecute() {
        LanguageHelper languageHelper = new LanguageHelper();
        String appLanguage = languageHelper.getAppLanguage(activity);
        languageHelper.setLanguage(activity,appLanguage,null);
        if(textReloadHelper != null){
            textReloadHelper.reload();
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setTextReloadHelper(TextReloadHelper textReloadHelper) {
        this.textReloadHelper = textReloadHelper;
    }
}
