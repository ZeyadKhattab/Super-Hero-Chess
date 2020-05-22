package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.game.Cell;
import model.game.Game;
import model.pieces.Piece;

public class Board {
	private GridPane board;
	private Piece selected;

	Board(Game game) throws FileNotFoundException {
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
					int id = Integer.parseInt(((Button) value.getSource()).getId());
					int row = id / game.getBoardHeight(), col = id % game.getBoardHeight();
					selected = game.getCellAt(row, col).getPiece();
				});
				gridPane.add(button, j, i);
			}
		this.board = gridPane;
	}

	public GridPane getBoard() {
		return board;
	}

	public Piece getSelected() {
		return selected;
	}
	void nullifySelected() {
		selected=null;
	}

}
