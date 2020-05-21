package view;

import java.io.FileInputStream;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.game.Cell;
import model.game.Game;
import model.game.Player;

public class Main extends Application {

	public static void main(String[] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("GridPane Experiment");
		String player1Name = /* JOptionPane.showInputDialog("Player 1 Name") */"zoz",
				player2Name = /* JOptionPane.showInputDialog("Player 2 Name") */"bro";
		Game game = new Game(new Player(player1Name), new Player(player2Name));

		GridPane gridPane = new GridPane();
		for (int i = 0; i < game.getBoardHeight(); i++)
			for (int j = 0; j < game.getBoardWidth(); j++) {
				Cell cell = game.getCellAt(i, j);
				Button button;

				FileInputStream input = new FileInputStream(cell.isEmpty() ? "images/empty.jpg" : "images/resize.jpg");
				Image image = new Image(input);
				ImageView imageView = new ImageView(image);
				button = new Button("", imageView);

				button.setId("" + (i * game.getBoardHeight() + j));
				button.setOnAction(value -> {
					System.out.println(((Button) value.getSource()).getId());
				});
				gridPane.add(button, j, i);
			}

		Scene scene = new Scene(gridPane, game.getBoardWidth() * 100, game.getBoardHeight() * 80);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
