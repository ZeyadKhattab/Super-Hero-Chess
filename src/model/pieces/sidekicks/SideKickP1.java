package model.pieces.sidekicks;

import java.util.ArrayList;

import exceptions.UnallowedMovementException;
import model.game.Direction;
import model.game.Game;

public class SideKickP1 extends SideKick {

	public SideKickP1(Game game, String name) {
		super(game.getPlayer1(), game, name);
	}

	@Override
	public void moveDown() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.DOWN);
	}

	@Override
	public void moveDownRight() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.DOWNRIGHT);
	}

	@Override
	public void moveDownLeft() throws UnallowedMovementException {
		throw new UnallowedMovementException(this, Direction.DOWNLEFT);
	}

	@Override
	public String getImageName() {
		return this.getType()+"p1";
	}

	@Override
	public ArrayList<Direction> getAllowedDirections() {
		ArrayList<Direction>ans=new ArrayList();
		ans.add(Direction.UP);
		ans.add(Direction.RIGHT);
		ans.add(Direction.LEFT);
		ans.add(Direction.UPLEFT);
		ans.add(Direction.UPRIGHT);
		return ans;

	}
}
