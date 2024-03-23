package chess;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Board extends JPanel {
    ArrayList<Piece> pieceList = new ArrayList<>();
    public int titeSize = 85;
    public Piece selectedPiece;
    public Controller controller = new Controller(this);
    public ChessScanner chessScanner = new ChessScanner(this);
    public int enPassantTitle = -1;
    private Move currentAIMove;
    private boolean playerIsWhite = true;
    int cols = 8;
    int rows = 8;

    public Board() {
        this.setPreferredSize(new Dimension(cols * titeSize, rows * titeSize));
        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);
        this.addPieces();
    }


    public void setCurrentAIMove(Move move) {
        this.currentAIMove = move;
    }

    public void setPlayerColor(boolean isWhite) {
        playerIsWhite = isWhite;
    }

    // tìm kiếm qyân cờ
    public Piece getPiece(int col, int row) {
        for (Piece piece : pieceList) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    // hàm kiểm tra duy chuyển
    public void makeMove(Move move) {
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else if (move.piece.name.equals("King")) {
            moveKing(move);
        }
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * titeSize;
        move.piece.yPos = move.newRow * titeSize;

        move.piece.isFirstMove = false;

        capture(move.capture);
    }

    public void undoMove(Move move) {
        move.piece.col = move.oldCol;
        move.piece.row = move.oldRow;
        move.piece.xPos = move.piece.col * titeSize;
        move.piece.yPos = move.piece.row * titeSize;
        move.piece.isFirstMove = move.piece.isPrevMove;

        pieceList.clear();
        addPieces();
    }


    /*Đoạn mã này giả sử rằng điều kiện cho việc thực hiện castling đã được kiểm tra trước đó để đảm bảo tính hợp lệ của nước đi.
    Castling là một nước đi đặc biệt trong cờ vua, cho phép di chuyển cùng một lúc cả King và Rook trong một nước đi.*/
    private void moveKing(Move move) {
        // kiểm tra thử đây có phải là 1 nước đi castling  hay không
        if (Math.abs(move.piece.col - move.newCol) == 2) {
            Piece rook = null;
            if (move.piece.col < move.newCol) {
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            } else if (move.piece.col > move.newCol) {
                rook = getPiece(0, move.piece.row);
                rook.col = 2;
            }
            rook.xPos = rook.col * titeSize;
        }
    }

    //     kỹ thuật bắt tốt qua đường
    private void movePawn(Move move) {
        int colorIndex = move.piece.isWhite ? 1 : -1;
        if (getTileNum(move.newCol, move.newRow) == enPassantTitle) {
            move.capture = getPiece(move.newCol, move.newRow + colorIndex);
        }
        if (Math.abs(move.piece.row - move.newRow) == 2) {
            enPassantTitle = getTileNum(move.newCol, move.newRow + colorIndex);
        } else {
            enPassantTitle = -1;
        }

        // promotion
        // kiểm tra xem quân tốt đã đi đến cuối hàng đối phương không
        // nếu có thì gọi phương thức promotePawn để chuyển nó thành quân hậu;
        colorIndex = move.piece.isWhite ? 0 : 7;
        if (move.newRow == colorIndex) {
            promotePawn(move);
        }

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPos = move.newCol * titeSize;
        move.piece.yPos = move.newRow * titeSize;

        move.piece.isFirstMove = false;

        capture(move.capture);
    }

    // thực hiện khi quân tốt xuống cuối hàng thì đổi thành hậu;
    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece) {
        pieceList.remove(piece);
    }

    // đọan mã này kiểm tra tính hợp lệ của 1 nuơcs đi
    public boolean isValidMove(Move move) {
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }
        if (!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }
        if (chessScanner.isKingChecked(move)) {
            return false;
        }
        return true;
    }

    // kiểm tra 2 đội có cùng màu không
    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    // tính toán và trả về thứ tự ô trên bàn cờ;
    // đếm từ trái sang phải từ trên xuống dưới;
    public int getTileNum(int col, int row) {
        return row * rows + col;
    }

    Piece findKing(boolean isWhile) {
        for (Piece piece : pieceList) {
            if (isWhile == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }


    public void addPieces() {
        //co den
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new King(this, 3, 0, false));
        pieceList.add(new Queen(this, 4, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));

        pieceList.add(new Pawn(this, 0, 1, false));
        pieceList.add(new Pawn(this, 1, 1, false));
        pieceList.add(new Pawn(this, 2, 1, false));
        pieceList.add(new Pawn(this, 3, 1, false));
        pieceList.add(new Pawn(this, 4, 1, false));
        pieceList.add(new Pawn(this, 5, 1, false));
        pieceList.add(new Pawn(this, 6, 1, false));
        pieceList.add(new Pawn(this, 7, 1, false));
//co trang
        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));

        pieceList.add(new Pawn(this, 0, 6, true));
        pieceList.add(new Pawn(this, 1, 6, true));
        pieceList.add(new Pawn(this, 2, 6, true));
        pieceList.add(new Pawn(this, 3, 6, true));
        pieceList.add(new Pawn(this, 4, 6, true));
        pieceList.add(new Pawn(this, 5, 6, true));
        pieceList.add(new Pawn(this, 6, 6, true));
        pieceList.add(new Pawn(this, 7, 6, true));
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // draw chess board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Color cellColor;
                if ((c + r) % 2 == 0) {
                    cellColor = new Color(227, 198, 181);
                } else {
                    cellColor = new Color(157, 105, 53);
                }
                g2d.setColor(cellColor);
                g2d.fillRect(c * titeSize, r * titeSize, titeSize, titeSize);
            }
        }

// Paint highlight
        if (selectedPiece != null) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (isValidMove(new Move(this, selectedPiece, c, r))) {
                        Color highlightColor = new Color(255, 255, 255, 199);
                        g2d.setColor(highlightColor);
                        g2d.fillRect(c * titeSize, r * titeSize, titeSize, titeSize);
                    }
                }
            }
            if (currentAIMove != null) {
                g.setColor(Color.RED);
                g.drawRect(currentAIMove.newCol * titeSize, currentAIMove.newRow * titeSize, titeSize, titeSize);
            }
        }

// Paint pieces
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }
        // Thêm mã sau ở đầu phương thức paintComponent
        if (currentAIMove != null) {
            makeMove(currentAIMove);
            currentAIMove = null;
        }

    }


    private boolean isKingInCheckmate(Piece king) {
        // them dieu kien king != null &&
        if (chessScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Move move = new Move(this, king, c, r);
                    if (isValidMove(move)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
