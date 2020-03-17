package com.example.aarcon.Rules;

import android.app.Activity;
import android.widget.TextView;

import com.example.aarcon.Control;

public class ChangeFontSizeRule extends Rule {
    private Activity activity;
    private TextView textView;
    private int sizeChange = 4;
 //TODO

    public ChangeFontSizeRule(Activity activity, TextView textView) {
        this.textView = textView;
        this.activity = activity;
    }

    public ChangeFontSizeRule(Activity activity, TextView textView, int sizeChange) {
        this.textView = textView;
        this.sizeChange = sizeChange;
        this.activity = activity;
    }

    public ChangeFontSizeRule(Control control, Activity activity, TextView textView) {
        super(control);
        this.textView = textView;
        this.activity = activity;
    }

    public ChangeFontSizeRule(Control control, Activity activity, TextView textView, int sizeChange) {
        super(control);
        this.textView = textView;
        this.sizeChange = sizeChange;
        this.activity = activity;
    }

    @Override
    public void execute() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setTextSize(textView.getTextSize()+sizeChange);
                System.out.println("################ Executed ChangeFontSizeRule");
            }
        });
    }

    @Override
    public void unexecute() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(textView.getTextSize()>sizeChange) {
                    textView.setTextSize(textView.getTextSize() - sizeChange);
                }
            }
        });
    }

    public void setTextView(TextView textView){
        this.textView = textView;
    }
}
