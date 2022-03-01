package bom;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class myButton {
	private JButton button;
	private int type;
	private int boms;
	private boolean opened;
	
	public myButton() {
		super();
		opened = false;
		this.button = new JButton();
		this.button.setBackground(Color.LIGHT_GRAY);
		this.button.setFont(new Font("Snap ITC", Font.PLAIN, 25));
		boms = 0;
		type = 0;
	}

	public myButton(JButton button, int type) {
		super();
		this.button = button;
		this.type = type;
	}


	public JButton getButton() {
		return button;
	}


	public void setButton(JButton button) {
		this.button = button;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getBoms() {
		return boms;
	}


	public void setBoms(int boms) {
		this.boms = boms;
	}


	public boolean isOpened() {
		return opened;
	}


	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	
	public void show() {
		if (type == 2) {
			button.setText("X");
			button.setBackground(Color.pink);
		} else if (type == 1) {
			button.setText(getBoms() + "");
		} else {
			button.setText("");
		}
	}
	
	public void setDefault() {
		type = boms = 0;
		opened = false;
		button.setBackground(Color.LIGHT_GRAY);
		button.setText("");
	}
}
