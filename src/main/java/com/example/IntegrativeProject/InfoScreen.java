package com.example.IntegrativeProject;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoScreen extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        Image menuButt = new Image("MenuButton.png");
        ImageView menuButtView = new ImageView(menuButt);
        menuButtView.setTranslateX(70);
        menuButtView.setTranslateY(700);
        menuButtView.setFitHeight(90);
        menuButtView.setFitWidth(192);
        VBox physicsBox = new VBox(15);

        Image collImage = new Image("Collisions.png");
        ImageView collView = new ImageView(collImage);
        Image displacementImage = new Image("DisplacementTrig.png");
        ImageView displacementView = new ImageView(displacementImage);

        displacementView.setFitWidth(340);
        displacementView.setFitHeight(224);
        collView.setFitHeight(224);
        collView.setFitWidth(340);
        physicsBox.getChildren().addAll(displacementView,collView);
        physicsBox.setTranslateX(1175);
        physicsBox.setTranslateY(200);
        Label lblTitle = new Label("Physics Info");
        lblTitle.setFont(Font.font("Times New Roman",32));
        lblTitle.setTranslateX(700);
        lblTitle.setTranslateY(50);


        Image backGroundIm = new Image("gameBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(bGIMG);

        pane.setBackground(background);

        Image physics = new Image("physics.png");

        ImageView imgViewPhysics = new ImageView(physics);


        HBox sliderBox = new HBox();

        Slider slider = new Slider();
        slider.setOrientation(Orientation.VERTICAL);

        sliderBox.getChildren().addAll(imgViewPhysics,slider);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sliderBox);
        scrollPane.setTranslateX(400);
        scrollPane.setTranslateY(100);

        pane.getChildren().addAll(scrollPane,lblTitle,menuButtView,physicsBox);

        menuButtView.setOnMouseClicked(e->{
            stage.close();
            Stage s = new Stage();
            try{
                new MainMenu().start(s);
            }catch(IOException ex){
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(pane,1550, 800);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}