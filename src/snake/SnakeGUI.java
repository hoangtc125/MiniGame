package snake;

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
import javax.swing.Action;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGUI {

	private JFrame myFrame;
	private boolean finished = false;
	private JPanel mainPanel = new JPanel();
	private JTextField tfStatus = new JTextField();
	JLabel lbScoreBot = new JLabel("0");
	JLabel lbScoreYou = new JLabel("0");
	private int scoreBot = 0, 
				scoreYou = 0;
    private Timer timer, botTimer;
    private boolean clock = false,
    				found = false;
    private int games = 0,
    			size = 2;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private int count = 10,
				des = (int) (Math.random() * count * count - 1),
				x,
				y;
	private Timer timerU = new Timer(), timerR = new Timer(), timerL = new Timer(), timerD = new Timer();
	private ArrayList<Integer> snake = new ArrayList<Integer>();
	private ArrayList<Integer> apple = new ArrayList<Integer>();
	private int delay = 100;
    private int period = 200;
    private int interval = 1000;
	JButton btnDown = new JButton("Down");
	JButton btnRight = new JButton("Right");
	JButton btnUp = new JButton("Up");
	JButton btnLeft = new JButton("Left");
	private JTextField tfControl;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SnakeGUI window = new SnakeGUI();
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
	public SnakeGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		for(int i = 0; i < size; i ++) {
			snake.add(i);
		}
		
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
		
		JLabel lblNewLabel = new JLabel("Minigame Baby Snake");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBackground(Color.white);
		lblNewLabel.setFont(new Font("Snap ITC", Font.BOLD, 30));
		topPanel.add(lblNewLabel);
		
		mainPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 15, true), "Game Area ", TitledBorder.CENTER, TitledBorder.TOP, new Font("Snap ITC", Font.BOLD, 20), new Color(0, 0, 0)));
		myFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(count, count));
		for(int i = 0; i < count * count; i++) {
			JButton button = new JButton();
			button.setEnabled(false);
			button.setBackground(Color.lightGray);
			buttons.add(button);
			mainPanel.add(buttons.get(i));
		
		}
		move();
		do {
			des = (int) (Math.random() * count * count - 1);
		}while(snake.contains(des));
		buttons.get(des).setBackground(Color.green);
		apple.add(des);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.white);
		myFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		JLabel lblMadebyTokuDa = new JLabel("MadeBy Toku Da Poet");
		lblMadebyTokuDa.setForeground(Color.DARK_GRAY);
		lblMadebyTokuDa.setFont(new Font("Snap ITC", Font.BOLD, 30));
		bottomPanel.add(lblMadebyTokuDa);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(4, 1));
		myFrame.getContentPane().add(eastPanel, BorderLayout.EAST);
		
		JButton btnReset = new JButton("New Game");
		btnReset.setForeground(Color.DARK_GRAY);
		btnReset.setFont(new Font("Snap ITC", Font.PLAIN, 25));
		btnReset.setBackground(Color.white);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JButton jButton : buttons) {
					jButton.setBackground(Color.LIGHT_GRAY);
				}
				size = 2;
				snake.clear();
				apple.clear();
				for(int i = 0; i < size; i ++) {
					snake.add(i);
				}
				do {
					des = (int) (Math.random() * count * count - 1);
				}while(snake.contains(des));
				buttons.get(des).setBackground(Color.green);
				apple.add(des);
				move();
				timerU.cancel();
				timerL.cancel();
				timerR.cancel();
				timerD.cancel();
				if(games != 0) timer.cancel();
				finished = false;
				interval = 100;
				tfStatus.setText(100 + " s");
				found = false;
				tfStatus.setForeground(null);
				clock = false;
				finished = false;
			}
		});
		
		JPanel notiPanel = new JPanel();
		notiPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 15), "Type Your Control  ", TitledBorder.CENTER, TitledBorder.TOP, new Font("Snap ITC", Font.BOLD, 20), null));
		notiPanel.setBackground(Color.white);
		eastPanel.add(notiPanel);
		notiPanel.setLayout(new BorderLayout());
		tfControl = new JTextField();
		notiPanel.add(tfControl, BorderLayout.CENTER);
		
		tfControl.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_W) {
					tfControl.setText("Up ");
					timerL.cancel();
					timerR.cancel();
					timerD.cancel();
					timerU.cancel();
					timerU = new Timer();
				    timerU.scheduleAtFixedRate(new TimerTask() {
				 
				        public void run() {
				        	if(snake.get(snake.size() - 1) - count == snake.get(snake.size() - 2)) return;
							if(snake.get(snake.size() - 1) < count) {
								snake.add(snake.get(snake.size() - 1) + count * (count - 1));
							} else {
								snake.add(snake.get(snake.size() - 1) - count);
							}
							buttons.get(snake.get(0)).setBackground(Color.LIGHT_GRAY);
							snake.remove(0);
							
							move();
				        }
				    }, delay, period);
				}
				
				if(e.getKeyCode() == KeyEvent.VK_A) {
					tfControl.setText("Left ");
					timerR.cancel();
					timerD.cancel();
					timerU.cancel();
					timerL.cancel();
					timerL = new Timer();
				    timerL.scheduleAtFixedRate(new TimerTask() {
				 
				        public void run() {
				        	if(snake.get(snake.size() - 1) - 1 == snake.get(snake.size() - 2)) return;
							if(snake.get(snake.size() - 1) % count == 0) {
								snake.add(snake.get(snake.size() - 1) + count - 1);
							} else {
								snake.add(snake.get(snake.size() - 1) - 1);
							}
							buttons.get(snake.get(0)).setBackground(Color.LIGHT_GRAY);
							snake.remove(0);
							
							move();
				        }
				    }, delay, period);
				}
				
				if(e.getKeyCode() == KeyEvent.VK_D) {
					tfControl.setText("Right ");
					timerL.cancel();
					timerD.cancel();
					timerU.cancel();
					timerR.cancel();
					timerR = new Timer();
				    timerR.scheduleAtFixedRate(new TimerTask() {
				 
				        public void run() {
							if(snake.get(snake.size() - 1) + 1 == snake.get(snake.size() - 2)) return;
							if(snake.get(snake.size() - 1) % count == count - 1) {
								snake.add(snake.get(snake.size() - 1) - count + 1);
							} else {
								snake.add(snake.get(snake.size() - 1) + 1);
							}
							buttons.get(snake.get(0)).setBackground(Color.LIGHT_GRAY);
							snake.remove(0);
							
							move();
				        }
				    }, delay, period);
				}
				
				if(e.getKeyCode() == KeyEvent.VK_S) {
					tfControl.setText("Down ");
					timerL.cancel();
					timerR.cancel();
					timerU.cancel();
					timerD.cancel();
					timerD = new Timer();
				    timerD.scheduleAtFixedRate(new TimerTask() {
				 
				        public void run() {
				        	if(snake.get(snake.size() - 1) + count == snake.get(snake.size() - 2)) return;
							if(snake.get(snake.size() - 1) >= count * (count - 1)) {
								snake.add(snake.get(snake.size() - 1) - count * (count - 1));
							} else {
								snake.add(snake.get(snake.size() - 1) + count);
							}
							buttons.get(snake.get(0)).setBackground(Color.LIGHT_GRAY);
							snake.remove(0);
							
							move();
				        }
				    }, delay, period);
				}
			}
		});
		tfControl.setFont(new Font("Snap ITC", Font.PLAIN, 30));
		tfControl.setHorizontalAlignment(SwingConstants.CENTER);
		tfControl.setColumns(10);
