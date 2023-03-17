import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
//import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Just noticed that you can't use the same button in two different scenes; it would only appear in one of them, but not the others.
// TODO: I tried to comment it out in one scene such that it now appears on the other scene. Maybe, we might need to declare a new button, but w/ scripts attached to it. 3/13/23

public class JavaFXTemplate extends Application {
	private Button playGameButton;
	private Button menuButton;
	private Button playAgainButton;
	private Button exitButton;
	private Button defaultThemeButton;
	private Button pinkThemeButton;
	private Button startDrawButton;
	private Button quickPickButton;
	private Button submitFormButton;
	private Button closeWindowButton;
	private Button displayRulesButton;
	private Button checkOddsWinningButton;
	private Button newLookButton;

	private TextField displayTotalWinningGame;
	private TextField instructionBoard;
	private TextField display20RandomNums;
	private TextField displayNumMatches;
	private TextField displayMatchedNums;
	private TextField displayDrawWinning;

	private GridPane betCard;
	private PlaySlip game;
	private boolean hasGameStarted = false;


	private ArrayList<String> defaultTheme;
	private ArrayList<String> pinkTheme;

	private PauseTransition pause;
	private HashMap<String, Scene> sceneMap;


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
		menuButton = new Button("MENU");
		displayRulesButton = new Button("Display Rules");
		checkOddsWinningButton = new Button("Show Odds of Winning");
		newLookButton = new Button("New Look");
		exitButton = new Button("Exit Button");
		closeWindowButton = new Button("X");

		defaultThemeButton = new Button("Default");
		pinkThemeButton = new Button("Pink-Red");

		sceneMap = new HashMap<String, Scene>();  // Store all the scenes

		// Create an instance of the game and switch to the gameplay scene
		playGameButton.setOnAction(e-> {
//			game = new PlaySlip();
			hasGameStarted = true;
			primaryStage.setScene(sceneMap.get("gameplay"));

		});

		// Switch to the menu scene
		menuButton.setOnAction(e-> primaryStage.setScene(sceneMap.get("menu")));

		// Display the rules in another screen
		displayRulesButton.setOnAction(e-> primaryStage.setScene(sceneMap.get("rules")));

		// Display the odds of winning in another screen
		checkOddsWinningButton.setOnAction(e-> primaryStage.setScene(sceneMap.get("oddsOfWinning")));

		// Look screen
		newLookButton.setOnAction(e-> primaryStage.setScene(sceneMap.get("look")));

		// Go to the previous screen after closing the menu screen that depend on whether the game have already started or not
		closeWindowButton.setOnAction(e-> {
			if (hasGameStarted) {
				primaryStage.setScene(sceneMap.get("gameplay"));
			} else {
				primaryStage.setScene(sceneMap.get("intro"));
			}
		});

		// Close the application window
		exitButton.setOnAction(e-> Platform.exit());

		// Put scenes in a hashmap
		sceneMap.put("intro", createIntroScene());
		sceneMap.put("gameplay", createGameScene());
		sceneMap.put("menu", createMenuScreen());
		sceneMap.put("rules", createRulesScreen());
		sceneMap.put("oddsOfWinning", createOddsOfWinningScreen());
		sceneMap.put("look", createLookScreen());

		// This is what the user sees when opening the program
		primaryStage.setScene(sceneMap.get("intro"));
		primaryStage.show();
	}

	public Scene createIntroScene() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #E5E3C9");

		Text gameTitle = new Text("KENO");
		gameTitle.setStyle("-fx-font: bold 150 serif;");

//		Circle oneCircle = new Circle();
//		oneCircle.setRadius(45.0f);
//		oneCircle.setFill(Paint.valueOf("#89B982"));
//
//		Circle twoCircle = new Circle();
//		twoCircle.setRadius(45.0f);
//		twoCircle.setFill(Paint.valueOf("#8AA3BD"));
//
//		Circle threeCircle = new Circle();
//		threeCircle.setRadius(45.0f);
//		threeCircle.setFill(Paint.valueOf("#A87D7D"));
//
//		Circle fourCircle = new Circle();
//		fourCircle.setRadius(45.0f);
//		fourCircle.setFill(Paint.valueOf("#FF8787"));


		//Customize the buttons (TODO: maybe move the background color to the main method b/c of newlook)
		playGameButton.setStyle("-fx-pref-height: 90px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10; -fx-background-color: #94B49F");
		menuButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 95px; -fx-font: bold 20 serif; -fx-background-radius: 10; -fx-background-color: #94B49F");

