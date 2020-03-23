package com.example.aarcon.Conditions;

import com.example.aarcon.Control;

/**
 * Condition subclass which is always true.
 */
public class TrueCondition extends Condition {
    public TrueCondition() {
    }

    public TrueCondition(Control control) {
        super(control);
    }

    @Override
    public boolean check() {
        return true;
    }
}
