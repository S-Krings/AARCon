package com.example.aarcon.Helpers;

import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.ar.sceneform.ux.ArFragment;

public class MoveAsideButtonAdder {
    public void addButton(final ArFragment arFragment, final ViewGroup viewGroup, final View.OnClickListener onClickListener){
        arFragment.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView button = new TextView(arFragment.getContext());
                button.setTextSize(30);
                button.setAllCaps(false);
                button.setGravity(Gravity.RIGHT|Gravity.TOP);
                button.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                button.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                button.setBackground(null);
                button.setOnClickListener(onClickListener);
                button.setMaxHeight(45);
                button.setText(Html.fromHtml("<sup>→</sup> ",Html.FROM_HTML_MODE_LEGACY));
                viewGroup.addView(button,0);
            }
        });
    }
}
