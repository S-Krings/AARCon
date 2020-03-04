package com.example.aarcon.NotForPublishment;

import android.graphics.Point;
import android.widget.ImageView;

import com.example.aarcon.Control;
import com.example.aarcon.Rules.Rule;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class IndicatorToEdgeRule extends Rule {
    ArFragment arFragment;
    ImageView indicatorView;
    TransformableNode transformableNode;

    public IndicatorToEdgeRule(ArFragment arFragment, ImageView indicatorView, TransformableNode transformableNode) {
        this.arFragment = arFragment;
        this.indicatorView = indicatorView;
        this.transformableNode = transformableNode;
    }

    public IndicatorToEdgeRule(Control control, ArFragment arFragment, ImageView indicatorView, TransformableNode transformableNode) {
        super(control);
        this.arFragment = arFragment;
        this.indicatorView = indicatorView;
        this.transformableNode = transformableNode;
    }

    @Override
    public void execute() {
        Vector3 nodeOnScreen = arFragment.getArSceneView().getScene().getCamera().worldToScreenPoint(transformableNode.getWorldPosition());
        int[] indicatorLocation = new int[]{0,0};
        indicatorView.getLocationOnScreen(indicatorLocation);
        Geometry g = new Geometry();
        float[] edgePosition = g.calculate(new Point((int)nodeOnScreen.x,(int)nodeOnScreen.y), new Point(indicatorLocation[0],indicatorLocation[1]));
        indicatorView.setTranslationX(edgePosition[0]);
        indicatorView.setTranslationY(edgePosition[1]);
    }
}
