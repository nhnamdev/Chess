package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rock;

public class Board extends JPanel {

	public int titleSize = 85;
	int cols = 8;
	int rows = 8;

	ArrayList<Piece> piecesList = new ArrayList<>();

	public Piece selectedPiece;
	Input input = new Input(this);

	public Board() {
		this.setPreferredSize(new Dimension(cols * titleSize, rows * titleSize));
		this.addMouseListener(input);
		this.addMouseMotionListener(input);
		addPieces();
	}

	public Piece getPiece(int col, int row) {
		for (Piece piece : piecesList) {
			if (piece.col == col && piece.row == row) {
				return piece;
			}
		}
		return null;
	}

	public void makeMove(Move move) {
		move.piece.col = move.newCol;
		move.piece.row = move.newRow;

		move.piece.xPos = move.newCol * titleSize;
		move.piece.yPos = move.newRow * titleSize;

		capture(move);

	}

	public void capture(Move move) {
		piecesList.remove(move.capture);
	}

	public boolean isValidMove(Move move) {
		if (sameTeam(move.piece, move.capture)) {
			return false;
		}
		return true;
	}

	public boolean sameTeam(Piece p1, Piece p2) {
		if (p1 == null || p2 == null) {
			return false;
		}
		return p1.isWhite == p2.isWhite;
	}

	public void addPieces() {
		piecesList.add(new Rock(this, 0, 0, false));
		piecesList.add(new Knight(this, 1, 0, false));
		piecesList.add(new Bishop(this, 2, 0, false));
		piecesList.add(new Queen(this, 3, 0, false));
		piecesList.add(new King(this, 4, 0, false));
		piecesList.add(new Bishop(this, 5, 0, false));
		piecesList.add(new Knight(this, 6, 0, false));
		piecesList.add(new Rock(this, 7, 0, false));

		piecesList.add(new Pawn(this, 0, 1, false));
		piecesList.add(new Pawn(this, 1, 1, false));
		piecesList.add(new Pawn(this, 2, 1, false));
		piecesList.add(new Pawn(this, 3, 1, false));
		piecesList.add(new Pawn(this, 4, 1, false));
		piecesList.add(new Pawn(this, 5, 1, false));
		piecesList.add(new Pawn(this, 6, 1, false));
		piecesList.add(new Pawn(this, 7, 1, false));

		piecesList.add(new Rock(this, 0, 7, true));
		piecesList.add(new Knight(this, 1, 7, true));
		piecesList.add(new Bishop(this, 2, 7, true));
		piecesList.add(new Queen(this, 3, 7, true));
		piecesList.add(new King(this, 4, 7, true));
		piecesList.add(new Bishop(this, 5, 7, true));
		piecesList.add(new Knight(this, 6, 7, true));
		piecesList.add(new Rock(this, 7, 7, true));

		piecesList.add(new Pawn(this, 0, 6, false));
		piecesList.add(new Pawn(this, 1, 6, false));
		piecesList.add(new Pawn(this, 2, 6, false));
		piecesList.add(new Pawn(this, 3, 6, false));
		piecesList.add(new Pawn(this, 4, 6, false));
		piecesList.add(new Pawn(this, 5, 6, false));
		piecesList.add(new Pawn(this, 6, 6, false));
		piecesList.add(new Pawn(this, 7, 6, false));

		// piecesList.add(new Knight(this, 6, 0, false));

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++) {
				g2d.setColor((c + r) % 2 == 0 ? Color.CYAN : Color.MAGENTA);
				g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
			}
		if(selectedPiece == null)
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++) {
				if(isValidMove(new Move(this, selectedPiece, c, r))) {
					g2d.setColor(new Color(68,108,57,190));
					g2d.fillRect(c*titleSize, r*titleSize, titleSize, titleSize);
				}
			}
		
		for (Piece piece : piecesList) {
			piece.paint(g2d);
		}
	}

}
