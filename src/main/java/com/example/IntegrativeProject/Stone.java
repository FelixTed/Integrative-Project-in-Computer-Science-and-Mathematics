package com.example.IntegrativeProject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Stone extends Circle implements Runnable{
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
            System.out.println(this.getPlayerID() + " " + this.getCenterX() + " " + this.getCenterY()+ " " + this.speed);
            this.setMoving(true);
            double movement = thisStone.getSpeed() * keyFrameTimeIntervalMillis/1000;
            thisStone.setCenterX(thisStone.getCenterX() + (movement*Math.cos(Math.toRadians(angle))));
            thisStone.setCenterY(thisStone.getCenterY() - (movement*Math.sin(Math.toRadians(angle))));
            thisStone.setSpeed(thisStone.getSpeed()-(FRICTIONCOEFFICIENT*9.81*(keyFrameTimeIntervalMillis/1000)));
            for(int i = 0; i < stones1.length;i++){
                if(checkOverlap(stones1[i])){
                    this.setSpeed((speed/2));
                    stones1[i].setSpeed(speed/2);
                    System.out.println(Math.atan((this.getCenterY()-stones1[i].getCenterY())/(this.getCenterX()-stones1[i].getCenterX()))+180);
                    this.setAngle(Math.atan((this.getCenterY()-stones1[i].getCenterY())/(this.getCenterX()-stones1[i].getCenterX()))+180);
                    stones1[i].setAngle(Math.atan((this.getCenterY()-stones1[i].getCenterY())/(this.getCenterX()-stones1[i].getCenterX())));
                    stones1[i].startMoving(stones1[i].speed,stones1[i].angle,stones1,stones2,stones1[i]);
                }
                if(checkOverlap(stones2[i])){
                    this.setSpeed((speed/2));
                    stones2[i].setSpeed(speed/2);
                    System.out.println(Math.atan((this.getCenterY()-stones2[i].getCenterY())/(this.getCenterX()-stones2[i].getCenterX()))+180);
                    this.setAngle(Math.atan((this.getCenterY()-stones2[i].getCenterY())/(this.getCenterX()-stones2[i].getCenterX()))+180);
                    stones2[i].setAngle(Math.atan((this.getCenterY()-stones2[i].getCenterY())/(this.getCenterX()-stones2[i].getCenterX())));
                    stones2[i].startMoving(stones2[i].speed,stones2[i].angle,stones1,stones2,stones2[i]);
                }
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

    /*public void checkCollision(Stone stone){
        if(this.checkOverlap(stone) && stone.isActive()){
            this.movementTimeline.stop();
            stone.movementTimeline.stop();
            System.out.println("COLLISION!");

            this.setSpeed((speed/2));
            stone.setSpeed(speed/2);
System.out.println(Math.atan((this.getCenterY()-stone.getCenterY())/(this.getCenterX()-stone.getCenterX()))+180);
            this.setAngle(Math.atan((this.getCenterY()-stone.getCenterY())/(this.getCenterX()-stone.getCenterX()))+180);
            stone.setAngle(Math.atan((this.getCenterY()-stone.getCenterY())/(this.getCenterX()-stone.getCenterX())));


            System.out.println("Angle "+getAngle() + "Speed " + getSpeed() + "K  " + getKEnergy());

            Thread thread1 = new Thread(this);
            Thread thread2 = new Thread(stone);

            thread1.start();
            thread2.start();

            *//*this.startMoving(100,this.getAngle(),stones1,stones2,this);
            stone.startMoving(100,stone.getAngle(),stones1,stones2,stone);*//*
        }
    }*/

    private boolean checkOverlap(Stone circle) {
        double xDiff = this.getCenterX() - circle.getCenterX();
        double yDiff = this.getCenterY() - circle.getCenterY();

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return distance < (this.getRadius() + circle.getRadius()) && !(xDiff == 0 && yDiff == 0) && circle.isActive();
    }
    public boolean collisionDetection(Circle circle){
        if(circle.getCenterX() == this.getCenterX() && circle.getCenterY() == this.getCenterY()){
            return false;
        }else return this.getBoundsInParent().intersects(circle.getBoundsInParent());
    }

    @Override
    public void run() {
        double movement = this.getSpeed() * keyFrameTimeIntervalMillis/1000;
        this.setCenterX(this.getCenterX() + (movement*Math.cos(Math.toRadians(angle))));
        this.setCenterY(this.getCenterY() - (movement*Math.sin(Math.toRadians(angle))));
        this.setSpeed(this.getSpeed()-(FRICTIONCOEFFICIENT*9.81*(keyFrameTimeIntervalMillis/1000)));
        this.startMoving(100,this.getAngle(),stones1,stones2,this);
    }
}
