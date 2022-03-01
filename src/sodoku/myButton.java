package sodoku;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class myButton {
	private JButton button;
	private int number;
	private boolean lock;
	
	public myButton() {
		super();
		this.button = new JButton();
		this.button.setBackground(Color.LIGHT_GRAY);
		this.button.setFont(new Font("Snap ITC", Font.PLAIN, 30));
		this.number = -1;
		this.lock = false;
	}
	

	public boolean isLock() {
		return lock;
	}


	public void setLock(boolean lock) {
		this.lock = lock;
	}


	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		this.button.setText(this.number + "");
	}
	
	public void setLock(int number) {
		this.number = number;
		this.button.setText(this.number + "");
		this.button.setBackground(Color.gray);
		this.lock = true;
	}
	
	public void setDefault() {
		this.button.setText("");
		this.button.setBackground(Color.LIGHT_GRAY);
		this.number = -1;
		this.lock = false;
	}

}
