package com.example.IntegrativeProject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class MainMenu extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Basic menu screen
        Image backGroundIm = new Image("CurlingBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(bGIMG);
        Pane pane = new Pane();
        pane.setBackground(background);
        try{
            Image iMPlay = new Image("PlayButton.png");

            Image iMDrawing = new Image("CurlingDrawing.jpg");

            Image iMExitButton = new Image("ExitButton.png");

            Image iMInfo = new Image("InfoSquareButton.png");


            ImageView iVPlay = new ImageView();

            ImageView iVDrawing = new ImageView();

            ImageView iVExit = new ImageView();

            ImageView iVInfo = new ImageView();

            iVDrawing.setImage(iMDrawing);
            iVPlay.setImage(iMPlay);
            iVExit.setImage(iMExitButton);
            iVInfo.setImage(iMInfo);


            iVDrawing.setFitHeight(168);
            iVDrawing.setFitWidth(220);
            iVDrawing.setTranslateX(482);
            iVDrawing.setTranslateY(0);
            iVDrawing.setFitHeight(90);
            iVDrawing.setFitWidth(90);

            Label title = new Label("CurlingFx");

            title.setTranslateX(430);
            title.setTranslateY(100);

            iVPlay.setFitHeight(90);
            iVPlay.setFitWidth(192);
            iVPlay.setTranslateX(440);
            iVPlay.setTranslateY(200);

            iVExit.setFitHeight(90);
            iVExit.setFitWidth(192);
            iVExit.setTranslateX(440);
            iVExit.setTranslateY(325);

            iVInfo.setFitWidth(50);
            iVInfo.setFitHeight(50);
            iVInfo.setTranslateY(500);
            iVInfo.setTranslateX(30);



            // Handler which launches either game or information application
            iVPlay.setOnMouseClicked(e->{
                stage.close();
                Stage s = new Stage();
                try {
                    new NameSelectionMenu().start(s);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            });
            iVInfo.setOnMouseClicked(e->{
                stage.close();
                Stage s = new Stage();
                try{
                    new InfoScreen().start(s);
                }catch(IOException ex){
                    throw new RuntimeException(ex);
                }
            });
            iVExit.setOnMouseClicked(e->{
                stage.close();

            });
            pane.getChildren().addAll(title,iVPlay,iVDrawing,iVExit, iVInfo);


        }catch(Exception e){
            e.printStackTrace();
        }





        Scene scene = new Scene(pane,1024, 576);
        stage.setTitle("Main Menu");
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/fontstyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}