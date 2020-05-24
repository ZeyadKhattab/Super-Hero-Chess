package view;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.*;

public class Controller implements Initializable {

	@FXML
	private GridPane board, graveYard;
	@FXML
	private Label player1Label, player2Label;
	@FXML
	private ImageView player1PayLoad, player2PayLoad;
	private Piece selected;
	private Game game;
	private int[][] boardState;
	private Direction[][] powerDirection;
	static final int moveTo = 1;
	static final int attack = 2;
	static final int SELECTED = 3;
	static final int power = 4;
	private Piece revive;
	private int[] teleportTo;

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String player1Name = JOptionPane.showInputDialog("Player 1 Name"),
				player2Name = JOptionPane.showInputDialog("Player 2 Name");
		Game game = new Game(new Player(player1Name), new Player(player2Name));
		setGame(game);
		player1Label.setText(game.getPlayer1().getName());
		player2Label.setText(game.getPlayer2().getName());
		drawBoard();
		boardState = new int[game.getBoardHeight()][game.getBoardWidth()];
		board.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.P) {
				try {
					handlePowerUse();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		powerDirection = new Direction[game.getBoardHeight()][game.getBoardWidth()];

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
						} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException
								| InvalidPowerUseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});

					if (cell.getPiece() != null)
						button.setTooltip(new Tooltip(cell.getPiece().toString()));
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
		try {
			// player 1
			Image img = new Image(new FileInputStream(game.getPlayer1().getPayloadPos() + ".png"));
			player1PayLoad.setImage(img);
			// player 2
			img = new Image(new FileInputStream(game.getPlayer2().getPayloadPos() + ".png"));
			player2PayLoad.setImage(img);

		} catch (Exception e) {
			System.out.println(e);

		}
	}

	private void selectCell(ActionEvent event)
			throws UnallowedMovementException, OccupiedCellException, WrongTurnException, InvalidPowerUseException {
		Button btn = ((Button) event.getSource());
		int[] rc = getRowCol(btn);
		int row = rc[0], col = rc[1];
		if (boardState[row][col] == moveTo || boardState[row][col] == attack) {
			selected.move(new Point(row, col));

			refresh();
			return;
		}
		if (boardState[row][col] == power) {
			if (selected instanceof Medic && revive == null) {
				return;
			}
			if (selected instanceof Tech) {
				Piece p = game.getCellAt(row, col).getPiece();
				if (p != null) {
					((Tech) selected).usePower(null, p,
							teleportTo == null ? null : new Point(teleportTo[0], teleportTo[1]));
				} else {
					teleportTo = rc;
					for (int i = 0, id = 0; i < game.getBoardHeight(); i++)
						for (int j = 0; j < game.getBoardWidth(); j++, id++) {
							Piece curr = game.getCellAt(i, j).getPiece();
							if (curr != null && curr.getOwner() == selected.getOwner() && curr != selected) {
								board.lookup("#" + id).setStyle("-fx-background-color:green;");
								boardState[i][j] = power;
							} else if (curr != selected) {
								board.lookup("#" + id).setStyle("");
								boardState[i][j] = 0;
							}
							if(i==rc[0] && j==rc[1])
								board.lookup("#" + id).setStyle("-fx-background-color:red;");

						}
					return;
				}

			} else
				((ActivatablePowerHero) selected).usePower(powerDirection[row][col], revive, null); // maybe a medic
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
		if (game.getWinner() != null) {
			board.getChildren().clear();
			Label winnerLabel = new Label(game.getWinner().getName() + " is the Winner");
			winnerLabel.setStyle("-fx-font-size:15");
			winnerLabel.getStyleClass().add("lbl-success");
			board.add(winnerLabel, 3, 3);
		} else
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
		revive = null;
		teleportTo = null;
		for (int i = 0, id = 0; i < boardState.length; i++)
			for (int j = 0; j < boardState[i].length; j++, id++) {
				boardState[i][j] = 0;
				board.lookup("#" + id).setStyle("");
			}
		graveYard.getChildren().clear();

	}

	public void handlePowerUse(Ranged curr) {
		ArrayList<Direction> directions = selected.getOrthogonalDirections();
		for (Direction d : directions) {
			Piece hit = null;
			Direction chosenDir = null;
			if (d == Direction.RIGHT) {
				for (int j = selected.getPosJ() + 1; j < game.getBoardWidth(); j++) {
					hit = getGame().getCellAt(curr.getPosI(), j).getPiece();
					chosenDir = d;
					if (hit != null) {
						chosenDir = d;

						break;
					}
				}
			} else if (d == Direction.LEFT) {
				for (int j = curr.getPosJ() - 1; j >= 0; j--) {
					hit = getGame().getCellAt(curr.getPosI(), j).getPiece();

					if (hit != null) {
						chosenDir = d;
						break;
					}
				}
			} else if (d == Direction.UP) {
				for (int i = curr.getPosI() - 1; i >= 0; i--) {
					hit = getGame().getCellAt(i, curr.getPosJ()).getPiece();
					if (hit != null) {
						chosenDir = d;
						break;
					}
				}
			} else if (d == Direction.DOWN) {
				for (int i = curr.getPosI() + 1; i < getGame().getBoardHeight(); i++) {
					hit = getGame().getCellAt(i, curr.getPosJ()).getPiece();
					if (hit != null) {
						chosenDir = d;
						break;
					}
				}
			}
			if (hit != null && hit.getOwner() != curr.getOwner()) {
				int row = hit.getPosI(), col = hit.getPosJ();
				boardState[row][col] = power;
				powerDirection[row][col] = chosenDir;
			}

		}
	}

	private void handlePowerUse(Super hero) {
		ArrayList<Direction> directions = hero.getOrthogonalDirections();
		for (Direction dir : directions) {
			Point p1 = hero.getDirectionPos(new Point(hero.getPosI(), hero.getPosJ()), dir);
			Point p2 = hero.getDirectionPos(p1, dir);
			Piece target;
			target = game.getCellAt(p1.x, p1.y).getPiece();
			if (target != null && target.getOwner() != hero.getOwner()) {
				boardState[p1.x][p1.y] = power;
				powerDirection[p1.x][p1.y] = dir;
			}
			target = game.getCellAt(p2.x, p2.y).getPiece();
			if (target != null && target.getOwner() != hero.getOwner()) {
				boardState[p2.x][p2.y] = power;
				powerDirection[p2.x][p2.y] = dir;
			}

		}

	}

	public Button getButton(Piece p) throws FileNotFoundException {
		FileInputStream input = new FileInputStream("images/" + p.getImageName() + ".jpg");
		Image image = new Image(input);
		ImageView imageView = new ImageView(image);
		Button button = new Button("", imageView);
		return button;
	}

	private void handlePowerUse(Medic hero) throws FileNotFoundException {
		if (hero.getOwner().getDeadCharacters().size() == 0)
			return;
		ArrayList<Direction> directions = hero.getAllDirections();
		for (Direction dir : directions) {
			Point p = hero.getDirectionPos(new Point(hero.getPosI(), hero.getPosJ()), dir);
			if (game.getCellAt(p.x, p.y).getPiece() == null) {
				boardState[p.x][p.y] = power;
				powerDirection[p.x][p.y] = dir;
			}
		}
		ArrayList<Piece> dead = hero.getOwner().getDeadCharacters();
		int row = 0, col = 0;
		for (int i = 0; i < dead.size(); i++) {
			Piece piece = dead.get(i);
			Button btn = getButton(piece);

			btn.setId("" + i);
			btn.setOnAction(event -> {
				revive = dead.get(Integer.parseInt(btn.getId()));
			});
			graveYard.add(btn, col, row);
			col++;
			if (col == 3) {
				col = 0;
				row++;
			}
		}

	}

	public void handlePowerUse() throws FileNotFoundException {
		if (selected == null || selected.getOwner() != game.getCurrentPlayer()
				|| !(selected instanceof ActivatablePowerHero))
			return;
		ActivatablePowerHero curr = (ActivatablePowerHero) selected;
		if (curr.isPowerUsed())
			return;
		Piece tmp = selected;
		clear();
		selected = tmp;

		if (curr instanceof Ranged) {
			handlePowerUse((Ranged) curr);
		}
		if (curr instanceof Super) {
			handlePowerUse((Super) curr);

		}
		if (curr instanceof Medic)
			handlePowerUse((Medic) curr);
		if (curr instanceof Tech)
			handlePowerUse((Tech) curr);
		if (!(curr instanceof Tech))
			colorPowerCells();
	}

	private void handlePowerUse(Tech hero) {
		for (int i = 0, id = 0; i < game.getBoardHeight(); i++)
			for (int j = 0; j < game.getBoardWidth(); j++, id++) {
				Piece curr = game.getCellAt(i, j).getPiece();
				if (curr == null || (curr.getOwner() == hero.getOwner() && curr instanceof ActivatablePowerHero
						&& ((ActivatablePowerHero) curr).isPowerUsed())) {
					// restore or move
					Button btn = (Button) board.lookup("#" + id);
					btn.setStyle("-fx-background-color:green");
					boardState[i][j] = power;
				} else if (curr != null && curr.getOwner() != hero.getOwner() && curr instanceof ActivatablePowerHero
						&& !((ActivatablePowerHero) curr).isPowerUsed()) {
					// hack
					Button btn = (Button) board.lookup("#" + id);
					btn.setStyle("-fx-background-color:red");
					boardState[i][j] = power;
				}

			}

	}

	void colorPowerCells() {
		for (int i = 0, id = 0; i < game.getBoardHeight(); i++)
			for (int j = 0; j < game.getBoardWidth(); j++, id++)
				if (boardState[i][j] == power) {
					Button btn = (Button) board.lookup("#" + id);
					btn.setStyle("-fx-background-color: red;");
				}
	}

	private Game getGame() {
		// TODO Auto-generated method stub
		return game;
	}

}
