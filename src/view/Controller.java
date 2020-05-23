package view;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.pieces.Piece;
import model.pieces.heroes.Speedster;

public class Controller implements Initializable {

	@FXML
	private GridPane board;
	@FXML
	private Label player1Label, player2Label;
	private Piece selected;
	private Game game;
	private int[][] boardState;

	static final int moveTo = 1;
	static final int attack = 2;
	static final int SELECTED = 3;

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Game game = new Game();
		setGame(game);
		player1Label.setText(game.getPlayer1().getName());
		player2Label.setText(game.getPlayer2().getName());
		drawBoard();
		boardState = new int[game.getBoardHeight()][game.getBoardWidth()];

	}

	public void drawBoard() {
		for (int i = 0; i < game.getBoardHeight(); i++)
			for (int j = 0; j < game.getBoardWidth(); j++) {
				Cell cell = game.getCellAt(i, j);
				Button button;

				FileInputStream input;
				try {
					input = new FileInputStream(
							cell.isEmpty() ? "images/empty.jpg" : "images/" + cell.getPiece().getImageName() + ".jpg");
					Image image = new Image(input);
					ImageView imageView = new ImageView(image);
					button = new Button("", imageView);
					button.setId("" + (i * game.getBoardWidth() + j));
					button.setOnAction(event -> {
						try {
							selectCell(event);
						} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					board.add(button, j, i);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		Label label = game.getCurrentPlayer() == game.getPlayer1() ? player1Label : player2Label;

		label.setStyle(
				"-fx-background-color: darkslateblue; -fx-text-fill: white;fx-alignment:center; -fx-padding:10;");
		if (player1Label != label)
			player1Label.setStyle("");
		else
			player2Label.setStyle("");
	}

	private void selectCell(ActionEvent event)
			throws UnallowedMovementException, OccupiedCellException, WrongTurnException {
		Button btn = ((Button) event.getSource());
		int[] rc = getRowCol(btn);
		int row = rc[0], col = rc[1];
		if (boardState[row][col] == moveTo || boardState[row][col] == attack) {
			selected.move(new Point(row, col));

			refresh();
			return;
		}
		Piece targetPiece = game.getCellAt(row, col).getPiece();
		if (targetPiece == null || targetPiece.getOwner() != game.getCurrentPlayer()) {
			clear();
			return;
		}
		clear();

		selected = game.getCellAt(row, col).getPiece();
		boardState[row][col] = SELECTED;
		btn.setStyle("-fx-background-color: yellow;");
		highlightRelevantMovements();

	}

	private void refresh() {

		clear();
		board.getChildren().clear();
		drawBoard();

	}

	int[] getRowCol(Button btn) {
		int id = Integer.parseInt(btn.getId());
		int row = id / game.getBoardWidth(), col = id % game.getBoardWidth();
		return new int[] { row, col };
	}

	public void highlightRelevantMovements() {
		ArrayList<Direction> allowedMovements = selected.getAllowedDirections();

		for (Direction dir : allowedMovements) {
			Point to = selected.getDirectionPos(new Point(selected.getPosI(), selected.getPosJ()), dir);
			Piece targetPiece = game.getCellAt(to.x, to.y).getPiece();
			if (selected instanceof Speedster)
				to = selected.getDirectionPos(to, dir);
			int id = to.x * game.getBoardWidth() + to.y;
			Button newButton = (Button) board.lookup("#" + id);
			if (targetPiece == null) {
				newButton.setStyle("-fx-background-color: green;");
				boardState[to.x][to.y] = moveTo;
			} else if (targetPiece.getOwner() != selected.getOwner()) {
				newButton.setStyle("-fx-background-color: red;");
				boardState[to.x][to.y] = attack;
			}
		}

	}

	void clear() {
		selected = null;
		for (int i = 0, id = 0; i < boardState.length; i++)
			for (int j = 0; j < boardState[i].length; j++, id++) {
				boardState[i][j] = 0;
				board.lookup("#" + id).setStyle("");
			}

	}

}
