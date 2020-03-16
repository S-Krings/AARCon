package com.example.aarcon.Conditions;

import com.example.aarcon.Control;

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