//		btnUp.setBackground(Color.LIGHT_GRAY);
//		btnUp.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				if(e.getSource() == btnUp) {
//					timerU = new Timer();
//					timerL.cancel();
//					timerR.cancel();
//					timerD.cancel();
//				    timerU.scheduleAtFixedRate(new TimerTask() {
//				 
//				        public void run() {
//				        	if(snake.get(snake.size() - 1) - count == snake.get(snake.size() - 2)) return;
//							if(snake.get(snake.size() - 1) < count) {
//								snake.add(snake.get(snake.size() - 1) + count * (count - 1));
//							} else {
//								snake.add(snake.get(snake.size() - 1) - count);
//							}
//							buttons.get(snake.get(0)).setBackground(Color.LIGHT_GRAY);
//							snake.remove(0);
//							
//							move();
//				        }
//				    }, delay, period);
//				} else {
//					timerU.cancel();
//				}
//			}
//		});
//		
//		JLabel label = new JLabel("");
//		notiPanel.add(label);
//		
//		notiPanel.add(btnUp);
//		
//		JLabel lb2 = new JLabel("");
//		notiPanel.add(lb2);
//		btnLeft.setBackground(Color.LIGHT_GRAY);
//		
//		btnLeft.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				if(e.getSource() == btnLeft) {
//					timerL.cancel();
//					timerR.cancel();
//					timerD.cancel();
//					timerU.cancel();
//					timerL = new Timer();
//				    timerL.scheduleAtFixedRate(new TimerTask() {
//				 
//				        public void run() {
//				        	if(snake.get(snake.size() - 1) - 1 == snake.get(snake.size() - 2)) return;
//							if(snake.get(snake.size() - 1) % count == 0) {
//								snake.add(snake.get(snake.size() - 1) + count - 1);
//							} else {
//								snake.add(snake.get(snake.size() - 1) - 1);
//							}
//							buttons.get(snake.get(0)).setBackground(Color.LIGHT_GRAY);
//							snake.remove(0);
//							
//							move();
//				        }
//				    }, delay, period);
//				} else {
//					timerL.cancel();
//				}
//			}
//		});
//		notiPanel.add(btnLeft);
//		btnDown.setBackground(Color.LIGHT_GRAY);
//		
//		btnDown.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				if(e.getSource() == btnDown) {
//					timerL.cancel();
//					timerR.cancel();
//					timerD.cancel();
//					timerU.cancel();
//					timerD = new Timer();
//				    timerD.scheduleAtFixedRate(new TimerTask() {
//				 
//				        public void run() {
//				        	if(snake.get(snake.size() - 1) + count == snake.get(snake.size() - 2)) return;
//							if(snake.get(snake.size() - 1) >= count * (count - 1)) {
//								snake.add(snake.get(snake.size() - 1) - count * (count - 1));
//							} else {
//								snake.add(snake.get(snake.size() - 1) + count);
//							}
//							buttons.get(snake.get(0)).setBackground(Color.LIGHT_GRAY);
//							snake.remove(0);
//							
//							move();
//				        }
//				    }, delay, period);
//				} else {
//					timerD.cancel();
//				}
//			}
//		});
//		notiPanel.add(btnDown);
//		btnRight.setBackground(Color.LIGHT_GRAY);
//		
//		btnRight.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				if(e.getSource() == btnRight) {
//					timerL.cancel();
//					timerR.cancel();
//					timerD.cancel();
//					timerU.cancel();
//					timerR = new Timer();
//				    timerR.scheduleAtFixedRate(new TimerTask() {
//				 
//				        public void run() {
//							if(snake.get(snake.size() - 1) + 1 == snake.get(snake.size() - 2)) return;
//							if(snake.get(snake.size() - 1) % count == count - 1) {
//								snake.add(snake.get(snake.size() - 1) - count + 1);
//							} else {
//								snake.add(snake.get(snake.size() - 1) + 1);
//							}
//							buttons.get(snake.get(0)).setBackground(Color.LIGHT_GRAY);
//							snake.remove(0);
//							
//							move();
//				        }
//				    }, delay, period);
//				} else {
//					timerR.cancel();
//				}
//			}
//		});
//		notiPanel.add(btnRight);
		
		JPanel timePanel = new JPanel();
		timePanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 15), "Timer:", TitledBorder.CENTER, TitledBorder.TOP, new Font("Snap ITC", Font.BOLD, 20), null));
		eastPanel.add(timePanel);
		timePanel.setLayout(new BorderLayout(0, 0));
		timePanel.add(tfStatus);
		tfStatus.setFont(new Font("Snap ITC", Font.BOLD, 38));
		tfStatus.setHorizontalAlignment(SwingConstants.CENTER);
		tfStatus.setBackground(Color.white);
		tfStatus.setText(100 + " s");
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

	private void move() {
		for(int i = 0; i < snake.size(); i++) {
			buttons.get(snake.get(i)).setBackground(Color.DARK_GRAY);
			buttons.get(snake.get(snake.size() - 1)).setBackground(Color.red);
		}
		do {
			des = (int) (Math.random() * count * count - 1);
		}while(snake.contains(des));
		apple.add(des);
		eat();
	}
	
	private void eat() {
		if(apple.contains(snake.get(snake.size() - 1))) {
			apple.remove(snake.get(snake.size() - 1));
			if(snake.get(1) - snake.get(0) == 1) {
				if(snake.get(0) % count > 0) {
					snake.add(0, snake.get(0) - 1);
				} else {
					snake.add(0, snake.get(0) + count - 1);
				}
			}
			if(snake.get(1) - snake.get(0) == -1) {
				if(snake.get(0) % count == (count - 1)) {
					snake.add(0, snake.get(0) + 1);
				} else {
					snake.add(0, snake.get(0) - count + 1);
				}
			}
			if(snake.get(1) - snake.get(0) == - count) {
				if(snake.get(0) >  count * (count - 1)) {
					snake.add(0, snake.get(0) - count * (count - 1));
				} else {
					snake.add(0, snake.get(0) + count);
				}
			}
			if(snake.get(1) - snake.get(0) == count) {
				if(snake.get(0) < count) {
					snake.add(0, snake.get(0) + count * (count - 1));
				} else {
					snake.add(0, snake.get(0) - count);
				}
			}
			buttons.get(des).setBackground(Color.green);
		}
	}
}
