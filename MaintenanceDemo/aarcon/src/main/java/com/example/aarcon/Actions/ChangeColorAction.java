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
 * Action subclass for changing color of Renderables in TransformableNodes
 */
public class ChangeColorAction extends Action {

    private Activity activity;
    private TransformableNode transformableNode;
    private Color color = new Color(255,0,0);
    private Renderable renderable;

    public ChangeColorAction(Activity activity, TransformableNode transformableNode) {
        this.activity = activity;
        this.transformableNode = transformableNode;
    }

    public ChangeColorAction(Activity activity, TransformableNode transformableNode, Color color) {
        this.activity = activity;
        this.transformableNode = transformableNode;
        this.color = color;
    }

    public ChangeColorAction(Control control, Activity activity, TransformableNode transformableNode) {
        super(control);
        this.activity = activity;
        this.transformableNode = transformableNode;
    }

    public ChangeColorAction(Control control, Activity activity, TransformableNode transformableNode, Color color) {
        super(control);
        this.activity = activity;
        this.transformableNode = transformableNode;
        this.color = color;
    }

    @Override
    public void execute() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                renderable = transformableNode.getRenderable();
                CompletableFuture<Material> materialCompletableFuture =
                        MaterialFactory.makeOpaqueWithColor(activity, color);
                //TODO transparentwithcolor?
                materialCompletableFuture.thenAccept(new Consumer<Material>() {
                    @Override
                    public void accept(Material material) {
                        Renderable renderable = transformableNode.getRenderable().makeCopy();
                        renderable.setMaterial(material);
                        transformableNode.setRenderable(renderable);
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
                if (renderable != null){
                    transformableNode.setRenderable(renderable);
                }
            }
        });
    }

    public void setContext(Activity activity) {
        this.activity = activity;
    }

    public void setTransformableNode(TransformableNode transformableNode) {
        this.transformableNode = transformableNode;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
