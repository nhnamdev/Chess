package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(Color.pink);
		frame.setLayout(new GridBagLayout());
		frame.setMinimumSize(new Dimension(800, 800));
		frame.setLocationRelativeTo(null);

		Board board = new Board();
		frame.add(board);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
