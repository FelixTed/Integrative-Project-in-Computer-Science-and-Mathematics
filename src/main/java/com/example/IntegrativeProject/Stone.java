package com.example.IntegrativeProject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Stone extends Circle implements Runnable {
    //Variable data fields
    private double speed;
    private boolean active = false;
    private double angle;
    private boolean isMoving = false;
    private int playerID;
    private Image graphic = new Image("blueStone.png");
    //Constant data fields
    private final double MASS = 20;
    private final double FRICTIONCOEFFICIENT = 1;
    private final double RADIUS = 50;
    private final int BASEX = 100;
    private final int BASEY = 380;
    private final double keyFrameTimeIntervalMillis = 10;

    public Stone(Image graphic){
        //Setting the physical radius of the stone
        this.setRadius(RADIUS);


        this.setFill(new ImagePattern(graphic));

        //Updating the position of the stone to the start position
        this.setCenterX(BASEX);
        this.setCenterY(BASEY);
    }
    public void startMoving(double kEnergy, double angle, Stone[] stones1, Stone[] stones2, Stone thisStone){
        this.setSpeed(kEnergy);
        this.angle = angle;
        active = true;

        Timeline movementTimeline = new Timeline();
        movementTimeline.setCycleCount(Timeline.INDEFINITE);

        //Each keyframe calculates the movement of the stone based on speed and angle then updates speed. Also check for collisions
        KeyFrame keyframe = new KeyFrame(Duration.millis(keyFrameTimeIntervalMillis), e ->{
            isMoving = true;
            double movement = thisStone.getSpeed() * keyFrameTimeIntervalMillis/1000;
            thisStone.setCenterX(thisStone.getCenterX() + (movement*Math.cos(Math.toRadians(angle))));
            thisStone.setCenterY(thisStone.getCenterY() - (movement*Math.sin(Math.toRadians(angle))));
            thisStone.setSpeed(thisStone.getSpeed()-(FRICTIONCOEFFICIENT*9.81*(keyFrameTimeIntervalMillis/1000)));
            if(thisStone.getSpeed() <= 0){
                isMoving= false;
                movementTimeline.stop();
            }
        });
        movementTimeline.getKeyFrames().add(keyframe);
        movementTimeline.play();
    }

    // Getter and Setter for speed
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }

    public void setSpeedKEnergy(double kEnergy) {
        //Setting speed according to kinetic energy and mass
        this.speed = Math.sqrt((2*kEnergy)/MASS);
    }

    // Getter and Setter for active
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Getter and Setter for angle
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    // Getter and Setter for isMoving
    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    // Getter and Setter for playerID
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public void run() {

    }
}
