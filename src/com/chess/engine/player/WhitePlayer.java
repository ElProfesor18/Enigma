package com.chess.engine.player;

import java.util.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.piece.Piece;
import com.chess.engine.piece.Rook;

import chess.com.engine.Alliance;

public class WhitePlayer extends Player{

	public WhitePlayer(final Board board, 
			final Collection<Move> whiteStandardLegalMoves,
			final  Collection<Move> blackStandardLegalMoves) {
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

	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
		
		final List <Move> kingCastle = new ArrayList <> ();
		
//		Legal Castling Rules
		if(this.playerKing.isFirstMove() && !this.isInCheck()){
//			Tiles between White King and White Rook must not be occupied!
			
//			WHITE's KING SIDE CASTLE
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				
				final Tile rookTile = this.board.getTile(63); // King Side Rook
				
				if(Player.calculateAttackOnTile(61, opponentLegals).isEmpty() &&
						Player.calculateAttackOnTile(62, opponentLegals).isEmpty()
						&& rookTile.getPiece().getPieceType().isRook()) {
					
					if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
						
	//					TODO: Add King Side castle move.
						kingCastle.add(new Move.KingSideCastleMove(this.board, 
								 this.playerKing, 62, (Rook) rookTile.getPiece(), 63, 61));
					}
				}
			}
			
//			WHITE's QUEEN SIDE CASTLE
			if(!this.board.getTile(59).isTileOccupied() && 
					!this.board.getTile(58).isTileOccupied() &&
					!this.board.getTile(57).isTileOccupied()) {
				
				final Tile rookTile = this.board.getTile(56); // Queen Side Rook
				
				if(Player.calculateAttackOnTile(58, opponentLegals).isEmpty() &&
						Player.calculateAttackOnTile(59, opponentLegals).isEmpty() 
						&& rookTile.getPiece().getPieceType().isRook()) {
				
					
					if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
						
	//					TODO: Add King Side castle move.
						kingCastle.add(new Move.QueenSideCastleMove(this.board, 
								 this.playerKing, 58, (Rook) rookTile.getPiece(), 56, 59));
					}
				}
			}
		}
		
		return kingCastle;
	}

}
