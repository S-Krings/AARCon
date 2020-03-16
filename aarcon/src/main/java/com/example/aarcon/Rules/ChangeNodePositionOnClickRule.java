package com.example.aarcon.Rules;

import android.view.View;

import com.example.aarcon.Control;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.TransformableNode;

public class ChangeNodePositionOnClickRule extends Rule {

    private View view;
    private TransformableNode node;
    private Vector3 position;

    public ChangeNodePositionOnClickRule(View view, TransformableNode node, Vector3 position) {
        this.view = view;
        this.node = node;
        this.position = position;
    }

    public ChangeNodePositionOnClickRule(Control control, View view, TransformableNode node, Vector3 position) {
        super(control);
        this.view = view;
        this.node = node;
        this.position = position;
    }

    @Override
    public void execute() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                node.setLocalPosition(position);
            }
        });
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setNode(TransformableNode node) {
        this.node = node;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }
}
