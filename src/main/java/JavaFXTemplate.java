import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

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
	private TextField displayDrawWinng;

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

		sceneMap = new HashMap<String, Scene>();  // Store all the scenes

		// Create an instance of the game and switch to the gameplay scene
		playGameButton.setOnAction(e-> {
			game = new PlaySlip();
			primaryStage.setScene(sceneMap.get("gameplay"));

		});

		// Switch to the menu scene
		menuButton.setOnAction(e-> {
			primaryStage.setScene(sceneMap.get("menu"));
		});


		// Put scenes in a hashmap
		sceneMap.put("intro", createIntroScene());
		sceneMap.put("gameplay", createGameScene());
		sceneMap.put("menu", createMenuScreen());

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
		return new Scene(pane, 850, 750);
	}

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
		pane.setStyle("-fx-background-color: #7896D7");  // temp
		return new Scene(pane, 850, 750);
	}

	public Scene createOddsOfWinningScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #7896D7");  // temp
		return new Scene(pane, 850, 750);
	}

	public Scene createPlayOrExitScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #7896D7");  // temp
		return new Scene(pane, 850, 750);
	}

	public Scene createLookScreen() {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #7896D7");  // temp
		return new Scene(pane, 850, 750);
	}

	public void applyNewLook(ArrayList<String> themeStyle) {}

}

