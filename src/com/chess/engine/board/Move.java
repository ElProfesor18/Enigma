package com.chess.engine.board;

import com.chess.engine.pieces.*;
import com.chess.engine.board.Board.Builder;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();

    Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board,
                 final int destinationCoordinate){
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    public int getCurrentCoordinate(){
        return this.movedPiece.getPiecePosition();
    }
    public int getDestinationCoordinate(){
        return this.destinationCoordinate;
    }
    public Piece getMovedPiece(){
        return this.movedPiece;
    }
    public boolean isAttack(){
        return false;
    }
    public boolean isCastlingMove(){
        return false;
    }
    public Piece getAttackedPiece(){
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + this.destinationCoordinate;
        result = result * prime + this.movedPiece.hashCode();
        result = result * prime + (this.isFirstMove == false ? 0 : 1);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                otherMove.getDestinationCoordinate() == getDestinationCoordinate() &&
                otherMove.getMovedPiece() == getMovedPiece();
    }

    public Board execute() {
        final Builder builder = new Builder();
        /* Set current players all the  pieces except the one that makes the move*/
        for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
            if(!(this.movedPiece.equals(piece))) {
                builder.setPiece(piece);
            }
        }
        /* Set opponents all the pieces */
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this)); /* Set the moved piece! */
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        /* Flip the move maker! */
        return builder.build();
    }

    public static class MajorAttackMove extends AttackMove {
        public MajorAttackMove(final Board board,
                               final Piece pieceMoved,
                               final int destinationCoordinate,
                               final Piece pieceAttacked){
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof MajorAttackMove && super.equals(other);
        }

        @Override
        public String toString(){
            return movedPiece.getPieceType().toString() +
                    "x" + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other){
            return (this == other || other instanceof MajorMove && super.equals(other));
        }

        @Override
        public String toString(){
            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(final Board board,
                          final Piece movedPiece,
                           final int destinationCoordinate,
                          final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }

            if(!(other instanceof AttackMove)) {
                return false;
            }

            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece()) ;
        }

        @Override
        public boolean isAttack(){
            return true;
        }

        @Override
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }

        @Override
        public String toString(){
            return movedPiece.getPieceType().toString() +
                    "x" + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static final class PawnMove extends Move{

        public PawnMove(final Board board, final Piece movedPiece, final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other){
            return (this == other || other instanceof PawnMove && super.equals(other));
        }
    }

    public static class PawnAttackMove extends AttackMove{

        public PawnAttackMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                              final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object other){
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        @Override
        public String toString(){
            return BoardUtils.getPositionAtCoordinate(attackedPiece.getPiecePosition()).substring(0, 1) +
                    "x" + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static final class PawnEnPassantAttack extends PawnAttackMove{

        public PawnEnPassantAttack(final Board board, final Piece movedPiece, final int destinationCoordinate,
                                   final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object other){
            return (this == other || other instanceof PawnEnPassantAttack && super.equals(other));
        }

        @Override
        public Board execute(){
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : board.currentPlayer().getOpponent().getActivePieces()) {
                if(!this.attackedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }

            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    public static class PawnJump extends Move{
        public PawnJump(final Board board, final Piece movedPiece, final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for(final Piece piece : board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);

            builder.setEnPassantPawn(movedPawn); /* If we don't set this, then it would be null by default! */
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

        @Override
        public String toString(){
            return BoardUtils.getPositionAtCoordinate(destinationCoordinate);
        }
    }

    static abstract class CastleMove extends Move {
        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public CastleMove(final Board board, final Piece movedPiece,
                          final int destinationCoordinate, final Rook castleRook,
                          final int castleRookStart, final int castleRookDestination){

            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public boolean isCastlingMove() {
            return true;
        }
        public Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();

            /* Set all pieces except the Castle Rook and current player's King! */
            for(final Piece piece : board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece) && !this.getCastleRook().equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            /* Set the King */

            /* Solve the movedPiece Castling Issue by manually creating a King object! */
            Piece castledKing = new King(movedPiece.getPieceAlliance(), this.destinationCoordinate);
            builder.setPiece(castledKing);
			 /* Manually create a new Rook object and then set it. */
//			 TODO: Set isFirst Move as False!
            builder.setPiece(new Rook(this.getCastleRook().getPieceAlliance(), this.castleRookDestination));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            /* Flip the move maker */

            return builder.build();
        }

        @Override
        public int hashCode(){
            final int prime = 31;
            int result = super.hashCode();
            result = result * prime + this.castleRook.hashCode();
            result = result * prime + this.castleRookDestination;
            return result;
        }

        @Override
        public boolean equals(final Object other){
            if(this == other){
                return true;
            }
            if(!(other instanceof CastleMove)){
                return false;
            }

            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }
    }

    public static final class KingSideCastleMove extends CastleMove{

        public KingSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                                  final Rook castleRook, final int castleRookStart, final int castleRookDestination){
            super(board, movedPiece, destinationCoordinate,
                    castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public boolean equals(final Object other){
            return (this == other || other instanceof KingSideCastleMove && super.equals(other));
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove{

        public QueenSideCastleMove(final Board board, final Piece movedPiece, final int destinationCoordinate,
                                   final Rook castleRook, final int castleRookStart, final int castleRookDestination){
            super(board, movedPiece, destinationCoordinate,
                    castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public boolean equals(final Object other){
            return (this == other || other instanceof QueenSideCastleMove && super.equals(other));
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static final class NullMove extends Move{
        //		TODO: More work here!
        public NullMove(){
            super(null, 65);
        }
        @Override
        public Board execute() {
            throw new RuntimeException("NullMove: Can not execute a null move!");
        }

        @Override
        public int getCurrentCoordinate(){
            return -1;
        }
    }

    public static class MoveFactory{
        private MoveFactory() {
            throw new RuntimeException("Move Factory: Not instantiable! ");
        }
        /* Given: To and From Coordinate */
        /* return the corresponding move! */
        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {

//			We can later review this code and get only current player's
//			legal moves
            for(final Move move : board.getAllLegalMoves()) {
                if(move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestinationCoordinate() == destinationCoordinate)
                    return move;
            }
            return NULL_MOVE;
        }
    }
}
