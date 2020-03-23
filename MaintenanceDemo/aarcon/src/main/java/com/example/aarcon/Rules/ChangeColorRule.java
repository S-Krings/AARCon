package com.example.aarcon.Rules;

import android.app.Activity;
import android.content.Context;

import com.example.aarcon.Control;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Rule subclass for changing color of Renderables in TransformableNodes
 */
public class ChangeColorRule extends Rule {

    private Activity activity;
    private TransformableNode transformableNode;
    private Color color = new Color(255,0,0);
    private Renderable renderable;

    public ChangeColorRule(Activity activity, TransformableNode transformableNode) {
        this.activity = activity;
        this.transformableNode = transformableNode;
    }

    public ChangeColorRule(Activity activity, TransformableNode transformableNode, Color color) {
        this.activity = activity;
        this.transformableNode = transformableNode;
        this.color = color;
    }

    public ChangeColorRule(Control control, Activity activity, TransformableNode transformableNode) {
        super(control);
        this.activity = activity;
        this.transformableNode = transformableNode;
    }

    public ChangeColorRule(Control control, Activity activity, TransformableNode transformableNode, Color color) {
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
