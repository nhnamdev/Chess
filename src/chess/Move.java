package chess;

import pieces.Piece;

public class Move {
    int oldCol;
    int oldRow;
    int newRow;
    int newCol;
    int currentRow;
    int currentCol;
    Piece capture;
    Piece piece;
    int evaluation;

    public Move(Board board, Piece piece, int newCol, int newRow) {
        if (piece == null) {
            this.oldCol = -1;
            this.oldRow = -1;
        } else {
            this.oldCol = piece.col;
            this.oldRow = piece.row;
        }
        this.newCol = newCol;
        this.newRow = newRow;

        this.piece = piece;
        this.capture = board.getPiece(newCol, newRow);
    }

    @Override
    public String toString() {
        return "Move{" + "oldCol=" + oldCol + ", oldRow=" + oldRow + ", newRow=" + newRow + ", newCol=" + newCol + '}';
    }
}
