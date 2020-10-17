/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package software.bernie.geckolib.core.snapshot;

import software.bernie.geckolib.core.processor.IBone;

public class BoneSnapshot {
    public String name;
    public float scaleValueX;
    public float scaleValueY;
    public float scaleValueZ;
    public float positionOffsetX;
    public float positionOffsetY;
    public float positionOffsetZ;
    public float rotationValueX;
    public float rotationValueY;
    public float rotationValueZ;
    public float mostRecentResetRotationTick = 0;
    public float mostRecentResetPositionTick = 0;
    public float mostRecentResetScaleTick = 0;
    public boolean isCurrentlyRunningRotationAnimation = true;
    public boolean isCurrentlyRunningPositionAnimation = true;
    public boolean isCurrentlyRunningScaleAnimation = true;
    private final IBone modelRenderer;

    public BoneSnapshot(IBone modelRenderer) {
        rotationValueX = modelRenderer.getPitch();
        rotationValueY = modelRenderer.getYaw();
        rotationValueZ = modelRenderer.getRoll();

        positionOffsetX = modelRenderer.getPositionX();
        positionOffsetY = modelRenderer.getPositionY();
        positionOffsetZ = modelRenderer.getPositionZ();

        scaleValueX = modelRenderer.getScaleX();
        scaleValueY = modelRenderer.getScaleY();
        scaleValueZ = modelRenderer.getScaleZ();

        this.modelRenderer = modelRenderer;
        this.name = modelRenderer.getName();
    }
    public BoneSnapshot(IBone modelRenderer, boolean dontSaveRotations) {
        if (dontSaveRotations) {
            rotationValueX = 0;
            rotationValueY = 0;
            rotationValueZ = 0;
        }

        rotationValueX = modelRenderer.getPitch();
        rotationValueY = modelRenderer.getYaw();
        rotationValueZ = modelRenderer.getRoll();

        positionOffsetX = modelRenderer.getPositionX();
        positionOffsetY = modelRenderer.getPositionY();
        positionOffsetZ = modelRenderer.getPositionZ();

        scaleValueX = modelRenderer.getScaleX();
        scaleValueY = modelRenderer.getScaleY();
        scaleValueZ = modelRenderer.getScaleZ();

        this.modelRenderer = modelRenderer;
        this.name = modelRenderer.getName();
    }
    public BoneSnapshot(BoneSnapshot snapshot) {
        scaleValueX = snapshot.scaleValueX;
        scaleValueY = snapshot.scaleValueY;
        scaleValueZ = snapshot.scaleValueZ;

        positionOffsetX = snapshot.positionOffsetX;
        positionOffsetY = snapshot.positionOffsetY;
        positionOffsetZ = snapshot.positionOffsetZ;

        rotationValueX = snapshot.rotationValueX;
        rotationValueY = snapshot.rotationValueY;
        rotationValueZ = snapshot.rotationValueZ;
        this.modelRenderer = snapshot.modelRenderer;
        this.name = snapshot.name;
    }

}
