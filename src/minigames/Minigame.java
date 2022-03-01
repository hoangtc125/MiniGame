package minigames;

import java.awt.EventQueue;
import bom.*;
import caro3.*;
import snake.*;
import sodoku.*;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Minigame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Minigame window = new Minigame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Minigame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 2, 0, 0));
		
		JButton btnDMn = new JButton("Dò mìn");
		btnDMn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bom.main(null);
			}
		});
		frame.getContentPane().add(btnDMn);
		
		JButton btnNewButton = new JButton("Caro 3");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				caro3.Caro3.main(null);
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		JButton btnRnCnMi = new JButton("Rắn cắn mồi");
		btnRnCnMi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				snake.SnakeGUI.main(null);
			}
		});
		frame.getContentPane().add(btnRnCnMi);
		
		JButton btnSodoku = new JButton("Sodoku");
		btnSodoku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sodoku.Sodoku.main(null);
			}
		});
		frame.getContentPane().add(btnSodoku);
	}

}
