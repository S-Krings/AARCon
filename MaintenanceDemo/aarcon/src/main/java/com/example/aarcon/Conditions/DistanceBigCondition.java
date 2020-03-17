package com.example.aarcon.Conditions;
import com.example.aarcon.Control;
import com.google.ar.core.Pose;

public class DistanceBigCondition extends Condition {

    private Pose pose1;
    private Pose pose2;
    private float threshold = 1.0f;

    public DistanceBigCondition(Pose pose1, Pose pose2) {
        this.pose1 = pose1;
        this.pose2 = pose2;
    }

    public DistanceBigCondition(Control control, Pose pose1, Pose pose2) {
        super(control);
        this.pose1 = pose1;
        this.pose2 = pose2;
    }

    public DistanceBigCondition(Pose pose1, Pose pose2, float threshold) {
        this.pose1 = pose1;
        this.pose2 = pose2;
        this.threshold = threshold;
    }

    public DistanceBigCondition(Control control, Pose pose1, Pose pose2, float threshold) {
        super(control);
        this.pose1 = pose1;
        this.pose2 = pose2;
        this.threshold = threshold;
    }

    @Override
    public boolean check() {
        float distanceX = pose1.tx() - pose2.tx();
        float distanceY = pose1.ty() - pose2.ty();
        float distanceZ = pose1.tz() - pose2.tz();
        return threshold < (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
    }

    public void setPose1(Pose pose1) {
        this.pose1 = pose1;
    }

    public void setPose2(Pose pose2) {
        this.pose2 = pose2;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

}
