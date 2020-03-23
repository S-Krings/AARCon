package com.example.aarcon.Conditions;
import android.app.Activity;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.LanguageHelper;

import java.util.Locale;

/**
 * Condition subclass for checking whether the application language matches the system language.
 */
public class LanguageNoFitCondition extends Condition {

    private Activity activity;

    public LanguageNoFitCondition(Activity activity) {
        this.activity = activity;
    }

    public LanguageNoFitCondition(Control control, Activity activity) {
        super(control);
        this.activity = activity;
    }

    @Override
    public boolean check() {
        LanguageHelper languageHelper = new LanguageHelper();
        return !(languageHelper.getSysLanguage().equals(languageHelper.getAppLanguage(activity)));
    }
}
