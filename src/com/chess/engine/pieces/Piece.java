package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.Alliance;
import com.chess.engine.board.Move;

import java.util.*;

public abstract class Piece {
    protected final int piecePosition;
    protected final PieceType pieceType;
    protected final Alliance pieceAlliance; //	White or Black
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType pieceType,
            final int piecePosition,
          final Alliance pieceAlliance)
    {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = true;
        this.cachedHashCode = computeHashCode();
    }

    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }

        if(!(other instanceof Piece)) {
            return false;
        }

        final Piece otherPiece = (Piece) other;
        return pieceAlliance == otherPiece.getPieceAlliance() &&
                pieceType == otherPiece.getPieceType() &&
                piecePosition == otherPiece.getPiecePosition() &&
                isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    private int computeHashCode() {
        int res = pieceType.hashCode();
        res = 31 * res + pieceAlliance.hashCode();
        res = 31 * res + piecePosition;
        res = 31 * res + (isFirstMove ? 1 : 0);

        return res;
    }

    public int getPiecePosition() {
        return piecePosition;
    }
    public boolean isFirstMove() {
        return this.isFirstMove;
    }
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }
    public PieceType getPieceType() { return this.pieceType; }

    public abstract Collection<Move> calculateLegalMove(final Board board);
    public abstract Piece movePiece(Move move);

    public enum PieceType {
        PAWN("P"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K"){
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
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
        public abstract boolean isRook();
    }
}
