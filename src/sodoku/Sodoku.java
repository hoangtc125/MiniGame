package sodoku;

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
import java.awt.Button;

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

public class Sodoku {

	private JFrame myFrame;
	private ArrayList<JPanel> panels = new ArrayList<JPanel>();
	private ArrayList<myButton> buttons = new ArrayList<myButton>();
	private ArrayList<myButton> numbers = new ArrayList<myButton>();
	private ArrayList<Row> row = new ArrayList<Row>();
	private ArrayList<Col> col = new ArrayList<Col>();
	private ArrayList<Squere> squere = new ArrayList<Squere>();
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
    private int interval = 1000;
    private boolean clock = false;
    private int games = 0;
    private int current = -1, prev = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sodoku window = new Sodoku();
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
	public Sodoku() {
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
		myFrame.setBounds(200, 0, 966, 800);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
//		topPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 10, true));
		topPanel.setBackground(Color.white);
		myFrame.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Minigame Sodoku");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBackground(Color.white);
		lblNewLabel.setFont(new Font("Snap ITC", Font.BOLD, 30));
		topPanel.add(lblNewLabel);
		
		JPanel mainPanel = new JPanel();
		
		mainPanel.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 15, true), "Game Area ", TitledBorder.CENTER, TitledBorder.TOP, new Font("Snap ITC", Font.BOLD, 20), new Color(0, 0, 0)));
		myFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(3, 3, 3, 3));
		for(int i = 0; i < 9; i++) {
			JPanel panel = new JPanel(new GridLayout(3, 3, 1, 1));
			panels.add(panel);
			Squere tmpSquere = new Squere();
			squere.add(tmpSquere);
			Row tmpRow = new Row();
			row.add(tmpRow);
			Col tmpCol = new Col();
			col.add(tmpCol);
			mainPanel.add(panels.get(i));
		}
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				myButton button = new myButton();
				buttons.add(button);
				buttons.get(y * 9 + x).getButton().addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(clock == false) {
							setLock();
						}
						clock = true;
						for(int i = 0; i < 9 * 9; i++) {
							if(e.getSource() == buttons.get(i).getButton() && buttons.get(i).isLock() == false) {
								prev = current;
								current = i;
								buttons.get(i).getButton().setBorder(new LineBorder(Color.yellow, 3));
							} else if (e.getSource() != buttons.get(i).getButton() && buttons.get(i).isLock() == false){
								buttons.get(prev > -1 ? prev : 0).getButton().setBorder(null);
							}
						}
					}
				});
				panels.get((y / 3) * 3 + x / 3).add(buttons.get(y * 9 + x).getButton());
			}
		}
		
		// add row, col, squere
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				row.get(y).getRows().add(buttons.get(y * 9 + x));
				col.get(x).getCols().add(buttons.get(y * 9 + x));
				squere.get((y / 3) * 3 + x / 3).getSqueres().add(buttons.get(y * 9 + x));
			} 
		}

		makeGame();
		
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
				for(int i = 0; i < 9 * 9; i++) {
					buttons.get(i).setDefault();
				}
				makeGame();
				if(games != 0) timer.cancel();
				interval = 1000;
				tfStatus.setText(interval + " s");
				tfStatus.setForeground(null);
				clock = false;
				finished = false;
			}
		});
		
		JPanel notiPanel = new JPanel();
		notiPanel.setBackground(Color.white);
		eastPanel.add(notiPanel);
		notiPanel.setLayout(new BorderLayout(0, 0));
		taNotify.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		notiPanel.add(taNotify, BorderLayout.NORTH);
		taNotify.setEditable(false);
		taNotify.setBackground(Color.white);
		taNotify.setText("Bạn có 1000s mỗi hiệp đấu\nBạn sẽ thắng khi đạt được 3 ô thẳng nhau");
		
		JPanel numPanel = new JPanel(new BorderLayout());
		numPanel.setBorder(new TitledBorder(new MatteBorder(0, 70, 0, 70, (Color) new Color(192, 192, 192)), "Choose your number ", TitledBorder.CENTER, TitledBorder.TOP, new Font("Snap ITC", Font.BOLD, 15), new Color(0, 0, 0)));
		notiPanel.add(numPanel);
		
		JPanel numberPanel = new JPanel(new GridLayout(3, 3));
		numberPanel.setBackground(Color.LIGHT_GRAY);
		for(int i = 0; i < 9; i++) {
			myButton button = new myButton();
			numbers.add(button);
			numbers.get(i).setNumber(9 - i);
			numbers.get(i).getButton().setBackground(Color.LIGHT_GRAY);
			numbers.get(i).getButton().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 0; i < 9; i++) {
						if(e.getSource() == numbers.get(i).getButton()) {
							buttons.get(current).setNumber(9 - i);
							checkDone();
						}
					}
				}
			});
			numberPanel.add(numbers.get(i).getButton());
		}
		numPanel.add(numberPanel, BorderLayout.CENTER);
		
		JPanel timePanel = new JPanel();
		timePanel.setBorder(new TitledBorder(null, "Time: ", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Snap ITC", Font.PLAIN, 25), null));
		eastPanel.add(timePanel);
		timePanel.setLayout(new BorderLayout(0, 0));
		timePanel.add(tfStatus);
		tfStatus.setFont(new Font("Snap ITC", Font.BOLD, 38));
		tfStatus.setHorizontalAlignment(SwingConstants.CENTER);
		tfStatus.setBackground(Color.white);
		tfStatus.setText(interval + " s");
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
	
	private boolean checkValue(int value, int pos, int y) {
		for (myButton tmpButton : row.get(y).getRows()) {
			if(tmpButton.getNumber() == value) {
				return false;
			}
		}
		for (myButton tmpButton : col.get(pos).getCols()) {
			if(tmpButton.getNumber() == value) {
				return false;
			}
		}
		for (myButton tmpButton : squere.get((y / 3) * 3 + pos / 3).getSqueres()) {
			if(tmpButton.getNumber() == value) {
				return false;
			}
		}
		return true;
	}
	
	private void checkDone() {
		int pos = ((current - current % 9) / 27) * 3 + (current % 9) / 3;
		int countR = 0, countC = 0, countS = 0;
		for(int i = 0; i < 9; i++) {
			if(row.get(current / 9).getRows().get(i).getNumber() == buttons.get(current).getNumber()) {
				countR++;
			}
			if(col.get(current % 9).getCols().get(i).getNumber() == buttons.get(current).getNumber()) {
				countC++;
			}
			if(squere.get(pos).getSqueres().get(i).getNumber() == buttons.get(current).getNumber()) {
				countS++;
			}
			if(countR > 1 || countC > 1|| countS > 1) {
				buttons.get(current).getButton().setForeground(Color.red);
			} else {
				buttons.get(current).getButton().setForeground(null);
			}
		}
		for(int i = 0; i < 9; i++) {
			row.get(i).check();
			col.get(i).check();
			squere.get(i).check();
		}
	}
	
	private boolean checkPossition(int pos, int y) {
		int tmp = 0;
		int tmp2 = 0;
		for(int i = (y / 3) * 3; i < (y / 3) * 3 + 3; y++) {
			if(col.get(pos).getCols().get(i).isLock()) {
				tmp++;
			}
		} 
		
		for (myButton tmpButton : col.get(pos).getCols()) {
			if(tmpButton.isLock()) {
				tmp2++;
			}
		}
		
		if(tmp2 < 3 && tmp == 0) {
			return true;
		}
		return false;
	}
	
	private void makeGame() {
		int pos = 0, value;
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 9; x++) {
				if(x % 3 == 0) {

					do {
						pos = (int) (Math.random() * 10);
					} while (checkPossition(pos + (x / 3) * 3, y) == false || pos > 2);
					pos += (x / 3) * 3; 
					do {
						value = (int) (Math.random() * 10);
					} while(checkValue(value, pos, y) == false || value > 9 || value < 1);
					buttons.get(y * 9 + pos).setLock(value);
				}
			}
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
