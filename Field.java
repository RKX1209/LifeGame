import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class Field{
    //大きさ
    public static int WIDTH;
    public static int HEIGHT;

    public static final int PIXEL_PER_TILE = 20;//1タイルごとのピクセル数

    public int COL;//列数
    public int ROW;//行数

    public Cell cells[][];//フィールド

    //ムーア近傍走査のための方向ベクトル
    /*
      012
      3#4
      567
     */
    public int[] dx = {-1,0,1,-1,1,-1,0,1};
    public int[] dy = {-1,-1,-1,0,0,1,1,1};
    
    Field(){
	COL = 30;
	ROW = 30;
	HEIGHT = ROW * PIXEL_PER_TILE;
	WIDTH = COL * PIXEL_PER_TILE;
	cells = new Cell[ROW][COL];
	for(int y = 0; y < ROW; y++){
	    for(int x = 0; x < COL; x++){
		cells[y][x] = new Cell(PIXEL_PER_TILE * x,
				       PIXEL_PER_TILE * y,false);
	    }
	}
	
    }

    //淘汰ルールを適用し、次の時間に進む
    public void nextTime(){
	
	Cell cells2[][] = new Cell[ROW][COL];
	for(int y = 0; y < ROW; y++){
	    for(int x = 0; x < COL; x++){
		cells2[y][x] = new Cell(PIXEL_PER_TILE * x,
					PIXEL_PER_TILE * y,cells[y][x].get_alive());
	    }
	}
	
	
	for(int y = 0; y < ROW; y++){
	    for(int x = 0; x < COL; x++){
		boolean now_alive = cells2[y][x].get_alive();
		
		//近傍の内、生きているor死んでいるセルの数
		int alive_num = 0;
		int dead_num = 0;
		
		//8方向走査
		for(int d = 0; d < 8; d++){
		    int nx = x + dx[d];
		    int ny = y + dy[d];
		    if(isInField(nx,ny)){//フィールド内なら
			if(cells2[ny][nx].get_alive()) alive_num++;
			else dead_num++;
		    }
		}
		
		//####ルール適用#####
		//ルール1: 誕生
		//死んでいるセルに隣接する生きたセルがちょうど3つあれば、次の世代が誕生する。
		if(!now_alive && alive_num == 3){
		    cells[y][x].birth();
		}
		//ルール2: 生存
		//生きているセルに隣接する生きたセルが2つか3つならば、次の世代でも生存する。
		if(now_alive && (alive_num == 2 || alive_num == 3)){
		    ;
		}
		//ルール3: 過疎
		//生きているセルに隣接する生きたセルが1つ以下ならば、過疎により死滅する。
		if(now_alive && alive_num <= 1){
		    cells[y][x].die();
		}
		//ルール4: 過密
		//生きているセルに隣接する生きたセルが4つ以上ならば、過密により死滅する。
		if(now_alive && alive_num >= 4){
		    cells[y][x].die();
		}
		//######################
	    }
	}
		    
    }
    //cellの状態を変える
    public void change_cell_state(int x,int y){
	x = PiexelToTile(x);
	y = PiexelToTile(y);	
	if(isInField(x,y)){//フィールド内なら	
	    cells[y][x].state_rev();
	}
    }

    //cellを幾何的に書く
    public void draw_cells(int x,int y){
	x = PiexelToTile(x);
	y = PiexelToTile(y);	
	if(isInField(x,y)){//フィールド内なら
	    boolean alive = cells[y][x].get_alive();
	    if(!alive)//もし新しく書けるなら
		change_cell_state(TileToPixel(x),TileToPixel(y));
	}
    }

    
    //フィールドの描画
    public void draw(Graphics g){
	for(int y = 0; y < ROW; y++){
	    for(int x = 0; x < COL; x++){
		cells[y][x].draw(g);
	    }
	}
    }

    //タイル[y][x]がフィールド内かどうか
    public boolean isInField(int x,int y){
	return 0 <= x && x < COL && 0 <= y && y < ROW;
    }

    //ピクセルからタイルへ変換
    public static int PiexelToTile(int pixel){
	return pixel / PIXEL_PER_TILE;
    }

    //タイルからピクセルへ変換
    public static int TileToPixel(int tile){
	return tile * PIXEL_PER_TILE;
    }
}