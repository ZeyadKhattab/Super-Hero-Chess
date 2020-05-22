package view;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.pieces.Piece;
import model.pieces.heroes.Speedster;

public class Controller implements Initializable {

	@FXML
	private GridPane board;
	private Piece selected;
	private Game game;
	private ArrayList<Button> moveToButtons = new ArrayList();

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Game game = new Game();
		setGame(game);
		for (int i = 0; i < game.getBoardHeight(); i++)
			for (int j = 0; j < game.getBoardWidth(); j++) {
				Cell cell = game.getCellAt(i, j);
				Button button;

				FileInputStream input;
				try {
					input = new FileInputStream(cell.isEmpty() ? "images/empty.jpg" : "images/resize.jpg");
					Image image = new Image(input);
					ImageView imageView = new ImageView(image);
					button = new Button("", imageView);
					button.setId("" + (i * game.getBoardHeight() + j));
					button.setOnAction(event -> selectCell(event));
					board.add(button, j, i);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

	}

	private void selectCell(ActionEvent event) {
		if (selected != null) {
			int oldId = selected.getPosI() * game.getBoardHeight() + selected.getPosJ();
			System.out.println("old " + oldId);
			Button oldButton = (Button) board.lookup("#" + oldId);
			oldButton.setStyle(/* "-fx-background-color: blue;" */"");
		}
		Button btn = ((Button) event.getSource());
		int id = Integer.parseInt(btn.getId());
		System.out.println("new " + id);
		int row = id / game.getBoardHeight(), col = id % game.getBoardHeight();
		if (game.getCellAt(row, col).getPiece() == null || game.getCellAt(row, col).getPiece() == selected) {
			clear();
			return;
		}
		selected = game.getCellAt(row, col).getPiece();
		if (selected != null) {
			btn.setStyle("-fx-background-color: yellow;");
			highlightRelevantMovements();
		}

	}

	public void highlightRelevantMovements() {
		ArrayList<Direction> allowedMovements = selected.getAllowedDirections();
		if (selected instanceof Speedster) {

		} else {
			for (Direction dir : allowedMovements) {
				Point to = selected.getDirectionPos(new Point(selected.getPosI(), selected.getPosJ()), dir);
				int id = to.x * game.getBoardHeight() + to.y;
				Button newButton = (Button) board.lookup("#" + id);
				newButton.setStyle("-fx-background-color: green;");
				moveToButtons.add(newButton);
			}
		}

	}

	void clear() {
		for (Button btn : moveToButtons)
			btn.setStyle("");
		if (selected != null) {
			int oldId = selected.getPosI() * game.getBoardHeight() + selected.getPosJ();
			Button oldButton = (Button) board.lookup("#" + oldId);
			oldButton.setStyle("");
		}
		selected = null;
		moveToButtons.clear();

	}

}
