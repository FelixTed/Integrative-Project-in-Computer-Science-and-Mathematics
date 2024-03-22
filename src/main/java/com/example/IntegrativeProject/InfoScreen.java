package com.example.IntegrativeProject;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
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
    int index = 0;
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

        ImageView physicsArray[] = new ImageView[4];
        Image collImage = new Image("Collisions.png");
        ImageView collView = new ImageView(collImage);
        Image displacementImage = new Image("DisplacementTrig.png");
        ImageView displacementView = new ImageView(displacementImage);
        Image physics = new Image("physics.png");
        ImageView imgViewPhysics1 = new ImageView(physics);
        Image physics2 = new Image("ImgViewPhysics2.png");
        ImageView imgViewPhysics2 = new ImageView(physics2);
        Image nextButt = new Image("Next.png");
        ImageView nextButtView = new ImageView(nextButt);
        Image backButt = new Image("Back.png");
        ImageView backButtView = new ImageView(backButt);
        Image physics3 = new Image("ImgViewPhysics3.png");
        ImageView imgViewPhysics3 = new ImageView(physics3);
        Image physics4 = new Image("ImgViewPhysics4.png");
        ImageView imgViewPhysics4 = new ImageView(physics4);

        nextButtView.setFitWidth(100);
        nextButtView.setFitHeight(100);
        nextButtView.setTranslateX(850);
        nextButtView.setTranslateY(700);

        backButtView.setFitWidth(100);
        backButtView.setFitHeight(100);
        backButtView.setTranslateX(650);
        backButtView.setTranslateY(700);

        imgViewPhysics1.setFitHeight(600);
        imgViewPhysics1.setFitWidth(733);

        imgViewPhysics2.setFitHeight(600);
        imgViewPhysics2.setFitWidth(733);

        imgViewPhysics3.setFitHeight(600);
        imgViewPhysics3.setFitWidth(733);

        imgViewPhysics4.setFitHeight(600);
        imgViewPhysics4.setFitWidth(733);

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

        physicsArray[0] = imgViewPhysics1;
        physicsArray[1] = imgViewPhysics2;
        physicsArray[2] = imgViewPhysics3;
        physicsArray[3] = imgViewPhysics4;

        Image backGroundIm = new Image("gameBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(bGIMG);

        pane.setBackground(background);




        HBox physicsPane = new HBox();

        physicsPane.setTranslateX(400);
        physicsPane.setTranslateY(100);


        physicsPane.getChildren().addAll(imgViewPhysics1);




        pane.getChildren().addAll(backButtView,nextButtView, physicsPane ,lblTitle,menuButtView,physicsBox);



            nextButtView.setOnMouseClicked(e -> {
                if(index<physicsArray.length-1) {
                    physicsPane.getChildren().clear();
                    index++;
                    physicsPane.getChildren().add(physicsArray[index]);
                }

            });



            backButtView.setOnMouseClicked(e -> {
                if(index>0) {
                    physicsPane.getChildren().clear();
                    index--;
                    physicsPane.getChildren().add(physicsArray[index]);
                }

            });


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