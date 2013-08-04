import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class Cell{    
    //大きさ
    private int width;
    private int height;

    //座標
    private int x;
    private int y;    

    public boolean alive;//生きているか否か
    
    public Cell(int x,int y,boolean alive){
	this.alive = alive;
	width = Field.PIXEL_PER_TILE;
	height = Field.PIXEL_PER_TILE;
	this.x = x;
	this.y = y;
    }
    
    //描画する
    public void draw(Graphics g){
	final int EPS = 1;//縁の幅
	g.setColor(Color.BLACK);
	g.fillRect(x , y , x + width , y + height);
	if(alive)
	    g.setColor(Color.BLACK);
	else
	    g.setColor(Color.WHITE);
	g.fillRect(x + EPS , y + EPS , x + width - EPS, y + height - EPS);
    }

    //生死を反転する
    public void state_rev(){
	alive = !alive;
    }
    
    //生まれる
    public void birth(){
	alive = true;
    }
    //死ぬ
    public void die(){
	alive = false;
    }

    public boolean get_alive(){
	return alive;
    }
}