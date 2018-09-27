package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Piece;

public class MajorMove extends Move {

	public MajorMove(Board board, Piece movedPiece, int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
		// TODO Auto-generated constructor stub
	}

	@Override
	//Create a new board
	public Board execute() {
		final Builder builder = new Builder();
		
		for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
			if(!this.movedPiece.equals(piece)) {
				//place all the non moved pieces onto the new board
				builder.setPiece(piece);
			}
		}
		//do the same for the opponent's pieces
		for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces() ) {
			builder.setPiece(piece);
		}
		//move the moved piece
		builder.setPiece(null);
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		return builder.build();
	}
	
}