package model.game;

import java.util.ArrayList;
import java.util.Stack;

import model.pieces.Piece;

public class Player {

	private String name;
	private int payloadPos;
	private int sideKilled;
	private ArrayList<Piece> deadCharacters;
	private Stack<Piece>[] deadCharacters2;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Player(String name) {
		this.name = name;
		this.deadCharacters = new ArrayList<Piece>();
		deadCharacters2 = new Stack[7];
		for (int i = 0; i < 7; i++) // {"Super","Ranged","Medic","Armored","Speedster","Tech","Sidekick"}
			deadCharacters2[i] = new Stack();
	}

	public String getName() {
		return name;
	}

	public int getPayloadPos() {
		return payloadPos;
	}

	public void setPayloadPos(int payloadPos) {
		this.payloadPos = payloadPos;
	}

	public int getSideKilled() {
		return sideKilled;
	}

	public void setSideKilled(int sideKilled) {
		this.sideKilled = sideKilled;
	}

	public ArrayList<Piece> getDeadCharacters() {
		return deadCharacters;
	}

	public Stack<Piece>[] getDeadCharacters2() {
		return deadCharacters2;
	}

	public int getPlayerNum(Game game) {
		return this == game.getPlayer1() ? 1 : 2;
	}

}
