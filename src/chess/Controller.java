package chess;

import pieces.Piece;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller extends MouseAdapter {
    private Board board;
    private boolean isPlayerTurn;

    public Controller(Board board) {
        this.board = board;
        board.setPlayerColor(true);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX() / board.titeSize;
        int row = e.getY() / board.titeSize;
        System.out.println(col + " and " + row);

        Piece pieceXY = board.getPiece(col, row);
        System.out.println(pieceXY);
        if (pieceXY != null) {
            board.selectedPiece = pieceXY;
            System.out.println(board.selectedPiece);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (board.selectedPiece != null) {
            board.selectedPiece.xPos = e.getX() - board.titeSize / 2;
            board.selectedPiece.yPos = e.getY() - board.titeSize / 2;
            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / board.titeSize;
        int row = e.getY() / board.titeSize;
        if (board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);
//            System.out.println(board.selectedPiece);

            if (board.isValidMove(move)) {
                board.makeMove(move);
            } else {
                board.selectedPiece.xPos = board.selectedPiece.col * board.titeSize;
                board.selectedPiece.yPos = board.selectedPiece.row * board.titeSize;
            }
        }
        board.selectedPiece = null;
        board.repaint();
    }
}
