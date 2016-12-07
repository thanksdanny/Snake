import com.sun.rowset.internal.Row;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by thanksdanny on 05/12/2016.
 */
public class Yard extends Frame{
    PaintThread paintThread = new PaintThread();
    private boolean gameOver = false; // 游戏是否结束

    /**
     * 行数
     */
    public static final int ROWS = 30;
    public static final int COLS = 30;
    public static final int BLOCK_SIZE = 15;

    private int score = 0;

    Snake s = new Snake(this);
    Egg e = new Egg();

    Image offScreenImage = null;


    public static void main(String[] args) {
        new Yard().lauch();
    }

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
        this.addKeyListener(new KeyMonitor());

        new Thread(new PaintThread()).start();
    }


    public void stop() {
        gameOver = false;
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        g.setColor(Color.DARK_GRAY);
        // 画出横线
        for (int i = 1; i < ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
        }
        for (int i = 1; i < COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE *i, BLOCK_SIZE * ROWS);
        }

        g.setColor(Color.YELLOW);
        g.drawString("score:" + score, 10, 60);

        if (gameOver) {
            g.drawString("gameover", 120, 180);

            paintThread.pause();
        }

        g.setColor(c);
        s.eat(e);
        e.draw(g);
        s.draw(g);


    }

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        }
        Graphics gOff = offScreenImage.getGraphics();
        print(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    private class PaintThread implements Runnable {
        private boolean running = true;
        private boolean pause = false;
        @Override
        public void run() {
            while(running) {
                if (pause) {
                    continue;
                } else {
                    repaint();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void pause() {
            this.pause = true;
        }

        public void reStart() {
            this.pause = false;
            s = new Snake(Yard.this);
            gameOver = false;
        }

        public void gameOver() {
            running = false;
        }
    }

    private class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            s.keyPressed(e);
        }
    }

    /**
     * 拿到所得的分数
     * @return 分数
     */
    public int getScore() {
        return score;
    }

    /**
     * 设置所得的分数
     * @param score 分数
     */
    public void setScore(int score) {
        this.score = score;
    }
}
