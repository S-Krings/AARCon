package com.example.aarcon.Actions;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.widget.Toast;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.LanguageHelper;

import java.util.Locale;

/**
 * Action subclass for enabling audio output
 */
public class ChangeAudioAction extends Action {

    private Activity activity;
    private String speech;
    private String utteranceID = "TextToSpeech";
    private TextToSpeech textToSpeech;


    public ChangeAudioAction(Activity activity, String speech) {
        this.activity = activity;
        this.speech = speech;
        instanciateTextToSpeech();
    }

    public ChangeAudioAction(Activity activity, String speech, String utteranceID) {
        this.activity = activity;
        this.speech = speech;
        this.utteranceID = utteranceID;
        instanciateTextToSpeech();
    }

    public ChangeAudioAction(Control control, Activity activity, String speech) {
        super(control);
        this.activity = activity;
        this.speech = speech;
        control.registerRule(this);
        instanciateTextToSpeech();
    }

    public ChangeAudioAction(Control control, Activity activity, String speech, String utteranceID) {
        super(control);
        this.activity = activity;
        this.speech = speech;
        this.utteranceID = utteranceID;
        control.registerRule(this);
        instanciateTextToSpeech();
    }

    @Override
    public void execute() {
        //TODO Timer timer = new Timer();
        //timer.schedule(new TimerTask() {
            //@Override
            //public void run() {
                //TODO System.out.println("--------");
                textToSpeech.speak(speech, TextToSpeech.QUEUE_FLUSH, null, utteranceID);
           // }
        //}, 500);
    }

    @Override
    public void unexecute() {
        if (textToSpeech!=null){
            textToSpeech.stop();
            //TODO textToSpeech.shutdown();
        }
    }

    private void instanciateTextToSpeech(){
        final LanguageHelper languageHelper = new LanguageHelper();
        textToSpeech = new TextToSpeech(activity.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int language;
                    try{
                        language = textToSpeech.setLanguage(languageHelper.getAppLanguageLocale(activity));
                    }
                    catch(Error e) {
                        language = textToSpeech.setLanguage(Locale.getDefault());
                    }
                    if (language == TextToSpeech.LANG_MISSING_DATA|| language == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast toast = Toast.makeText(activity, "Unable to load this language for TextToSpeech.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else {}
                }
                else {}
            }
        });
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setSpeech(String speech) {
        this.speech.replace(this.speech,speech);
        this.speech = speech;
    }

    public void setUtteranceID(String utteranceID) {
        this.utteranceID = utteranceID;
    }

    public TextToSpeech getTextToSpeech(){
        return textToSpeech;
    }
}
