package com.example.aarcon.Conditions;

import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import com.example.aarcon.Control;
public class FinishedSpeakingCondition extends Condition {

    private TextToSpeech textToSpeech;
    private boolean finishedSpeaking = false;

    public FinishedSpeakingCondition(TextToSpeech textToSpeech) {
        this.textToSpeech = textToSpeech;
        this.textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                finishedSpeaking = false;
            }

            @Override
            public void onDone(String utteranceId) {
                finishedSpeaking = true;
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
    }

    public FinishedSpeakingCondition(Control control, TextToSpeech textToSpeech) {
        super(control);
        this.textToSpeech = textToSpeech;
        this.textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                finishedSpeaking = false;
            }

            @Override
            public void onDone(String utteranceId) {
                finishedSpeaking = true;
                System.out.println("+++++++++++++");
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
    }

    @Override
    public boolean check() {
        return finishedSpeaking;
    }
}
