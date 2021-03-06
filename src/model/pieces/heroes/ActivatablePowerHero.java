package model.pieces.heroes;

import java.awt.Point;

import exceptions.InvalidPowerUseException;
import exceptions.PowerAlreadyUsedException;
import exceptions.WrongTurnException;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;

public abstract class ActivatablePowerHero extends Hero {

	private boolean powerUsed;

	public ActivatablePowerHero(Player player, Game game, String name, String type) {
		super(player, game, name, type);
	}

	public String getImageName() {
		return getType() + "P" + (isPowerUsed() ? "Used" : "NotUsed") + "p" + this.getOwner().getPlayerNum(getGame());
	}

	public void usePower(Direction d, Piece target, Point newPos) throws InvalidPowerUseException, WrongTurnException {

		if (getOwner() != getGame().getCurrentPlayer())
			throw new WrongTurnException(this);

		if (powerUsed)
			throw new PowerAlreadyUsedException(this);

	}

	public boolean isPowerUsed() {
		return powerUsed;
	}

	public void setPowerUsed(boolean powerUsed) {
		this.powerUsed = powerUsed;
	}
}
