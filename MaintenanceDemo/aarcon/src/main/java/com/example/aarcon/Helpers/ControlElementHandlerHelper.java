package com.example.aarcon.Helpers;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Function;

public class ControlElementHandlerHelper {

    private Renderable moveRenderable;
    private Renderable rotateRenderable;
    private Renderable scaleRenderable;
    private Activity activity;
    private android.os.Handler handler;

    public ControlElementHandlerHelper(final TransformableNode controlNode, Activity activity) {
        this.activity = activity;
        renderAll();
        final Vector3 controlPosition= controlNode.getLocalPosition();
        handler = new android.os.Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                if(controlNode.getRenderable()== moveRenderable){
                    controlNode.setRenderable(rotateRenderable);
                    setScale(0.8f,controlNode);
                    //TODO remove System.out.println("rotate");
                }
                else{
                    if(controlNode.getRenderable()== rotateRenderable){
                        controlNode.setRenderable(scaleRenderable);
                        setScale(0.5f,controlNode);
                        controlNode.setLocalPosition(new Vector3(controlNode.getLocalPosition().x+0.15f,controlNode.getLocalPosition().y,controlNode.getLocalPosition().z));
                        //System.out.println("scale");
                    }
                    else {
                        controlNode.setRenderable(moveRenderable);
                        setScale(0.5f,controlNode);
                        controlNode.setLocalPosition(controlPosition);
                        //System.out.println("move");
                    }
                }
            }
        };
    }

    private void setScale(float scale, TransformableNode node){
        node.getScaleController().setMinScale(scale/2);
        node.getScaleController().setMaxScale(scale*2);
    }

    public Handler getHandler(){
        return handler;
    }

    public void renderAll(){
        renderMove();
        renderRotate();
        renderScale();
    }

    public void renderMove(){
        ModelRenderable.builder()
                .setSource(activity, Uri.parse("arrow_move.sfb"))

                .build()
                .thenAccept(new Consumer<ModelRenderable>() {
                    @Override
                    public void accept(ModelRenderable renderable) {
                        moveRenderable = renderable;
                    }
                })
                .exceptionally(
                        new Function<Throwable, Void>() {
                            @Override
                            public Void apply(Throwable throwable) {
                                Toast toast =
                                        Toast.makeText(activity, "Unable to load move renderable", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            }
                        });
    }

    public void renderRotate(){
        ModelRenderable.builder()
                .setSource(activity, Uri.parse("arrow_rotate.sfb"))

                .build()
                .thenAccept(new Consumer<ModelRenderable>() {
                    @Override
                    public void accept(ModelRenderable renderable) {
                        rotateRenderable = renderable;
                    }
                })
                .exceptionally(
                        new Function<Throwable, Void>() {
                            @Override
                            public Void apply(Throwable throwable) {
                                Toast toast =
                                        Toast.makeText(activity, "Unable to load rotate renderable", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            }
                        });
    }

    public void renderScale(){
        ModelRenderable.builder()
                .setSource(activity, Uri.parse("arrow_scale.sfb"))

                .build()
                .thenAccept(new Consumer<ModelRenderable>() {
                    @Override
                    public void accept(ModelRenderable renderable) {
                        scaleRenderable = renderable;
                    }
                })
                .exceptionally(
                        new Function<Throwable, Void>() {
                            @Override
                            public Void apply(Throwable throwable) {
                                Toast toast =
                                        Toast.makeText(activity, "Unable to load scale renderable", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return null;
                            }
                        });
    }
}
