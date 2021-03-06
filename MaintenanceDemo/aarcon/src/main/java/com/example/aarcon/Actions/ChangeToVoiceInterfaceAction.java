package com.example.aarcon.Actions;

import android.app.Activity;
import android.widget.Button;

import com.example.aarcon.Conditions.FinishedSpeakingCondition;
import com.example.aarcon.Conditions.SettableCondition;
import com.example.aarcon.Control;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Action subclass for activating a voice interface by using a combination of the ChangeAudioAction and the ChangeToVoiceRecognitionAction.
 */
public class ChangeToVoiceInterfaceAction extends Action {

    private Activity activity;
    private String speech;
    private Button button;
    private Timer timer;

    private ChangeAudioAction changeAudioRule;
    private ChangeToVoiceRecognitionAction changeToVoiceRecognitionRule;
    private SettableCondition settableCondition;
    private FinishedSpeakingCondition finishedSpeakingCondition;

    public ChangeToVoiceInterfaceAction(Activity activity, String speech, Button button) {
        this.activity = activity;
        this.speech = speech;
        this.button = button;
    }

    public ChangeToVoiceInterfaceAction(Control control, Activity activity, String speech, Button button) {
        super(control);
        this.activity = activity;
        this.speech = speech;
        this.button = button;
    }

    @Override
    public void execute() {
        //TODO System.out.println("----combi");
        if(changeAudioRule == null) {
            changeAudioRule = new ChangeAudioAction(control, activity, speech);
            changeToVoiceRecognitionRule = new ChangeToVoiceRecognitionAction(control, activity, button);
            settableCondition = new SettableCondition(control);
            finishedSpeakingCondition = new FinishedSpeakingCondition(control, changeAudioRule.getTextToSpeech());

            changeAudioRule.addCondition(settableCondition);
            changeToVoiceRecognitionRule.addCondition(finishedSpeakingCondition);
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                settableCondition.setTruthState(!settableCondition.getTruthState());
                //System.out.println("---"+settableCondition.getTruthState());
            }
        }, 20,10000);
    }

    @Override
    public void unexecute(){
        settableCondition.setTruthState(false);
        timer.cancel();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        try {
            changeAudioRule.setActivity(activity);
            changeToVoiceRecognitionRule.setActivity(activity);
        }
        catch(NullPointerException n){}
    }

    public void setSpeech(String speech) {
        this.speech = speech;
        try {
            changeAudioRule.setSpeech(speech);
        }
        catch(NullPointerException n){}
    }

    public void setButton(Button button) {
        this.button = button;
        try {
            changeToVoiceRecognitionRule.setButton(button);
        }
        catch(NullPointerException n){}
    }
}
