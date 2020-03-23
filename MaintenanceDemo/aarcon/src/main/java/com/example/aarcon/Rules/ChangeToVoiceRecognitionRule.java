package com.example.aarcon.Rules;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.ReconitionListenerButtonClicker;
import com.example.aarcon.R;

import java.util.Locale;

/**
 * Rule subclass for activating the voice recognition by google.
 */
public class ChangeToVoiceRecognitionRule extends Rule {
    private Activity activity;
    private Button button;

    public ChangeToVoiceRecognitionRule(Activity activity, Button button) {
        this.activity = activity;
        this.button = button;
    }

    public ChangeToVoiceRecognitionRule(Control control, Activity activity, Button button) {
        super(control);
        this.activity = activity;
        this.button = button;
    }

    @Override
    public void execute() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO},44);

        final String buttonText = button.getText().toString();
        //TODO System.out.println("_______a");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //System.out.println("_______");

                ReconitionListenerButtonClicker listener = new ReconitionListenerButtonClicker(activity.getResources().getStringArray(R.array.next_array),button);
                SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);
                speechRecognizer.setRecognitionListener(listener);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //TODO intent.putExtra(RecognizerIntent.EXTRA_PROMPT, activity.getString(R.string.voice_prompt)+" \""+buttonText+"\"");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,3000);
                speechRecognizer.startListening(intent);
             }
        });
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
