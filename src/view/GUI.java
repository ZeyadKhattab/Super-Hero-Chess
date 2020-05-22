package view;

import java.io.FileInputStream;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.game.Cell;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;

public class GUI extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("GridPane Experiment");
		String player1Name = /* JOptionPane.showInputDialog("Player 1 Name") */"zoz",
				player2Name = /* JOptionPane.showInputDialog("Player 2 Name") */"bro";
		Game game = new Game(new Player(player1Name), new Player(player2Name));
		BorderPane pane = new BorderPane();
		Board board = new Board(game);
		pane.setCenter(board.getBoard());
		Scene scene = new Scene(pane, game.getBoardWidth() * 140, game.getBoardHeight() * 110);
		primaryStage.setScene(scene);
		primaryStage.show();

		Button attack = new Button("attack");
		Button usePower = new Button("Use power");
		FlowPane buttons = new FlowPane();

		buttons.getChildren().add(attack);
		buttons.getChildren().add(usePower);
		pane.setTop(buttons);

		attack.setOnAction(value -> {
			Piece selected = board.getSelected();
			if (selected == null)
				board.nullifySelected();
			else {
//				System.out.println(selected);
				String dir = JOptionPane.showInputDialog("Enter direction");

			}

		});

		usePower.setOnAction(value -> {
			Piece selected = board.getSelected();
			if (selected == null)
				board.nullifySelected();
			else {
				System.out.println(selected);
			}
		});

	}

}
