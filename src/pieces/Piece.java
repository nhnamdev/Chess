package pieces;

import chess.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Piece {

    public int col, row;
    public int xPos, yPos;
    public boolean isWhite;
    public String name;
    public int value;
    public boolean isFirstMove = true;
    public boolean isPrevMove = true;
    Image sprite;
    Board board;
    BufferedImage sheet;
    // BufferedImage dùng để lưu trữ hình ảnh của tất cả các quân cờ trong trò chơi
    // try-catch dùng để đọc ảnh từ pieces.png
    protected int sheetScale;
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("pieces.png"));
            if (sheet != null) {
                sheetScale = sheet.getWidth() / 6;
            } else {
                System.out.println("error");
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public Piece(Board board) {
        this.board = board;
    }
    //kiểm tra nước đi từ vị trí hiện tại đến vị trí (col, row) có hợp lệ hay không dựa trên luật của quân cờ cụ thể.
    public boolean isValidMovement(int col, int row) {
        return true;
    }

    // kiểm tra nước đi từ vị trí hiện tại đến vị trí (col, row)  có va chạm với qyân cờ khác trên bảng hay không.
    public boolean moveCollidesWithPiece(int col, int row) {
        return false;
    }
    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }
}
