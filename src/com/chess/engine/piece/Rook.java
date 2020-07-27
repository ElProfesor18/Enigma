package com.chess.engine.piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import chess.com.engine.Alliance;

public class Rook extends Piece{
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-8, -1, 1, 8};
	
	Rook(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMove(final  Board board) {

		int candidateDestinationCoordinate;
		List <Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES)
		{
			candidateDestinationCoordinate = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) 
			{
				
				candidateDestinationCoordinate += currentCandidateOffset;
				
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) 
				{
					
					if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || 
							isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
								continue;
							}
				
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
						
//						If encounter a blockade, we can't move over it => break
						break;
					}
				}
			}
		}
		
		return legalMoves;
	}
	
//	Edge Cases:
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) { 
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
	} 
	

} 