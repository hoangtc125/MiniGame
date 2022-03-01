package sodoku;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

public class Col {
	private ArrayList<myButton> cols = new ArrayList<myButton>();
	private ArrayList<Integer> numList = new ArrayList<Integer>();
	private boolean done;

	public Col() {
		super();
		done = false;
	}

	public ArrayList<myButton> getCols() {
		return cols;
	}

	public void setCols(ArrayList<myButton> cols) {
		this.cols = cols;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void check() {
		for(int i = 0; i < 9; i++) {
			numList.add(cols.get(i).getNumber());
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
				if(cols.get(i).isLock() == false) {
					cols.get(i).getButton().setBackground(Color.green);
				} 
			}
		} else {
			for(int i = 0; i < 9; i++) {
				if(cols.get(i).isLock() == false) {
					cols.get(i).getButton().setBackground(Color.lightGray);
				} 
			}
		}
	}
	
}
