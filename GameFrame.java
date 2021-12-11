package miniProject;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JSplitPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	private ImageIcon startNormalIcon = new ImageIcon("startNormal.gif");
	private ImageIcon startPressedIcon = new ImageIcon("startPressed.gif");
	private ImageIcon startOverIcon = new ImageIcon("startOver.gif");
	private ImageIcon stopNormalIcon = new ImageIcon("stopNormal.gif");
	private ImageIcon stopPressedIcon = new ImageIcon("stopPressed.gif");
	private ImageIcon stopOverIcon = new ImageIcon("stopOver.gif");

	private JMenuItem startMenuItem = new JMenuItem("start");
	private JMenuItem stopMenuItem = new JMenuItem("stop");
	private JMenuItem exitMenuItem = new JMenuItem("exit");

	private JButton startBtn = new JButton(startNormalIcon);
	private JButton resetBtn = new JButton(stopNormalIcon);

	private ScorePanel sPanel = new ScorePanel();
	private EditPanel ePanel = new EditPanel();
	private GamePanel gPanel = new GamePanel();
	
	private boolean startBtnClicked = false;
		
	public GameFrame() {
		setTitle("타이핑 게임");
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
		
		JMenu fileMenu = new JMenu("GAME");
		
		fileMenu.add(startMenuItem);
		fileMenu.add(stopMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		mBar.add(fileMenu);
		
		startMenuItem.addActionListener(new StartAction());
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
		resetBtn.setPressedIcon(stopPressedIcon);
		resetBtn.setRolloverIcon(stopOverIcon);
	}
	
	private class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gPanel.ggp.gameStart();
			sPanel.timeStart();
			
			startBtn.setEnabled(false);
			resetBtn.setEnabled(true);
		}
	}
	
	private class ResetAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gPanel.ggp.gameSet();
			sPanel.timeSet();
			
			startBtn.setEnabled(true);
			resetBtn.setEnabled(false);
		}
	}
}
