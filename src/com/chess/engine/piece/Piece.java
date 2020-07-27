package com.chess.engine.piece;

import chess.com.engine.Alliance;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.*;

public abstract class Piece {
	
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
//	TODO More work here!
	protected final boolean isFirstMove = false;
//	White or Black
	
	Piece(final int piecePosition, final Alliance pieceAlliance)
	{
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
	}
	
	public int getPiecePosition() {
		return piecePosition;
	}
	
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}
	
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	public abstract Collection<Move> calculateLegalMove(final Board board);
//	All our pieces will have methods which will over ride this method
//	accordingly.
	
	public enum PieceType {
		
		PAWN("P"),
		KNIGHT("N"),
		BISHOP("B"),
		ROOK("R"),
		QUEEN("Q"),
		KING("K");
		
		private String pieceName;
		
		PieceType(final String pieceName){
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName ;
		}
	}
}
