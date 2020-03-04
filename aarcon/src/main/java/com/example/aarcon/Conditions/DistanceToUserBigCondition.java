package com.example.aarcon.Conditions;

import com.example.aarcon.Control;
import com.google.ar.core.Pose;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class DistanceToUserBigCondition extends Condition {

    private ArFragment arFragment;
    private TransformableNode transformableNode;
    private float threshold = 1.2f;

    public DistanceToUserBigCondition(ArFragment arFragment, TransformableNode transformableNode) {
        this.arFragment = arFragment;
        this.transformableNode = transformableNode;
    }

    public DistanceToUserBigCondition(Control control, ArFragment arFragment, TransformableNode transformableNode) {
        super(control);
        this.arFragment = arFragment;
        this.transformableNode = transformableNode;
    }

    public DistanceToUserBigCondition(ArFragment arFragment, TransformableNode transformableNode, float threshold) {
        this.arFragment = arFragment;
        this.transformableNode = transformableNode;
        this.threshold = threshold;
    }

    public DistanceToUserBigCondition(Control control, ArFragment arFragment, TransformableNode transformableNode, float threshold) {
        super(control);
        this.arFragment = arFragment;
        this.transformableNode = transformableNode;
        this.threshold = threshold;
    }

    @Override
    public boolean check() {
        Pose userPose = arFragment.getArSceneView().getArFrame().getCamera().getPose();
        Vector3 pose = transformableNode.getWorldPosition();
        float distanceX = pose.x - userPose.tx();
        float distanceY = pose.y - userPose.ty();
        float distanceZ = pose.z - userPose.tz();
        return threshold < Math.sqrt(distanceX*distanceX + distanceY*distanceY + distanceZ*distanceZ);
    }

    public void setArFragment(ArFragment arFragment) {
        this.arFragment = arFragment;
    }

    public void setTransformableNode(TransformableNode transformableNode) {
        this.transformableNode = transformableNode;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}
