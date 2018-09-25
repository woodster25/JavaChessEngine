package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.alliance.Alliance;
import com.chess.engine.board.AttackMove;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.MajorMove;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class King extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9, 8, -8, 1, -1};

	public King(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance, PieceType.KING);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		// TODO Auto-generated method stub
		
		List<Move> legalMoves = new ArrayList<>();
		
		for(int candidateCoordinateOffset : CANDIDATE_MOVE_COORDINATES) {
			
			int candidateCoordinateDestination = this.piecePosition;
			
			if(BoardUtils.isValidTileCoordinate(candidateCoordinateDestination)) {
				
				if(isFirstColumnExclusion(candidateCoordinateDestination, candidateCoordinateOffset) || 
						isEighthColumnExclusion(candidateCoordinateDestination, candidateCoordinateOffset)) {
					continue;
				}
				
				candidateCoordinateDestination += candidateCoordinateOffset;
				
				if(BoardUtils.isValidTileCoordinate(candidateCoordinateDestination)) {
					Tile candidateDestinationTile = board.getTile(candidateCoordinateDestination);
					if(!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateCoordinateDestination));;
						
					} else {
						Piece pieceAtDestination = candidateDestinationTile.getPiece();
						Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						if(this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new AttackMove(board, this, candidateCoordinateDestination, pieceAtDestination));
						}
					}
				}
			}
			
		}
		return legalMoves;
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.KING.toString();
	}
	
	static boolean isFirstColumnExclusion(int currentPosition, int candidateCoordinateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateCoordinateOffset == -9 ||
				candidateCoordinateOffset == -1 || candidateCoordinateOffset == 7);
	}
	
	static boolean isEighthColumnExclusion(int currentPosition, int candidateCoordinateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateCoordinateOffset == -7 ||
				candidateCoordinateOffset == 1 || candidateCoordinateOffset == 9);
	}
}
