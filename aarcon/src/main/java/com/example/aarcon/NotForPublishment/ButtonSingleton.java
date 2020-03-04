package com.example.aarcon.NotForPublishment;

import android.widget.Button;

public class ButtonSingleton {
    private static ButtonSingleton instance;
    private Button button;
    private ButtonSingleton() {}

    public static ButtonSingleton getInstance () {
        if (ButtonSingleton.instance == null) {
            ButtonSingleton.instance = new ButtonSingleton();
        }
        return ButtonSingleton.instance;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }
}
