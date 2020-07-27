package com.chess.engine.piece;

import chess.com.engine.Alliance;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.*;

public abstract class Piece {
	
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
//	White or Black
	
	Piece(final int piecePosition, final Alliance pieceAlliance)
	{
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
	}
	
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}
	
	public abstract Collection<Move> calculateLegalMove(final Board board);
//	All our pieces will have methods which will over ride this method
//	accordingly.
	
	
}
