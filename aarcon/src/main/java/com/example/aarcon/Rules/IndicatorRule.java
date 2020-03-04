package com.example.aarcon.Rules;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.ImageHelper;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class IndicatorRule extends Rule {
    private ArFragment arFragment;
    private ImageView indicatorView;
    private TransformableNode transformableNode;
    private final Drawable standardDrawable;
    private Scene.OnUpdateListener onUpdateListener;
    private boolean flip = false;

    public IndicatorRule(ArFragment arFragment, ImageView indicatorView, TransformableNode transformableNode) {
        this.arFragment = arFragment;
        this.indicatorView = indicatorView;
        this.transformableNode = transformableNode;
        standardDrawable = indicatorView.getDrawable();
    }

    public IndicatorRule(Control control, ArFragment arFragment, ImageView indicatorView, TransformableNode transformableNode) {
        super(control);
        this.arFragment = arFragment;
        this.indicatorView = indicatorView;
        this.transformableNode = transformableNode;
        standardDrawable = indicatorView.getDrawable();
    }

    @Override
    public void execute() {
        ImageHelper ih = new ImageHelper();
        final Drawable originalDrawable = standardDrawable;
        final Drawable flippedDrawable = ih.bitmapToDrawable(arFragment.getContext(),ih.flipBitmap(ih.drawableToBitmap(standardDrawable)));

        Scene.OnUpdateListener newOnUpdateListener =  new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                Vector3 nodeOnScreen = arFragment.getArSceneView().getScene().getCamera().worldToScreenPoint(transformableNode.getWorldPosition());
                int[] indicatorLocation = new int[]{0,0};
                indicatorView.getLocationOnScreen(indicatorLocation);
                Vector3 indicatorOnScreen = new Vector3(indicatorLocation[0],indicatorLocation[1],0);
                Vector3 screenVector = Vector3.subtract(nodeOnScreen, indicatorOnScreen);
                float angle = (float) Math.toDegrees(Math.atan2(screenVector.y - 1, screenVector.x - 0));

                if(angle < -90 || angle > 90){
                    flip = true;
                    angle += 180;
                }

                indicatorView.setRotation(angle);
                if (flip){
                    indicatorView.setImageDrawable(flippedDrawable);
                }
                else{
                    indicatorView.setImageDrawable(originalDrawable);
                }
                flip = false;
            }
        };
        if (onUpdateListener != null) {
            arFragment.getArSceneView().getScene().removeOnUpdateListener(onUpdateListener);
        }
        arFragment.getArSceneView().getScene().addOnUpdateListener(newOnUpdateListener);
        onUpdateListener = newOnUpdateListener;
    }

    @Override
    public void unexecute() {
        indicatorView.setImageDrawable(standardDrawable);
        if (onUpdateListener != null) {
            arFragment.getArSceneView().getScene().removeOnUpdateListener(onUpdateListener);
        }
    }

    public void setArFragment(ArFragment arFragment) {
        this.arFragment = arFragment;
    }

    public void setIndicatorView(ImageView indicatorView) {
        this.indicatorView = indicatorView;
    }

    public void setTransformableNode(TransformableNode transformableNode) {
        this.transformableNode = transformableNode;
    }
}
