package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.*;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection <Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final Collection <Move> legalMoves,
           final Collection <Move> opponentMoves){

        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.calculateAttackOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
        /* Do the opponent moves attack current position? */
    }

    public King getPlayerKing() {
        return this.playerKing;
    }
    public Collection<Move> getLegalMoves() { return this.legalMoves; }

    public static Collection<Move> calculateAttackOnTile(int piecePosition, Collection<Move> moves) {
        final List <Move> attackMoves = new ArrayList <>();
        for(final Move move : moves) {
            if(piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private boolean hasEscapeMoves() {
        for(final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);

            if(transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    private King establishKing(){
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Should Not Reach Here! Not a Valid Board!!");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck(){
        return this.isInCheck;
    }
    public boolean isInCheckmate(){
        return this.isInCheck && !hasEscapeMoves();
    }
    public boolean isInStalemate(){
        return !this.isInCheck && !hasEscapeMoves();
    }
    public boolean isCastled(){
//        TODO
        return false;
    }

    public MoveTransition makeMove(final Move move) {

        if(!isMoveLegal(move)) {
            /* Can't make the move. Return the same board */
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard =  move.execute();

        final Collection <Move> kingAttacks =
                Player.calculateAttackOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                        transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()) {
            /* Can't make the move. Return the same board */
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        /* Can make the move! */
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection <Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract  Player getOpponent();
    protected abstract Collection <Move> calculateKingCastles(Collection <Move> playerLegals, Collection <Move> opponentLegals);
}
