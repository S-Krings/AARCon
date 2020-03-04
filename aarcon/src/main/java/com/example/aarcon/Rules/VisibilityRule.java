package com.example.aarcon.Rules;

import android.view.View;
import android.widget.ImageView;

import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.ArFragment;

public class VisibilityRule extends Rule {
    private ArFragment arFragment;
    private ImageView imageView;

    public VisibilityRule(ArFragment arFragment, ImageView imageView) {
        this.arFragment = arFragment;
        this.imageView = imageView;
    }

    public VisibilityRule(Control control, ArFragment arFragment, ImageView imageView) {
        super(control);
        this.arFragment = arFragment;
        this.imageView = imageView;
    }

    @Override
    public void execute() {
        arFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.VISIBLE);
                imageView.requestLayout();
            }
        });
    }

    @Override
    public void unexecute(){
        arFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.GONE);
                imageView.requestLayout();
            }
        });
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
