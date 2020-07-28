package com.chess.engine.player;
import java.util.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.piece.King;
import com.chess.engine.piece.Piece;

import chess.com.engine.Alliance;

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
		this.legalMoves = concatenate(legalMoves, calculateKingCastles(legalMoves, opponentMoves));
		this.isInCheck = !Player.calculateAttackOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
	}
	
	private Collection<Move> concatenate(Collection<Move> collectionA, Collection<Move> collectionB) {
		Collection <Move> res = new ArrayList <>();
		
		for(Move move : collectionA) {
			res.add(move);
		}
		
		for(Move move : collectionB) {
			res.add(move);
		}
		
		return res;
	}

	public King getPlayerKing() {
		return this.playerKing;
	}
	
	public Collection <Move> getLegalMoves(){
		return this.legalMoves;
	}

	public static Collection<Move> calculateAttackOnTile(int piecePosition, Collection<Move> moves) {
		final List <Move> attackMoves = new ArrayList <>();
		
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()) {
				attackMoves.add(move);
			}
		}
		
//		We simply do not return true / false, because this List
//		helps in deciding the available legal moves to get out of check.
		return attackMoves;
//		attackMoves is basically a collection of Moves which attack the 
//		current player's King
	}

	private King establishKing() {
		// TODO 
		for(final Piece piece : getActivePieces()) {
			if(piece.getPieceType().isKing()) {
//				Type Cast the piece as King piece!
				return (King) piece;
			}
		}
		
		throw new RuntimeException("Should Not Reach Here! Not a Valid Board!!");
	}
	
	public boolean isMoveLegal(final Move move) {
//		Check whether the move passed in is legal.
		return this.legalMoves.contains(move);
	}
	
	
	public boolean isInCheck() {
		return this.isInCheck;
	}
	
//	TODO: Implement this methods!
	public boolean isInCheckMate() {
//		King in check + No lega Moves to Make
		return this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	
//	So we try out each of the available legal moves,
//	if none of them is valid return false, else return true!
	private boolean hasEscapeMoves() {
		for(final Move move : this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		
		return false; 
	}
	
//	TODO: Implement this methods! 
	public boolean isCastled() {
		return false; 
	}
	
	public MoveTransition makeMove(final Move move) {
		
//		If the move is illegal then no transition takes place. 
//		Return the same board, with illegal move status.
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard =  move.execute();
		 
//		currentPlayer assignment is based on the fact that in the Board Class
//		we have a member Alliance nextMoveMaker which decide whether the
//		current player is whitePlayer or blackPlayer.
		
//		So now that the current move is valid, we make the move and check whether,
//		the current move result into attack on the current players King.
		
//		Take Care to pass the current player's opponent's King as the roles will get
//		interchanged.
		final Collection <Move> kingAttacks = 
				Player.calculateAttackOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), 
						transitionBoard.currentPlayer().getLegalMoves());
		
//		If it attacks the current players King, we again make no transition.
//		Return the same board, with leaves player in check status.
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
		
//		Now that the current move is valid and in no way whatsoever can be deemed illegal,
//		we make it and return a transitioned board with status done. 
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}

	public abstract Collection <Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	protected abstract Collection <Move> calculateKingCastles(Collection <Move> playerLegals, Collection <Move> opponentLegals);  
}










