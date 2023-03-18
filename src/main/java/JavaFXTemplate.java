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

// TODO: Just noticed that you can't use the same button in two different scenes; it would only appear in one of them, but not the others.
// TODO: I tried to comment it out in one scene such that it now appears on the other scene. Maybe, we might need to declare a new button, but w/ scripts attached to it. 3/13/23

public class JavaFXTemplate extends Application {
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

	private TextField displayTotalWinningGame;
	private TextField instructionBoard;
	private TextField display20RandomNums;
	private TextField displayNumMatches;
	private TextField displayMatchedNums;
	private TextField displayDrawWinning;

	private GridPane betCard;
	private PlaySlip game;
	private boolean hasGameStarted = false;


	// Each theme style contains the colors' hex codes
	private String[]defaultTheme = new String[] {
			"#94B49F",
			"#E5E3C9"
	};
	private String[]pinkTheme = new String[] {
			"#F24726",
			"#FF8787"
	};

	private PauseTransition pause;
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

		sceneMap = new HashMap<String, Scene>();  // Store all the scenes

		// Create an instance of the game and switch to the gameplay scene
		playGameButton.setOnAction(e-> {
//			game = new PlaySlip();
			hasGameStarted = true;
			primaryStage.setScene(sceneMap.get("gameplay"));

		});

		// Switch to the menu scene
		menuButton1.setOnAction(e-> primaryStage.setScene(sceneMap.get("menu")));
		menuButton2.setOnAction(e-> primaryStage.setScene(sceneMap.get("menu")));

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
		displayRulesButton.setOnAction(e-> primaryStage.setScene(sceneMap.get("rules")));

		// Display the odds of winning in another screen
		checkOddsWinningButton.setOnAction(e-> primaryStage.setScene(sceneMap.get("oddsOfWinning")));

		// Look screen
		newLookButton.setOnAction(e-> primaryStage.setScene(sceneMap.get("look")));

		// Change the look of the intro screen and the gameplay screen w/ the default theme colors (green)
		defaultThemeButton.setOnAction(e-> {
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
		pinkThemeButton.setOnAction(e-> {
			sceneMap.put("intro", createIntroScene(pinkTheme));
			// TODO: need to change colors for the gameplay (but cannot restart)

			if (hasGameStarted) {
				primaryStage.setScene(sceneMap.get("gameplay"));
			} else {
				primaryStage.setScene(sceneMap.get("intro"));
			}
		});


		// Go to the previous screen after closing the menu screen that depend on whether the game have already started or not
		closeMenuButton.setOnAction(e-> {
			if (hasGameStarted) {
				primaryStage.setScene(sceneMap.get("gameplay"));
			} else {
				primaryStage.setScene(sceneMap.get("intro"));
			}
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
		primaryStage.setScene(sceneMap.get("oddsOfWinning"));
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
		pane.setStyle("-fx-background-color: #FF8787");  // Temp
		return new Scene(pane, 850, 750);
	}

	public Scene createMenuScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #7896D7");

		Text menuTitle = new Text("MENU");
		menuTitle.setStyle("-fx-font: bold 100 serif;");
		menuTitle.setUnderline(true);

		// Close the application window
		Button exitButton = new Button("Exit Button");
		exitButton.setOnAction(e-> Platform.exit());

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

		Text endTitle = new Text("Want to Play Again?");
		endTitle.setStyle("-fx-font: bold 75 serif;");

		// Close the application window
		Button exitGameButton = new Button("Exit The Game");
		exitGameButton.setOnAction(e-> Platform.exit());

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

	public void applyNewLook(String[]themeStyle) {
	}

}

