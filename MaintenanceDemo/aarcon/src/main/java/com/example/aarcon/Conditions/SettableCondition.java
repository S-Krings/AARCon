package com.example.aarcon.Conditions;

import com.example.aarcon.Control;

/**
 * Condition subclass whose truth state can be set by the programmer.
 */
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