//		HBox circles = new HBox(oneCircle, twoCircle, threeCircle, fourCircle);
//		circles.setAlignment(Pos.CENTER);

		VBox paneCenter = new VBox(gameTitle, playGameButton);
		HBox paneRight = new HBox(menuButton);

		// Adjust its arrangements/margins
		paneRight.setMargin(menuButton, new Insets(15));
		paneCenter.setAlignment(Pos.CENTER);
		paneRight.setAlignment(Pos.TOP_RIGHT);

		// Add the elements onto the BorderPane
		pane.setCenter(paneCenter);
		pane.setTop(paneRight);

		return new Scene(pane, 850, 750);
	}

	public Scene createGameScene() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #FF8787");  // Temp

		// left components
		displayTotalWinningGame = new TextField("Winning Game Total: XXX");
		betCard = new GridPane();
		quickPickButton = new Button("Quick Pick");
		TextField startDrawNo = new TextField("Start Draw #: XXX");

		// right components
		instructionBoard = new TextField("Instruction Board");

		display20RandomNums = new TextField("20 Random Numbers");
		displayNumMatches = new TextField("No. of matches");
		displayMatchedNums = new TextField("Numbers matched: XXX");
		displayDrawWinning = new TextField("How much they earned this draw:");


//		HBox leftBottom = new HBox(quickPickButton, startDrawNo);
//		VBox leftSide = new VBox(winGameTotal, betCard, leftBottom);
//
//		int count = 0;
//		for (int row = 0; row < 10 ; row++) {
//			for (int col = 0; col < 8; col++) {
//				betCard.add(new Button(String.valueOf(count)), col, row, 1, 1);
//				count++;
//			}
//		}
//		pane.setLeft(leftSide);
//
//		return new Scene(pane, 850, 750);
//	}

	public Scene createMenuScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #7896D7");

		Text menuTitle = new Text("MENU");
		menuTitle.setStyle("-fx-font: bold 100 serif;");
		menuTitle.setUnderline(true);

		// Customize the buttons
		displayRulesButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10;");
		checkOddsWinningButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10;");
		newLookButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10;");
		exitButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 600px; -fx-font: bold 36 serif; -fx-background-radius: 10;");
		closeWindowButton.setStyle("-fx-pref-height: 55px; -fx-pref-width: 55px; -fx-font: bold 36 serif; -fx-background-radius: 10; -fx-background-color: #6584C8");

		VBox paneCenter = new VBox(15, menuTitle, displayRulesButton, checkOddsWinningButton, newLookButton, exitButton);
		HBox paneRight = new HBox(closeWindowButton);

		// Adjust its arrangements/margins
		paneCenter.setAlignment(Pos.CENTER);
		paneRight.setAlignment(Pos.CENTER_RIGHT);
		paneRight.setMargin(closeWindowButton, new Insets(15));

		// Add the elements onto the BorderPane
		pane.setCenter(paneCenter);
		pane.setTop(paneRight);

		return new Scene(pane, 850, 750);
	}

	public Scene createRulesScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #FF8787");  // temp

		Text rulesTile = new Text("RULES");

		Text rule1 = new Text("1. Select the number of spots (1, 4, 8, or 10) and drawings (1-4) to play. This cannot be changed once it begins.");
		Text rule2 = new Text("2. Depending on the number of spots you pick, select that amount of numbers (1-80) on the grid. You cannot change it once the drawings begin.");
		Text rule3 = new Text("3. For each draw, 20 random distinct numbers will be drawn, and you win money based on the number of matches you made with your selected numbers.");
		// TODO: Feel free to modify/add more rules.

		VBox paneCenter = new VBox(rulesTile, rule1, rule2, rule3);

		// Adjust its arrangements/margins
		paneCenter.setAlignment(Pos.CENTER);

		// Add the elements onto the BorderPane
		pane.setCenter(paneCenter);

		return new Scene(pane, 850, 750);
	}

	public Scene createOddsOfWinningScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #89B982");  // temp

		Text oddsTitle = new Text("ODDS OF WINNING");

		VBox paneCenter = new VBox(oddsTitle);

		// TODO: Add an image of all the odds of winning

		// Adjust its arrangements/margins
		paneCenter.setAlignment(Pos.CENTER);

		// Add the elements onto the BorderPane
		pane.setCenter(paneCenter);


		return new Scene(pane, 850, 750);
	}

	public Scene createLookScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #89B982");  // temp

		Text lookTitle = new Text("Choose a Theme Style");
		lookTitle.setStyle("-fx-font: bold 75 serif;");
		lookTitle.setUnderline(true);

		defaultThemeButton.setStyle("-fx-font: bold 50 serif; -fx-background-color: #0CA789;");
		pinkThemeButton.setStyle("-fx-font: bold 50 serif; -fx-background-color: #AC1E2D;");

		HBox buttons = new HBox(defaultThemeButton, pinkThemeButton);
		VBox paneCenter = new VBox(lookTitle, buttons);
//		HBox paneRight = new HBox(closeWindowButton);

		// Adjust its arrangements/margins
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(20);

		paneCenter.setAlignment(Pos.CENTER);
		paneCenter.setSpacing(20);

//		paneRight.setAlignment(Pos.CENTER_RIGHT);
//		paneRight.setMargin(closeWindowButton, new Insets(15));

		// Add the elements onto the BorderPane
		pane.setCenter(paneCenter);
//		pane.setTop(paneRight);

		return new Scene(pane, 850, 750);
	}

	public Scene createPlayOrExitScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #7896D7");  // temp
		return new Scene(pane, 850, 750);
	}
	public void applyNewLook(ArrayList<String> themeStyle) {}



}

