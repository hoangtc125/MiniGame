package sodoku;

import java.awt.Button;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

public class Row {
	private ArrayList<myButton> rows = new ArrayList<myButton>();
	private ArrayList<Integer> numList = new ArrayList<Integer>();
	private boolean done;

	public Row() {
		super();
		done = false;
	}

	
	public boolean isDone() {
		return done;
	}


	public void setDone(boolean done) {
		this.done = done;
	}


	public ArrayList<myButton> getRows() {
		return rows;
	}

	public void setRows(ArrayList<myButton> rows) {
		this.rows = rows;
	}
	
	public void check() {
		for(int i = 0; i < 9; i++) {
			numList.add(rows.get(i).getNumber());
		}
		boolean mark = true;
		for(int i = 1; i <= 9; i++) {
			if(numList.contains(i) == false) {
				mark = false;
				break;
			}
		}
		if(mark == true) {
			done = true;
			for(int i = 0; i < 9; i++) {
				if(rows.get(i).isLock() == false) {
					rows.get(i).getButton().setBackground(Color.green);
				} 
			}
		} else {
			for(int i = 0; i < 9; i++) {
				if(rows.get(i).isLock() == false) {
					rows.get(i).getButton().setBackground(Color.lightGray);
				} 
			}
		}
	}
}
