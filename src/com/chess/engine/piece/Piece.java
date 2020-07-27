package com.chess.engine.piece;

import chess.com.engine.Alliance;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.*;

public abstract class Piece {
	
	protected final int piecePosition;
	protected final PieceType pieceType;
	protected final Alliance pieceAlliance;
//	TODO More work here!
	protected final boolean isFirstMove = false;
//	White or Black
	
	Piece(final PieceType pieceType,
			final int piecePosition,
				final Alliance pieceAlliance)
	{
		this.pieceType = pieceType; 
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
	
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	public enum PieceType {
		
		PAWN("P"){
			@Override
			public boolean isKing() {
				return false;
			}
		},
		
		KNIGHT("N"){
			@Override
			public boolean isKing() {
				return false;
			}
		},
		
		BISHOP("B"){
			@Override
			public boolean isKing() {
				return false;
			}
		},
		
		ROOK("R"){
			@Override
			public boolean isKing() {
				return false;
			}
		},
		
		QUEEN("Q"){
			@Override
			public boolean isKing() {
				return false;
			}
		},
		
		KING("K"){
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		private String pieceName;
		
		PieceType(final String pieceName){
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName ;
		}

		 public abstract boolean isKing();
	}
}
