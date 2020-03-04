package com.example.aarcon.Helpers;

import android.text.Layout;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.TransformableNode;

public class NodeToTextHelper {
    public static String textFromNode(TransformableNode node, int childNumber){
        try {
            ViewRenderable vr = (ViewRenderable) node.getRenderable();
            ViewGroup v = (ViewGroup) vr.getView();
            TextView t = (TextView) v.getChildAt(childNumber);
            return t.getText().toString();
        }
        catch (Exception e){
            return "Error. Text not found.";
        }
    }
}
