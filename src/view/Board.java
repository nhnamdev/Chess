package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Board extends JPanel {

	public int titleSize = 85;
	int cols = 8;
	int rows = 8;

	public Board() {
		this.setPreferredSize(new Dimension(cols * titleSize,rows * titleSize));
		this.setBackground(Color.BLACK);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				g2d.setColor((c + r) % 2 == 0 ? Color.white : Color.BLACK);
				g2d.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
			
			}
		}
	}
}
