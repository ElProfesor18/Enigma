package com.chess.engine.piece;

import java.util.*;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.piece.Piece.PieceType;

import chess.com.engine.Alliance;

public class Knight extends Piece{
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
	
	public Knight(Alliance pieceAlliance, int piecePosition) {
		super(PieceType.KNIGHT, piecePosition, pieceAlliance);
	}
	
	@Override
	public Collection<Move> calculateLegalMove(final Board board) {
		
		int candidateDestinationCoordinate;
		List <Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES)
		{
			candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				 
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				} 
				
				else {
					final Piece pieceAtDestination =  candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					
					if(this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, 
								pieceAtDestination));
					}
				}
			}
		}
		
		return legalMoves;
	}
	
	@Override 
	public String toString() {
		 return PieceType.KNIGHT.toString();
	}
	
//	Edge Cases:
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 
				|| candidateOffset == 6 || candidateOffset == 15);
		
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6); 
	}
	
	
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 17 || candidateOffset == 10 
				|| candidateOffset == -6 || candidateOffset == -15);
		
	}
	
}















