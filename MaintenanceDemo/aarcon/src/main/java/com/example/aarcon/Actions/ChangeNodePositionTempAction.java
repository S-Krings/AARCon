package com.example.aarcon.Actions;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aarcon.Conditions.NodeOffScreenCondition;
import com.example.aarcon.Conditions.SettableCondition;
import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * Action subclass combining the ChangeIndicatorPositionAction, the ChangeLayoutMoveNodeAction, the ChangeNodePositionOnClickAction and ChangeVisibilityAction to make it possible to teleport a ViewRenderable one meter to the right by clicking an automatically added arrow and get it back by clicking the off-screen-indicator appearing on its disappearance.
 */
public class ChangeNodePositionTempAction extends Action {

    private ImageView indicatorView;
    private ArFragment arFragment;
    private ViewGroup viewGroup;
    private TransformableNode node;

    private ChangeIndicatorPositionAction changeIndicatorPositionRule;
    private ChangeLayoutMoveNodeAction changeLayoutMoveNodeRule;
    private ChangeNodePositionOnClickAction changeNodePositionOnClickRule;
    private ChangeVisibilityAction changeVisibilityRule;
    private SettableCondition settableCondition;
    private NodeOffScreenCondition nodeOffScreenCondition;

    public ChangeNodePositionTempAction(ImageView indicatorView, ArFragment arFragment, ViewGroup viewGroup, TransformableNode node) {
        this.indicatorView = indicatorView;
        this.arFragment = arFragment;
        this.viewGroup = viewGroup;
        this.node = node;
    }

    public ChangeNodePositionTempAction(Control control, ImageView indicatorView, ArFragment arFragment, ViewGroup viewGroup, TransformableNode node) {
        super(control);
        this.indicatorView = indicatorView;
        this.arFragment = arFragment;
        this.viewGroup = viewGroup;
        this.node = node;
    }

    @Override
    public void execute() {
        if(changeIndicatorPositionRule == null) {
            changeIndicatorPositionRule = new ChangeIndicatorPositionAction(control, arFragment, indicatorView, node);
            changeLayoutMoveNodeRule = new ChangeLayoutMoveNodeAction(control, arFragment, viewGroup, node);
            changeNodePositionOnClickRule = new ChangeNodePositionOnClickAction(control, indicatorView, node, changeLayoutMoveNodeRule.getLocalPosition());
            changeVisibilityRule = new ChangeVisibilityAction(control, arFragment, indicatorView);

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
