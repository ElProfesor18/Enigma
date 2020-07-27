package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.Piece;

import chess.com.engine.Alliance;

public class BlackPlayer extends Player{

	public BlackPlayer(Board board, 
			Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves) {
		
//		We need to change the order of black and white here,
//		because of the signature of the Player constructor.
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}
	
	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}

}
