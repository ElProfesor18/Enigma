package com.chess.engine.piece;

import java.util.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.piece.Piece.PieceType;

import chess.com.engine.Alliance;

public class Pawn extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = {8};

	public Pawn(final Alliance pieceAlliance, final int piecePosition) { 
		super(PieceType.PAWN, piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMove(Board board) {
		int candidateDestinationCoordinate;
		List <Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES)
		{
//			Alliance decides directionality.
			candidateDestinationCoordinate = this.piecePosition + 
					(this.getPieceAlliance().getDirection() * currentCandidateOffset);

			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
//				TODO : More Work (Deal with promotion)!
				legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
			}
			
			else if(currentCandidateOffset == 16 && this.isFirstMove() && 
					( (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()) ) ) {
//				
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection()*8);
				
//				TODO : More Work!
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
						 !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				}
			}
			
//			Attack Move
			else if(currentCandidateOffset == 7 &&
					( (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite()) ||
					   (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack())
					   )) {
					
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
//						TODO: We can add an attack move.
//						Conflict: Attack + Promotion
						
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
					}
			}
			
			else if(currentCandidateOffset == 9 &&
					( (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.getPieceAlliance().isWhite()) ||
					   (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.getPieceAlliance().isBlack())
					   )) {
					
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
//						TODO: We can add an attack move.
//						Conflict: Attack + Promotion
						
						legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
					}
			}
		}
		
		return legalMoves;
	}
	
	@Override 
	public String toString() {
		 return PieceType.PAWN.toString();
	}
	
//	TODO: Precomputation!
	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getMovedPiece().getPieceAlliance(), 
				move.getDestinationCoordinate());
	}
	
	
	@Override
	public PieceType getPieceType() {
		return PieceType.PAWN;
	} 
}


// Deal with Attack Moves, Promotion, En Passant
