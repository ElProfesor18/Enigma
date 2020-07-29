package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
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

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {

        final List <Move> kingCastle = new ArrayList <> ();

//		Legal Castling Rules
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
//			Tiles between White King and White Rook must not be occupied!

//			BLACK's KING SIDE CASTLE
            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {

                final Tile rookTile = this.board.getTile(7); // King Side Rook

                if(Player.calculateAttackOnTile(5, opponentLegals).isEmpty() &&
                        Player.calculateAttackOnTile(6, opponentLegals).isEmpty()
                        && rookTile.getPiece().getPieceType().isRook()) {

                    if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {

//                        TODO: Add King Side castle move.
                        kingCastle.add(new Move.KingSideCastleMove(this.board,
                                                                    this.playerKing,
                                                                    6,
                                                                    (Rook) rookTile.getPiece(),
                                                                    7,
                                                                    5));
                    }
                }
            }

//			WHITE's QUEEN SIDE CASTLE
            if(!this.board.getTile(1).isTileOccupied() &&
                    !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()) {

                final Tile rookTile = this.board.getTile(0); // Queen Side Rook

                if(Player.calculateAttackOnTile(2, opponentLegals).isEmpty() &&
                        Player.calculateAttackOnTile(3, opponentLegals).isEmpty()
                        && rookTile.getPiece().getPieceType().isRook()) {


                    if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {

//                        TODO: Add Queem Side castle move.
                        kingCastle.add(new Move.QueenSideCastleMove(this.board,
                                                                     this.playerKing,
                                                                    2,
                                                                    (Rook) rookTile.getPiece(),
                                                                    0,
                                                                    3));
                    }
                }
            }
        }
        return kingCastle;
    }
}
