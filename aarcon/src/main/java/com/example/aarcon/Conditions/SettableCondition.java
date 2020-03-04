package com.example.aarcon.Conditions;

import com.example.aarcon.Control;

public class SettableCondition extends Condition {

    private boolean truthState = false;

    public SettableCondition(Control control) {
        super(control);
    }

    public SettableCondition(Control control, boolean truthState) {
        super(control);
        this.truthState = truthState;
    }

    @Override
    public boolean check() {
        return truthState;
    }

    public boolean getTruthState() {
        return truthState;
    }

    public void setTruthState(boolean truthState) {
        this.truthState = truthState;
    }

}
