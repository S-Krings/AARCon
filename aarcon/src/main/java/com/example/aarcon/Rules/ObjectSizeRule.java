package com.example.aarcon.Rules;
import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.TransformableNode;

public class ObjectSizeRule extends Rule {

    private TransformableNode node;
    private float minScale = 0.5f;
    private float maxScale = 2f;

    public ObjectSizeRule(TransformableNode node, float minScale, float maxScale) {
        this.node = node;
        this.minScale = minScale;
        this.maxScale = maxScale;
    }

    public ObjectSizeRule(TransformableNode node, float scale) {
        this.node = node;
        setScale(scale);
    }

    public ObjectSizeRule(Control control, TransformableNode node, float minScale, float maxScale) {
        super(control);
        this.node = node;
        this.minScale = minScale;
        this.maxScale = maxScale;
    }

    public ObjectSizeRule(Control control, TransformableNode node, float scale) {
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
