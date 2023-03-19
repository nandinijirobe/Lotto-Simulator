import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
//import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
// TODO: Just noticed that you can't use the same button in two different scenes; it would only appear in one of them, but not the others.
// TODO: I tried to comment it out in one scene such that it now appears on the other scene. Maybe, we might need to declare a new button, but w/ scripts attached to it. 3/13/23

public class JavaFXTemplate extends Application {
    private Button playGameButton;
    private Button menuButton1, menuButton2;
    private Button playAgainButton;
    private Button exitButton;
    private Button defaultThemeButton;
    private Button pinkThemeButton;
    private Button startDrawButton; // what is this for?
    private Button quickPickButton;
    private Button submitFormButton;
    private Button closeMenuButton, closeRulesButton, closeOddsButton, closeLookButton;
    private Button displayRulesButton;
    private Button checkOddsWinningButton;
    private Button newLookButton;

    private Text startDrawNum;
    private Text displayTotalWinningGame;
    private Text instructionBoard;
    private Text display20RandomNums;
    private Text displayNumMatches;
    private Text displayMatchedNums;
    private Text displayDrawWinning;
    private Text endTitle;

    private GridPane betCard;
    private GridPane spotsToPlay;
    private GridPane drawsToPlay;
    private PlaySlip game;
    private boolean hasGameStarted = false;
    private ArrayList<ToggleButton> arrayOfGridButtons = new ArrayList<>();
    private ArrayList<RadioButton> arrayOfFormButtons = new ArrayList<>();

    // Each theme style contains the colors' hex codes
    private String[] defaultTheme = new String[]{
            "#94B49F",
            "#E5E3C9"
    };
    private String[] pinkTheme = new String[]{
            "#F24726",
            "#FF8787"
    };

    private PauseTransition pause;
    private PauseTransition endPause;
    private PauseTransition playAgainPause;
    private HashMap<String, Scene> sceneMap;

    private EventHandler<ActionEvent> closeMenuOptionHandler;


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    //feel free to remove the starter code from this method
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Welcome to JavaFX");

        // Declaring variables
        playGameButton = new Button("PLAY THE GAME");
        menuButton1 = new Button("MENU");
        menuButton2 = new Button("MENU");

        displayRulesButton = new Button("Display Rules");
        checkOddsWinningButton = new Button("Show Odds of Winning");
        newLookButton = new Button("New Look");

        closeMenuButton = new Button("X");
        closeRulesButton = new Button("X");
        closeOddsButton = new Button("X");
        closeLookButton = new Button("X");

        defaultThemeButton = new Button("Default");
        pinkThemeButton = new Button("Pink-Red");

        playAgainButton = new Button("Play Again");
        endTitle = new Text("Want to Play Again?");

        sceneMap = new HashMap<String, Scene>();  // Store all the scenes

        // Create an instance of the game and switch to the gameplay scene
        playGameButton.setOnAction(e -> {
            hasGameStarted = true;
            primaryStage.setScene(sceneMap.get("gameplay"));
            instructionBoard.setText("Welcome! Please select number of the spots and drawings you would like to play!");
            instructionBoard.setTextAlignment(TextAlignment.CENTER);
        });

        // Switch to the menu scene
        menuButton1.setOnAction(e -> primaryStage.setScene(sceneMap.get("menu")));
        menuButton2.setOnAction(e -> primaryStage.setScene(sceneMap.get("menu")));

