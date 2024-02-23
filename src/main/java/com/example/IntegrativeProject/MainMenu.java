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
            title.setFont(new Font("Times New Roman", 48));
            title.setTranslateX(440);
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
                    new GameClass().start(s);
                } catch (IOException ex) {
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
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
private void launchGameApplication(String appType){
        Stage newStage = new Stage();
        newStage.setTitle("New application");
    Pane newPane = new Pane();
    if(appType == "game") {
        Circle stone1 = new Circle(100, 400, 50);
        Image stoneImg = new Image("C:/Users/Dell/Pictures/stone.png");

        Image backGroundIm = new Image("C:/Users/Dell/Pictures/gameBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(bGIMG);

        newPane.setBackground(background);
        ImageView stoneView = new ImageView(stoneImg);
        stoneView.setFitWidth(101);
        stoneView.setFitHeight(97);
        stoneView.setTranslateX(50);
        stoneView.setTranslateY(350);

        Circle wholeInner = new Circle(1200, 400, 30);
        Circle insideInner = new Circle(1200, 400, 15);


        Shape donut1 = Shape.subtract(wholeInner, insideInner);
        donut1.setFill(Color.RED);

        Circle wholeMid = new Circle(1200, 400, 60);
        Circle insideMid = new Circle(1200, 400, 30);
        insideMid.setFill(Color.TRANSPARENT);

        Shape donut2 = Shape.subtract(wholeMid, insideMid);
        donut2.setFill(Color.LIGHTGRAY);

        Circle wholeOuter = new Circle(1200, 400, 90);
        Circle insideOuter = new Circle(1200, 400, 60);
        insideOuter.setFill(Color.TRANSPARENT);

        Shape donut3 = Shape.subtract(wholeOuter, insideOuter);
        donut3.setFill(Color.BLUE);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(50));

        Label lbl1 = new Label("Kinetic Energy: ");
        TextField tF1 = new TextField();
        Label lbl2 = new Label("Angle: ");
        TextField tF2 = new TextField();
        Button Launch = new Button("Launch");
        hBox.getChildren().addAll(lbl1, tF1, lbl2, tF2, Launch);

        hBox.setTranslateX(50);
        hBox.setTranslateY(600);


        Label Turn = new Label("Player 1's turn");
        Turn.setFont(Font.font("Times New Roman", 32));
        Turn.setTranslateX(30);
        Turn.setTranslateY(30);

        Rectangle innerRect = new Rectangle(25, 175, 1350, 450);
        innerRect.setFill(Color.LIGHTCYAN);
        innerRect.setStroke(Color.BLACK);

        Line normal = new Line(150, 400, 250, 400);
        normal.getStrokeDashArray().addAll(4d);
        newPane.getChildren().addAll(innerRect, stone1, stoneView, donut1, donut2, donut3, hBox, Turn, normal);
        Scene gameScene = new Scene(newPane, 1440, 720);
        newStage.setScene(gameScene);
        newStage.show();
    }else if(appType == "info"){
        Image menuButt = new Image("C:/Users/Dell/Downloads/MenuButton.png");
        ImageView menuButtView = new ImageView(menuButt);
        menuButtView.setTranslateX(70);
        menuButtView.setTranslateY(700);
        menuButtView.setFitHeight(90);
        menuButtView.setFitWidth(192);
        VBox physicsBox = new VBox(15);

        Image collImage = new Image("C:/Users/Dell/Pictures/Collisions.png");
        ImageView collView = new ImageView(collImage);
        Image displacementImage = new Image("C:/Users/Dell/Pictures/DisplacementTrig.png");
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


        Image backGroundIm = new Image("C:/Users/Dell/Pictures/gameBackground.jpg");
        BackgroundImage bGIMG = new BackgroundImage(backGroundIm, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(bGIMG);

        newPane.setBackground(background);

        Image physics = new Image("C:/Users/Dell/Pictures/physics.png");

        ImageView imgViewPhysics = new ImageView(physics);


        HBox sliderBox = new HBox();

        Slider slider = new Slider();
        slider.setOrientation(Orientation.VERTICAL);

        sliderBox.getChildren().addAll(imgViewPhysics,slider);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(sliderBox);
        scrollPane.setTranslateX(400);
        scrollPane.setTranslateY(100);

        newPane.getChildren().addAll(scrollPane,lblTitle,menuButtView,physicsBox);
        Scene infoScene = new Scene(newPane, 1550, 800);
        newStage.setScene(infoScene);
        newStage.show();
    }

}
    public static void main(String[] args) {
        launch();
    }
}