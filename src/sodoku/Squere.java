package sodoku;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.border.LineBorder;

public class Squere {
	private ArrayList<myButton> squeres = new ArrayList<myButton>();
	private ArrayList<Integer> numList = new ArrayList<Integer>();
	private boolean done;

	public Squere() {
		super();
		done = false;
	}

	public ArrayList<myButton> getSqueres() {
		return squeres;
	}

	public void setSqueres(ArrayList<myButton> squeres) {
		this.squeres = squeres;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void check() {
		for(int i = 0; i < 9; i++) {
			numList.add(squeres.get(i).getNumber());
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
				if(squeres.get(i).isLock() == false) {
					squeres.get(i).getButton().setBackground(Color.green);
				} 
			}
		} else {
			for(int i = 0; i < 9; i++) {
				if(squeres.get(i).isLock() == false) {
					squeres.get(i).getButton().setBackground(Color.lightGray);
				} 
			}
		}
	}
	
}
