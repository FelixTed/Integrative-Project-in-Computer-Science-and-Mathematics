package com.example.IntegrativeProject;

import javafx.scene.shape.Circle;

public class Target {
    private Circle radius1;
    private Circle radius2;
    private Circle radius3;
    private int points1 =100;
    private int points2 = 50;
    private int points3 = 25;

    public Target(Circle radius1, Circle radius2, Circle radius3) {
        this.radius1 = radius1;
        this.radius2 = radius2;
        this.radius3 = radius3;
    }

    public int calculatePoints(Stone[] stones) {
        int totalPoints = 0;
        for (Stone stone : stones) {
            double distance = Math.sqrt(Math.pow((stone.getCenterX() - radius1.getCenterX()), 2) + Math.pow((stone.getCenterY() - radius1.getCenterY()), 2)); // Assuming the center of the target is at (0, 0)
            if (distance <= radius1.getRadius()) {
                totalPoints += points1;
            } else if (distance <= radius2.getRadius()) {
                totalPoints += points2;
            } else if (distance <= radius3.getRadius()) {
                totalPoints += points3;
            }
        }
        return totalPoints;
    }

    public Circle getRadius1() {
        return radius1;
    }

    public Circle getRadius2() {
        return radius2;
    }

    public Circle getRadius3() {
        return radius3;
    }

    public void setRadius1(Circle radius1) {
        this.radius1 = radius1;
    }

    public void setRadius2(Circle radius2) {
        this.radius2 = radius2;
    }

    public void setRadius3(Circle radius3) {
        this.radius3 = radius3;
    }

    public int getPoints1() {
        return points1;
    }

    public int getPoints2() {
        return points2;
    }

    public int getPoints3() {
        return points3;
    }

    public void setPoints1(int points1) {
        this.points1 = points1;
    }

    public void setPoints2(int points2) {
        this.points2 = points2;
    }

    public void setPoints3(int points3) {

    }
}