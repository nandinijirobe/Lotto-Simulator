import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;


public class JavaFXTemplate extends Application {
    private final ArrayList<ToggleButton> arrayOfGridButtons = new ArrayList<>();
    private final ArrayList<RadioButton> arrayOfFormButtons = new ArrayList<>();
    private final String[] defaultTheme = new String[]{"#94B49F", "#E5E3C9", "#E2F0E7", "#929F97", "#A87D7D", "#B4CFB0"};  // Contains the colors: green, beige, super light green, gray, red-brown, and light green in hex codes
    private final String[] pinkTheme = new String[]{"#AC1E2D", "#FF8787", "#E8CFD3", "#995757", "#AC1E2D", "#E8CFD3"};  // Contains the colors: dark red, pink, light pink, brown, dark red, and light pink in hex codes
    private Button playGameButton;
    private Button menuButton1, menuButton2;
    private Button playAgainButton;
    private Button defaultThemeButton, pinkThemeButton;
    private Button startDrawButton;
    private Button quickPickButton;
    private Button submitFormButton;
    private Button closeMenuButton, closeRulesButton, closeOddsButton, closeLookButton;
    private Button displayRulesButton;
    private Button checkOddsWinningButton;
    private Button newLookButton;
    private Text displayTotalWinningGame, display20RandomNums, displayNumMatches, displayMatchedNums, displayDrawWinning;
    private Text instructionBoard;
    private Text endTitle;
    private GridPane betCard;
    private PlaySlip game;
    private boolean hasGameStarted = false;  // Check if the player is already playing the game
    private BorderPane gameBorderpane;
    private StackPane displayTotalWinningGameFlow, display20RandomNumsFlow, displayNumMatchesFlow, displayMatchedNumsFlow, displayDrawWinningFlow;
    private StackPane formText1Pane, formText2Pane;
    private VBox setupForm;
    private StackPane instructionBoardFlow;
    private String gridButtonBorder = "#B4CFB0";
    private PauseTransition endPause, playAgainPause;
    private HashMap<String, Scene> sceneMap;
    private EventHandler<ActionEvent> closeMenuOptionHandler;
    private int timelineCounter = 1;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Keno Game");

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

        sceneMap = new HashMap<String, Scene>();  // Store all the scenes (for switching scenes)

        // Declare a game object
        game = new PlaySlip();

        // Create an instance of the game and switch to the gameplay scene
        playGameButton.setOnAction(e -> {
            hasGameStarted = true;
            primaryStage.setScene(sceneMap.get("gameplay"));
            instructionBoard.setText("Welcome! Please select number of the spots and drawings you would like to play!");
            instructionBoard.setTextAlignment(TextAlignment.CENTER);
        });

        // When the menu button, the program will change its scene to the menu scene
        menuButton1.setOnAction(e -> primaryStage.setScene(sceneMap.get("menu")));
        menuButton2.setOnAction(e -> primaryStage.setScene(sceneMap.get("menu")));

