package com.example.aarcon.Rules;

import com.example.aarcon.Control;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ChangePoseToUserRule extends Rule {

    private ArFragment arFragment;
    private TransformableNode transformableNode;
    private Scene.OnUpdateListener onUpdateListener;


    public ChangePoseToUserRule(ArFragment arFragment, TransformableNode transformableNode) {
        this.arFragment = arFragment;
        this.transformableNode = transformableNode;
    }

    public ChangePoseToUserRule(Control control, ArFragment arFragment, TransformableNode transformableNode) {
        super(control);
        this.arFragment = arFragment;
        this.transformableNode = transformableNode;
    }

    @Override
    public void execute() {
        onUpdateListener = new Scene.OnUpdateListener() {
                    @Override
                    public void onUpdate(FrameTime frameTime) {
                        try {
                            /** Orientated on Google Developers https://developers.google.com/ar/develop/java/sceneform/build-scene**/
                            Vector3 camPos = arFragment.getArSceneView().getScene().getCamera().getWorldPosition();
                            Vector3 nPos = transformableNode.getWorldPosition();
                            Vector3 direction = Vector3.subtract(camPos, nPos);
                            Quaternion lookRotation = Quaternion.lookRotation(direction, Vector3.up());
                            transformableNode.setWorldRotation(lookRotation);
                        } catch (NullPointerException n) {
                            System.out.println("Nullpointer Exception. Are arFragment and transformableNode set?");
                        }
                    }
                };
        arFragment.getArSceneView().getScene().addOnUpdateListener(onUpdateListener);

    }

    @Override
    public void unexecute() {
        if (onUpdateListener != null){
            arFragment.getArSceneView().getScene().removeOnUpdateListener(onUpdateListener);
        }
    }

    public void setArFragment(ArFragment arFragment){
        this.arFragment = arFragment;
    }

    public void setTransformableNode(TransformableNode transformableNode){
        this.transformableNode = transformableNode;
    }
}