        // Declare a handler that the player can return back to the menu scene from the menu option scene
        closeMenuOptionHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(sceneMap.get("menu"));
            }
        };

        closeRulesButton.setOnAction(closeMenuOptionHandler);
        closeOddsButton.setOnAction(closeMenuOptionHandler);
        closeLookButton.setOnAction(closeMenuOptionHandler);

        // Display the rules in another screen
        displayRulesButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("rules")));

        // Display the odds of winning in another screen
        checkOddsWinningButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("oddsOfWinning")));

        // Look screen
        newLookButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("look")));

        // Change the look of the intro screen and the gameplay screen w/ the default theme colors (green)
        defaultThemeButton.setOnAction(e -> {
            sceneMap.put("intro", createIntroScene(defaultTheme));
            // TODO: need to change colors for the gameplay (but cannot restart)

            if (hasGameStarted) {
                primaryStage.setScene(sceneMap.get("gameplay"));
                // update all the changes in the gameplay
            } else {
                primaryStage.setScene(sceneMap.get("intro"));
            }
        });

        // Change the look of the intro screen and the gameplay screen w/ the pink-red theme colors
        pinkThemeButton.setOnAction(e -> {
            sceneMap.put("intro", createIntroScene(pinkTheme));
            // TODO: need to change colors for the gameplay (but cannot restart)

            if (hasGameStarted) {
                primaryStage.setScene(sceneMap.get("gameplay"));
            } else {
                primaryStage.setScene(sceneMap.get("intro"));
            }
        });


        // Go to the previous screen after closing the menu screen that depend on whether the game have already started or not
        closeMenuButton.setOnAction(e -> {
            if (hasGameStarted) {
                primaryStage.setScene(sceneMap.get("gameplay"));
            } else {
                primaryStage.setScene(sceneMap.get("intro"));
            }
        });

        // Declare a game object
        game = new PlaySlip();

        // Done playing the bet card (round)
        endPause = new PauseTransition(Duration.seconds(3));
        endPause.setOnFinished(e-> {
            primaryStage.setScene(sceneMap.get("ending"));
        });

        // Go back to the gameplay if the player want to play again
        playAgainPause = new PauseTransition((Duration.seconds(3)));
        playAgainPause.setOnFinished(e-> {
            primaryStage.setScene(sceneMap.get("gameplay"));
            endTitle.setText("Play Again?");
        });

        // Put scenes in a hashmap
        sceneMap.put("intro", createIntroScene(defaultTheme));
        sceneMap.put("gameplay", createGameScene());
        sceneMap.put("menu", createMenuScreen());
        sceneMap.put("rules", createRulesScreen());
        sceneMap.put("oddsOfWinning", createOddsOfWinningScreen());
        sceneMap.put("look", createLookScreen());
        sceneMap.put("ending", createPlayOrExitScreen());

        // This is what the user sees when opening the program
        primaryStage.setScene(sceneMap.get("intro"));
        primaryStage.show();
    }

    public Scene createIntroScene(String[] themeColors) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: " + themeColors[1]);

        Text gameTitle = new Text("KENO");
        gameTitle.setStyle("-fx-font: bold 150 serif;");

        //Customize the buttons (TODO: maybe move the background color to the main method b/c of newlook)
        playGameButton.setStyle("-fx-pref-height: 90px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10; -fx-background-color: " + themeColors[0]);
        menuButton1.setStyle("-fx-pref-height: 55px; -fx-pref-width: 95px; -fx-font: bold 20 serif; -fx-background-radius: 10; -fx-background-color: " + themeColors[0]);

        VBox paneCenter = new VBox(gameTitle, playGameButton);
        HBox paneRight = new HBox(menuButton1);

        // Adjust its arrangements/margins
        paneRight.setMargin(menuButton1, new Insets(15));
        paneCenter.setAlignment(Pos.CENTER);
        paneRight.setAlignment(Pos.TOP_RIGHT);

        // Add the elements onto the BorderPane
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        return new Scene(pane, 850, 750);
    }

    public Scene createGameScene() {

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #E5E3C9");

        // left components
        betCard = new GridPane();
        quickPickButton = new Button("Quick Pick");
        quickPickButton.setDisable(true);
        quickPickButton.setStyle("-fx-background-color: #A87D7D; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-min-height: 50; -fx-text-fill: black;");

        startDrawButton = new Button("Start Draw No: 1");
//		StackPane startDrawNumFlow = new StackPane(startDrawButton);
        startDrawButton.setDisable(true);
        startDrawButton.setStyle("-fx-background-color: #A87D7D; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-text-fill: black; -fx-min-height: 50; -fx-alignment: center; -fx-border-width: 10; -fx-background-radius: 5;");

        displayTotalWinningGame = new Text("Winning Game Total: XXX");
        StackPane displayTotalWinningGameFlow = new StackPane(displayTotalWinningGame);
        displayTotalWinningGameFlow.setStyle("-fx-background-color: #E2F0E7; -fx-font: bold 15 Helvetica;  -fx-min-width: 480; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");


        // left arrangement
        HBox leftBottom = new HBox(quickPickButton, startDrawButton);
        VBox leftSide = new VBox(displayTotalWinningGameFlow, betCard, leftBottom);
        leftBottom.setMargin(quickPickButton, new Insets(10));
        leftBottom.setMargin(startDrawButton, new Insets(10));
        leftSide.setMargin(displayTotalWinningGameFlow, new Insets(10));
        leftSide.setMargin(betCard, new Insets(10));

        Text formText1 = new Text("Select the Number of Spots to Play");
        StackPane formText1Pane = new StackPane(formText1);
        formText1Pane.setStyle("-fx-background-color: #E2F0E7; -fx-font: bold 15 Helvetica;  -fx-min-width: 80; -fx-min-height: 30; -fx-alignment: center; -fx-border-width: 30;");

        Text formText2 = new Text("Select the Number of Drawings");
        StackPane formText2Pane = new StackPane(formText2);
        formText2Pane.setStyle("-fx-background-color: #E2F0E7; -fx-font: bold 15 Helvetica;  -fx-min-width: 80; -fx-min-height: 30; -fx-alignment: center; -fx-border-width: 30;");

        submitFormButton = new Button("Submit");
        submitFormButton.setStyle("-fx-pref-height: 20px; -fx-pref-width: 95px; -fx-font: bold 15 Helvetica; -fx-background-color: #89B982;");
        menuButton2.setStyle("-fx-pref-height: 55px; -fx-pref-width: 95px; -fx-font: bold 20 serif; -fx-background-radius: 10; -fx-background-color: #89B982;");

//		spotsToPlay = new GridPane();
//		drawsToPlay = new GridPane();

        HBox spotsToPlay = new HBox();
        HBox drawsToPlay = new HBox();
        ToggleGroup group1 = new ToggleGroup();
        ToggleGroup group2 = new ToggleGroup();


        int[] spotOptions = {1, 4, 8, 10};
        for (int i = 0; i < 4; i++) {
            RadioButton gridButton1 = new RadioButton(String.valueOf(spotOptions[i]));
            RadioButton gridButton2 = new RadioButton(String.valueOf(i + 1));
            gridButton1.setStyle("-fx-min-height: 45px; -fx-min-width: 80px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: #B4CFB0; -fx-border-width:5;");
            gridButton2.setStyle("-fx-min-height: 45px; -fx-min-width: 80px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: #B4CFB0; -fx-border-width:5;");
            gridButton1.setToggleGroup(group1);
            gridButton2.setToggleGroup(group2);
            spotsToPlay.getChildren().add(gridButton1);
            drawsToPlay.getChildren().add(gridButton2);
            arrayOfFormButtons.add(gridButton1);
            arrayOfFormButtons.add(gridButton2);
        }

        VBox spotsBox = new VBox(formText1Pane, spotsToPlay);
        VBox drawingsBox = new VBox(formText2Pane, drawsToPlay);
        HBox spotsNdrawings = new HBox(spotsBox, drawingsBox);
        HBox submitBox = new HBox(submitFormButton);
        submitBox.setAlignment(Pos.CENTER);
        VBox setupForm = new VBox(spotsNdrawings, submitBox);
        setupForm.setStyle("-fx-background-color: #E2F0E7;");


        spotsBox.setMargin(spotsToPlay, new Insets(10));
        drawingsBox.setMargin(drawsToPlay, new Insets(10));
        spotsBox.setMargin(formText1, new Insets(10));
        drawingsBox.setMargin(formText2, new Insets(10));
        submitBox.setMargin(submitFormButton, new Insets(10));

        instructionBoard = new Text("Instruction Board");
        StackPane instructionBoardFlow = new StackPane(instructionBoard);
        instructionBoardFlow.setStyle("-fx-background-color: #B4CFB0; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        display20RandomNums = new Text("20 Random Numbers");
        StackPane display20RandomNumsFlow = new StackPane(display20RandomNums);
        display20RandomNumsFlow.setStyle("-fx-background-color: #929F97; -fx-font: bold 15 Helvetica;  -fx-min-width: 60; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        displayNumMatches = new Text("Number of matches: XXXXXXX");
        displayNumMatches.setWrappingWidth(200);
        displayNumMatches.setTextAlignment(TextAlignment.CENTER);
        StackPane displayNumMatchesFlow = new StackPane(displayNumMatches);
        displayNumMatchesFlow.setStyle("-fx-background-color: #E2F0E7; -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        displayMatchedNums = new Text("Numbers matched: XXXXXXX");
        displayMatchedNums.setWrappingWidth(200);
        displayMatchedNums.setTextAlignment(TextAlignment.CENTER);
        StackPane displayMatchedNumsFlow = new StackPane(displayMatchedNums);
        displayMatchedNumsFlow.setStyle("-fx-background-color: #E2F0E7;  -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        displayDrawWinning = new Text("Current Draw Winnings: XXXXXXX");
        displayDrawWinning.setWrappingWidth(200);
        displayDrawWinning.setTextAlignment(TextAlignment.CENTER);
        StackPane displayDrawWinningFlow = new StackPane(displayDrawWinning);
        displayDrawWinningFlow.setStyle("-fx-background-color: #E2F0E7;  -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        // right arrangement
        HBox rightTop = new HBox(menuButton2);
        rightTop.setMargin(menuButton2, new Insets(10));
        rightTop.setAlignment(Pos.TOP_RIGHT);

        HBox rightBottom = new HBox(displayNumMatchesFlow, displayMatchedNumsFlow, displayDrawWinningFlow);
        VBox rightSide = new VBox(rightTop, instructionBoardFlow, setupForm, display20RandomNumsFlow, rightBottom);
        rightBottom.setMargin(displayNumMatchesFlow, new Insets(10));
        rightBottom.setMargin(displayMatchedNumsFlow, new Insets(10));
        rightBottom.setMargin(displayDrawWinningFlow, new Insets(10));
        rightSide.setMargin(instructionBoardFlow, new Insets(10));
        rightSide.setMargin(display20RandomNumsFlow, new Insets(10));
        rightSide.setAlignment(Pos.CENTER);

        int count = 1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                ToggleButton gridButton = new ToggleButton(String.valueOf(count)); // switched type from Button to Toggle Button
                gridButton.setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: #B4CFB0; -fx-border-width:5;");
                betCard.add(gridButton, col, row, 1, 1);
                arrayOfGridButtons.add(gridButton);
                gridButton.setDisable(true);
                gridButton.setOnAction(e -> {
                    if (gridButton.isSelected()) {
                        gridButton.setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #88998E; -fx-border-color: #B4CFB0; -fx-border-width:5;");
                        game.addSpotNum(Integer.valueOf(gridButton.getText()));
                    } else {
                        gridButton.setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: #B4CFB0; -fx-border-width:5;");
                        game.removeSpotNum(Integer.valueOf(gridButton.getText()));
                    }
                });
                count++;
            }
        }


        HBox gameBoard = new HBox(leftSide, rightSide);
        gameBoard.setAlignment(Pos.CENTER);

        pane.setCenter(gameBoard);
        pane.setMargin(gameBoard, new Insets(100));

        // Scroll through the whole scene's contents
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(pane);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);


        // event handlers
        // left component event handlers
        quickPickButton.setOnAction(e -> {
            game.quickPick();  //TODO: Write comments for the if-else conditions - FLora
            instructionBoard.setText("We have chosen the numbers on the bet card for you...");
            for (int i = 0; i < 80; i++) {
                if (arrayOfGridButtons.get(i).isSelected() == true && !(game.getSelectedSpots().contains(Integer.valueOf(arrayOfGridButtons.get(i).getText())))) {
                    arrayOfGridButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: #B4CFB0; -fx-border-width:5;");
                    game.removeSpotNum(Integer.valueOf(arrayOfGridButtons.get(i).getText()));
                    arrayOfGridButtons.get(i).setSelected(false);
                } else if (arrayOfGridButtons.get(i).isSelected() == false && game.getSelectedSpots().contains(Integer.valueOf(arrayOfGridButtons.get(i).getText()))) {
                    arrayOfGridButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #88998E; -fx-border-color: #B4CFB0; -fx-border-width:5;");
                    arrayOfGridButtons.get(i).setSelected(true);
                }
            }
        });

        submitFormButton.setOnAction(e -> {
            if ((ToggleButton)group1.getSelectedToggle() == null || (ToggleButton)group2.getSelectedToggle() == null) {
                instructionBoard.setText("Oops! You haven't filled out the form yet.");
            } else {
                submitFormButton.setDisable(true);
                for (int i = 0; i < arrayOfFormButtons.size(); i++) {
                    arrayOfFormButtons.get(i).setDisable(true);
                }
                int drawNum = Integer.valueOf(((ToggleButton) group1.getSelectedToggle()).getText());
                int spotsNum = Integer.valueOf(((ToggleButton) group2.getSelectedToggle()).getText());
                game.newRound(spotsNum, drawNum);

                instructionBoard.setText("Now choose " + spotsNum + " spots on the bet card.");

                // Enable the grid buttons
                for (int i = 0; i < arrayOfGridButtons.size(); i++) {
                    arrayOfGridButtons.get(i).setDisable(false);
                }
                quickPickButton.setDisable(false);
                startDrawButton.setDisable(false);
            }
        });


        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(t -> {
            System.out.println("pause worked");
        });

        startDrawButton.setOnAction(e-> {

            if (game.checkNumSpots()) {
                // Disable buttons
                quickPickButton.setDisable(true);
                for (int i = 0; i < arrayOfGridButtons.size(); i++) {
                    arrayOfGridButtons.get(i).setDisable(true);
                }

                // do the instruction board that says "loading loading...."
                instructionBoard.setText("Loading...");
                pause.play();
//                display20RandomNums.setText("sudfjkfjkdsajksjksdkj");
                instructionBoard.setText("You have " + (game.getNumDrawings() - game.getCurrentDrawNum()) + " draws left.");

                game.newDraw();

                display20RandomNums.setText(game.get20RandNumString());
                display20RandomNums.setTextAlignment(TextAlignment.CENTER);

                displayNumMatches.setText("Number of matches:\n" + String.valueOf(game.getNumMatches()));
                displayMatchedNums.setTextAlignment(TextAlignment.CENTER);
                displayMatchedNums.setText(game.getMatchesString());
                displayMatchedNums.setTextAlignment(TextAlignment.CENTER);
                displayDrawWinning.setText("Current draw winnings:\n$"+String.valueOf(game.getDrawWinningMoney()));
                displayDrawWinning.setTextAlignment(TextAlignment.CENTER);
                displayTotalWinningGame.setText("Total Game Winnings:\n$" +  String.valueOf(game.getTotalGameWinningMoney()));
                displayTotalWinningGame.setTextAlignment(TextAlignment.CENTER);

                startDrawButton.setText("Start Draw No: " + game.getCurrentDrawNum());

            } else {
                if(game.getNumSelectedSpots() > game.getNumSpots()){
                    instructionBoard.setText("Oops! You chose too many spots. Please select only " + game.getNumSpots() + ".");
                } else {
                    instructionBoard.setText("Oops! You chose too few spots. Please select only " + game.getNumSpots() + ".");
                }
            }
            if (game.checkDrawsCompleted() == true) {
                startDrawButton.setDisable(true);
                instructionBoard.setText("Game Completed!");
                instructionBoard.setTextAlignment(TextAlignment.CENTER);
                startDrawButton.setText("No More Draw!");
                endPause.play();
            }
        });

        playAgainButton.setOnAction(t -> {
            instructionBoard.setText("Instruction Board");
            display20RandomNums.setText("20 Random Numbers");
            displayNumMatches.setText("Number of matches: XXXXXXX");
            displayMatchedNums.setText("Numbers matched: XXXXXXX");
            displayDrawWinning.setText("Current Draw Winnings: XXXXXXX");

            startDrawButton.setText("Start Draw No: 1");

            // Enable the submit form buttons
            submitFormButton.setDisable(false);
            for (int i = 0; i < arrayOfFormButtons.size(); i++) {
                arrayOfFormButtons.get(i).setDisable(false);
            }

            // Restart the grid
            for (int i = 0; i < 80; i++) {
                if (arrayOfGridButtons.get(i).isSelected() == true) {
                    arrayOfGridButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: #B4CFB0; -fx-border-width:5;");
                    game.removeSpotNum(Integer.valueOf(arrayOfGridButtons.get(i).getText()));
                    arrayOfGridButtons.get(i).setSelected(false);
                }
            }

            endTitle.setText("Rebooting...");
            playAgainPause.play();  // return to the gameplay scene
        });

        return new Scene(scroll, 850, 750);
    }

    public Scene createMenuScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #7896D7");

        Text menuTitle = new Text("MENU");
        menuTitle.setStyle("-fx-font: bold 100 serif;");
        menuTitle.setUnderline(true);

        // Close the application window
        Button exitButton = new Button("Exit Button");
        exitButton.setOnAction(e -> Platform.exit());

        // Customize the buttons
        displayRulesButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10;");
        checkOddsWinningButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10;");
        newLookButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10;");
        exitButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10;");
        closeMenuButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 serif; -fx-background-radius: 10; -fx-background-color: #6584C8");

        VBox paneCenter = new VBox(15, menuTitle, displayRulesButton, checkOddsWinningButton, newLookButton, exitButton);
        HBox paneRight = new HBox(closeMenuButton);

        // Adjust its arrangements/margins
        paneCenter.setAlignment(Pos.CENTER);
        paneRight.setAlignment(Pos.CENTER_RIGHT);
        paneRight.setMargin(closeMenuButton, new Insets(15));

        // Add the elements onto the BorderPane
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        return new Scene(pane, 850, 750);
    }

    public Scene createRulesScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #FF8787");  // temp

        Text rulesTitle = new Text("RULES");
        rulesTitle.setStyle("-fx-font: bold 75 serif;");
        rulesTitle.setUnderline(true);

        Text rule1 = new Text("Select the number of spots (1, 4, 8, or 10) and drawings (1-4) to play. This cannot be changed once it begins.");
        Text rule2 = new Text("Depending on the number of spots you pick, select that amount of numbers (1-80) on the grid. You cannot change it once the drawings begin.");
        Text rule3 = new Text("For each draw, 20 random distinct numbers will be drawn, and you win money based on the number of matches you made with your selected numbers.");
        // TODO: Feel free to modify/add more rules.

        rule1.setStyle("-fx-font: 25 serif;");
        rule1.setWrappingWidth(840);
        rule1.setTextAlignment(TextAlignment.CENTER);
        rule2.setStyle("-fx-font: 25 serif;");
        rule2.setWrappingWidth(840);
        rule2.setTextAlignment(TextAlignment.CENTER);
        rule3.setStyle("-fx-font: 25 serif;");
        rule3.setWrappingWidth(840);
        rule3.setTextAlignment(TextAlignment.CENTER);

        closeRulesButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 serif; -fx-background-radius: 10; -fx-background-color: #E77070");

        VBox paneCenter = new VBox(rulesTitle, rule1, rule2, rule3);
        HBox paneRight = new HBox(closeRulesButton);

        // Adjust its arrangements/margins
        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.setSpacing(12);

        paneRight.setAlignment(Pos.CENTER_RIGHT);
        paneRight.setMargin(closeRulesButton, new Insets(15));

        // Add the elements onto the BorderPane
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        return new Scene(pane, 850, 750);
    }

    public Scene createOddsOfWinningScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #8f7ea3");  // temp

        closeOddsButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 serif; -fx-background-radius: 10; -fx-background-color: #796a8c");

        Text oddsTitle = new Text("ODDS OF WINNING");
        oddsTitle.setStyle("-fx-font: bold 75 serif;");
        oddsTitle.setUnderline(true);

        // Creating a 1 Spot Table
        Text oneSpotTitleText = new Text("One Spot Game");
        oneSpotTitleText.setStyle("-fx-font: bold 35 serif;");
        oneSpotTitleText.setUnderline(true);
        Text oneSpotOddsText = new Text("Overall Odds: 1 in 4.0 million");
        oneSpotOddsText.setStyle("-fx-font: 15 serif;");

        FlowPane oneSpotTitleHeader = new FlowPane(oneSpotTitleText, oneSpotOddsText);  // 1 Spot Table Header
        oneSpotTitleHeader.setStyle("-fx-background-color: #c05299");
        oneSpotTitleHeader.setPadding(new Insets(10));
        oneSpotTitleHeader.setAlignment(Pos.CENTER);

        Text oneSpotMatchTitle = new Text("Match");
        oneSpotMatchTitle.setStyle("-fx-font: bold 20 serif;");
        Text oneSpotPrizeTitle = new Text("Prize");
        oneSpotPrizeTitle.setStyle("-fx-font: bold 20 serif;");

        Text oneSpotMatchEntries = new Text("1\n0");
        oneSpotMatchEntries.setStyle("-fx-font: 20 serif;");
        oneSpotMatchEntries.setTextAlignment(TextAlignment.CENTER);
        Text oneSpotPrizeEntries = new Text("$2\n$0");
        oneSpotPrizeEntries.setStyle("-fx-font: 20 serif;");
        oneSpotPrizeEntries.setTextAlignment(TextAlignment.CENTER);

        VBox oneSpotMatchCol = new VBox(oneSpotMatchTitle, oneSpotMatchEntries);
        oneSpotMatchCol.setAlignment(Pos.CENTER);
        VBox oneSpotPrizeCol = new VBox(oneSpotPrizeTitle, oneSpotPrizeEntries);
        oneSpotPrizeCol.setAlignment(Pos.CENTER);
        HBox oneSpotCols = new HBox(oneSpotMatchCol, oneSpotPrizeCol);
        oneSpotCols.setSpacing(145);

        VBox oneSpotTable = new VBox(oneSpotTitleHeader, oneSpotCols);  // Create the 1 Spot table
        oneSpotTable.setSpacing(5);
        oneSpotTable.setPadding(new Insets(10));
        oneSpotTable.setMaxWidth(100);
        oneSpotTable.setStyle("-fx-background-color: #ea698b");


        // Creating a 4 Spot Table
        Text fourSpotTitleText = new Text("Four Spot Game");
        fourSpotTitleText.setUnderline(true);
        fourSpotTitleText.setStyle("-fx-font: bold 35 serif;");
        Text fourSpotOddsText = new Text("Overall Odds: 1 in 3.86 million");
        fourSpotOddsText.setStyle("-fx-font: 15 serif;");

        FlowPane fourSpotTitleHeader = new FlowPane(fourSpotTitleText, fourSpotOddsText);  // 4 Spot Table Header
        fourSpotTitleHeader.setStyle("-fx-background-color: #e7c6ff");
        fourSpotTitleHeader.setPadding(new Insets(10));
        fourSpotTitleHeader.setAlignment(Pos.CENTER);

        Text fourSpotMatchTitle = new Text("Match");
        fourSpotMatchTitle.setStyle("-fx-font: bold 20 serif;");
        Text fourSpotPrizeTitle = new Text("Prize");
        fourSpotPrizeTitle.setStyle("-fx-font: bold 20 serif;");

        Text fourSpotMatchEntries = new Text("4\n3\n2\n1\n0");
        fourSpotMatchEntries.setStyle("-fx-font: 20 serif;");
        Text fourSpotPrizeEntries = new Text("$75\n$5\n$1\n$0\n$0");
        fourSpotPrizeEntries.setTextAlignment(TextAlignment.CENTER);
        fourSpotPrizeEntries.setStyle("-fx-font: 20 serif;");

        VBox fourSpotMatchCol = new VBox(fourSpotMatchTitle, fourSpotMatchEntries);
        fourSpotMatchCol.setAlignment(Pos.CENTER);
        VBox fourSpotPrizeCol = new VBox(fourSpotPrizeTitle, fourSpotPrizeEntries);
        fourSpotPrizeCol.setAlignment(Pos.CENTER);
        HBox fourSpotCols = new HBox(fourSpotMatchCol, fourSpotPrizeCol);
        fourSpotCols.setSpacing(160);

        VBox fourSpotTable = new VBox(fourSpotTitleHeader, fourSpotCols);  // Creating the 4 Spot table
        fourSpotTable.setMaxWidth(100);
        fourSpotTable.setSpacing(5);
        fourSpotTable.setPadding(new Insets(10));
        fourSpotTable.setStyle("-fx-background-color: #c8b6ff");


        // Creating a 8 Spot Table
        Text eightSpotTitleText = new Text("Eight Spot Game");
        eightSpotTitleText.setUnderline(true);
        eightSpotTitleText.setStyle("-fx-font: bold 35 serif;");
        Text eightSpotOddsText = new Text("Overall Odds: 1 in 9.77 million");
        eightSpotOddsText.setStyle("-fx-font: 15 serif;");

        FlowPane eightSpotTitleHeader = new FlowPane(eightSpotTitleText, eightSpotOddsText);  // 8 Spot Table Header
        eightSpotTitleHeader.setStyle("-fx-background-color: #da9ac3");
        eightSpotTitleHeader.setPadding(new Insets(10));
        eightSpotTitleHeader.setAlignment(Pos.CENTER);

        Text eightSpotMatchTitle = new Text("Match");
        eightSpotMatchTitle.setStyle("-fx-font: bold 20 serif;");
        Text eightSpotPrizeTitle = new Text("Prize");
        eightSpotPrizeTitle.setStyle("-fx-font: bold 20 serif;");

        Text eightSpotMatchEntries = new Text("8\n7\n6\n5\n4\n3\n2\n1\n0");
        eightSpotMatchEntries.setStyle("-fx-font: 20 serif;");
        Text eightSpotPrizeEntries = new Text("$10,000\n$750\n$50\n$12\n$2\n$0\n$0\n$0\n$0");
        eightSpotPrizeEntries.setTextAlignment(TextAlignment.CENTER);
        eightSpotPrizeEntries.setStyle("-fx-font: 20 serif;");

        VBox eightSpotMatchCol = new VBox(eightSpotMatchTitle, eightSpotMatchEntries);
        eightSpotMatchCol.setAlignment(Pos.CENTER);
        VBox eightSpotPrizeCol = new VBox(eightSpotPrizeTitle, eightSpotPrizeEntries);
        eightSpotPrizeCol.setAlignment(Pos.CENTER);
        HBox eightSpotCols = new HBox(eightSpotMatchCol, eightSpotPrizeCol);
        eightSpotCols.setSpacing(150);

        VBox eightSpotTable = new VBox(eightSpotTitleHeader, eightSpotCols);  // Creating the 8 Spot table
        eightSpotTable.setMaxWidth(100);
        eightSpotTable.setSpacing(5);
        eightSpotTable.setPadding(new Insets(10));
        eightSpotTable.setStyle("-fx-background-color: #c05299");

        // Creating a 10 Spot Table
        Text tenSpotTitleText = new Text("Ten Spot Game");
        tenSpotTitleText.setUnderline(true);
        tenSpotTitleText.setStyle("-fx-font: bold 35 serif;");
        Text tenSpotOddsText = new Text("Overall Odds: 1 in 9.05 million");
        tenSpotOddsText.setStyle("-fx-font: 15 serif;");

        FlowPane tenSpotTitleHeader = new FlowPane(tenSpotTitleText, tenSpotOddsText);  // 1 Spot Table Header
        tenSpotTitleHeader.setStyle("-fx-font: bold 35 serif; -fx-background-color: #abb1d6");
        tenSpotTitleHeader.setPadding(new Insets(10));
        tenSpotTitleHeader.setAlignment(Pos.CENTER);

        Text tenSpotMatchTitle = new Text("Match");
        tenSpotMatchTitle.setStyle("-fx-font: bold 20 serif;");
        Text tenSpotPrizeTitle = new Text("Prize");
        tenSpotPrizeTitle.setStyle("-fx-font: bold 20 serif;");

        Text tenSpotMatchEntries = new Text("10\n9\n8\n7\n6\n5\n4\n3\n2\n1\n0");
        tenSpotMatchEntries.setStyle("-fx-font: 20 serif;");
        tenSpotMatchEntries.setTextAlignment(TextAlignment.CENTER);
        Text tenSpotPrizeEntries = new Text("$100,000\n$4,250\n$450\n$40\n$15\n$2\n$0\n$0\n$0\n$0\n$5");
        tenSpotPrizeEntries.setTextAlignment(TextAlignment.CENTER);
        tenSpotPrizeEntries.setStyle("-fx-font: 20 serif;");

        VBox tenSpotMatchCol = new VBox(tenSpotMatchTitle, tenSpotMatchEntries);
        tenSpotMatchCol.setAlignment(Pos.CENTER);
        VBox tenSpotPrizeCol = new VBox(tenSpotPrizeTitle, tenSpotPrizeEntries);
        tenSpotPrizeCol.setAlignment(Pos.CENTER);
        HBox tenSpotCols = new HBox(tenSpotMatchCol, tenSpotPrizeCol);
        tenSpotCols.setSpacing(125);

        VBox tenSpotTable = new VBox(tenSpotTitleHeader, tenSpotCols);  // Creating the 10 Spot table
        tenSpotTable.setMaxWidth(100);
        tenSpotTable.setSpacing(5);
        tenSpotTable.setPadding(new Insets(10));
        tenSpotTable.setStyle("-fx-background-color: #abc6d6");

        // Combine all the odds tabled
        HBox oddsTables = new HBox(oneSpotTable, fourSpotTable, eightSpotTable, tenSpotTable);
        oddsTables.setSpacing(35);
        oddsTables.setAlignment(Pos.CENTER);
        oddsTables.setMargin(oneSpotTable, new Insets(0, 0, 0, 50));
        oddsTables.setMargin(tenSpotTable, new Insets(0, 50, 0, 0));

        // Assemble all the parts for the paneBorder
        VBox paneCenter = new VBox(oddsTitle, oddsTables);
        HBox paneRight = new HBox(closeOddsButton);


        // Adjust its arrangements/margins
        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.setSpacing(35);

        paneRight.setAlignment(Pos.CENTER_RIGHT);
        paneRight.setMargin(closeOddsButton, new Insets(15));

        // Add the elements onto the BorderPane
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        // Scroll through the whole scene's contents
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(pane);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(scroll, 850, 750);
    }

    public Scene createLookScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #89B982");  // temp

        Text lookTitle = new Text("Choose a Theme Style");
        lookTitle.setStyle("-fx-font: bold 75 serif;");
        lookTitle.setUnderline(true);

        defaultThemeButton.setStyle("-fx-font: bold 50 serif; -fx-background-color: #0CA789;");
        pinkThemeButton.setStyle("-fx-font: bold 50 serif; -fx-background-color: #AC1E2D;");
        closeLookButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 serif; -fx-background-radius: 10; -fx-background-color: #6AA761");

        HBox buttons = new HBox(defaultThemeButton, pinkThemeButton);
        VBox paneCenter = new VBox(lookTitle, buttons);
        HBox paneRight = new HBox(closeLookButton);

        // Adjust its arrangements/margins
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);

        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.setSpacing(20);

        paneRight.setAlignment(Pos.CENTER_RIGHT);
        paneRight.setMargin(closeLookButton, new Insets(15));

        // Add the elements onto the BorderPane
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        return new Scene(pane, 850, 750);
    }

    public Scene createPlayOrExitScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #388251");

//        Text endTitle = new Text("Want to Play Again?");
        endTitle.setStyle("-fx-font: bold 75 serif;");

        // Close the application window
        Button exitGameButton = new Button("Exit The Game");
        exitGameButton.setOnAction(e -> Platform.exit());

        playAgainButton.setStyle("-fx-font: bold 50 serif; -fx-background-color: #CEE741;");
        exitGameButton.setStyle("-fx-font: bold 50 serif; -fx-background-color: #CEE741;");

        HBox buttons = new HBox(playAgainButton, exitGameButton);
        VBox paneCenter = new VBox(endTitle, buttons);

        // Adjust its arrangements/margins
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(30);

        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.setSpacing(30);

        pane.setCenter(paneCenter);

        return new Scene(pane, 850, 750);
    }

    public void applyNewLook(ArrayList<String> themeStyle) {
    }

    public void applyNewLook(String[] themeStyle) {
    }


}

