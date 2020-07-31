package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;

public class MiniMax implements MoveStrategy {

    private final BoardEvaluator boardEvaluator;
    private final int searchDepth;

    public MiniMax(final int searchDepth){
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString(){
        return "MiniMax";
    }


    @Override
    public Move execute(Board board) {

        final long startTime = System.currentTimeMillis();

        Move bestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue = 0;

        System.out.println(board.currentPlayer() +
                "THINKING with depth = " + this.searchDepth);

        int numMoves = board.currentPlayer().getLegalMoves().size();
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                currentValue = (board.currentPlayer().getAlliance().isWhite() ?
                        max(moveTransition.getTransitionBoard(), this.searchDepth-1) :
                        min(moveTransition.getTransitionBoard(), this.searchDepth-1));
            }

            if(board.currentPlayer().getAlliance().isWhite() && currentValue>=highestSeenValue){
                highestSeenValue = currentValue;
                bestMove = move;
            }

            if(board.currentPlayer().getAlliance().isWhite() && currentValue<=lowestSeenValue){
                lowestSeenValue = currentValue;
                bestMove = move;
            }
        }

        final long executionTime = System.currentTimeMillis();

        return bestMove;
    }

    /* For Black Player */
    public int min(final Board board, final int depth){
        if(depth == 0 || isEndGameScenario(board)){
            return boardEvaluator.evaluate(board, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = max(moveTransition.getTransitionBoard(), depth-1);
                if(currentValue <= lowestSeenValue){
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }

    /* For White Player */
    public int max(final Board board, final int depth){
        if(depth == 0 || isEndGameScenario(board)){
            return boardEvaluator.evaluate(board, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for(final Move move : board.currentPlayer().getLegalMoves()){
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if(moveTransition.getMoveStatus().isDone()){
                final int currentValue = min(moveTransition.getTransitionBoard(), depth-1);
                if(currentValue >= highestSeenValue){
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }

    private boolean isEndGameScenario(Board board) {
        return board.currentPlayer().isInCheckmate() || board.currentPlayer().isInStalemate();
    }
}
