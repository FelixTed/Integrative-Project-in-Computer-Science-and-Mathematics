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

import java.io.IOException;

public class Board extends Application {
    private TextField energyField = new TextField();
    private TextField angleField = new TextField();
    private Label statusLabel = new Label("Player 1's turn");
    private Button launchButton = new Button("Launch");
    private  Line angleLine = new Line(150,380,250,380);
    private Target target = new Target(new Circle(1100,380,40),new Circle(1100,380,100),new Circle(1100,380,180));
    private IntegerProperty currentStone = new SimpleIntegerProperty();
    private int currentPlayer;
    @Override
    public void start(Stage stage) throws IOException {
        Stone[] stones1 = new Stone[3];
        Stone[] stones2 = new Stone[3];
        Pane pane = new Pane();
        Stone stone1 = new Stone();
        stone1.setCenterX(100);
        stone1.setCenterY(380);

        //Test case
        stone1.startMoving(150,-25,stones1,stones2,stone1);

        Image backGroundIm = new Image("gameBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(bGIMG);

        pane.setBackground(background);


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
        statusLabel.setTranslateX(30);
        statusLabel.setTranslateY(30);

        Rectangle innerRect = new Rectangle(25,80,1350,600);
        innerRect.setFill(Color.LIGHTCYAN);
        innerRect.setStroke(Color.BLACK);

/*target.getRadius1().setFill(null);
target.getRadius2().setFill(null);
target.getRadius3().setFill(null);

        target.getRadius1().setStroke(Color.BLACK);
        target.getRadius2().setStroke(Color.BLACK);
        target.getRadius3().setStroke(Color.BLACK);*/

        angleLine.getStrokeDashArray().addAll(4d);
        pane.getChildren().addAll(innerRect,wholeInner,donut2,donut3,statusLabel,angleLine,stone1,hBox);

        //User Input
        launchButton.setOnAction(e -> {

        });

        Scene scene = new Scene( pane,1440,720);
        stage.setTitle("Board");
        stage.setScene(scene);
        stage.show();
    }


}