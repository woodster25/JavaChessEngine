package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public class AttackMove extends Move {
	final Piece attackedPiece;

	public AttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
		super(board, movedPiece, destinationCoordinate);
		this.attackedPiece = attackedPiece;
	}

	@Override
	public Board execute() {
		// TODO Auto-generated method stub
		return null;
	}
}