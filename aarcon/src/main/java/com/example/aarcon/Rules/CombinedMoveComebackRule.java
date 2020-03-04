package com.example.aarcon.Rules;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aarcon.Conditions.NodeOffScreenCondition;
import com.example.aarcon.Conditions.SettableCondition;
import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class CombinedMoveComebackRule extends Rule {

    private ImageView indicatorView;
    private ArFragment arFragment;
    private ViewGroup viewGroup;
    private TransformableNode node;

    private IndicatorRule indicatorRule;
    private MoveAsideRule moveAsideRule;
    private MoveNodeOnClickRule moveNodeOnClickRule;
    private VisibilityRule visibilityRule;
    private SettableCondition settableCondition;
    private NodeOffScreenCondition nodeOffScreenCondition;

    public CombinedMoveComebackRule(ImageView indicatorView, ArFragment arFragment, ViewGroup viewGroup, TransformableNode node) {
        this.indicatorView = indicatorView;
        this.arFragment = arFragment;
        this.viewGroup = viewGroup;
        this.node = node;
    }

    public CombinedMoveComebackRule(Control control, ImageView indicatorView, ArFragment arFragment, ViewGroup viewGroup, TransformableNode node) {
        super(control);
        this.indicatorView = indicatorView;
        this.arFragment = arFragment;
        this.viewGroup = viewGroup;
        this.node = node;
    }

    @Override
    public void execute() {
        if(indicatorRule == null) {
            indicatorRule = new IndicatorRule(control, arFragment, indicatorView, node);
            moveAsideRule = new MoveAsideRule(control, arFragment, viewGroup, node);
            moveNodeOnClickRule = new MoveNodeOnClickRule(control, indicatorView, node, moveAsideRule.getLocalPosition());
            visibilityRule = new VisibilityRule(control, arFragment, indicatorView);

            settableCondition = new SettableCondition(control);
            moveAsideRule.addCondition(settableCondition);
            moveNodeOnClickRule.addCondition(settableCondition);

            nodeOffScreenCondition = new NodeOffScreenCondition(control, arFragment, node);
            indicatorRule.addCondition(nodeOffScreenCondition);
            visibilityRule.addCondition(nodeOffScreenCondition);
        }
        settableCondition.setTruthState(true);
    }

    public void setIndicatorView(ImageView indicatorView) {
        if (this.indicatorView != indicatorView) {
            this.indicatorView = indicatorView;
            indicatorRule.setIndicatorView(indicatorView);
            moveNodeOnClickRule.setView(indicatorView);
            visibilityRule.setImageView(indicatorView);
        }
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        moveAsideRule.setViewGroup(viewGroup);
        moveAsideRule.execute();
    }

    public void setTransformableNode(TransformableNode node) {
        this.node = node;
        indicatorRule.setTransformableNode(node);
        moveAsideRule.setNode(node);
        moveNodeOnClickRule.setNode(node);
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
        indicatorRule.unexecute();
        moveAsideRule.unexecute();
        moveNodeOnClickRule.unexecute();
        visibilityRule.unexecute();
    }
}
