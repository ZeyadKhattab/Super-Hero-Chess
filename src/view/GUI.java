package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.game.Game;
import model.game.Player;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GUI extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		Board board=new Board(new Game(new Player("zoz"),new Player("bro")));

	}

}
