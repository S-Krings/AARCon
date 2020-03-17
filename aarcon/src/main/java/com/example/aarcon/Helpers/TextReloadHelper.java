package com.example.aarcon.Helpers;

import android.app.Activity;
import android.widget.TextView;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.ux.ArFragment;

public class TextReloadHelper {
    private TextView textView;
    private int textID;
    private Activity activity;

    public TextReloadHelper(TextView textView, int textID, Activity activity) {
        this.textView = textView;
        this.textID = textID;
        this.activity = activity;
    }

    public void addToOnUpdate(ArFragment arFragment){
        arFragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                try {
                    reload();
                }
                catch (NullPointerException n){}
            }
        });
    }

    public void reload(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(activity.getString(textID));
            }
        });
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }


    public void getTextID(int id){
        this.textID = id;
    }

    public void revalue(TextView textView, int textID){
        if(this.textView != textView) {
            this.textView = textView;
        }
        this.textID = textID;
        if(!this.textView.getText().equals(activity.getString(textID))) {
            reload();
        }
    }
}

