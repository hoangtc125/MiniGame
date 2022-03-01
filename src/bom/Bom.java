package bom;

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

public class Bom {

	private JFrame myFrame;
	private boolean finished = false;
	private JPanel mainPanel = new JPanel();
	private ArrayList<myButton> buttons = new ArrayList<myButton>();
	private ArrayList<Integer> bomPossition = new ArrayList<Integer>();
	private JTextField tfStatus = new JTextField();
	private JTextArea taNotify = new JTextArea();
	JLabel lbScoreBot = new JLabel("0");
	JLabel lbScoreYou = new JLabel("0");
	private int scoreBot = 0, 
				scoreYou = 0;
    private Timer timer, botTimer;
	private int delay = 1000;
    private int period = 1000;
    private int interval = 100;
    private boolean clock = false,
    				found = false;
    private int games = 0,
    			count = 10,
    			size = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bom window = new Bom();
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
	public Bom() {
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
		
		JLabel lblNewLabel = new JLabel("Minigame Dò Mìn");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBackground(Color.white);
		lblNewLabel.setFont(new Font("Snap ITC", Font.BOLD, 30));
		topPanel.add(lblNewLabel);
		
		mainPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 15, true), "Game Area ", TitledBorder.CENTER, TitledBorder.TOP, new Font("Snap ITC", Font.BOLD, 20), new Color(0, 0, 0)));
		myFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(size, size, 0, 0));
		makeGame();
		for(int i = 0; i < size * size; i++) {
			buttons.get(i).getButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(clock == false) {
						setLock();
					}
					clock = true;
					for(int i = 0; i < size * size; i++) {
						if(e.getSource() == buttons.get(i).getButton() && buttons.get(i).isOpened() == false && finished == false) {
							buttons.get(i).getButton().setBackground(Color.white);
							clickBtn(i);
							buttons.get(i).setOpened(true);
						}
					}
				}
			});

			mainPanel.add(buttons.get(i).getButton());
		}
		
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
				bomPossition.clear();
				for(int i = 0; i < size * size; i++) {
					buttons.get(i).setDefault();
				}
				count = 10;
				if(games != 0) timer.cancel();
				finished = false;
				interval = 100;
				tfStatus.setText(100 + " s");
				found = false;
				tfStatus.setForeground(null);
				clock = false;
				finished = false;
				makeGame();
			}
		});
		
		JPanel notiPanel = new JPanel();
		notiPanel.setBackground(Color.white);
		eastPanel.add(notiPanel);
		notiPanel.setLayout(new GridLayout(3, 1, 0, 0));
		taNotify.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 15));
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
	
	private void makeGame() {
		int tmp;
		while(count > 0) {
			do {
				tmp = (int) (Math.random() * 100);
			} while(bomPossition.contains(tmp) || tmp > 99);
			bomPossition.add(tmp);
			count--;
		}
		
		// add to panel
		for(int i = 0; i < size * size; i++) {
			myButton myBtn = new myButton();
			buttons.add(myBtn);
			if(bomPossition.contains(i)) {
				buttons.get(i).setType(2);
				buttons.get(i).getButton().setText("");
			} else {
				buttons.get(i).setType(0);
				buttons.get(i).getButton().setText("");
			}
		}
		
		// set up map
		for(int i = 0; i < size * size; i++) {
			int pos;
			tmp = 1;
			if(i  % size == 0 && bomPossition.contains(i) == false) {
				for(int y = -tmp; y <= tmp; y++) {
					for(int x = 0; x <= tmp; x++) {
						pos = i + size * y + x;
						setUpBtn(pos, i);
					}
				}
			}
			
			if(i  % size == size - 1 && bomPossition.contains(i) == false) {
				for(int y = -tmp; y <= tmp; y++) {
					for(int x = -tmp; x <= 0; x++) {
						pos = i + size * y + x;
						setUpBtn(pos, i);
					}
				}
			}
			
			if(i  % size != size - 1 && i % size != 0 && bomPossition.contains(i) == false) {
				for(int y = -tmp; y <= tmp; y++) {
					for(int x = -tmp; x <= tmp; x++) {
						pos = i + size * y + x;
						setUpBtn(pos, i);
					}
				}
			}
//			if(i  % size == 0 && bomPossition.contains(i) == false) {
//				pos = i - size;
//				setUpBtn(pos, i);
//				pos = i + size;
//				setUpBtn(pos, i);
//				pos = i - size + 1;
//				setUpBtn(pos, i);
//				pos = i + 1;
//				setUpBtn(pos, i);
//				pos = i + size + 1;
//				setUpBtn(pos, i);
//			}
//			
//			if(i  % size == size - 1 && bomPossition.contains(i) == false) {
//				pos = i - size;
//				setUpBtn(pos, i);
//				pos = i + size;
//				setUpBtn(pos, i);
//				pos = i - size - 1;
//				setUpBtn(pos, i);
//				pos = i - 1;
//				setUpBtn(pos, i);
//				pos = i + size - 1;
//				setUpBtn(pos, i);
//			}
//			
//			if(i  % size != size - 1 && i % size != 0 && bomPossition.contains(i) == false) {
//				pos = i - size;
//				setUpBtn(pos, i);
//				pos = i + size;
//				setUpBtn(pos, i);
//				pos = i - size - 1;
//				setUpBtn(pos, i);
//				pos = i - 1;
//				setUpBtn(pos, i);
//				pos = i + size - 1;
//				setUpBtn(pos, i);
//				pos = i - size + 1;
//				setUpBtn(pos, i);
//				pos = i + 1;
//				setUpBtn(pos, i);
//				pos = i + size + 1;
//				setUpBtn(pos, i);
//			}
//			
		}
		
	}
	
	private void clickBtn(int i) {
		buttons.get(i).setOpened(true);
		if(buttons.get(i).getType() == 2) {
			buttons.get(i).show();
			for(int index = 0; index < size * size; index++) {
				buttons.get(index).show();
			}
			tfStatus.setText("You lose");
			tfStatus.setForeground(Color.red);
			scoreBot++;
			lbScoreBot.setText(scoreBot + "");
			finished = true;
		} else if(buttons.get(i).getType() == 1) {
			buttons.get(i).show();
		} else {
			openArea(i);
		}
		if(checkWin() == true) {
			finished = true;
			tfStatus.setText("You won");
			tfStatus.setForeground(Color.green);
			for(int index = 0; index < size * size; index++) {
				buttons.get(index).show();
			}
			scoreYou++;
			lbScoreYou.setText(scoreYou + "");
		}
	}
	
	private boolean checkWin() {
		for(int i = 0; i < size * size; i++) {
			if(buttons.get(i).isOpened() == false && buttons.get(i).getType() != 2) {
				return false;
			}
		}
		return true;
	}
	
	private void openArea(int i) {
		int tmp = 1;
		int pos;
		found = false;
		while(found == false) {
			
			if(i  % size == 0 && bomPossition.contains(i) == false) {
				for(int y = -tmp; y <= tmp; y++) {
					for(int x = 0; x <= tmp; x++) {
						pos = i + size * y + x;
						findArea(pos, i);
					}
				}
			}
			
			if(i  % size == size - 1 && bomPossition.contains(i) == false) {
				for(int y = -tmp; y <= tmp; y++) {
					for(int x = -tmp; x <= 0; x++) {
						pos = i + size * y + x;
						findArea(pos, i);
					}
				}
			}
			
			if(i  % size != size - 1 && i % size != 0 && bomPossition.contains(i) == false) {
				for(int y = -tmp; y <= tmp; y++) {
					for(int x = -tmp; x <= tmp; x++) {
						pos = i + size * y + x;
						findArea(pos, i);
					}
				}
			}
			
			tmp++;
		}
		tmp -= 2;
		if(i  % size == 0 && bomPossition.contains(i) == false) {
			for(int y = -tmp; y <= tmp; y++) {
				for(int x = 0; x <= tmp; x++) {
					pos = i + size * y + x;
					showArea(pos);
				}
			}
		}
		
		if(i  % size == size - 1 && bomPossition.contains(i) == false) {
			for(int y = -tmp; y <= tmp; y++) {
				for(int x = -tmp; x <= 0; x++) {
					pos = i + size * y + x;
					showArea(pos);
				}
			}
		}
		
		if(i  % size != size - 1 && i % size != 0 && bomPossition.contains(i) == false) {
			for(int y = -tmp; y <= tmp; y++) {
				for(int x = -tmp; x <= tmp; x++) {
					pos = i + size * y + x;
					showArea(pos);
				}
			}
		}
	}
	
	private void showArea(int pos) {
		if(pos >= 0 && pos < size * size) {
			buttons.get(pos).show();
			buttons.get(pos).getButton().setBackground(Color.white);
		}
	}
	
	private void findArea(int pos, int i) {
		if(pos >= 0 && pos < size * size && buttons.get(pos).getType() == 2) {
			found = true;
		}
	}
	
	private void setUpBtn(int pos, int i) {
		if(pos >= 0 && pos < size * size && buttons.get(pos).getType() == 2) {
			buttons.get(i).setBoms(buttons.get(i).getBoms() + 1);
			buttons.get(i).setType(1);
		}
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
