package caro3;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.Window.Type;
import javax.swing.border.MatteBorder;

public class Caro3 {

	private JFrame myFrame;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private ArrayList<JButton> btnUser = new ArrayList<JButton>();
	private ArrayList<JButton> btnBot = new ArrayList<JButton>();
	private ArrayList<Integer> UserChoice = new ArrayList<Integer>();
	private ArrayList<Integer> BotChoice = new ArrayList<Integer>();
	private boolean finished = false;
	private JTextField tfStatus = new JTextField();
	private JTextArea taNotify = new JTextArea();
	private int scoreBot = 0, 
				scoreYou = 0;
	JLabel lbScoreBot = new JLabel("0");
	JLabel lbScoreYou = new JLabel("0");
    private Timer timer, botTimer;
	private int delay = 1000;
    private int period = 1000;
    private int interval = 100;
    private boolean clock = false;
    private int games = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Caro3 window = new Caro3();
					window.myFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Caro3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		myFrame = new JFrame();
		myFrame.setType(Type.POPUP);
		myFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\ADMIN\\Pictures\\Ảnh nền\\anh bia.jpg"));
		myFrame.setBackground(Color.WHITE);
		myFrame.setTitle("Caro 3");
		myFrame.setBounds(100, 100, 966, 711);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
//		topPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 10, true));
		topPanel.setBackground(Color.white);
		myFrame.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Minigame Caro 3");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBackground(Color.white);
		lblNewLabel.setFont(new Font("Snap ITC", Font.BOLD, 30));
		topPanel.add(lblNewLabel);
		
		JPanel mainPanel = new JPanel();
		
		mainPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 15, true), "Game Area ", TitledBorder.CENTER, TitledBorder.TOP, new Font("Snap ITC", Font.BOLD, 20), new Color(0, 0, 0)));
		myFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(3, 3, 0, 0));
		for(int i = 0; i < 9; i++) {
			JButton btn = new JButton();
			buttons.add(btn);
			buttons.get(i).setBackground(Color.lightGray);
			buttons.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(clock == false) {
						setLock();
					}
					clock = true;
					for(int i = 0; i < 9; i++) {
						if(e.getSource() == buttons.get(i) && finished == false 
								&& !UserChoice.contains(i) && !BotChoice.contains(i)) {
							userGo(i);
							UserChoice.add(i);
							checkDraw();
							checkUserWin();
							botTimer = new Timer();
						    botTimer.scheduleAtFixedRate(new TimerTask() {
						 
						        public void run() {
									BotAttack();
									botTimer.cancel();
						        }
						    }, delay / 5, period);
						}
					}
				}
			});
			mainPanel.add(buttons.get(i));
		}
		
		JPanel bottomPanel = new JPanel();
//		bottomPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 10, true));
		bottomPanel.setBackground(Color.white);
		myFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		JLabel lblMadebyTokuDa = new JLabel("MadeBy Toku Da Poet");
		lblMadebyTokuDa.setForeground(Color.DARK_GRAY);
		lblMadebyTokuDa.setFont(new Font("Snap ITC", Font.BOLD, 30));
		bottomPanel.add(lblMadebyTokuDa);
		
		JPanel eastPanel = new JPanel();
