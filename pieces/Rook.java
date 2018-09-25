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

public class Rook extends Piece {

	final static int[] CANDIDATE_MOVES_VECTOR_COORDINATES = {8, -8, 1, -1};
	
	public Rook(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance, PieceType.ROOK);
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		List<Move> legalMoves = new ArrayList<>();
		
		for (final int candidateCoordinateOffset: CANDIDATE_MOVES_VECTOR_COORDINATES) {
			
			int candidateDestinationCoordinate = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
						isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}
				
				candidateDestinationCoordinate += candidateCoordinateOffset;
				
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					
					Tile candidateCoordinateTile = board.getTile(candidateDestinationCoordinate);
					
					if(!candidateCoordinateTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					} else {
						Piece pieceAtDestination = candidateCoordinateTile.getPiece();
						Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
						if(this.pieceAlliance != pieceAlliance) {
							legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
						}
						break;
					}
				}
				
			
			}
					
			
		}
		
		return legalMoves;
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.ROOK.toString();
	}
	
	private static boolean isFirstColumnExclusion(int currentPosition, int candidateCoordinateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && candidateCoordinateOffset == -1;
	}
	
	private static boolean isEighthColumnExclusion(int currentPosition, int candidateCoordinateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && candidateCoordinateOffset == 1;
	}

}
