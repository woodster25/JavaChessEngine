package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.alliance.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

public class Board {
	
	private final List<Tile> gameBoard;
	private final Collection<Piece> whitePieces;
	private final Collection<Piece> blackPieces;
	
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	
	private final Player currentPlayer;
	
	
	private Board(Builder builder) {
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
	
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		
		this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);

		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
	}
	
	//
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			
			//creates a variable tileText to represent the piece in the tile
			final String tileText = this.gameBoard.get(i).toString();
			
			//appends the string with 3 spaces
			builder.append(String.format("%3s", tileText));
			//if the the row has been appended 8 times, append a new line space
			if((i+1) % BoardUtils.NUM_TILES_PER_ROW == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	//calculates all the legal moves of all pieces
	private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final Piece piece : pieces) {
			
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		return legalMoves;
	}


	//returns the number of pieces that occupy tiles within the game board
	private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard, Alliance alliance) {
		final List<Piece> activePieces = new ArrayList<>();
		for(final Tile tile : gameBoard) {
			if(tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if(piece.getPieceAlliance() == alliance) {
					activePieces.add(piece);
				}
			}
		}
		return activePieces;
	}

	//crates an array of tiles
	//createTile returns an OccupiedTile object when the specified coordinate is not null
	//returns an emptyTile object when it is null
	private static List<Tile> createGameBoard(Builder builder) {
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		List<Tile> tiles1 = Arrays.asList(tiles);
	return tiles1;
	}
	
	public static Board createStandardBoard() {
		//Instantiates the builder class
		final Builder builder = new Builder();
		
		//set the pieces in their right place within the board
		//Black Layout
		builder.setPiece(new Rook(0, Alliance.BLACK));
		builder.setPiece(new Knight(1, Alliance.BLACK));
		builder.setPiece(new Bishop(2, Alliance.BLACK));
		builder.setPiece(new Queen(3, Alliance.BLACK));
		builder.setPiece(new King(4, Alliance.BLACK));
		builder.setPiece(new Bishop(5, Alliance.BLACK));
		builder.setPiece(new Knight(6, Alliance.BLACK));
		builder.setPiece(new Rook(7, Alliance.BLACK));
		builder.setPiece(new Pawn(8, Alliance.BLACK));
		builder.setPiece(new Pawn(9, Alliance.BLACK));
		builder.setPiece(new Pawn(10, Alliance.BLACK));
		builder.setPiece(new Pawn(11, Alliance.BLACK));
		builder.setPiece(new Pawn(12, Alliance.BLACK));
		builder.setPiece(new Pawn(13, Alliance.BLACK))	;
		builder.setPiece(new Pawn(14, Alliance.BLACK));
		builder.setPiece(new Pawn(15, Alliance.BLACK));
		
		//White Layout
		builder.setPiece(new Rook(63, Alliance.WHITE));
		builder.setPiece(new Knight(62, Alliance.WHITE));
		builder.setPiece(new Bishop(61, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE));
		builder.setPiece(new Queen(59, Alliance.WHITE));
		builder.setPiece(new Bishop(58, Alliance.WHITE));
		builder.setPiece(new Knight(57, Alliance.WHITE));
		builder.setPiece(new Rook(56, Alliance.WHITE));
		builder.setPiece(new Pawn(55, Alliance.WHITE));
		builder.setPiece(new Pawn(54, Alliance.WHITE));
		builder.setPiece(new Pawn(53, Alliance.WHITE));
		builder.setPiece(new Pawn(52, Alliance.WHITE));
		builder.setPiece(new Pawn(51, Alliance.WHITE));
		builder.setPiece(new Pawn(50, Alliance.WHITE));
		builder.setPiece(new Pawn(49, Alliance.WHITE));
		builder.setPiece(new Pawn(48, Alliance.WHITE));
				
		//Uses the build method of builder to finalize the board
		return builder.build();
	}

	//Returns the tile that is located at the specified coordinate
	public Tile getTile(final int tileCoordinate) {
		return gameBoard.get(tileCoordinate);
	}
	
	
	public static class Builder {
		
		Map<Integer,Piece> boardConfig;
		Alliance nextMoveMaker;
		
		//Constructor for builder
		public Builder() {
			this.boardConfig = new HashMap<>();
		}
		
		//Uses the map to set the piece on the right coordinate and returns the changed board
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		//Sets the moves and returns the board
		public Builder setMoveMaker(final Alliance nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		//build/finalize the board;
		public Board build() {
			return new Board(this);
		}
	}


	public Collection<Piece> getBlackPieces() {
		return this.blackPieces;
	}
	
	public Collection<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	public Player blackPlayer() {
		return this.blackPlayer;
	}
	
	public Player whitePlayer() {
		return this.whitePlayer;
	}

	public Player currentPlayer() {
		return this.currentPlayer;
	}
}
