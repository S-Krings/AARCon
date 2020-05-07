package com.example.aarcon.Actions;

import android.app.Activity;

import com.example.aarcon.Control;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Action subclass for  changing the transparency of a TransformableNode.
 */
public class ChangeTransparencyAction extends Action {

    private Activity activity;
    private TransformableNode transformableNode;
    private Material baseMaterial;
    private Color transparentColor = new Color(200,200,200,0.2f);

    public ChangeTransparencyAction(Activity activity, TransformableNode transformableNode) {
        this.activity = activity;
        this.transformableNode = transformableNode;
    }

    public ChangeTransparencyAction(Activity activity, TransformableNode transformableNode, Color transparentColor) {
        this.activity = activity;
        this.transformableNode = transformableNode;
        this.transparentColor = transparentColor;
    }

    public ChangeTransparencyAction(Control control, Activity activity, TransformableNode transformableNode) {
        super(control);
        this.transformableNode = transformableNode;
        this.activity = activity;
    }

    public ChangeTransparencyAction(Control control, Activity activity, TransformableNode transformableNode, Color transparentColor) {
        super(control);
        this.activity = activity;
        this.transformableNode = transformableNode;
        this.transparentColor = transparentColor;
    }

    @Override
    public void execute() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                baseMaterial = transformableNode.getRenderable().getMaterial().makeCopy();

                CompletableFuture<Material> materialCompletableFuture =
                        MaterialFactory.makeTransparentWithColor(activity, transparentColor);

                materialCompletableFuture.thenAccept(new Consumer<Material>() {
                    @Override
                    public void accept(Material material) {
                        Renderable r2 = transformableNode.getRenderable().makeCopy();
                        r2.setMaterial(material);
                        transformableNode.setRenderable(r2);
                    }
                });
            }
        });
    }

    @Override
    public void unexecute() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (baseMaterial != null){
                    transformableNode.getRenderable().setMaterial(baseMaterial);
                }
            }
        });
    }
}
