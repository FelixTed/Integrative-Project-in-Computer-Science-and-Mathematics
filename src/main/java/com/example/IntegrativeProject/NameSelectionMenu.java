package com.example.IntegrativeProject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class NameSelectionMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Creating the background image for the menu screen
        Image backGroundIm = new Image("CurlingBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        // Creating the background using the image
        Background background = new Background(bGIMG);
        Pane pane = new Pane();
        pane.setBackground(background);

        // Label and text field for Player 1's name input
        Label promptPlayer1 = new Label("Player 1, enter your name below");
        promptPlayer1.setTranslateX(340);
        promptPlayer1.setTranslateY(100);
        promptPlayer1.setFont(new Font("Times New Roman", 30));

        TextField player1Name = new TextField();
        player1Name.setAlignment(Pos.CENTER);
        player1Name.setTranslateX(440);
        player1Name.setTranslateY(150);

        // Label and text field for Player 2's name input
        Label promptPlayer2 = new Label("Player 2, enter your name below");
        promptPlayer2.setTranslateX(340);
        promptPlayer2.setTranslateY(200);
        promptPlayer2.setFont(new Font("Times New Roman", 30));

        TextField player2Name = new TextField();
        player2Name.setAlignment(Pos.CENTER);
        player2Name.setTranslateX(440);
        player2Name.setTranslateY(250);

        // Creating the play button
        Image iMPlay = new Image("PlayButton.png");
        ImageView iVPlay = new ImageView();
        iVPlay.setImage(iMPlay);
        iVPlay.setFitHeight(90);
        iVPlay.setFitWidth(192);
        iVPlay.setTranslateX(420);
        iVPlay.setTranslateY(300);

        // Limiting the length of player names to 20 characters
        int max = 20;
        player1Name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > max) {
                String copy = player1Name.getText().substring(0, max);
                player1Name.setText(copy);
            }
        });
        player2Name.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > max) {
                String copy = player2Name.getText().substring(0, max);
                player2Name.setText(copy);
            }
        });

        // Event handler for when the play button is clicked
        iVPlay.setOnMouseClicked(e ->{
            Stage s = new Stage();
            try{
                // Starting the game board with player names
                Board board = new Board();
                board.setPlayerNames(player1Name.getText(),player2Name.getText());
                board.start(s);
            }catch(IOException ex){
                throw new RuntimeException(ex);
            }
            // Closing the name selection menu stage
            stage.close();
        });

        // Adding all elements to the pane
        pane.getChildren().addAll(player1Name,promptPlayer1,promptPlayer2,player2Name,iVPlay);

        // Creating the scene for the name selection menu
        Scene infoScene = new Scene(pane, 1024, 576);
        // Adding CSS stylesheet for font style
        infoScene.getStylesheets().add(getClass().getResource("/fontstyle3.css").toExternalForm());
        stage.setScene(infoScene);
        stage.show();
    }
}
