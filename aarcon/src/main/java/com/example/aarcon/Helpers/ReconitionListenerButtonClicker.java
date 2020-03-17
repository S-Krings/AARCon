package com.example.aarcon.Helpers;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Button;

import java.util.ArrayList;

public class ReconitionListenerButtonClicker implements RecognitionListener {

    private String[] orderOptions;
    private Button button;

    public ReconitionListenerButtonClicker(String[] orderOptions, Button button) {
        this.orderOptions = orderOptions;
        this.button = button;
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(button!=null) {
            if (data.contains(button.getText().toString())) {
                button.callOnClick();
                return;
            }
            else {
                for (int i = 0; i < orderOptions.length; i++) {
                    if (data.contains(orderOptions[i])) {
                        button.callOnClick();
                        // TODO System.out.println("~~~~~~~~~~~~");
                        /*for (int j = 0; j < data.size(); j++) {
                            System.out.println(data.get(j));
                        }*/
                        return;
                    }
                }
            }
            return;
        }
    }
    @Override
    public void onReadyForSpeech(Bundle params) {
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float rmsdB) {
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int error) {
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }
}