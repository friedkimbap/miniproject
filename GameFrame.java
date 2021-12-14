package miniProject;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	private ImageIcon startNormalIcon = new ImageIcon("startBtn.gif");
	private ImageIcon startPressedIcon = new ImageIcon("startPressedBtn.gif");
	private ImageIcon startOverIcon = new ImageIcon("startOverBtn.gif");
	private ImageIcon resetNormalIcon = new ImageIcon("resetBtn.gif");
	private ImageIcon resetPressedIcon = new ImageIcon("resetPressedBtn.gif");
	private ImageIcon resetOverIcon = new ImageIcon("resetOverBtn.gif");

	private JMenuItem startMenuItem = new JMenuItem("시작");
	private JMenuItem resetMenuItem = new JMenuItem("초기화");
	private JMenuItem exitMenuItem = new JMenuItem("나가기");
	private JMenuItem rankMenuItem = new JMenuItem("랭킹 확인");
	private JMenuItem addMenuItem = new JMenuItem("단어 추가");

	private JButton startBtn = new JButton(startNormalIcon);
	private JButton resetBtn = new JButton(resetNormalIcon);

	private ScorePanel sPanel = new ScorePanel();
	private EditPanel ePanel = new EditPanel();
	private GamePanel gPanel = new GamePanel();
		
	public GameFrame() {
		setTitle("우당탕탕~ 왁자지껄~ 두더지 대소동!!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,600);
		setResizable(false);
		splitPane();
		makeMenu();
		makeToolBar();
		setVisible(true);
	}
	
	private void splitPane() {
		JSplitPane hPane = new JSplitPane();
		
		getContentPane().add(hPane, BorderLayout.CENTER);
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(525);
		hPane.setEnabled(false);
		hPane.setLeftComponent(gPanel);
		
		JSplitPane pPane = new JSplitPane();
		
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(300);
		pPane.setLeftComponent(sPanel);
		pPane.setRightComponent(ePanel);
		
		hPane.setRightComponent(pPane);
	}
	
	private void makeMenu() {
		JMenuBar mBar = new JMenuBar();
		setJMenuBar(mBar);
		
		JMenu fileMenu = new JMenu("게임");
		JMenu setMenu = new JMenu("설정");
		
		fileMenu.add(startMenuItem);
		fileMenu.add(resetMenuItem);
		
		setMenu.add(rankMenuItem);
		setMenu.add(addMenuItem);
		setMenu.addSeparator();
		setMenu.add(exitMenuItem);
		
		mBar.add(fileMenu);
		mBar.add(setMenu);		
		startMenuItem.addActionListener(new StartAction());
		resetMenuItem.addActionListener(new ResetAction());
		
		exitMenuItem.addActionListener(new ExitAction());
		
		rankMenuItem.addActionListener(new RankAction());
		
		addMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new addWordFrame();
			}
			
		});
	}
	
	private void makeToolBar() {
		JToolBar tBar = new JToolBar();
		tBar.add(startBtn);
		tBar.add(resetBtn);
		getContentPane().add(tBar, BorderLayout.NORTH);
		
		startBtn.addActionListener(new StartAction());
		startBtn.setPressedIcon(startPressedIcon);
		startBtn.setRolloverIcon(startOverIcon);
		resetBtn.addActionListener(new ResetAction());
		resetBtn.setEnabled(false);
		resetBtn.setPressedIcon(resetPressedIcon);
		resetBtn.setRolloverIcon(resetOverIcon);
	}
	
	private class StartAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			gPanel.ggp.gameStart();
			sPanel.timeStart();
			
			startBtn.setEnabled(false);
			resetBtn.setEnabled(true);
		}
	}
	
	private class ResetAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			gPanel.ggp.gameSet();
			sPanel.timeSet();
			
			startBtn.setEnabled(true);
			resetBtn.setEnabled(false);
		}
	}
	
	private class ExitAction implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	private class RankAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gPanel.ggp.gameSet();
			sPanel.timeSet();
			gPanel.changePanel();
			startBtn.setEnabled(true);
			resetBtn.setEnabled(false);
			revalidate();
			repaint();
		}
	}
}

class addWordFrame extends JFrame {
	private JTextField input = new JTextField(15);
	
	private JLabel inputLabel = new JLabel("추가할 단어를 입력하시오.");
	
	private ImageIcon addNormalIcon = new ImageIcon("addBtn.gif");
	private ImageIcon addPressedIcon = new ImageIcon("addPressedBtn.gif");
	private ImageIcon addOverIcon = new ImageIcon("addOverBtn.gif");
	private JButton addBtn = new JButton(addNormalIcon);
	
	public addWordFrame() {
		Container c = getContentPane();
		
		setSize(320,150);
		setResizable(false);
		setTitle("단어 추가");
		
		c.setBackground(Color.LIGHT_GRAY);
		c.setLayout(new FlowLayout());

		input.addActionListener(new AddAction());
		
		addBtn.setPressedIcon(addPressedIcon);
		addBtn.setRolloverIcon(addOverIcon);
		addBtn.addActionListener(new AddAction());
		addBtn.setBorderPainted(false); 
		addBtn.setFocusPainted(false); 
		addBtn.setContentAreaFilled(false);
		addBtn.setPreferredSize(new Dimension(89,33));
		
		inputLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		inputLabel.setForeground(Color.YELLOW);
		inputLabel.setHorizontalAlignment(JLabel.CENTER);

		
		c.add(input);
		c.add(addBtn);
		c.add(inputLabel);
		
		setVisible(true);
	}
	
	private class AddAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				String word = input.getText().trim();
				
				FileWriter wordsFile = new FileWriter("words.txt", true);
				
				if(input.getText().equals("")) {
					inputLabel.setForeground(Color.RED);
					inputLabel.setText("추가할 단어를 입력하시오");
				}
				else if(input.getText().length() > 12) {
					inputLabel.setForeground(Color.RED);
					inputLabel.setText("12글자 이하로 입력하시오.");
				}
				else {
					wordsFile.write('\n');
					wordsFile.write(word,0,word.length());
					wordsFile.close();
					inputLabel.setForeground(Color.YELLOW);
					inputLabel.setText("\"" + word  + "\"가 저장되었습니다.");
					input.setText("");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
