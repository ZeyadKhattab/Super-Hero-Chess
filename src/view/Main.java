package view;

import javax.swing.JOptionPane;

import model.game.Game;
import model.game.Player;

public class Main {

	public static void main(String[] args) {
		String player1Name=JOptionPane.showInputDialog("Player 1 Name"),player2Name=JOptionPane.showInputDialog("Player 2 Name");
		Game game=new Game(new Player(player1Name),new Player(player2Name));

	}

}
