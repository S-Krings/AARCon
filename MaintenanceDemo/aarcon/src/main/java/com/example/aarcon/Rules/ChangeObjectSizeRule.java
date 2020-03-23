package com.example.aarcon.Rules;
import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * Rule subclass for changing the size of a TransformableNode.
 */
public class ChangeObjectSizeRule extends Rule {

    private TransformableNode node;
    private float minScale = 0.5f;
    private float maxScale = 2f;

    public ChangeObjectSizeRule(TransformableNode node, float minScale, float maxScale) {
        this.node = node;
        this.minScale = minScale;
        this.maxScale = maxScale;
    }

    public ChangeObjectSizeRule(TransformableNode node, float scale) {
        this.node = node;
        setScale(scale);
    }

    public ChangeObjectSizeRule(Control control, TransformableNode node, float minScale, float maxScale) {
        super(control);
        this.node = node;
        this.minScale = minScale;
        this.maxScale = maxScale;
    }

    public ChangeObjectSizeRule(Control control, TransformableNode node, float scale) {
        super(control);
        this.node = node;
        setScale(scale);
    }

    @Override
    public void execute() {
        node.getScaleController().setMinScale(minScale);
        node.getScaleController().setMaxScale(maxScale);
    }


    public void setScale(float min, float max){
        minScale = min;
        maxScale = max;
    }

    public void setScale(float scale){
        minScale = scale/2;
        maxScale = scale*2;
    }
}
