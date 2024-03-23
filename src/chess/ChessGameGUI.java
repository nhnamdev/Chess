package chess;

import javax.swing.*;

public class ChessGameGUI extends JFrame {
    private Board board;

    public ChessGameGUI() {
        board = new Board();
        this.setTitle("Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(board);
        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
