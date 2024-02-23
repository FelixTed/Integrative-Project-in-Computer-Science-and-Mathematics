package com.example.IntegrativeProject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class GameClass extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Stone[] stones1 = new Stone[3];
        Stone[] stones2 = new Stone[3];
        Pane pane = new Pane();
        Stone stone1 = new Stone();
        stone1.setCenterX(100);
        stone1.setCenterY(400);

        //Test case
        stone1.startMoving(150,25,stones1,stones2,stone1);

        Image backGroundIm = new Image("gameBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(bGIMG);

        pane.setBackground(background);


        Circle wholeInner = new Circle(1200 ,400,30);
        Circle insideInner = new Circle(1200,400,15);


        Shape donut1 = Shape.subtract(wholeInner, insideInner);
        donut1.setFill(Color.RED);

        Circle wholeMid = new Circle(1200,400,60);
        Circle insideMid = new Circle(1200,400,30);
        insideMid.setFill(Color.TRANSPARENT);

        Shape donut2 = Shape.subtract(wholeMid,insideMid);
        donut2.setFill(Color.LIGHTGRAY);

        Circle wholeOuter = new Circle(1200,400,90);
        Circle insideOuter = new Circle(1200,400,60);
        insideOuter.setFill(Color.TRANSPARENT);

        Shape donut3= Shape.subtract(wholeOuter,insideOuter);
        donut3.setFill(Color.BLUE);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(50));

        Label lbl1 = new Label("Kinetic Energy: ");
        TextField tF1 = new TextField();
        Label lbl2 = new Label("Angle: ");
        TextField tF2 = new TextField();
        Button Launch = new Button("Launch");
        hBox.getChildren().addAll(lbl1,tF1,lbl2,tF2,Launch);

        hBox.setTranslateX(50);
        hBox.setTranslateY(600);


        Label Turn = new Label("Player 1's turn");
        Turn.setFont(Font.font("Times New Roman",32));
        Turn.setTranslateX(30);
        Turn.setTranslateY(30);

        Rectangle innerRect = new Rectangle(25,175,1350,450);
        innerRect.setFill(Color.LIGHTCYAN);
        innerRect.setStroke(Color.BLACK);

        Line normal = new Line(150,400,250,400);
        normal.getStrokeDashArray().addAll(4d);
        pane.getChildren().addAll(innerRect,donut1,donut2,donut3,Turn,normal,stone1,hBox);


        Scene scene = new Scene( pane,1440,720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


}