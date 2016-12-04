import com.sun.rowset.internal.Row;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by thanksdanny on 05/12/2016.
 */
public class Yard extends Frame{
    private static final int ROWS = 50;
    private static final int COLS = 50;
    private static final int BLOCK_SIZE = 10;

//    Image offScreenImage = null;

    public void lauch() {
        this.setLocation(200, 200);
        this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Yard().lauch();
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        g.setColor(Color.DARK_GRAY);
        g.setColor(Color.DARK_GRAY);
        for (int i = 1; i < ROWS; i++) {
            // 画出横线
            g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
        }

        for (int i = 1; i < COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE *i, BLOCK_SIZE * ROWS);

        }
        g.setColor(c);
    }
}
