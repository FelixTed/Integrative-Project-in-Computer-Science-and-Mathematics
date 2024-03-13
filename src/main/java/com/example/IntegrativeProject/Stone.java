package com.example.IntegrativeProject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Stone extends Circle{
    //Variable data fields
    Timeline movementTimeline = new Timeline();
    private double speed;
    private boolean active = false;
    private double angle;
    private boolean isMoving = false;
    private int playerID;

    private Stone[] stones1;
    private Stone[] stones2;
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
        this.stones1 = stones1;
        this.stones2 = stones2;
        this.setSpeed(kEnergy);
        this.angle = angle;
        active = true;

        movementTimeline.setCycleCount(Timeline.INDEFINITE);

        //Each keyframe calculates the movement of the stone based on speed and angle then updates speed. Also check for collisions
        KeyFrame keyframe = new KeyFrame(Duration.millis(keyFrameTimeIntervalMillis), e ->{
            System.out.println(this.getPlayerID() + " " + this.getCenterX() + " " + this.getCenterY());
            this.setMoving(true);
            double movement = thisStone.getSpeed() * keyFrameTimeIntervalMillis/1000;
            thisStone.setCenterX(thisStone.getCenterX() + (movement*Math.cos(Math.toRadians(angle))));
            thisStone.setCenterY(thisStone.getCenterY() - (movement*Math.sin(Math.toRadians(angle))));
            thisStone.setSpeed(thisStone.getSpeed()-(FRICTIONCOEFFICIENT*9.81*(keyFrameTimeIntervalMillis/1000)));
            for(int i = 0; i < stones1.length;i++){
                checkCollision(stones1[i]);
                checkCollision(stones2[i]);
            }
            if(thisStone.getSpeed() <= 0){
                this.setMoving(false);
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
    public double getKEnergy(){
        return 0.5*this.MASS*Math.pow(this.getSpeed(),2);
    }

    public void checkCollision(Stone stone){
        if(this.checkOverlap(stone) && stone.isActive()){
            this.movementTimeline.stop();
            stone.movementTimeline.stop();
            System.out.println("COLLISION!");

            this.setSpeed((speed/2));
            stone.setSpeed(speed/2);

            this.setAngle(Math.atan((this.getCenterY()-stone.getCenterY())/(this.getCenterX()-stone.getCenterX())));
            stone.setAngle(Math.atan((this.getCenterY()-stone.getCenterY())/(this.getCenterX()-stone.getCenterX())));


            System.out.println("Angle "+getAngle() + "Speed " + getSpeed() + "K  " + getKEnergy());

            this.startMoving(100,this.getAngle(),stones1,stones2,this);
            stone.startMoving(100,stone.getAngle(),stones1,stones2,this);
        }
    }

    private boolean checkOverlap(Circle circle) {
        double dx = this.getCenterX() - circle.getCenterX();
        double dy = this.getCenterY() - circle.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double radiusSum = this.getRadius() + circle.getRadius();
        return distance <= radiusSum && distance != 0;
    }
}
