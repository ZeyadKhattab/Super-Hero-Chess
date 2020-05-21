package view;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import model.game.Game;
import model.game.Player;

public class Main extends Application{

	public static void main(String[] args) {
//		String player1Name=JOptionPane.showInputDialog("Player 1 Name"),player2Name=JOptionPane.showInputDialog("Player 2 Name");
//		Game game=new Game(new Player(player1Name),new Player(player2Name));
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("GridPane Experiment");

        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");
        Button button4 = new Button("Button 4");
        Button button5 = new Button("Button 5");
        Button button6 = new Button("Button 6");

        GridPane gridPane = new GridPane();

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        gridPane.add(button4, 0, 1, 1, 1);
        gridPane.add(button5, 1, 1, 1, 1);
        gridPane.add(button6, 2, 1, 1, 1);

        Scene scene = new Scene(gridPane, 240, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}

}
