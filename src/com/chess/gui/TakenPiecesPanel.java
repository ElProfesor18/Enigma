package com.chess.gui;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table.MoveLog;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TakenPiecesPanel extends  JPanel{
    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Color PANEL_COLOR = Color.decode("0xFDF5E6");
    private static final Dimension TAKEN_PIECE_DIMENSION = new Dimension(40, 80);

    TakenPiecesPanel(){
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8,2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);

        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);

        setPreferredSize(TAKEN_PIECE_DIMENSION);
    }

    public void redo(final MoveLog moveLog){
        northPanel.removeAll();
        southPanel.removeAll();

        final java.util.List<Piece> blackTakenPieces = new ArrayList<>();
        final java.util.List <Piece> whiteTakenPieces = new ArrayList<>();

        for(final Move move : moveLog.getMoves()){
            if(move.isAttack()){
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceAlliance().isWhite()){
                    whiteTakenPieces.add(takenPiece);
                }
                else if (takenPiece.getPieceAlliance().isBlack()){
                    blackTakenPieces.add(takenPiece);
                }
                else {
                    throw new RuntimeException("Taken Piece Panel: Should not" +
                            "Reach Here!");
                }
            }
        }

        Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        for(final Piece takenPiece : whiteTakenPieces){
            try {
                final BufferedImage image = ImageIO.read(new File("art/plain" +
                        takenPiece.getPieceAlliance().toString().substring(0, 1) + "" +
                        takenPiece.toString()));

                final ImageIcon imageIcon= new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                this.southPanel.add(imageLabel);
            } catch (final IOException e){
                e.printStackTrace();
            }
        }

        for(final Piece takenPiece : blackTakenPieces){
            try {
                final BufferedImage image = ImageIO.read(new File("art/plain" +
                        takenPiece.getPieceAlliance().toString().substring(0, 1) + "" +
                        takenPiece.toString()));

                final ImageIcon imageIcon= new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                this.southPanel.add(imageLabel);
            } catch (final IOException e){
                e.printStackTrace();
            }
        }

        validate();
    }
}
