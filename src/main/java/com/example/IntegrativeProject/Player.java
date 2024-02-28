package com.example.IntegrativeProject;

public class Player {
    private int playerID;
    private Stone[] stoneList;
    private int pointsTotal;
    public Player(int playerID, Stone[] stonesList){
        this.playerID = playerID;
        this.stoneList = stonesList;
    }

    // Setter for playerID
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    // Getter for playerID
    public int getPlayerID() {
        return playerID;
    }

    // Setter for stoneList
    public void setStoneList(Stone[] stoneList) {
        this.stoneList = stoneList;
    }

    // Getter for stoneList
    public Stone[] getStoneList() {
        return stoneList;
    }

    // Setter for pointsTotal
    public void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    // Getter for pointsTotal
    public int getPointsTotal() {
        return pointsTotal;
    }
}
