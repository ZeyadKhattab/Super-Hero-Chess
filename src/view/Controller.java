package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.game.Cell;
import model.game.Game;

public class Controller implements Initializable {

	@FXML
	private GridPane board;
	private Cell selected;
	private Game game;

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

		int id = Integer.parseInt(((Button) event.getSource()).getId());
		int row = id / game.getBoardHeight(), col = id % game.getBoardHeight();
		selected = game.getCellAt(row, col);

	}

}
