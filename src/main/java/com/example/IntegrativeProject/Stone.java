package com.example.IntegrativeProject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Stone extends Circle{
    //Variable data fields
    Timeline movementTimeline = new Timeline();
    private double speed;
    private boolean active = false;
    private double angle;
    private BooleanProperty isMoving = new SimpleBooleanProperty(false);
    private int playerID;

    private Stone[] stones1;
    private Stone[] stones2;
    private Line[] borders;
    //Constant data fields
    private final double MASS = 20;
    private final double FRICTIONCOEFFICIENT = 0.05;
    private final double RADIUS = 50;
    private final int BASEX = 100;
    private final int BASEY = 380;
    private final double keyFrameTimeIntervalMillis = 10;


    public Stone(Image graphic,Line[] borders){
        //Setting the physical radius of the stone
        this.setRadius(RADIUS);

        this.borders = borders;

        this.setFill(new ImagePattern(graphic));

        //Updating the position of the stone to the start position
        this.setCenterX(BASEX);
        this.setCenterY(BASEY);
    }
    public void startMoving(double kEnergy, double angle, Stone[] stones1, Stone[] stones2, Stone thisStone){
        this.stones1 = stones1;
        this.stones2 = stones2;
        //Temporary fix for the bug happening at 150 J since we cannot find the cause
        if(kEnergy == 150){
            kEnergy = 149.9;
        }
        this.setSpeedKEnergy(kEnergy);
        this.angle = angle;
        if(this.angle < 0)
            angle = 360 + this.angle;
        active = true;

        movementTimeline.setCycleCount(Timeline.INDEFINITE);

        //Each keyframe calculates the movement of the stone based on speed and angle then updates speed. Also check for collisions
        KeyFrame keyframe = new KeyFrame(Duration.millis(keyFrameTimeIntervalMillis), e ->{
            System.out.println(this.getPlayerID() + " " + this.getAngle());
            this.setMoving(true);
            double movement = 100*thisStone.getSpeed() * keyFrameTimeIntervalMillis/1000;
            thisStone.setCenterX(thisStone.getCenterX() + (movement*Math.cos(Math.toRadians(this.angle))));
            thisStone.setCenterY(thisStone.getCenterY() - (movement*Math.sin(Math.toRadians(this.angle))));
            thisStone.setSpeed(thisStone.getSpeed()-(FRICTIONCOEFFICIENT*9.81*(keyFrameTimeIntervalMillis/1000)));
            for(int i = 0; i < stones1.length;i++){
                int coefficient = 0;
                if(checkOverlap(stones1[i])){
                    if(thisStone.getCenterX()>stones1[i].getCenterX())
                        coefficient = 1;
                    System.out.println("COLLISION! " + Math.toDegrees(Math.atan((thisStone.getCenterY()-stones1[i].getCenterY())/(thisStone.getCenterX()-stones1[i].getCenterX()))));
                    thisStone.setAngle(coefficient*180+(180+Math.toDegrees(Math.atan(-(thisStone.getCenterY()-stones1[i].getCenterY())/(thisStone.getCenterX()-stones1[i].getCenterX())))));
                    stones1[i].setAngle(coefficient*180+Math.toDegrees(Math.atan(-(thisStone.getCenterY()-stones1[i].getCenterY())/(thisStone.getCenterX()-stones1[i].getCenterX()))));

                    double tempMovement = 1*thisStone.getSpeed() * keyFrameTimeIntervalMillis/1000;
                    thisStone.setCenterX(thisStone.getCenterX() + (tempMovement*Math.cos(Math.toRadians(this.angle))));
                    thisStone.setCenterY(thisStone.getCenterY() - (tempMovement*Math.sin(Math.toRadians(this.angle))));
                    stones1[i].setCenterX(stones1[i].getCenterX() + (tempMovement*Math.cos(Math.toRadians(stones1[i].getAngle()))));
                    stones1[i].setCenterY(stones1[i].getCenterY() - (tempMovement*Math.sin(Math.toRadians(stones1[i].getAngle()))));

                    thisStone.setSpeedKEnergy((thisStone.getKEnergy()/2));
                    stones1[i].startMoving(thisStone.getKEnergy(),stones1[i].angle,stones1,stones2,stones1[i]);
                    thisStone.setSpeedKEnergy(this.getKEnergy()-0.3* thisStone.getKEnergy());


                }
                if(checkOverlap(stones2[i])){
                    if(thisStone.getCenterX()>stones2[i].getCenterX())
                        coefficient = 1;
                    System.out.println("COllision!" +Math.toDegrees(Math.atan(-(thisStone.getCenterY()-stones2[i].getCenterY())/(thisStone.getCenterX()-stones2[i].getCenterX()))));
                    thisStone.setAngle(coefficient*180+(180+Math.toDegrees(Math.atan(-(thisStone.getCenterY()-stones2[i].getCenterY())/(thisStone.getCenterX()-stones2[i].getCenterX())))));
                    stones2[i].setAngle(coefficient*180+Math.toDegrees(Math.atan(-(thisStone.getCenterY()-stones2[i].getCenterY())/(thisStone.getCenterX()-stones2[i].getCenterX()))));

                    double tempMovement = 1*thisStone.getSpeed() * keyFrameTimeIntervalMillis/1000;
                    thisStone.setCenterX(thisStone.getCenterX() + (tempMovement*Math.cos(Math.toRadians(this.angle))));
                    thisStone.setCenterY(thisStone.getCenterY() - (tempMovement*Math.sin(Math.toRadians(this.angle))));
                    stones2[i].setCenterX(stones2[i].getCenterX() + (tempMovement*Math.cos(Math.toRadians(stones2[i].getAngle()))));
                    stones2[i].setCenterY(stones2[i].getCenterY() - (tempMovement*Math.sin(Math.toRadians(stones2[i].getAngle()))));


                    thisStone.setSpeedKEnergy((thisStone.getKEnergy()/2));
                    stones2[i].startMoving(thisStone.getKEnergy(),stones2[i].angle,stones1,stones2,stones2[i]);
                    thisStone.setSpeedKEnergy(this.getKEnergy()-0.3* thisStone.getKEnergy());
                }


            }
            for(int i = 0; i<borders.length;i++){
                if(thisStone.intersects(borders[i].getBoundsInLocal())){
                    //Upper or lower border
                    if(i%2 == 1)
                        this.setAngle(-this.angle);
                    //right or left border
                    else
                        this.setAngle(180-this.angle);
                }
            }
            if(thisStone.getSpeed() <= 0){
                this.setMoving(false);
                if(thisStone.getCenterX() <250){
                    this.setActive(false);
                    this.setFill(null);
                }
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
    public void setKEnergy(double kEnergy){

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
    public BooleanProperty isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving.set(moving);
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
}
