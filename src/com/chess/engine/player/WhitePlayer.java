package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.Piece;

import chess.com.engine.Alliance;

public class WhitePlayer extends Player{

	public WhitePlayer(Board board, 
			Collection<Move> whiteStandardLegalMoves,
			Collection<Move> blackStandardLegalMoves) {
		// TODO 	
		
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

}
