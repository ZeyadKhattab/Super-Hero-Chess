package model.pieces.heroes;

import model.game.Game;
import model.game.Player;
import model.pieces.Piece;

public abstract class Hero extends Piece {

	public Hero(Player player, Game game, String name,String type) {
		super(player, game, name,type);
	}

}
