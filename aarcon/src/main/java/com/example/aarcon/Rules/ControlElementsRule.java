package com.example.aarcon.Rules;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.example.aarcon.Control;
import com.example.aarcon.Helpers.ControlElementHandlerHelper;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.Timer;
import java.util.TimerTask;

public class ControlElementsRule extends Rule{

    private Handler handler;
    private Timer timer;
    private TransformableNode transformableNode;

    public ControlElementsRule(Activity activity, TransformableNode transformableNode){
        this.transformableNode = transformableNode;
        handler = new ControlElementHandlerHelper(transformableNode,activity).getHandler();
    }

    public ControlElementsRule(Control control, Activity activity, TransformableNode transformableNode){
        super(control);
        this.transformableNode = transformableNode;
        handler = new ControlElementHandlerHelper(transformableNode,activity).getHandler();
    }

    @Override
    public void execute() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.sendToTarget();
            }
        },20,1000);
    }

    @Override
    public void unexecute() {
        timer.cancel();
        transformableNode.setEnabled(false);
    }
}