//		eastPanel.setBorder(new MatteBorder(0, 10, 0, 10, (Color) new Color(192, 192, 192)));
		eastPanel.setLayout(new GridLayout(4, 1));
		myFrame.getContentPane().add(eastPanel, BorderLayout.EAST);
		
		JButton btnReset = new JButton("New Game");
		btnReset.setForeground(Color.DARK_GRAY);
		btnReset.setFont(new Font("Snap ITC", Font.PLAIN, 25));
		btnReset.setBackground(Color.white);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < 9; i++) {
//					buttons.get(i).setIcon(null);
					buttons.get(i).setText("");
					buttons.get(i).setBackground(Color.lightGray);
				}
				if(games != 0) timer.cancel();
				interval = 100;
				tfStatus.setText(100 + " s");
				tfStatus.setForeground(null);
				clock = false;
				finished = false;
				btnBot.clear();
				btnUser.clear();
				UserChoice.clear();
				BotChoice.clear();
			}
		});
		
		JPanel notiPanel = new JPanel();
		notiPanel.setBackground(Color.white);
		eastPanel.add(notiPanel);
		notiPanel.setLayout(new GridLayout(3, 1, 0, 0));
		taNotify.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		notiPanel.add(taNotify);
		taNotify.setEditable(false);
		taNotify.setBackground(Color.white);
		taNotify.setText("Bạn có 100s mỗi hiệp đấu\nBạn sẽ thắng khi đạt được 3 ô thẳng nhau");
		
		JLabel lblNewLabel_1 = new JLabel("");
		notiPanel.add(lblNewLabel_1);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setFont(new Font("Snap ITC", Font.PLAIN, 28));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setForeground(Color.DARK_GRAY);
		notiPanel.add(lblTime);
		tfStatus.setFont(new Font("Snap ITC", Font.BOLD, 38));
		tfStatus.setHorizontalAlignment(SwingConstants.CENTER);
		tfStatus.setBackground(Color.white);
		tfStatus.setText(100 + " s");
		
		eastPanel.add(tfStatus);
		tfStatus.setColumns(10);
		tfStatus.setEditable(false);
		
		JPanel scorePanel = new JPanel();
		scorePanel.setBorder(new LineBorder(Color.white));
		scorePanel.setBackground(Color.white);
		eastPanel.add(scorePanel);
		scorePanel.setLayout(new GridLayout(1, 5, 0, 0));
		
		JLabel lblBot = new JLabel("BOT");
		lblBot.setFont(new Font("Snap ITC", Font.BOLD, 28));
		lblBot.setForeground(Color.DARK_GRAY);
		lblBot.setHorizontalAlignment(SwingConstants.CENTER);
		scorePanel.add(lblBot);
		
		lbScoreBot.setHorizontalAlignment(SwingConstants.CENTER);
		lbScoreBot.setFont(new Font("Snap ITC", Font.BOLD, 22));
		lbScoreBot.setForeground(Color.DARK_GRAY);
		scorePanel.add(lbScoreBot);
		
		JLabel lblNewLabel_2 = new JLabel("-");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Snap ITC", Font.PLAIN, 32));
		lblNewLabel_2.setForeground(Color.DARK_GRAY);
		scorePanel.add(lblNewLabel_2);
		
		lbScoreYou.setHorizontalAlignment(SwingConstants.CENTER);
		lbScoreYou.setFont(new Font("Snap ITC", Font.BOLD, 22));
		lbScoreYou.setForeground(Color.DARK_GRAY);
		scorePanel.add(lbScoreYou);
		
		JLabel lbYou = new JLabel("You");
		lbYou.setForeground(Color.DARK_GRAY);
		lbYou.setFont(new Font("Snap ITC", Font.BOLD, 28));
		lbYou.setHorizontalAlignment(SwingConstants.CENTER);
		scorePanel.add(lbYou);
		eastPanel.add(btnReset);
	}
	
	void checkUserWin() { 
		if( (UserChoice.contains(0) && UserChoice.contains(1) && UserChoice.contains(2))
			|| (UserChoice.contains(3) && UserChoice.contains(4) && UserChoice.contains(5))
			|| (UserChoice.contains(6) && UserChoice.contains(7) && UserChoice.contains(8))
			|| (UserChoice.contains(0) && UserChoice.contains(3) && UserChoice.contains(6))
			|| (UserChoice.contains(1) && UserChoice.contains(4) && UserChoice.contains(7))
			|| (UserChoice.contains(2) && UserChoice.contains(5) && UserChoice.contains(8))
			|| (UserChoice.contains(0) && UserChoice.contains(4) && UserChoice.contains(8))
			|| (UserChoice.contains(2) && UserChoice.contains(4) && UserChoice.contains(6))
				) {
			finished = true;
			tfStatus.setText("You Win");
			tfStatus.setForeground(Color.green);
			scoreYou++;
			lbScoreYou.setText(scoreYou + "");
		}
	}
	
	void BotAttack() {
		if(finished == true) return;
		
		// check bot can win
		int mark = 0;
		for(int i = 0; i < 9; i++) {
			if(UserChoice.contains(i) == false && BotChoice.contains(i) == false) {
				BotChoice.add(i);
				if( (BotChoice.contains(0) && BotChoice.contains(1) && BotChoice.contains(2))
						|| (BotChoice.contains(3) && BotChoice.contains(4) && BotChoice.contains(5))
						|| (BotChoice.contains(6) && BotChoice.contains(7) && BotChoice.contains(8))
						|| (BotChoice.contains(0) && BotChoice.contains(3) && BotChoice.contains(6))
						|| (BotChoice.contains(1) && BotChoice.contains(4) && BotChoice.contains(7))
						|| (BotChoice.contains(2) && BotChoice.contains(5) && BotChoice.contains(8))
						|| (BotChoice.contains(0) && BotChoice.contains(4) && BotChoice.contains(8))
						|| (BotChoice.contains(2) && BotChoice.contains(4) && BotChoice.contains(6))
							) {
					botGo(i);
					BotChoice.add(i);
					mark = 1;
					checkDraw();
					checkBotWin();
					return;
				}
				BotChoice.remove(BotChoice.size() - 1);
			}
			if(mark == 1) break;
		}
		
		// check user can win 
		mark = 0;
		for(int i = 0; i < 9; i++) {
			if(UserChoice.contains(i) == false && BotChoice.contains(i) == false) {
				UserChoice.add(i);
				if( (UserChoice.contains(0) && UserChoice.contains(1) && UserChoice.contains(2))
						|| (UserChoice.contains(3) && UserChoice.contains(4) && UserChoice.contains(5))
						|| (UserChoice.contains(6) && UserChoice.contains(7) && UserChoice.contains(8))
						|| (UserChoice.contains(0) && UserChoice.contains(3) && UserChoice.contains(6))
						|| (UserChoice.contains(1) && UserChoice.contains(4) && UserChoice.contains(7))
						|| (UserChoice.contains(2) && UserChoice.contains(5) && UserChoice.contains(8))
						|| (UserChoice.contains(0) && UserChoice.contains(4) && UserChoice.contains(8))
						|| (UserChoice.contains(2) && UserChoice.contains(4) && UserChoice.contains(6))
							) {
					botGo(i);
					BotChoice.add(i);
					mark = 1;
					UserChoice.remove(UserChoice.size() - 1);
					checkDraw();
					checkBotWin();
					return;
				}
				UserChoice.remove(UserChoice.size() - 1);
			}
			if(mark == 1) break;
		}
		
		// Bot go 
		if(UserChoice.size() == 1) {
			if(!UserChoice.contains(4)) {
				BotChoice.add(4);
				botGo(4);
				return;
			} else {
				BotChoice.add(0);
				botGo(0);
				return;
			}
		} 
		
		if(UserChoice.size() == 2 &&
			(UserChoice.get(0) == 0 || UserChoice.get(0) == 2 || UserChoice.get(0) == 6 || UserChoice.get(0) == 8)) {
			if(!UserChoice.contains(1) && !BotChoice.contains(1)) {
				BotChoice.add(1);
				botGo(1);
				return;
			}
			if(!UserChoice.contains(3) && !BotChoice.contains(3)) {
				BotChoice.add(3);
				botGo(3);
				return;
			}
			if(!UserChoice.contains(5) && !BotChoice.contains(5)) {
				BotChoice.add(5);
				botGo(5);
				return;
			}
			if(!UserChoice.contains(7) && !BotChoice.contains(7)) {
				BotChoice.add(7);
				botGo(7);
				return;
			}
		}
		
		if(UserChoice.size() == 2 && UserChoice.get(0) == 4) {
			if(!UserChoice.contains(0) && !BotChoice.contains(0)) {
				BotChoice.add(0);
				botGo(0);
				return;
			}
			if(!UserChoice.contains(2) && !BotChoice.contains(2)) {
				BotChoice.add(2);
				botGo(2);
				return;
			}
			if(!UserChoice.contains(6) && !BotChoice.contains(6)) {
				BotChoice.add(6);
				botGo(6);
				return;
			}
			if(!UserChoice.contains(8) && !BotChoice.contains(8)) {
				BotChoice.add(8);
				botGo(8);
				return;
			}
		}
				
		// check user can win - double 
		for(int i = 0; i < 9; i++) {
			if(UserChoice.contains(i) == false && BotChoice.contains(i) == false) {
				UserChoice.add(i);
				mark = 0;
				for(int j = 0; j < 9; j++) {
					if(UserChoice.contains(j) == false && BotChoice.contains(j) == false) {
						UserChoice.add(j);
						if( (UserChoice.contains(0) && UserChoice.contains(1) && UserChoice.contains(2))
								|| (UserChoice.contains(3) && UserChoice.contains(4) && UserChoice.contains(5))
								|| (UserChoice.contains(6) && UserChoice.contains(7) && UserChoice.contains(8))
								|| (UserChoice.contains(0) && UserChoice.contains(3) && UserChoice.contains(6))
								|| (UserChoice.contains(1) && UserChoice.contains(4) && UserChoice.contains(7))
								|| (UserChoice.contains(2) && UserChoice.contains(5) && UserChoice.contains(8))
								|| (UserChoice.contains(0) && UserChoice.contains(4) && UserChoice.contains(8))
								|| (UserChoice.contains(2) && UserChoice.contains(4) && UserChoice.contains(6))
									) {
							mark++;
						}
						UserChoice.remove(UserChoice.size() - 1);
					}
				}
				if(mark == 2) {
					botGo(i);
					BotChoice.add(i);
					UserChoice.remove(UserChoice.size() - 1);
					checkDraw();
					checkBotWin();
					return;
				}
				UserChoice.remove(UserChoice.size() - 1);
			}
		}
				
		// Bot go random
		do {
			mark = (int) (Math.random() * 10);
		} while(UserChoice.contains(mark) == true || BotChoice.contains(mark) == true || mark > 8);
		BotChoice.add(mark);
		botGo(mark);
		checkBotWin();
		checkDraw();
		return;
	}
	
	void checkBotWin() {
		// check bot win
		if( (BotChoice.contains(0) && BotChoice.contains(1) && BotChoice.contains(2))
				|| (BotChoice.contains(3) && BotChoice.contains(4) && BotChoice.contains(5))
				|| (BotChoice.contains(6) && BotChoice.contains(7) && BotChoice.contains(8))
				|| (BotChoice.contains(0) && BotChoice.contains(3) && BotChoice.contains(6))
				|| (BotChoice.contains(1) && BotChoice.contains(4) && BotChoice.contains(7))
				|| (BotChoice.contains(2) && BotChoice.contains(5) && BotChoice.contains(8))
				|| (BotChoice.contains(0) && BotChoice.contains(4) && BotChoice.contains(8))
				|| (BotChoice.contains(2) && BotChoice.contains(4) && BotChoice.contains(6))
					) {
			finished = true;
			tfStatus.setText("You lose");
			tfStatus.setForeground(Color.red);
			scoreBot++;
			lbScoreBot.setText(scoreBot + "");
			return;
		}
	}
	
	void checkDraw() {
		// check draw
		if(UserChoice.size() + BotChoice.size() == 9) {
			finished = true;
			tfStatus.setText("Draw");
			tfStatus.setForeground(Color.BLUE);
			return;
		}
	}
	
	public void userGo(int i) {
//		buttons.get(i).setIcon(new ImageIcon("C:\\Users\\ADMIN\\eclipse-workspace\\MiniGames\\src\\caro3\\x.png"));
		buttons.get(i).setText("X");
		buttons.get(i).setFont(new Font("Snap ITC", Font.PLAIN, 100));
	}
	
	public void botGo(int i) {
//		buttons.get(i).setIcon(new ImageIcon("C:\\Users\\ADMIN\\eclipse-workspace\\MiniGames\\src\\caro3\\o.png"));
		buttons.get(i).setText("O");
		buttons.get(i).setFont(new Font("Snap ITC", Font.PLAIN, 100));
		buttons.get(i).setBackground(Color.gray);
	}

	private void setLock() {
		games++;
	    timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	 
	        public void run() {
	        	if(finished == true) {
	    	        timer.cancel();
	        		return;
	        	}
	        	interval--;
    	    	tfStatus.setText((interval) + " s");
	    	    if (interval == 0) {
	    	    	tfStatus.setText("You lose");
	    	    	finished = true;
	    	        timer.cancel();
	    	        return;
	    	    }
	        }
	    }, delay, period);
	}
}
