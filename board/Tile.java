package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;

public abstract class Tile {
	
	
	//Sets up the coordinate of the tile
	protected final int tileCoordinate;
	
	//Creates a map containing an index and an emptytile object.
	private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();
	
	//Function to create the map
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		
		for(int i = 0; i < 64; i++)
			emptyTileMap.put(i,new EmptyTile(i));
		
		return emptyTileMap;
	}
	
	//Returns an occupied tile (with the tile coordinate and piece) or an empty tile based on the tile coordinate.
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece!= null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES.get(tileCoordinate);
	}

	public Tile(int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}

	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	//Class of an empty tile.
	public static final class EmptyTile extends Tile {
		EmptyTile(final int coordinate) {
			super(coordinate);
		}
		
		@Override
		public String toString() {
			return "-";
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	
	
	//Class of an occupied tile.
	public static final class OccupiedTile extends Tile {
		
		private final Piece pieceOnTile;
		
		OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
				getPiece().toString();
		}
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
			
		}
	}
	
}
