package com.example.IntegrativeProject;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Board extends Application {
    //Data field
    private TextField energyField = new TextField();
    private TextField angleField = new TextField();
    private Label statusLabel = new Label("Player 1's Turn");
    private Button launchButton = new Button("Launch");
    private  Line angleLine = new Line(100,380,250,380);
    private double lineLength = 150;
    private Target target = new Target(new Circle(1100,380,40),new Circle(1100,380,100),new Circle(1100,380,180));
    private IntegerProperty currentStone = new SimpleIntegerProperty();
    private int currentPlayer;
    @Override
    public void start(Stage stage) throws IOException {
        //Initializing player 1 and 2, with corresponding colored stones
        Stone[] stones1 = {new Stone(new Image("blueStone.png")), new Stone(new Image("blueStone.png")), new Stone(new Image("blueStone.png"))};
        Stone[] stones2 = {new Stone(new Image("redStone.png")), new Stone(new Image("redStone.png")), new Stone(new Image("redStone.png"))};

        for(int i = 0; i< stones1.length;i++){
            stones1[i].setPlayerID(1);
            stones2[i].setPlayerID(2);
        }

        //Creating the players and linking them to their personal list of stone
        Player player1 = new Player(1,stones1);
        Player player2 = new Player(2,stones2);
        Player[] player = {player1,player2};

        //Setting the starting state of the game
        currentStone.set(0);
        currentPlayer = 0;
        //Setting up pane to make nodes visible
        Pane pane = new Pane();

        //setting the background
        Image backGroundIm = new Image("gameBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(bGIMG);
        pane.setBackground(background);

        //Setting circles for the target
        Circle wholeInner = new Circle(1100 ,380,40);


        wholeInner.setFill(Color.RED);

        Circle wholeMid = new Circle(1100,380,100);
        Circle insideMid = new Circle(1100,380,40);
        insideMid.setFill(Color.TRANSPARENT);

        Shape donut2 = Shape.subtract(wholeMid,insideMid);
        donut2.setFill(Color.LIGHTGRAY);

        Circle wholeOuter = new Circle(1100,380,180);
        Circle insideOuter = new Circle(1100,380,100);
        insideOuter.setFill(Color.TRANSPARENT);

        Shape donut3= Shape.subtract(wholeOuter,insideOuter);
        donut3.setFill(Color.BLUE);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setPadding(new Insets(50));

        Label lbl1 = new Label("Kinetic Energy: ");

        Label lbl2 = new Label("Angle: ");

        hBox.getChildren().addAll(lbl1,energyField,lbl2,angleField,launchButton);

        hBox.setTranslateX(50);
        hBox.setTranslateY(600);

        statusLabel.setFont(Font.font("Times New Roman",32));
        statusLabel.setTextFill(Color.BLUE);
        statusLabel.setTranslateX(30);
        statusLabel.setTranslateY(30);

        Rectangle innerRect = new Rectangle(25,80,1350,600);
        innerRect.setFill(Color.LIGHTCYAN);
        innerRect.setStroke(Color.BLACK);

        target.getRadius1().setFill(null);
        target.getRadius2().setFill(null);
        target.getRadius3().setFill(null);

        target.getRadius1().setStroke(Color.BLACK);
        target.getRadius2().setStroke(Color.BLACK);
        target.getRadius3().setStroke(Color.BLACK);

        angleLine.getStrokeDashArray().addAll(4d);
        pane.getChildren().addAll(innerRect,wholeInner,donut2,donut3,statusLabel,angleLine,hBox,stones1[0]);

        //Modify the angle of the line according to the angle value inputted
        angleField.textProperty().addListener(e -> {
            drawAngle();
        });
        //Modify the angle of the line according to the angle value inputted
        energyField.textProperty().addListener(e -> {
            drawLength();
        });
        //User Input
        launchButton.setOnAction(e -> {
            Stone tempStone = player[currentPlayer].getStoneList()[currentStone.getValue()];
            try {
                tempStone.startMoving(Integer.parseInt(energyField.getText()), Integer.parseInt(angleField.getText()), stones1, stones2, tempStone);
            }catch(NumberFormatException nfe){
                try {
                    tempStone.startMoving(Integer.parseInt(energyField.getText()), 0, stones1, stones2, tempStone);
                }catch(NumberFormatException nfe2){
                    tempStone.startMoving(0,0,stones1,stones2,tempStone);
                }
            }
            tempStone.setActive(true);

           /* //Wait for the stone to stop moving before allowing the game to continue
            launchButton.setDisable(true);
            while(tempStone.isMoving()){
            }
            launchButton.setDisable(false);*/

            if(currentPlayer == 1){
                currentStone.set(currentStone.getValue()+1);
                currentPlayer = 0;
                statusLabel.setText("Player 1's Turn");
                statusLabel.setTextFill(Color.BLUE);
            }else {
                currentPlayer = 1;
                statusLabel.setText("Player 2's Turn");
                statusLabel.setTextFill(Color.RED);
            }
            try {
                pane.getChildren().add(player[currentPlayer].getStoneList()[currentStone.getValue()]);
            }catch(ArrayIndexOutOfBoundsException aioobe){
                launchButton.setDisable(true);
                //call endgame method
            }
        });
        Scene scene = new Scene( pane,1440,720);
        stage.setTitle("Board");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    //Modify the length of the line based on the value in energyField
    public void drawLength(){
        try {
            int inputtedLength = Integer.parseInt(energyField.getText());
            if(inputtedLength < 30)
                lineLength = 1.75 * 30;
            else if(inputtedLength > 300)
                lineLength = 1.75 * 300;
            else
                lineLength = 1.75 * inputtedLength;
        }catch(NumberFormatException nfe){
            lineLength = 150;
        }finally{
            drawAngle();
        }
    }
    //Draw the
    public  void drawAngle(){
        try {
            double angleRad = Math.toRadians(Integer.parseInt(angleField.getText()));
            angleLine.setEndX(100 + lineLength * Math.cos(angleRad));
            angleLine.setEndY(380 - lineLength * Math.sin(angleRad));
        }catch(NumberFormatException nfe){
            //if angle input is not a numerical value, set angle line to initial position
            angleLine.setEndX(100+lineLength);
            angleLine.setEndY(380);
        }
    }

}