        // Declare a handler that the player can return back to the menu scene when the player exits out of the rule scene, odds of winning scene, or the look scene
        closeMenuOptionHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(sceneMap.get("menu"));
            }
        };

        // Attach the close handler to these following scenes
        closeRulesButton.setOnAction(closeMenuOptionHandler);
        closeOddsButton.setOnAction(closeMenuOptionHandler);
        closeLookButton.setOnAction(closeMenuOptionHandler);

        // When the player clicks on the rule option in the menu, switch the scene to the rules scene
        displayRulesButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("rules")));

        // When the player clicks on the odds of winning option in the menu, switch the scene to the odds of winning scene
        checkOddsWinningButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("oddsOfWinning")));

        // When the player clicks on the looks option in the menu, switch the scene to the looks scene
        newLookButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("look")));

        // Change the look of the intro screen and the gameplay screen w/ the default theme colors (green)
        defaultThemeButton.setOnAction(e -> {
            sceneMap.put("intro", createIntroScene(defaultTheme));
            applyNewLookGame(defaultTheme);

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
            applyNewLookGame(pinkTheme);

            if (hasGameStarted) {
                primaryStage.setScene(sceneMap.get("gameplay"));
            } else {
                primaryStage.setScene(sceneMap.get("intro"));
            }
        });

        // Go to the previous screen (intro or gameplay) after closing the menu scene based on whether the player have already started the game or not
        closeMenuButton.setOnAction(e -> {
            if (hasGameStarted) {  // If the game have started, switch to the gameplay scene
                primaryStage.setScene(sceneMap.get("gameplay"));
            } else {  // Otherwise, switch to the intro scene
                primaryStage.setScene(sceneMap.get("intro"));
            }
        });

        // Declare a pause to transition from the gameplay scene to the ending scene after the player is done drawing
        endPause = new PauseTransition(Duration.seconds(3));

        // Once the pause is completed, switch to the ending scene
        endPause.setOnFinished(e -> {
            primaryStage.setScene(sceneMap.get("ending"));  // Switch to the ending scene
        });

        // Declare a pause to transition from the ending scene to the gameplay scene if the player decides to continue playing the game
        playAgainPause = new PauseTransition((Duration.seconds(3)));
        playAgainPause.setOnFinished(e -> {
            primaryStage.setScene(sceneMap.get("gameplay"));  // Switch to the gameplay scene
            endTitle.setText("Play Again?");  // Restart the ending scene's title
        });

        // Put scenes into a hashmap
        sceneMap.put("intro", createIntroScene(defaultTheme));
        sceneMap.put("gameplay", createGameScene());
        sceneMap.put("menu", createMenuScreen());
        sceneMap.put("rules", createRulesScreen());
        sceneMap.put("oddsOfWinning", createOddsOfWinningScreen());
        sceneMap.put("look", createLookScreen());
        sceneMap.put("ending", createPlayOrExitScreen());

        // This is what the player will first see when opening the program
        primaryStage.setScene(sceneMap.get("intro"));
        primaryStage.show();
    }

    // Create the intro screen the player will see first
    // themeColors contains the colors in hex codes to customize all the buttons, borderPanes, and other GUI features
    public Scene createIntroScene(String[] themeColors) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: " + themeColors[1]);

        // Game title
        Text gameTitle = new Text("KENO");
        gameTitle.setStyle("-fx-font: bold 150 Helvetica;");  // Game title's customization

        // Apply the customization to the buttons
        playGameButton.setStyle("-fx-pref-height: 90px; -fx-pref-width: 600px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10; -fx-background-color: " + themeColors[0]);
        menuButton1.setStyle("-fx-pref-height: 55px; -fx-pref-width: 95px; -fx-font: bold 20 Helvetica; -fx-background-radius: 10; -fx-background-color: " + themeColors[0]);

        // Place the buttons and the text into components
        VBox paneCenter = new VBox(gameTitle, playGameButton);
        HBox paneRight = new HBox(menuButton1);

        // Adjust the placement and arrangement of the components
        HBox.setMargin(menuButton1, new Insets(15));
        paneCenter.setAlignment(Pos.CENTER);
        paneRight.setAlignment(Pos.TOP_RIGHT);

        // Place the components into the BorderPane (depending on the location)
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        return new Scene(pane, 1600, 750);  // Returns the newly created intro scene
    }

    // Creates the game scene where the user will fill out a form amd a bet card to play the game
    public Scene createGameScene() {
        // set background style
        gameBorderpane = new BorderPane();
        gameBorderpane.setStyle("-fx-background-color: #E5E3C9");

        // create and style left components
        betCard = new GridPane();
        quickPickButton = new Button("Quick Pick");
        quickPickButton.setDisable(true);
        quickPickButton.setStyle("-fx-background-color: #A87D7D; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-min-height: 50; -fx-text-fill: black;");

        startDrawButton = new Button("Start Draw No: 1");
        startDrawButton.setDisable(true);
        startDrawButton.setStyle("-fx-background-color: #A87D7D; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-text-fill: black; -fx-min-height: 50; -fx-alignment: center; -fx-border-width: 10; -fx-background-radius: 5;");

        displayTotalWinningGame = new Text("Winning Game Total: XXX");
        displayTotalWinningGameFlow = new StackPane(displayTotalWinningGame);
        displayTotalWinningGameFlow.setStyle("-fx-background-color: #E2F0E7; -fx-font: bold 15 Helvetica;  -fx-min-width: 480; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        // create bet card
        int count = 1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                ToggleButton gridButton = new ToggleButton(String.valueOf(count)); // switched type from Button to Toggle Button
                gridButton.setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color:" + gridButtonBorder + "; -fx-border-width:5;");
                betCard.add(gridButton, col, row, 1, 1);
                arrayOfGridButtons.add(gridButton);
                gridButton.setDisable(true);
                gridButton.setOnAction(e -> {
                    if (gridButton.isSelected()) {
                        gridButton.setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #88998E; -fx-border-color:" + gridButtonBorder + "; -fx-border-width:5;");
                        game.addSpotNum(Integer.valueOf(gridButton.getText()));
                    } else {
                        gridButton.setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: " + gridButtonBorder + "; -fx-border-width:5;");
                        game.removeSpotNum(Integer.valueOf(gridButton.getText()));
                    }
                });
                count++;
            }
        }

        // arrange all the left components
        HBox leftBottom = new HBox(quickPickButton, startDrawButton);
        VBox leftSide = new VBox(displayTotalWinningGameFlow, betCard, leftBottom);
        HBox.setMargin(quickPickButton, new Insets(10));
        HBox.setMargin(startDrawButton, new Insets(10));
        VBox.setMargin(displayTotalWinningGameFlow, new Insets(10));
        VBox.setMargin(betCard, new Insets(10));
        leftSide.setAlignment(Pos.CENTER);

        // create right and style components
        Text formText1 = new Text("Select the Number of Spots to Play");
        formText1Pane = new StackPane(formText1);
        formText1Pane.setStyle("-fx-background-color: #E2F0E7; -fx-font: bold 15 Helvetica;  -fx-min-width: 80; -fx-min-height: 30; -fx-alignment: center; -fx-border-width: 30;");

        Text formText2 = new Text("Select the Number of Drawings");
        formText2Pane = new StackPane(formText2);
        formText2Pane.setStyle("-fx-background-color: #E2F0E7; -fx-font: bold 15 Helvetica;  -fx-min-width: 80; -fx-min-height: 30; -fx-alignment: center; -fx-border-width: 30;");

        submitFormButton = new Button("Submit");
        submitFormButton.setStyle("-fx-pref-height: 20px; -fx-pref-width: 95px; -fx-font: bold 15 Helvetica; -fx-background-color: #89B982;");
        menuButton2.setStyle("-fx-pref-height: 55px; -fx-pref-width: 95px; -fx-font: bold 20 Helvetica; -fx-background-radius: 10; -fx-background-color: #89B982;");

        // create and arrange the form buttons
        HBox spotsToPlay = new HBox();
        HBox drawsToPlay = new HBox();
        ToggleGroup group1 = new ToggleGroup();
        ToggleGroup group2 = new ToggleGroup();

        int[] spotOptions = {1, 4, 8, 10};
        for (int i = 0; i < 4; i++) {
            RadioButton gridButton1 = new RadioButton(String.valueOf(spotOptions[i]));
            RadioButton gridButton2 = new RadioButton(String.valueOf(i + 1));
            gridButton1.setStyle("-fx-min-height: 45px; -fx-min-width: 80px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-border-color: #B4CFB0; -fx-border-width:5;");
            gridButton2.setStyle("-fx-min-height: 45px; -fx-min-width: 80px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-border-color: #B4CFB0; -fx-border-width:5;");
            gridButton1.setToggleGroup(group1);
            gridButton2.setToggleGroup(group2);
            spotsToPlay.getChildren().add(gridButton1);
            drawsToPlay.getChildren().add(gridButton2);
            arrayOfFormButtons.add(gridButton1);
            arrayOfFormButtons.add(gridButton2);
        }

        // create and style results text boxes
        instructionBoard = new Text("Instruction Board");
        instructionBoardFlow = new StackPane(instructionBoard);
        instructionBoardFlow.setStyle("-fx-background-color: #B4CFB0; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        display20RandomNums = new Text("20 Random Numbers");
        display20RandomNumsFlow = new StackPane(display20RandomNums);
        display20RandomNumsFlow.setStyle("-fx-background-color: #929F97; -fx-font: bold 15 Helvetica;  -fx-min-width: 60; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        displayNumMatches = new Text("Number of matches: XXXXXXX");
        displayNumMatches.setWrappingWidth(200);
        displayNumMatches.setTextAlignment(TextAlignment.CENTER);
        displayNumMatchesFlow = new StackPane(displayNumMatches);
        displayNumMatchesFlow.setStyle("-fx-background-color: #E2F0E7; -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        displayMatchedNums = new Text("Numbers matched: XXXXXXX");
        displayMatchedNums.setWrappingWidth(200);
        displayMatchedNums.setTextAlignment(TextAlignment.CENTER);
        displayMatchedNumsFlow = new StackPane(displayMatchedNums);
        displayMatchedNumsFlow.setStyle("-fx-background-color: #E2F0E7;  -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        displayDrawWinning = new Text("Current Draw Winnings: XXXXXXX");
        displayDrawWinning.setWrappingWidth(200);
        displayDrawWinning.setTextAlignment(TextAlignment.CENTER);

        displayDrawWinningFlow = new StackPane(displayDrawWinning);
        displayDrawWinningFlow.setStyle("-fx-background-color: #E2F0E7;  -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");

        // arrange all the right components
        VBox spotsBox = new VBox(formText1Pane, spotsToPlay);
        VBox drawingsBox = new VBox(formText2Pane, drawsToPlay);
        HBox spotsNdrawings = new HBox(spotsBox, drawingsBox);
        HBox submitBox = new HBox(submitFormButton);
        submitBox.setAlignment(Pos.CENTER);
        setupForm = new VBox(spotsNdrawings, submitBox);
        setupForm.setStyle("-fx-background-color: #E2F0E7;");

        VBox.setMargin(spotsToPlay, new Insets(10));
        VBox.setMargin(drawsToPlay, new Insets(10));
        VBox.setMargin(formText1, new Insets(10));
        VBox.setMargin(formText2, new Insets(10));
        HBox.setMargin(submitFormButton, new Insets(10));

        HBox rightTop = new HBox(menuButton2);
        HBox.setMargin(menuButton2, new Insets(10));
        rightTop.setAlignment(Pos.TOP_RIGHT);

        HBox rightBottom = new HBox(displayNumMatchesFlow, displayMatchedNumsFlow, displayDrawWinningFlow);
        VBox rightSide = new VBox(rightTop, instructionBoardFlow, setupForm, display20RandomNumsFlow, rightBottom);
        HBox.setMargin(displayNumMatchesFlow, new Insets(10));
        HBox.setMargin(displayMatchedNumsFlow, new Insets(10));
        HBox.setMargin(displayDrawWinningFlow, new Insets(10));
        VBox.setMargin(instructionBoardFlow, new Insets(10));
        VBox.setMargin(display20RandomNumsFlow, new Insets(10));
        rightSide.setAlignment(Pos.CENTER);

        // arrange both the left and right side
        HBox gameBoard = new HBox(leftSide, rightSide);
        gameBoard.setAlignment(Pos.CENTER);
        gameBorderpane.setCenter(gameBoard);
        BorderPane.setMargin(gameBoard, new Insets(100));

        // Scroll through the whole scene's contents
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(gameBorderpane);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);


        // EVENT HANDLERS FOR LEFT COMPONENTS:
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(t -> {
            System.out.println("Program Paused...");
        });

        // The program will randomly select the spot numbers from the grid for the player if the quickpick button is clicked
        quickPickButton.setOnAction(e -> {
            game.quickPick();  // Choose random spots for the player to have
            instructionBoard.setText("We have chosen the numbers on the bet card for you...");  // Update the instruction board

            // Iterating through all the grid number buttons
            for (int i = 0; i < 80; i++) {
                // If the player already selected that grid button, but it's not the one the program picks for them, deselect that grid button and remove it from the selectedSpots set
                if (arrayOfGridButtons.get(i).isSelected() && !(game.getSelectedSpots().contains(Integer.valueOf(arrayOfGridButtons.get(i).getText())))) {
                    arrayOfGridButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: " + gridButtonBorder + ";  -fx-border-width:5;");  // Change color to show it's no longer selected
                    game.removeSpotNum(Integer.valueOf(arrayOfGridButtons.get(i).getText()));
                    arrayOfGridButtons.get(i).setSelected(false);
                } else if (!arrayOfGridButtons.get(i).isSelected() && game.getSelectedSpots().contains(Integer.valueOf(arrayOfGridButtons.get(i).getText()))) {
                    // If the player didn't select that grid button, but it's the one the program picks for them, select that grid button and add it to the selectedSpots set
                    arrayOfGridButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #88998E; -fx-border-color: " + gridButtonBorder + ";  -fx-border-width:5;");  // Change color to show it's selected
                    arrayOfGridButtons.get(i).setSelected(true);
                }
            }
        });

        submitFormButton.setOnAction(e -> {
            if (group1.getSelectedToggle() == null || group2.getSelectedToggle() == null) {
                instructionBoard.setText("Oops! You haven't filled out the form yet.");
            } else {
                // Disable the form buttons
                submitFormButton.setDisable(true);
                for (int i = 0; i < arrayOfFormButtons.size(); i++) {
                    arrayOfFormButtons.get(i).setDisable(true);
                }
                // Figure out the spots and drawings the user has selected in form and begin new round
                int drawNum = Integer.valueOf(((ToggleButton) group2.getSelectedToggle()).getText());
                int spotsNum = Integer.valueOf(((ToggleButton) group1.getSelectedToggle()).getText());
                game.newRound(drawNum, spotsNum);
                instructionBoard.setText("Now choose " + spotsNum + " spots on the bet card.");

                // Enable the bet card
                for (int i = 0; i < arrayOfGridButtons.size(); i++) {
                    arrayOfGridButtons.get(i).setDisable(false);
                }
                // Enable the quick pick and start draw buttons
                quickPickButton.setDisable(false);
                startDrawButton.setDisable(false);
            }
        });

        startDrawButton.setOnAction(e -> {
            if (game.checkNumSpots()) {
                // Disable left buttons and also bet card
                quickPickButton.setDisable(true);
                for (int i = 0; i < arrayOfGridButtons.size(); i++) {
                    arrayOfGridButtons.get(i).setDisable(true);
                }

                // update instructions and begin new draw
                instructionBoard.setText("Loading...");
                pause.play();
                instructionBoard.setText("You have " + (game.getNumDrawings() - game.getCurrentDrawNum()) + " draws left.");
                game.newDraw();

                // update all results boards
//                timelineCounter=0;
//                ArrayList<Integer> generatedRandNums = game.get20RandNumArrayList();
//                display20RandomNums.setText("Here are the 20 random generated numbers:\n");
//                display20RandomNums.setText(display20RandomNums.getText() + generatedRandNums.get(0));
//                display20RandomNums.setTextAlignment(TextAlignment.CENTER);
//                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), x -> {
//                    display20RandomNums.setText(display20RandomNums.getText() + ", " + generatedRandNums.get(timelineCounter));
//                    display20RandomNums.setTextAlignment(TextAlignment.CENTER);
//                    timelineCounter++;
//                }));
//                timeline.setCycleCount(generatedRandNums.size() - 1);
//                timeline.playFromStart();
//                pause.play();
//                pause.play();

                display20RandomNums.setText(game.get20RandNumString());
                display20RandomNums.setTextAlignment(TextAlignment.CENTER);
                displayNumMatches.setText("Number of matches:\n" + game.getNumMatches());
                displayMatchedNums.setTextAlignment(TextAlignment.CENTER);
                displayMatchedNums.setText(game.getMatchesString());
                displayMatchedNums.setTextAlignment(TextAlignment.CENTER);
                displayDrawWinning.setText("Current draw winnings:\n$" + game.getDrawWinningMoney());
                displayDrawWinning.setTextAlignment(TextAlignment.CENTER);
                displayTotalWinningGame.setText("Total Game Winnings:\n$" + game.getTotalGameWinningMoney());
                displayTotalWinningGame.setTextAlignment(TextAlignment.CENTER);
                startDrawButton.setText("Start Draw No: " + game.getCurrentDrawNum());
//                startDrawButton.setDisable(false);
            } else {
                if (game.getNumSelectedSpots() > game.getNumSpots()) {
                    instructionBoard.setText("Oops! You chose too many spots. Please select only " + game.getNumSpots() + ".");
                } else {
                    instructionBoard.setText("Oops! You chose too few spots. Please select only " + game.getNumSpots() + ".");
                }
            }
            if (game.checkDrawsCompleted()) {
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
                if (arrayOfGridButtons.get(i).isSelected()) {
                    arrayOfGridButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color: #B4CFB0; -fx-border-width:5;");
                    game.removeSpotNum(Integer.valueOf(arrayOfGridButtons.get(i).getText()));
                    arrayOfGridButtons.get(i).setSelected(false);
                }
            }
            endTitle.setText("Rebooting...");
            playAgainPause.play();  // return to the gameplay scene
        });

        return new Scene(scroll, 1600, 750);
    }

    // Create the menu scene that will display all the menu options the player can choose
    public Scene createMenuScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #7896D7");

        // Menu Title
        Text menuTitle = new Text("MENU");
        menuTitle.setStyle("-fx-font: bold 100 Helvetica;");  // Menu title's customization
        menuTitle.setUnderline(true);

        // Create an exit button where the player can exit the game, terminating the program
        Button exitButton = new Button("Exit the Game");
        exitButton.setOnAction(e -> Platform.exit());

        // Apply the customization to the buttons
        displayRulesButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10;");
        checkOddsWinningButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10;");
        newLookButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10;");
        exitButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10;");
        closeMenuButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10; -fx-background-color: #6584C8");

        // Place the buttons and the text into components
        VBox paneCenter = new VBox(15, menuTitle, displayRulesButton, checkOddsWinningButton, newLookButton, exitButton);
        HBox paneRight = new HBox(closeMenuButton);

        // Adjust the placement and arrangement of the components
        paneCenter.setAlignment(Pos.CENTER);
        paneRight.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(closeMenuButton, new Insets(15));

        // Place the components into the BorderPane (depending on the location)
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        return new Scene(pane, 1600, 750);  // Returns the newly created menu scene
    }

    // Create the rules scene where the players can read the game instructions
    public Scene createRulesScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #FF8787");  // temp

        // Rules Title
        Text rulesTitle = new Text("RULES");
        rulesTitle.setStyle("-fx-font: bold 75 Helvetica;");  // Rules Title's customization
        rulesTitle.setUnderline(true);

        // Instruction text
        Text rule1 = new Text("1. Select the number of spots (1, 4, 8, or 10) and drawings (1-4) to play. This cannot be changed once it begins.");
        Text rule2 = new Text("2. Depending on the number of spots you pick, select that amount of numbers (1-80) on the grid. You cannot change it once the drawings begin.");
        Text rule3 = new Text("3. For each draw, 20 random distinct numbers will be drawn, and you win money based on the number of matches you made with your selected numbers.");

        // Apply the customization to the instruction text
        rule1.setStyle("-fx-font: 25 Helvetica;");
        rule1.setWrappingWidth(840);
        rule1.setTextAlignment(TextAlignment.LEFT);
        rule2.setStyle("-fx-font: 25 Helvetica;");
        rule2.setWrappingWidth(840);
        rule2.setTextAlignment(TextAlignment.LEFT);
        rule3.setStyle("-fx-font: 25 Helvetica;");
        rule3.setWrappingWidth(840);
        rule3.setTextAlignment(TextAlignment.LEFT);

        // Apply the customization to the close button that will close the rule screen
        closeRulesButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10; -fx-background-color: #E77070");

        // Place the buttons and the text into components
        VBox paneCenter = new VBox(rulesTitle, rule1, rule2, rule3);
        HBox paneRight = new HBox(closeRulesButton);
        HBox.setMargin(closeRulesButton, new Insets(15));  // Adjust the placement of the close button

        // Adjust the placement and arrangement of the components
        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.setSpacing(12);
        paneRight.setAlignment(Pos.CENTER_RIGHT);

        // Place the components into the BorderPane (depending on the location)
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        return new Scene(pane, 1600, 750);  // Returns the newly created rules scene
    }

    /* Create the odds of winning scene that displays the amount of money the player can win depending on the number of
       matches they get per draw and the number of spots they play */
    public Scene createOddsOfWinningScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #8f7ea3");

        // Odds of Winning Title
        Text oddsTitle = new Text("ODDS OF WINNING");
        oddsTitle.setStyle("-fx-font: bold 75 Helvetica;");  // Odds of Winning Title's customization
        oddsTitle.setUnderline(true);

        // Apply the customization to the button that the player can click to close the Odds of Winning scene
        closeOddsButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10; -fx-background-color: #796a8c");

        // Creating a 1 Spot Table
        // 1 Spot Table Title
        Text oneSpotTitleText = new Text("One Spot Game");
        oneSpotTitleText.setStyle("-fx-font: bold 35 Helvetica;");
        oneSpotTitleText.setUnderline(true);

        // 1 Spot Table Odds Text
        Text oneSpotOddsText = new Text("Overall Odds: 1 in 4.0 million");
        oneSpotOddsText.setStyle("-fx-font: 15 Helvetica;");

        // 1 Spot Table Title Header (containing both the title and odds text)
        FlowPane oneSpotTitleHeader = new FlowPane(oneSpotTitleText, oneSpotOddsText);
        oneSpotTitleHeader.setStyle("-fx-background-color: #c05299");
        oneSpotTitleHeader.setPadding(new Insets(10));
        oneSpotTitleHeader.setAlignment(Pos.CENTER);

        // 1 Spot Table's column names (Match and Prize)
        Text oneSpotMatchTitle = new Text("Match");
        oneSpotMatchTitle.setStyle("-fx-font: bold 20 Helvetica;");
        Text oneSpotPrizeTitle = new Text("Prize");
        oneSpotPrizeTitle.setStyle("-fx-font: bold 20 Helvetica;");

        // Match/Prize entries for the 1 Spot Table
        Text oneSpotMatchEntries = new Text("1\n0");
        oneSpotMatchEntries.setStyle("-fx-font: 20 Helvetica;");
        oneSpotMatchEntries.setTextAlignment(TextAlignment.CENTER);
        Text oneSpotPrizeEntries = new Text("$2\n$0");
        oneSpotPrizeEntries.setStyle("-fx-font: 20 Helvetica;");
        oneSpotPrizeEntries.setTextAlignment(TextAlignment.CENTER);

        // Create components to assemble the 1 Spot Table's columns
        VBox oneSpotMatchCol = new VBox(oneSpotMatchTitle, oneSpotMatchEntries);
        oneSpotMatchCol.setAlignment(Pos.CENTER);
        VBox oneSpotPrizeCol = new VBox(oneSpotPrizeTitle, oneSpotPrizeEntries);
        oneSpotPrizeCol.setAlignment(Pos.CENTER);
        HBox oneSpotCols = new HBox(oneSpotMatchCol, oneSpotPrizeCol);
        oneSpotCols.setSpacing(145);

        // Create a component that create the 1 Spot Table
        VBox oneSpotTable = new VBox(oneSpotTitleHeader, oneSpotCols);
        oneSpotTable.setSpacing(5);
        oneSpotTable.setPadding(new Insets(10));
        oneSpotTable.setMaxWidth(100);
        oneSpotTable.setStyle("-fx-background-color: #ea698b");

        // Creating a 4 Spot Table
        // 4 Spots Table Title
        Text fourSpotTitleText = new Text("Four Spot Game");
        fourSpotTitleText.setUnderline(true);
        fourSpotTitleText.setStyle("-fx-font: bold 35 Helvetica;");

        // 4 Spots Table Odds Text
        Text fourSpotOddsText = new Text("Overall Odds: 1 in 3.86 million");
        fourSpotOddsText.setStyle("-fx-font: 15 Helvetica;");

        // 4 Spots Table Title Header (containing both the title and odds text)
        FlowPane fourSpotTitleHeader = new FlowPane(fourSpotTitleText, fourSpotOddsText);
        fourSpotTitleHeader.setStyle("-fx-background-color: #e7c6ff");
        fourSpotTitleHeader.setPadding(new Insets(10));
        fourSpotTitleHeader.setAlignment(Pos.CENTER);

        // 4 Spots Table's column names (Match and Prize)
        Text fourSpotMatchTitle = new Text("Match");
        fourSpotMatchTitle.setStyle("-fx-font: bold 20 Helvetica;");
        Text fourSpotPrizeTitle = new Text("Prize");
        fourSpotPrizeTitle.setStyle("-fx-font: bold 20 Helvetica;");

        // Match/Prize entries for the 4 Spots Table
        Text fourSpotMatchEntries = new Text("4\n3\n2\n1\n0");
        fourSpotMatchEntries.setStyle("-fx-font: 20 Helvetica;");
        Text fourSpotPrizeEntries = new Text("$75\n$5\n$1\n$0\n$0");
        fourSpotPrizeEntries.setTextAlignment(TextAlignment.CENTER);
        fourSpotPrizeEntries.setStyle("-fx-font: 20 Helvetica;");

        // Create components to assemble the 4 Spots Table's columns
        VBox fourSpotMatchCol = new VBox(fourSpotMatchTitle, fourSpotMatchEntries);
        fourSpotMatchCol.setAlignment(Pos.CENTER);
        VBox fourSpotPrizeCol = new VBox(fourSpotPrizeTitle, fourSpotPrizeEntries);
        fourSpotPrizeCol.setAlignment(Pos.CENTER);
        HBox fourSpotCols = new HBox(fourSpotMatchCol, fourSpotPrizeCol);
        fourSpotCols.setSpacing(160);

        // Create a component that create the 4 Spots Table
        VBox fourSpotTable = new VBox(fourSpotTitleHeader, fourSpotCols);
        fourSpotTable.setMaxWidth(100);
        fourSpotTable.setSpacing(5);
        fourSpotTable.setPadding(new Insets(10));
        fourSpotTable.setStyle("-fx-background-color: #c8b6ff");


        // Creating a 8 Spot Table
        // 8 Spots Table Title
        Text eightSpotTitleText = new Text("Eight Spot Game");
        eightSpotTitleText.setUnderline(true);
        eightSpotTitleText.setStyle("-fx-font: bold 35 Helvetica;");

        // 8 Spots Table Odds Text
        Text eightSpotOddsText = new Text("Overall Odds: 1 in 9.77 million");
        eightSpotOddsText.setStyle("-fx-font: 15 Helvetica;");

        // 8 Spots Table Title Header (containing both the title and odds text)
        FlowPane eightSpotTitleHeader = new FlowPane(eightSpotTitleText, eightSpotOddsText);
        eightSpotTitleHeader.setStyle("-fx-background-color: #da9ac3");
        eightSpotTitleHeader.setPadding(new Insets(10));
        eightSpotTitleHeader.setAlignment(Pos.CENTER);

        // 8 Spots Table's column names (Match and Prize)
        Text eightSpotMatchTitle = new Text("Match");
        eightSpotMatchTitle.setStyle("-fx-font: bold 20 Helvetica;");
        Text eightSpotPrizeTitle = new Text("Prize");
        eightSpotPrizeTitle.setStyle("-fx-font: bold 20 Helvetica;");

        // Match/Prize entries for the 8 Spots Table
        Text eightSpotMatchEntries = new Text("8\n7\n6\n5\n4\n3\n2\n1\n0");
        eightSpotMatchEntries.setStyle("-fx-font: 20 Helvetica;");
        Text eightSpotPrizeEntries = new Text("$10,000\n$750\n$50\n$12\n$2\n$0\n$0\n$0\n$0");
        eightSpotPrizeEntries.setTextAlignment(TextAlignment.CENTER);
        eightSpotPrizeEntries.setStyle("-fx-font: 20 Helvetica;");

        // Create components to assemble the 8 Spots Table's columns
        VBox eightSpotMatchCol = new VBox(eightSpotMatchTitle, eightSpotMatchEntries);
        eightSpotMatchCol.setAlignment(Pos.CENTER);
        VBox eightSpotPrizeCol = new VBox(eightSpotPrizeTitle, eightSpotPrizeEntries);
        eightSpotPrizeCol.setAlignment(Pos.CENTER);
        HBox eightSpotCols = new HBox(eightSpotMatchCol, eightSpotPrizeCol);
        eightSpotCols.setSpacing(150);

        // Create a component that create the 8 Spots Table
        VBox eightSpotTable = new VBox(eightSpotTitleHeader, eightSpotCols);
        eightSpotTable.setMaxWidth(100);
        eightSpotTable.setSpacing(5);
        eightSpotTable.setPadding(new Insets(10));
        eightSpotTable.setStyle("-fx-background-color: #c05299");

        // Creating a 10 Spot Table
        // 10 Spots Table Title
        Text tenSpotTitleText = new Text("Ten Spot Game");
        tenSpotTitleText.setUnderline(true);
        tenSpotTitleText.setStyle("-fx-font: bold 35 Helvetica;");

        // 10 Spots Table Odds Text
        Text tenSpotOddsText = new Text("Overall Odds: 1 in 9.05 million");
        tenSpotOddsText.setStyle("-fx-font: 15 Helvetica;");

        // 10 Spots Table Title Header (containing both the title and odds text)
        FlowPane tenSpotTitleHeader = new FlowPane(tenSpotTitleText, tenSpotOddsText);
        tenSpotTitleHeader.setStyle("-fx-font: bold 35 Helvetica; -fx-background-color: #abb1d6");
        tenSpotTitleHeader.setPadding(new Insets(10));
        tenSpotTitleHeader.setAlignment(Pos.CENTER);

        // 10 Spots Table's column names (Match and Prize)
        Text tenSpotMatchTitle = new Text("Match");
        tenSpotMatchTitle.setStyle("-fx-font: bold 20 Helvetica;");
        Text tenSpotPrizeTitle = new Text("Prize");
        tenSpotPrizeTitle.setStyle("-fx-font: bold 20 Helvetica;");

        // Match/Prize entries for the 10 Spots Table
        Text tenSpotMatchEntries = new Text("10\n9\n8\n7\n6\n5\n4\n3\n2\n1\n0");
        tenSpotMatchEntries.setStyle("-fx-font: 20 Helvetica;");
        tenSpotMatchEntries.setTextAlignment(TextAlignment.CENTER);
        Text tenSpotPrizeEntries = new Text("$100,000\n$4,250\n$450\n$40\n$15\n$2\n$0\n$0\n$0\n$0\n$5");
        tenSpotPrizeEntries.setTextAlignment(TextAlignment.CENTER);
        tenSpotPrizeEntries.setStyle("-fx-font: 20 Helvetica;");

        // Create components to assemble the 10 Spots Table's columns
        VBox tenSpotMatchCol = new VBox(tenSpotMatchTitle, tenSpotMatchEntries);
        tenSpotMatchCol.setAlignment(Pos.CENTER);
        VBox tenSpotPrizeCol = new VBox(tenSpotPrizeTitle, tenSpotPrizeEntries);
        tenSpotPrizeCol.setAlignment(Pos.CENTER);
        HBox tenSpotCols = new HBox(tenSpotMatchCol, tenSpotPrizeCol);
        tenSpotCols.setSpacing(125);

        // Create a component that create the 10 Spots Table
        VBox tenSpotTable = new VBox(tenSpotTitleHeader, tenSpotCols);  // Creating the 10 Spot table
        tenSpotTable.setMaxWidth(100);
        tenSpotTable.setSpacing(5);
        tenSpotTable.setPadding(new Insets(10));
        tenSpotTable.setStyle("-fx-background-color: #abc6d6");

        // Place the button, text, and the odds tables into components
        HBox oddsTables = new HBox(oneSpotTable, fourSpotTable, eightSpotTable, tenSpotTable);
        oddsTables.setSpacing(35);
        oddsTables.setAlignment(Pos.CENTER);
        HBox.setMargin(oneSpotTable, new Insets(0, 0, 0, 50));
        HBox.setMargin(tenSpotTable, new Insets(0, 50, 0, 0));
        VBox paneCenter = new VBox(oddsTitle, oddsTables);
        HBox paneRight = new HBox(closeOddsButton);

        // Adjust the placement and arrangement of the components
        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.setSpacing(35);
        paneRight.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(closeOddsButton, new Insets(15));

        // Place the components into the BorderPane (depending on the location)
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        // Scroll through the whole scene's contents (depending on the window's size)
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(pane);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

        return new Scene(scroll, 1600, 750);  // Returns the newly created odds of winning scene
    }

    // Create the looks scene where the player can select a color theme to customize the game's intro and gameplay screens
    public Scene createLookScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #89B982");

        // Looks Title
        Text lookTitle = new Text("Choose a Theme Style");
        lookTitle.setStyle("-fx-font: bold 75 Helvetica;");  // Looks Title's customization
        lookTitle.setUnderline(true);

        // Apply the customization to the buttons
        defaultThemeButton.setStyle("-fx-font: bold 50 Helvetica; -fx-background-color: #0CA789;");
        pinkThemeButton.setStyle("-fx-font: bold 50 Helvetica; -fx-background-color: #AC1E2D;");
        closeLookButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 Helvetica; -fx-background-radius: 10; -fx-background-color: #6AA761");

        // Place the buttons and the text into components
        HBox buttons = new HBox(defaultThemeButton, pinkThemeButton);
        VBox paneCenter = new VBox(lookTitle, buttons);
        HBox paneRight = new HBox(closeLookButton);

        // Adjust the placement and arrangement of the components
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.setSpacing(20);
        paneRight.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(closeLookButton, new Insets(15));

        // Place the components into the BorderPane (depending on the location)
        pane.setCenter(paneCenter);
        pane.setTop(paneRight);

        return new Scene(pane, 1600, 750);  // Returns the newly created looks scene
    }

    // Create the ending scene where the player can make a choice whether to continue to play the game or leave the game
    public Scene createPlayOrExitScreen() {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #388251");

        // End Title's customization
        endTitle.setStyle("-fx-font: bold 75 Helvetica;");

        // Create an exit button that the player can exit the game, terminating the program
        Button exitGameButton = new Button("Exit The Game");
        exitGameButton.setOnAction(e -> Platform.exit());

        // Apply the customization to the buttons
        playAgainButton.setStyle("-fx-font: bold 50 Helvetica; -fx-background-color: #CEE741;");
        exitGameButton.setStyle("-fx-font: bold 50 Helvetica; -fx-background-color: #CEE741;");

        // Place the buttons and the text into components
        HBox buttons = new HBox(playAgainButton, exitGameButton);
        VBox paneCenter = new VBox(endTitle, buttons);

        // Adjust the placement and arrangement of the components
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(30);
        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.setSpacing(30);

        // Place the components into the BorderPane (at the center)
        pane.setCenter(paneCenter);

        return new Scene(pane, 1600, 750);  // Returns the newly created ending scene
    }

    /* This changes the theme of the entire game by applying different colors to all the game components.
     * It uses an array of string hex codes (which is passed in as a parameter) to get the colors*/
    public void applyNewLookGame(String[] themeStyle) {
        gridButtonBorder = themeStyle[0];
        gameBorderpane.setStyle("-fx-background-color: " + themeStyle[1]);
        quickPickButton.setStyle("-fx-background-color: " + themeStyle[4] + "; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-min-height: 50; -fx-text-fill: black;");
        startDrawButton.setStyle("-fx-background-color:" + themeStyle[4] + "; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-text-fill: black; -fx-min-height: 50; -fx-alignment: center; -fx-border-width: 10; -fx-background-radius: 5;");
        displayTotalWinningGameFlow.setStyle("-fx-background-color:" + themeStyle[2] + ";  -fx-font: bold 15 Helvetica;  -fx-min-width: 480; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");
        formText1Pane.setStyle("-fx-background-color: " + themeStyle[2] + "; -fx-font: bold 15 Helvetica;  -fx-min-width: 80; -fx-min-height: 30; -fx-alignment: center; -fx-border-width: 30;");
        formText2Pane.setStyle("-fx-background-color: " + themeStyle[2] + "; -fx-font: bold 15 Helvetica;  -fx-min-width: 80; -fx-min-height: 30; -fx-alignment: center; -fx-border-width: 30;");
        menuButton2.setStyle("-fx-pref-height: 55px; -fx-pref-width: 95px; -fx-font: bold 20 Helvetica; -fx-background-radius: 10; -fx-background-color:" + themeStyle[0]);
        setupForm.setStyle("-fx-background-color:" + themeStyle[2]);
        instructionBoardFlow.setStyle("-fx-background-color:" + themeStyle[2] + "; -fx-font: bold 15 Helvetica;  -fx-min-width: 240; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");
        display20RandomNumsFlow.setStyle("-fx-background-color:" + themeStyle[3] + "; -fx-font: bold 15 Helvetica;  -fx-min-width: 60; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");
        displayNumMatchesFlow.setStyle("-fx-background-color:" + themeStyle[2] + "; -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");
        displayMatchedNumsFlow.setStyle("-fx-background-color:" + themeStyle[2] + "; -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");
        displayDrawWinningFlow.setStyle("-fx-background-color:" + themeStyle[2] + "; -fx-border-insets:20; -fx-font: bold 15 Helvetica;  -fx-min-width: 160; -fx-min-height: 100; -fx-alignment: center; -fx-border-width: 30; -fx-background-radius: 5;");
        menuButton2.setStyle("-fx-pref-height: 55px; -fx-pref-width: 95px; -fx-font: bold 20 Helvetica; -fx-background-radius: 10; -fx-background-color: " + themeStyle[0] + ";");
        submitFormButton.setStyle("-fx-pref-height: 20px; -fx-pref-width: 95px; -fx-font: bold 15 Helvetica; -fx-background-color:" + themeStyle[4] + ";");

        /*This iterates through all the form buttons to change the style of each one*/
        for (int i = 0; i < arrayOfFormButtons.size(); i++) {
            arrayOfFormButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 80px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-border-color:" + gridButtonBorder + ";-fx-border-width:5;");
        }

        /*This iterates through all the grid buttons to change the style of each one*/
        for (int i = 0; i < arrayOfGridButtons.size(); i++) {
            if (arrayOfGridButtons.get(i).isSelected()) {
                arrayOfGridButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #88998E; -fx-border-color:" + gridButtonBorder + ";-fx-border-width:5;");
            } else {
                arrayOfGridButtons.get(i).setStyle("-fx-min-height: 45px; -fx-min-width: 50px; -fx-background-radius: 1;-fx-font: bold 15 Helvetica; -fx-background-color: #E2F0E7; -fx-border-color:" + gridButtonBorder + ";-fx-border-width:5;");
            }
        }
    }


}

