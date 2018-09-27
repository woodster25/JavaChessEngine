package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.alliance.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

public abstract class Player {

	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isInCheck;

	Player(final Board board,
			final Collection<Move> legalMoves,
			final Collection<Move> opponentMoves) {
		
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = legalMoves;
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
	}
	
	
	//Does enemy moves overlap with King's position
	private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentMoves) {
		final List<Move> attackMoves = new ArrayList<>();
		for(final Move move : opponentMoves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		return attackMoves;
	}

	private King establishKing() {
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King) piece;
			}
		}
		throw new RuntimeException("Should not reach here. Not a valid board!");
	}
	
	public King getPlayerKing() {
		return this.playerKing;
	}
	
	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	public boolean isInCheck() {
		return this.isInCheck;
	}
	
	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	
	private boolean hasEscapeMoves() {
		
		for(final Move move : this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}


	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isCastled() {
		return false;
	}

	public MoveTransition makeMove(final Move move) {
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute();
		
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), 
				transitionBoard.currentPlayer().getLegalMoves());
	
		//You cannot make a move that exposes your king to check
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	
	private Collection<Move> getLegalMoves() {
		// TODO Auto-generated method stub
		return null;
	}


	public abstract Collection<Piece> getActivePieces();
	
	public abstract Alliance getAlliance();
	
	public abstract Player getOpponent();
}
