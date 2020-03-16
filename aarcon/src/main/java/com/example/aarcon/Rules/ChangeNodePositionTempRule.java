package com.example.aarcon.Rules;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aarcon.Conditions.NodeOffScreenCondition;
import com.example.aarcon.Conditions.SettableCondition;
import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ChangeNodePositionTempRule extends Rule {

    private ImageView indicatorView;
    private ArFragment arFragment;
    private ViewGroup viewGroup;
    private TransformableNode node;

    private ChangeIndicatorPositionRule changeIndicatorPositionRule;
    private ChangeLayoutMoveNodeRule changeLayoutMoveNodeRule;
    private ChangeNodePositionOnClickRule changeNodePositionOnClickRule;
    private ChangeVisibilityRule changeVisibilityRule;
    private SettableCondition settableCondition;
    private NodeOffScreenCondition nodeOffScreenCondition;

    public ChangeNodePositionTempRule(ImageView indicatorView, ArFragment arFragment, ViewGroup viewGroup, TransformableNode node) {
        this.indicatorView = indicatorView;
        this.arFragment = arFragment;
        this.viewGroup = viewGroup;
        this.node = node;
    }

    public ChangeNodePositionTempRule(Control control, ImageView indicatorView, ArFragment arFragment, ViewGroup viewGroup, TransformableNode node) {
        super(control);
        this.indicatorView = indicatorView;
        this.arFragment = arFragment;
        this.viewGroup = viewGroup;
        this.node = node;
    }

    @Override
    public void execute() {
        if(changeIndicatorPositionRule == null) {
            changeIndicatorPositionRule = new ChangeIndicatorPositionRule(control, arFragment, indicatorView, node);
            changeLayoutMoveNodeRule = new ChangeLayoutMoveNodeRule(control, arFragment, viewGroup, node);
            changeNodePositionOnClickRule = new ChangeNodePositionOnClickRule(control, indicatorView, node, changeLayoutMoveNodeRule.getLocalPosition());
            changeVisibilityRule = new ChangeVisibilityRule(control, arFragment, indicatorView);

            settableCondition = new SettableCondition(control);
            changeLayoutMoveNodeRule.addCondition(settableCondition);
            changeNodePositionOnClickRule.addCondition(settableCondition);

            nodeOffScreenCondition = new NodeOffScreenCondition(control, arFragment, node);
            changeIndicatorPositionRule.addCondition(nodeOffScreenCondition);
            changeVisibilityRule.addCondition(nodeOffScreenCondition);
        }
        settableCondition.setTruthState(true);
    }

    public void setIndicatorView(ImageView indicatorView) {
        if (this.indicatorView != indicatorView) {
            this.indicatorView = indicatorView;
            changeIndicatorPositionRule.setIndicatorView(indicatorView);
            changeNodePositionOnClickRule.setView(indicatorView);
            changeVisibilityRule.setImageView(indicatorView);
        }
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        changeLayoutMoveNodeRule.setViewGroup(viewGroup);
        changeLayoutMoveNodeRule.execute();
    }

    public void setTransformableNode(TransformableNode node) {
        this.node = node;
        changeIndicatorPositionRule.setTransformableNode(node);
        changeLayoutMoveNodeRule.setNode(node);
        changeNodePositionOnClickRule.setNode(node);
        nodeOffScreenCondition.setTransformableNode(node);
    }

    public ImageView getIndicatorView() {
        return indicatorView;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public TransformableNode getNode() {
        return node;
    }

    @Override
    public void unexecute() {
        super.unexecute();
        settableCondition.setTruthState(false);
        changeIndicatorPositionRule.unexecute();
        changeLayoutMoveNodeRule.unexecute();
        changeNodePositionOnClickRule.unexecute();
        changeVisibilityRule.unexecute();
    }
}
