package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.alliance.Alliance;
import com.chess.engine.board.AttackMove;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.MajorMove;
import com.chess.engine.board.Move;

public class Pawn extends Piece {

	//incremental moves by bishop assuming all can be done
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {8, 16, 7, 9};
	
	public Pawn(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance,PieceType.PAWN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		// TODO Auto-generated method stub
		
		List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
		
			//If the candidateDestinationCoordinate not within 0 - 63, then skip the currentCandidateOffset
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			//Add the move if the tile in "front" of the piece is not occupied
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				//todo more
				legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
			
			//If it is the pawn's first move and it is located in its original position (isnt this redundant though?)
			} else if (currentCandidateOffset == 16 && this.isFirstMove() && 
					((BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) ||
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite()))) {
				
				//the tile 2 tiles in front of the pawn is not occupied 
				//and the tile in front of the pawn is not occupied
				//add to MajorMove
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
			
			//Check if the upper right position is available
			} else if(currentCandidateOffset == 7 &&
					!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
			
				//Check if there is a piece on its upper right
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					
					//Check if that piece on its upper right is an enemy
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						//
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						
					}
				}
	
			//Same with the earlier code but on its upper left
			} else if(currentCandidateOffset == 9 &&
					!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()) ||
					(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()))) {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						//
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						
					}
				
				
				
				}
			}	
		}
	return legalMoves;
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.PAWN.toString();
	}
}
