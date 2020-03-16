package com.example.aarcon.NotForPublishment;

import android.content.res.Resources;
import android.graphics.Point;

import com.google.ar.sceneform.math.Vector3;

public class Geometry {
    public float[] calculate(Point point1, Point point2){
        float[] sideIntersection = new float[]{-1,-1};
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        Vector3[] vectors = new Vector3[]{new Vector3(point1.x,point1.y,0),new Vector3(point2.x,point2.y,0)};
        float m = calculateMN(vectors)[0];
        float n = calculateMN(vectors)[1];

        float leftIntersect = calculateYforX(m,n,0);
        float rightIntersect = calculateYforX(m,n,width);
        float topIntersect = calculateXForY(m,n,0);
        float bottomIntersect = calculateXForY(m,n,height);

        if((leftIntersect > 0 || leftIntersect < height)&& point1.x <= point2.x){
            sideIntersection = new float[]{0,leftIntersect};
        }
        if((rightIntersect > 0 || rightIntersect < height)&& point1.x > point2.x) {
            sideIntersection = new float[]{width, rightIntersect};
        }
        if((topIntersect > 0 || topIntersect < width)&& point1.y > point2.y){
            sideIntersection = new float[]{topIntersect,0};
        }
        if((bottomIntersect > 0 || bottomIntersect < width)&& point1.y <= point2.y){
            sideIntersection = new float[]{bottomIntersect,height};
        }

        return sideIntersection;
    }

    public float[] calculateMN(Vector3[] vectors){
        float m;
        float n;
        if(vectors[0].y != vectors[1].y) {
            m = (vectors[1].x - vectors[0].x) / (vectors[1].y - vectors[0].y);
            n = vectors[0].y-vectors[0].x*m;
        }
        else{
            m = vectors[1].x - vectors[0].x;
            n = vectors[0].x;
        }
        return new float[]{m,n};
    }

    public float calculateXForY(float m, float n, float y){
        float x;
        if (m==0){
            x = n;
        }
        else {
            x = (y-n)/m;
        }
        return x;
    }

    public float calculateYforX(float m, float n, float x){
        return m*x+n;
    }

    public boolean p1Rightofp2(Point point1, Point point2){
        return (point1.x >= point2.x);
    }

    public  boolean directionUp(Point point1, Point point2){
        return (point1.y >= point2.y);
    }
}
