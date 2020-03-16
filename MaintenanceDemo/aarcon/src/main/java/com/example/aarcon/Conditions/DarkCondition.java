package com.example.aarcon.Conditions;

import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.ArFragment;

public class DarkCondition extends Condition {

    private ArFragment arFragment;

    public DarkCondition(ArFragment arFragment) {
        this.arFragment = arFragment;
    }

    /**
     * Constructor for DarkCondition
     * @param control Control object to add as Observer
     * @param arFragment ArFragment for which the lighting should be checked
     */
    public DarkCondition(Control control, ArFragment arFragment) {
        super(control);
        this.arFragment = arFragment;
    }

    @Override
    public boolean check() {
        return 0.3f > arFragment.getArSceneView().getArFrame().getLightEstimate().getPixelIntensity();
    }


}
