import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class SubPanel extends JPanel implements Runnable,ActionListener{
    // パネルサイズ
    public static int WIDTH = 300;
    public static int HEIGHT = MainPanel.HEIGHT;

    private Thread timeLoop;//時間経過のスレッド
    
    private MainPanel mainpanel;//MainPanelへの参照

    private boolean spending_time;//時間が進んでいるか否か

    //ボタン
    JButton one_step_button;
    JButton clear_button;
    JToggleButton time_step_button;
    
    public SubPanel(MainPanel mainpanel) {
	this.mainpanel = mainpanel;
	spending_time = false;
        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // パネルがキー入力を受け付けるようにする
        setFocusable(true);

	//ボタン配置
	time_step_button = new JToggleButton("Start");
	time_step_button.addActionListener(this);
	this.add(time_step_button);

	
	//ボタン配置
	one_step_button = new JButton("NextStep");
	one_step_button.addActionListener(this);
	this.add(one_step_button);


	//ボタン配置
	clear_button = new JButton("Clear");
	clear_button.addActionListener(this);
	this.add(clear_button);

	//描画ループ開始
        timeLoop = new Thread(this);
	timeLoop.start();
    }

    public void run() {
        while (true) {
	    if(spending_time){
		Field field = mainpanel.field;
		field.nextTime();
	    }	    
            // 休止
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
	Object obj = e.getSource();
	Field field = mainpanel.field;
	if(obj == one_step_button){//一世代ずつ進める
	    if(!spending_time)
		field.nextTime();	    
	}
	if(obj == time_step_button){//時間を流す
	    if(time_step_button.isSelected()){		
		time_step_button.setText("Stop");
		spending_time = true;

	    }
	    else{
		time_step_button.setText("Start");
		spending_time = false;
	    }
	}
	if(obj == clear_button){//クリアする
	    for(int y = 0; y < field.ROW;y++){
		for(int x = 0; x < field.COL;x++){		    
		    field.cells[y][x].die();
		}
	    }
	}
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
