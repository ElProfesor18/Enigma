package com.chess.engine.board;

import com.chess.engine.piece.Piece;

import java.util.*;  

// abstract => Class can not be instantiated.
public abstract class Tile {

	protected final int tileCoordinate;
	
	private static final Map <Integer , EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles()
	{
		final Map <Integer, EmptyTile> emptyTileMap = new HashMap<> ();
		
		for(int i=0; i<BoardUtils.NUM_TILES; i++)
		{
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
//		Part of Guava library.
//		return ImmutableMap.copyof(emptyTileMap);
		
		return Collections.unmodifiableMap(emptyTileMap);
//		return emptyTileMap;
	}
	
	public static Tile  createTile(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	}
	
	private Tile(final int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile {
		
		private EmptyTile(final int coordinate){
//			Calls constructor of the parent class.
			super(coordinate);
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		@Override
		public Piece getPiece() {
			return null;
		}
		
		@Override
		public String toString() {
			return "-";
		}
	}
	
	public static final class OccupiedTile extends Tile {
		
		private final Piece pieceOnTile;
		
		private OccupiedTile(int coordinate, final Piece pieceOnTile){
//			Calls constructor of the parent class.
			super(coordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public boolean isTileOccupied() {
			return true;
		}
		
		@Override
		public Piece getPiece() { 
			return this.pieceOnTile;
		}
		
		@Override
		public String toString() {
//			Black Pieces Show Up as LowerCase
//			White Pieces Show Up as UpperCase
			
			
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
				getPiece().toString().toUpperCase();
		}
	}
}


// Note: User can basically access only createTile function.
// We can create a new occupied tile or return a cached empty tile 
// from the EMPTY_TILES map.
