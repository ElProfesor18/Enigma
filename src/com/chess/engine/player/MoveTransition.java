package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

// This Class basically represents transition between
// two states, before and after a particular move. 
public class MoveTransition {

	private final Board transitionBoard;
	private final Move move;
//	MoveStatus indicates whether we were able to successfully
//	execute the move or not for whatsoever reason. (Pinned down piece,
//	Illegal Castle etc.)
	private final MoveStatus moveStatus;
	
	public MoveTransition(final Board transitionBoard,
			final Move move, final MoveStatus moveStatus) {
		
		this.transitionBoard = transitionBoard;
		this.moveStatus = moveStatus;
		this.move = move;
	}
	
	public MoveStatus getMoveStatus() {
		return this.moveStatus;
	}
}
