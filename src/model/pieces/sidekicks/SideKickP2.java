package model.pieces.sidekicks;

import java.util.ArrayList;

import exceptions.UnallowedMovementException;
import model.game.Direction;
import model.game.Game;

public class SideKickP2 extends SideKick {

	public SideKickP2(Game game, String name) {
		super(game.getPlayer2(), game, name);
	}

	@Override
	public void moveUp() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.UP);
	}

	@Override
	public void moveUpRight() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.UPRIGHT);
	}

	@Override
	public void moveUpLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.UPLEFT);
	}

	@Override
	public String getImageName() {
		return this.getType() + "p2";
	}

	@Override
	public ArrayList<Direction> getAllowedDirections() {
		ArrayList<Direction> ans = new ArrayList();
		ans.add(Direction.DOWN);
		ans.add(Direction.RIGHT);
		ans.add(Direction.LEFT);
		ans.add(Direction.DOWNLEFT);
		ans.add(Direction.DOWNRIGHT);
		return ans;
	}
}
