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

public class Knight extends Piece {
	
	
	//Available moves by the Knight Piece assuming all can be done
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

	
	//Constructs the Knight Piece given the its initial position and color
	public Knight(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance, PieceType.KNIGHT);
	}

	//Returns a list of moves that can be done by the Knight piece given constraints. 
	//Constraints are the boundary of the board and whether or not there is an ally piece on its destination.
	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		int candidateDestinationCoordinate;
		final List<Move> legalMoves = new ArrayList<>();
		
		//Get the coordinates of the candidate destinations of the Knight piece given its current position.
		for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES) {
			candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			//If the tile destination is within the possible position range (0 - 63)
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)   ||
				   isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)  ||
				   isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
				   isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					
					continue;
					
				}
				
				//Get the tile where the piece is calculated to go to
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				
				//If the tile destination is not occupied
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					
				//If the tile destination is occupied
				} else {
					
					//Gets the Piece object found on the destination and its alliance/color.
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					
					//Check whether or not the other piece occupying the tile destination is an ally. 
					if(this.pieceAlliance != pieceAlliance) {
						//If the piece occupying the candidate Tile is an enemy, add it to its possible move list.
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}
		}
		return legalMoves;
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.KNIGHT.toString();
	}

	public static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10
				|| candidateOffset == 6 || candidateOffset == 15);
	}
	
	public static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
	}
	
	public static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	
	public static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6
				|| candidateOffset == 10 || candidateOffset == 17);
	}
	
}
