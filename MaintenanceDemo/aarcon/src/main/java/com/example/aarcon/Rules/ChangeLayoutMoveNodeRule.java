package com.example.aarcon.Rules;

import android.view.View;
import android.view.ViewGroup;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.PositionChangeButtonAdder;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * Rule subclass for adding an arrow to any ViewRenderable, which, when clicked, teleports it one meter to the right.
 */
public class ChangeLayoutMoveNodeRule extends Rule{

    private ArFragment arFragment;
    private ViewGroup viewGroup;
    private TransformableNode node;
    private Vector3 localPosition;

    public ChangeLayoutMoveNodeRule(ArFragment arFragment, ViewGroup viewGroup, TransformableNode node) {
        this.arFragment = arFragment;
        this.viewGroup = viewGroup;
        this.node = node;
        localPosition = node.getLocalPosition();
    }

    public ChangeLayoutMoveNodeRule(Control control, ArFragment arFragment, ViewGroup viewGroup, TransformableNode node) {
        super(control);
        this.arFragment = arFragment;
        this.viewGroup = viewGroup;
        this.node = node;
        localPosition = node.getLocalPosition();
    }

    @Override
    public void execute() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                node.setLocalPosition((new Vector3(node.getLocalPosition().x+1,node.getLocalPosition().y,node.getLocalPosition().z)));
            }
        };
        PositionChangeButtonAdder m = new PositionChangeButtonAdder();
        m.addButton(arFragment, viewGroup, onClickListener);
    }

    public Vector3 getLocalPosition() {
        return localPosition;
    }

    @Override
    public void unexecute(){
        node.setLocalPosition(localPosition);
        viewGroup.removeViewAt(0);
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    public void setNode(TransformableNode node) {
        this.node = node;
    }

    public void setLocalPosition(Vector3 localPosition) {
        this.localPosition = localPosition;
    }
}
