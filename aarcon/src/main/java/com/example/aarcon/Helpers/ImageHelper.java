package com.example.aarcon.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.aarcon.R;
import com.google.ar.sceneform.ux.ArFragment;

public class ImageHelper {

    public Bitmap drawableToBitmap(Drawable drawable){
        return ((BitmapDrawable)drawable).getBitmap();
    }

    public Bitmap flipBitmap(Bitmap bitmap){
        Matrix matrix = new Matrix();
        //TODO matrix.preScale(-1.0f, 1.0f);
        matrix.postRotate(180);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public BitmapDrawable bitmapToDrawable(Context context, Bitmap bitmap){
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
