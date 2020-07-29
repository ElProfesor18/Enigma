package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player{

    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves,
                       Collection<Move> blackStandardLegalMoves) {
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

        final List<Move> kingCastle = new ArrayList<>();

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

//                        TODO: Add a King Side castle move.
                        kingCastle.add(new Move.KingSideCastleMove(this.board,
                                                                    this.playerKing,
                                                                    62,
                                                                    (Rook) rookTile.getPiece(),
                                                                    63,
                                                                    61));
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

//                        TODO: Add Queen Side castle move.
                        kingCastle.add(new Move.QueenSideCastleMove(this.board,
                                                                     this.playerKing,
                                                                    58,
                                                                    (Rook) rookTile.getPiece(),
                                                                    56,
                                                                    59));
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastle);
    }
}
