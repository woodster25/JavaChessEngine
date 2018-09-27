package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
	
	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	public Move(Board board, Piece movedPiece, int destinationCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}

	public abstract Board execute();

	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
}



