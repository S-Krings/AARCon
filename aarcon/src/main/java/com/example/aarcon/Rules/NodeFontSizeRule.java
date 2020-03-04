package com.example.aarcon.Rules;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aarcon.Control;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.TransformableNode;

public class NodeFontSizeRule extends Rule {

    private Activity activity;
    private TransformableNode transformableNode;
    private float sizeChange = 10f;
    TextView textView;
    float originalSize;

    public NodeFontSizeRule(Control control, Activity activity, TransformableNode transformableNode, int sizeChange) {
        super(control);
        this.activity = activity;
        this.transformableNode = transformableNode;
        this.sizeChange = sizeChange;
        ViewRenderable vr = (ViewRenderable) transformableNode.getRenderable();
        ViewGroup v = (ViewGroup) vr.getView();
        textView = (TextView) v.getChildAt(0);
        originalSize = textView.getTextSize();
        System.out.println("wwwwwwwwwwwwwww"+ originalSize);
    }

    @Override
    public void execute() {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewRenderable vr = (ViewRenderable) transformableNode.getRenderable();
                ViewGroup v = (ViewGroup) vr.getView();
                textView = (TextView) v.getChildAt(1);
                textView.setTextSize(textView.getTextSize()+sizeChange);
                System.out.println("################ Executed NodeFontSizeRule"+ textView.getText()+"   "+(textView.getTextSize() - sizeChange));
            }
        });
    }

    @Override
    public void unexecute() {
        System.out.println("&&&&&&&&&&&&&");


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("aaaaaaaaaaaaaa");

                //ViewRenderable vr = (ViewRenderable) transformableNode.getRenderable();
                //ViewGroup v = (ViewGroup) vr.getView();
                //textView = (TextView) v.getChildAt(1);
                if(textView.getTextSize()>sizeChange) {
                    textView.setTextSize(textView.getTextSize() - sizeChange);
                    System.out.println("bbbbbbbbbbbbb Unxecuted NodeFontSizeRule"+ textView.getText()+"   "+(textView.getTextSize() - sizeChange));
                }
                else{
                    textView.setTextSize(4);
                    System.out.println("ccccccccccccccccccccccc Unxecuted NodeFontSizeRule"+ textView.getText());
                }
            }
        });
    }

    public void setTransformableNode(TransformableNode transformableNode) {
        this.transformableNode = transformableNode;
    }
}
