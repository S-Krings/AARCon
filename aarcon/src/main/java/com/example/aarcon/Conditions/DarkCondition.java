package com.example.aarcon.Conditions;

import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.ArFragment;

public class DarkCondition extends Condition {

    private ArFragment arFragment;

    public DarkCondition(ArFragment arFragment) {
        this.arFragment = arFragment;
    }

    public DarkCondition(Control control, ArFragment arFragment) {
        super(control);
        this.arFragment = arFragment;
    }

    @Override
    public boolean check() {
        //System.out.println("Check Dark"+arFragment.getArSceneView().getArFrame().getLightEstimate().getPixelIntensity());
        return 0.3f > arFragment.getArSceneView().getArFrame().getLightEstimate().getPixelIntensity();
    }
}
