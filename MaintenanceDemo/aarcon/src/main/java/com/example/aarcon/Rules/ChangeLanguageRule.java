package com.example.aarcon.Rules;

import android.app.Activity;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.LanguageHelper;
import com.example.aarcon.Helpers.TextReloadHelper;

import java.util.Locale;

/**
 * Rule subclass for changng the language f the application and reloading ViewRenderables for the changes to appear.
 */
public class ChangeLanguageRule extends Rule {
    //TODO have to reload renderable for changes to take effect

    private Activity activity;
    private String language;
    private String country;
    private TextReloadHelper textReloadHelper;
    private Locale locale;

    public ChangeLanguageRule(Activity activity, String language) {
        this.activity = activity;
        this.language = language;
    }

    public ChangeLanguageRule(Activity activity, String language, String country) {
        this.activity = activity;
        this.language = language;
        this.country = country;
    }

    public ChangeLanguageRule(Control control, Activity activity, String language, String country) {
        super(control);
        this.activity = activity;
        this.language = language;
        this.country = country;
    }

    public ChangeLanguageRule(Control control, Activity activity, String language) {
        super(control);
        this.activity = activity;
        this.language = language;
    }

    @Override
    public void execute() {
        LanguageHelper l = new LanguageHelper();
        locale = l.getAppLanguageLocale(activity);
        l.setLanguage(activity,language,country);
        if(textReloadHelper != null){
            textReloadHelper.reload();
        }
    }

    @Override
    public void unexecute(){
        try {
            new LanguageHelper().setLanguage(activity, locale);
            if(textReloadHelper != null){
                textReloadHelper.reload();
            }
        }
        catch (NullPointerException n){}
    }


    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setTextReloadHelper(TextReloadHelper textReloadHelper) {
        this.textReloadHelper = textReloadHelper;
    }
}
