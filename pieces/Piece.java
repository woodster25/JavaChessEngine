package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.alliance.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {
	
	
	protected final PieceType pieceType;
	//piecePosition is the current position of the piece.
	//possible piecePosition ranges from 0 - 63.
	protected final int piecePosition;
	
	//pieceAlliance is the alliance the piece belongs to.
	//Check whether or not it is white or black or ally or enemy.
	protected final Alliance pieceAlliance;
	
	protected final boolean isFirstMove;
	
	public int getPiecePosition() {
		return piecePosition;
	}

	public boolean isFirstMove() {
		return isFirstMove;
	}
	public Alliance getPieceAlliance() {
		return pieceAlliance;
	}

	public Piece(final int piecePosition, final Alliance pieceAlliance, final PieceType pieceType) {
		this.pieceType = pieceType;
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.isFirstMove = false;
	}
	
	public PieceType getPieceType() {
		return pieceType;
	}

	public abstract List<Move> calculateLegalMoves(final Board board);
	
	public enum PieceType {
		
		PAWN("P") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		QUEEN("Q") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				// TODO Auto-generated method stub
				return true;
			}
		};
		
		private String pieceName;
		
		PieceType(String pieceName) {
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName;
		}

		public abstract boolean isKing();
	}
	
	
	
	
}
