package com.example.aarcon.Conditions;

import android.content.Context;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.SystemInfoHelper;


public class FontBigCondition extends Condition {
    private Context context;

    public FontBigCondition(Context context) {
        this.context = context;
    }

    public FontBigCondition(Control control, Context context) {
        addObserver(control);
        this.context=context;
    }

    @Override
    public boolean check() {
        float scale = new SystemInfoHelper().getFontScale(context);
        //TODO System.out.println("##################Scale: "+scale);
        return scale > 1.0;
    }

    public Condition setContext(Context context){
        this.context = context;
        return this;
    }
}