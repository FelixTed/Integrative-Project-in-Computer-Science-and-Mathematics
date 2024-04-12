package com.example.IntegrativeProject;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Board extends Application {
    //Data field
    private String player1Name = "player 1";
    private String player2Name = "player 2";
    private Player player1;
    private Player player2;
    private Stone[] stones1;
    private Stone[] stones2;
    private TextField energyField = new TextField();
    private TextField angleField = new TextField();
    private Label statusLabel = new Label(player1Name+"'s Turn");
    private Label winningLabel = new Label("");
    private Button launchButton = new Button("Launch");
    private  Line angleLine = new Line(100,380,250,380);
    private double lineLength = 150;
    private Target target = new Target(new Circle(1100,380,40),new Circle(1100,380,100),new Circle(1100,380,180));
    private IntegerProperty currentStone = new SimpleIntegerProperty();
    private int currentPlayer;
    private Boolean paused = false;
    private Boolean endgame = false;

    private Line upperBorder = new Line(25,80,1375,80);
    private Line leftBorder = new Line(25,80,25,680);
    private Line rightBorder = new Line(1375,80,1375,680);
    private Line lowerBorder = new Line(25,680,1375,680);
    private Line limit = new Line(250,80,250,680);
    //Stores value for kEnergy, used to check if kEnergy is too big
    double value;

    @Override
    public void start(Stage stage) throws IOException {
        //Setting correct name for user
        statusLabel.setText(player1Name+"'s turn");
        statusLabel.getStyleClass().add("status");
        winningLabel.getStyleClass().add("status");

        //Putting the borders of the game in a list
        Line[] borders = {leftBorder,upperBorder,rightBorder,lowerBorder};

        limit.getStrokeDashArray().addAll(2d, 21d);;

        //Initializing player 1 and 2, with corresponding colored stones
        stones1 = new Stone[]{new Stone(new Image("blueStone.png"),borders), new Stone(new Image("blueStone.png"),borders), new Stone(new Image("blueStone.png"),borders)};
        stones2 = new Stone[]{new Stone(new Image("redStone.png"),borders), new Stone(new Image("redStone.png"),borders), new Stone(new Image("redStone.png"),borders)};
        double[] currentSpeed = new double[stones1.length+ stones2.length];


        for(int i = 0; i< stones1.length;i++){
            stones1[i].setPlayerID(1);
            stones2[i].setPlayerID(2);
        }

        //Creating the players and linking them to their personal list of stone
        player1 = new Player(1,stones1);
        player2 = new Player(2,stones2);
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
        lbl1.getStyleClass().add("input");

        Label lbl2 = new Label("Angle: ");
        lbl2.getStyleClass().add("input");

        hBox.getChildren().addAll(lbl1,energyField,lbl2,angleField,launchButton);

        hBox.setTranslateX(50);
        hBox.setTranslateY(600);

        statusLabel.setFont(Font.font("Times New Roman",32));
        statusLabel.setTextFill(Color.DARKBLUE);
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

        winningLabel.setLayoutX(550);
        winningLabel.setLayoutY(375);

        pane.getChildren().addAll(innerRect,wholeInner,donut2,donut3,limit,statusLabel,angleLine,hBox,stones1[0],upperBorder,lowerBorder,rightBorder,leftBorder);


        //Modify the angle of the line according to the angle value inputted
        angleField.textProperty().addListener(e -> {
            drawAngle();
        });
        //Modify the angle of the line according to the angle value inputted
        energyField.textProperty().addListener(e -> {
            drawLength();
        });


        VBox vBox = new VBox();
        Scene scene = new Scene(pane,1440,720);
        Image continueButt = new Image("ContinueButton.png");
        ImageView continueButtView = new ImageView(continueButt);
        Image menuButt = new Image("MenuButton.png");
        ImageView menuButtView = new ImageView(menuButt);
        Label pauseLbl = new Label("Paused");
        pauseLbl.setFont(Font.font("Times New Roman", 64));
        vBox.getChildren().addAll(pauseLbl,continueButtView,menuButtView);
        vBox.setMinSize(1440,720);
        // Pause menu of the game
        scene.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.ESCAPE && !paused && !endgame){
                paused = true;
                for(int i = 0; i<stones1.length; i++){
                    currentSpeed[i] = stones1[i].getKEnergy();
                    currentSpeed[i+3] = stones2[i].getKEnergy();
                    stones1[i].setSpeed(0);
                    stones2[i].setSpeed(0);
                }

                continueButtView.setFitHeight(200);
                continueButtView.setFitWidth(600);
                menuButtView.setFitHeight(200);
                menuButtView.setFitWidth(600);
                pane.getChildren().addAll(vBox);
                vBox.setAlignment(Pos.CENTER);

            }else if(e.getCode() == KeyCode.ESCAPE && paused && !endgame){
                paused = false;
                pane.getChildren().remove(vBox);
                for(int i =0;i< stones1.length;i++){
                    if(stones1[i].isActive()) {
                        stones1[i].startMoving(currentSpeed[i], stones1[i].getAngle(), stones1, stones2, stones1[i]);
                    }
                    if(stones2[i].isActive()) {
                        stones2[i].startMoving(currentSpeed[i + 3], stones2[i].getAngle(), stones1, stones2, stones2[i]);
                    }
                }
            }else if(e.getCode() == KeyCode.ESCAPE && endgame){
                stage.close();
                Stage s = new Stage();
                try{
                    new MainMenu().start(s);
                }catch(IOException ex){
                    throw new RuntimeException(ex);
                }
            }

        });
        continueButtView.setOnMouseClicked(ev->{
            paused = false;
            pane.getChildren().remove(vBox);
            for(int i =0;i< stones1.length;i++){
                if(stones1[i].isActive()) {
                    stones1[i].startMoving(currentSpeed[i], stones1[i].getAngle(), stones1, stones2, stones1[i]);
                }
                if(stones2[i].isActive()) {
                    stones2[i].startMoving(currentSpeed[i + 3], stones2[i].getAngle(), stones1, stones2, stones2[i]);
                }
            }
        });
        menuButtView.setOnMouseClicked(ev->{
            paused = false;
            stage.close();
            Stage s = new Stage();
            try{
                new MainMenu().start(s);
            }catch(IOException ex){
                throw new RuntimeException(ex);
            }
        });
        //User Input
        launchButton.setOnAction(e -> {

            Stone tempStone = player[currentPlayer].getStoneList()[currentStone.getValue()];
            try {
                value = Double.parseDouble(energyField.getText());
                if(value > 500){
                    statusLabel.setText("Value cannot be more than 500!");
                    value = 500;
                }
                tempStone.startMoving(value, Integer.parseInt(angleField.getText()), stones1, stones2, tempStone);
            }catch(NumberFormatException nfe){
                try {
                    tempStone.startMoving(value, 0, stones1, stones2, tempStone);
                }catch(NumberFormatException nfe2){
                    tempStone.startMoving(0,0,stones1,stones2,tempStone);
                }
            }
            tempStone.setActive(true);

           //Wait for the stone to stop moving before allowing the game to continue
            tempStone.isMoving().addListener((e2,e3,value) ->{
                if(value){
                        launchButton.setDisable(true);
                }else if(endgame && !value){

                    launchButton.setDisable(true);
                    try {
                        endGame();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }else if(currentStone.getValue() != 4) {
                    launchButton.setDisable(false);
                }
            });

            if(currentPlayer == 1){
                currentStone.set(currentStone.getValue()+1);
                currentPlayer = 0;
                statusLabel.setText(player1Name+"'s Turn");
                statusLabel.setTextFill(Color.DARKBLUE);
            }else {
                currentPlayer = 1;
                statusLabel.setText(player2Name+"'s Turn");
                statusLabel.setTextFill(Color.RED);
            }
            try {
                pane.getChildren().add(player[currentPlayer].getStoneList()[currentStone.getValue()]);
            }catch(ArrayIndexOutOfBoundsException aioobe){
                launchButton.setDisable(true);
                pane.getChildren().add(winningLabel);
                endgame = true;
            }
        });

        stage.setTitle("Board");
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/fontstyle2.css").toExternalForm());
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
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
    public void endGame() throws InterruptedException {
        winningLabel.setFont(new Font(50));
        statusLabel.setTextFill(Color.BLACK);
        winningLabel.setText("Calculating Points...");

        Thread.sleep(1000);

        player1.setPointsTotal(target.calculatePoints(stones1));
        player2.setPointsTotal(target.calculatePoints(stones2));
        statusLabel.setText(player1Name+": " + player1.getPointsTotal() + " points\t"+player2Name+": " + player2.getPointsTotal() + " points");

        int winner;
        if(player1.getPointsTotal() > player2.getPointsTotal()) {
            winningLabel.setTextFill(Color.BLUE);
            winningLabel.setText(player1Name+" WINS!");
        }
        else if(player1.getPointsTotal() < player2.getPointsTotal()){
            winningLabel.setTextFill(Color.RED);
            winningLabel.setText(player2Name+" WINS!");
        }
        else{
            winningLabel.setTextFill(Color.BLACK);
            winningLabel.setText("Draw");
        }
        for(int i = 0; i<stones1.length;i++){
            stones1[i].setSpeedKEnergy(0);
            stones2[i].setSpeedKEnergy(0);
        }
    }
    public void setPlayerNames(String player1, String player2){
        this.player1Name = player1;
        this.player2Name = player2;
    }
    public static abstract class After extends Thread {

        private int sleep = 0;

        public After(int sleep) {
            this.sleep = sleep;
        }

        public void run() {
            try {
                Thread.sleep(this.sleep);
            } catch(InterruptedException e) {
                //do something with e
            }
            this.after();
        }

        public abstract void after();
    }
}