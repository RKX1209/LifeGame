import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MainPanel extends JPanel implements Runnable, MouseListener,MouseMotionListener {
    // パネルサイズ
    public static int WIDTH = 600;
    public static int HEIGHT = 600;

    private Thread drawLoop;//描画用のスレッド

    public Field field;//フィールド
    
    public MainPanel() {
        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // パネルがキー入力を受け付けるようにする
        setFocusable(true);

	addMouseListener(this);
	addMouseMotionListener(this);


	field = new Field();

	//描画ループ開始
        drawLoop = new Thread(this);
        drawLoop.start();
    }

    public void run() {
        while (true) {
            // 再描画
            repaint();

            // 休止
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
	field.draw(g);
    }
    public void mouseClicked(MouseEvent e){
	int x = e.getX();
	int y = e.getY();	
	field.change_cell_state(x,y);
    }

    public void mouseDragged(MouseEvent e){
	int x = e.getX();
	int y = e.getY();	
	field.draw_cells(x,y);
    }

    public void mouseMoved(MouseEvent e){
    }
    public void mouseEntered(MouseEvent e){
    }

    public void mouseExited(MouseEvent e){
    }

    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){

    }

}
