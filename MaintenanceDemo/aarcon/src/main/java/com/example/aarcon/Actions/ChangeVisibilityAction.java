package com.example.aarcon.Actions;

import android.view.View;
import android.widget.ImageView;

import com.example.aarcon.Control;
import com.google.ar.sceneform.ux.ArFragment;

/**
 * Action subclass for changing the visibility of an ImageView (between "visible" and "gone").
 */
public class ChangeVisibilityAction extends Action {
    private ArFragment arFragment;
    private ImageView imageView;

    public ChangeVisibilityAction(ArFragment arFragment, ImageView imageView) {
        this.arFragment = arFragment;
        this.imageView = imageView;
    }

    public ChangeVisibilityAction(Control control, ArFragment arFragment, ImageView imageView) {
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
