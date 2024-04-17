package com.example.IntegrativeProject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Leaderboard extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Map<String, Integer> scores = new HashMap<>();
        File scoresFile = new File("src//main//resources//playerData.txt");
        Scanner input = new Scanner(scoresFile);
        while(input.hasNextLine()){
            scores.put(input.nextLine(),Integer.parseInt(input.nextLine()));
        }
        input.close();
        // Convert the map to a list of entries
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(scores.entrySet());

        // Sort the list based on the values (points)
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // Compare points (values)
                return entry2.getValue().compareTo(entry1.getValue());            }
        });

        // Creating the background image for the menu screen
        Image backGroundIm = new Image("CurlingBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        // Creating the background using the image
        Background background = new Background(bGIMG);
        Pane pane = new Pane();
        pane.setBackground(background);

        // Label and text field for Player 1's name input
        Label promptPlayer1 = new Label("Leaderboard");
        promptPlayer1.setTranslateX(440);
        promptPlayer1.setTranslateY(50);

        //Adding a background to make text more clear
        Rectangle backgroundText = new Rectangle(350,305);
        backgroundText.setTranslateX(325);
        backgroundText.setTranslateY(180);
        backgroundText.setFill(Color.WHITE);
        backgroundText.setArcHeight(10);
        backgroundText.setArcWidth(10);

        // Label and text field for Player 2's name input
        Label scoresList = new Label("Name\t\t\tScore\n");
        scoresList.setTranslateX(340);
        scoresList.setTranslateY(200);
        int i = 1;
        for (Map.Entry<String, Integer> entry : entries) {
            if(i > 10)
                break;
            scoresList.setText(scoresList.getText()+ i + ". "+String.format("%-23s",entry.getKey())+"\t"+ entry.getValue() +"\n");
            i++;
        }



        // Creating the play button
        Image iMPlay = new Image("MenuButton.png");
        ImageView iVPlay = new ImageView();
        iVPlay.setImage(iMPlay);
        iVPlay.setFitHeight(90);
        iVPlay.setFitWidth(192);
        iVPlay.setTranslateX(700);
        iVPlay.setTranslateY(200);


        // Event handler for when the play button is clicked
        iVPlay.setOnMouseClicked(e ->{
            Stage s = new Stage();
            try{
                // Starting the game board with player names
                MainMenu mainMenu = new MainMenu();
                mainMenu.start(s);
            }catch(IOException ex){
                throw new RuntimeException(ex);
            }
            // Closing the name selection menu stage
            stage.close();
        });

        // Adding all elements to the pane
        pane.getChildren().addAll(backgroundText,promptPlayer1,scoresList,iVPlay);

        // Creating the scene for the name selection menu
        Scene infoScene = new Scene(pane, 1024, 576);
        // Adding CSS stylesheet for font style
        infoScene.getStylesheets().add(getClass().getResource("/fontstyle3.css").toExternalForm());
        stage.setScene(infoScene);
        stage.show();
    }
}
