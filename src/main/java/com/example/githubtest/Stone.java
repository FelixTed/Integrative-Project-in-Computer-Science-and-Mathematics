package com.example.githubtest;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Stone extends Circle implements Runnable {
    //Variable data fields
    private double speed;
    private boolean active = false;
    private double angle;
    private boolean isMoving = false;
    private int playerID;
    //Constant data fields
    private final double MASS = 50;
    private final ImageView GRAPHIC = new ImageView(new Image("PLACEHOLDER"));
    private final double FRICTIONCOEFFICIENT = 0.5;
    private final double RADIUS = 50;
    private final int BASEX = 0;
    private final int BASEY = 0;
    private final double keyFrameTimeIntervalMillis = 50;

    public Stone(){
        //Setting the physical radius of the stone
        this.setRadius(RADIUS);


        //Binding the positional values of the circle and the graphics of the stone together
        this.GRAPHIC.xProperty().bindBidirectional(this.centerXProperty());
        this.GRAPHIC.yProperty().bindBidirectional(this.centerYProperty());

        //Updating the position of the stone to the start position
        this.setCenterX(BASEX);
        this.setCenterY(BASEY);
    }
    public void startMoving(double speed, double angle, Stone[] stones1, Stone[] stones2){
        EventHandler calculateMovement = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //ADD MOVEMENT HANDLING
            }

        };
        Timeline movement = new Timeline(new KeyFrame(Duration.millis(keyFrameTimeIntervalMillis)));
        movement.setCycleCount(Timeline.INDEFINITE);
    }

    // Getter and Setter for speed
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double kEnergy) {
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
