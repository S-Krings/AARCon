package com.example.aarcon.Conditions;

import android.content.res.Resources;

import com.example.aarcon.Control;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class NodeOffScreenCondition extends Condition {

    private ArFragment arFragment;
    private TransformableNode transformableNode;

    public NodeOffScreenCondition(ArFragment arFragment, TransformableNode transformableNode) {
        this.arFragment = arFragment;
        this.transformableNode = transformableNode;
    }

    public NodeOffScreenCondition(Control control, ArFragment arFragment, TransformableNode transformableNode) {
        super(control);
        this.arFragment = arFragment;
        this.transformableNode = transformableNode;
    }

    @Override
    public boolean check() {
        Vector3 nodeOnScreen = arFragment.getArSceneView().getScene().getCamera().worldToScreenPoint(transformableNode.getWorldPosition());
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        // TODO System.out.println(nodeOnScreen.y+"/"+height+" "+nodeOnScreen.x+"/"+width);
        if(nodeOnScreen.x<0|| nodeOnScreen.x>width||nodeOnScreen.y<0||nodeOnScreen.y>height){
            System.out.println("222NodeOff");
            return true;
        }
        else{
            System.out.println("222NodeOn");
            return false;
        }
    }

    public void setArFragment(ArFragment arFragment) {
        this.arFragment = arFragment;
    }

    public void setTransformableNode(TransformableNode transformableNode) {
        this.transformableNode = transformableNode;
    }
}
