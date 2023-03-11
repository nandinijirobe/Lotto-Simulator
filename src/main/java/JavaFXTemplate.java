import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Text;

import java.util.ArrayList;


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


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to JavaFX");

//	     Scene scene = new Scene(root, 700,700);
//			primaryStage.setScene(scene);
//			primaryStage.show();
	}

	public void introScene() {}

	public void gameScene() {}

	public void menuScreen() {}

	public void rulesScreen() {}

	public void oddsOfWinningScreen() {}

	public void playOrExitScreen() {}

	public void applyNewLook(ArrayList<String> themeStyle) {}

}

