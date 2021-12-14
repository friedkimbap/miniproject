package miniProject;

import java.awt.*;

import javax.swing.*;

public class ScorePanel extends JPanel {
	private ImageIcon icon = new ImageIcon("scoreback.png");
	private Image img = icon.getImage();
	
	static public boolean ending = false;
	static public int score = 0;
	
	private int time = 60;
	
	private JLabel textLabel = new JLabel("시간");
	private JLabel timeLabel = new JLabel(Integer.toString(time));
	private JLabel textLabel2 = new JLabel("점수");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	
	private TimeThread timeThread = null;
	
	public ScorePanel() {		
		
		this.setBackground(new Color(172,187,21));
		setLayout(null);
		
		Font font = new Font("맑은 고딕", Font.BOLD, 25);
		
		textLabel.setFont(font);
		textLabel.setForeground(new Color(237,124,47));
		textLabel.setSize(60,40);
		textLabel.setLocation(50,30);
		add(textLabel);
		
		timeLabel.setFont(font);
		timeLabel.setForeground(Color.BLACK);
		timeLabel.setSize(100,40);
		timeLabel.setLocation(160,30);
		add(timeLabel);
		
		textLabel2.setFont(font);
		textLabel2.setForeground(new Color(237,124,47));
		textLabel2.setSize(60,40);
		textLabel2.setLocation(50,90);
		add(textLabel2);
		
		scoreLabel.setFont(font);
		scoreLabel.setForeground(Color.BLACK);
		scoreLabel.setSize(100,40);
		scoreLabel.setLocation(160,90);
		add(scoreLabel);
	}
	
	public void timeStart() {
		ending = false;
		time = 61;
		score = 0;
		timeThread = new TimeThread(time, timeLabel);
		timeThread.start();
	}
	
	public void timeSet() {
		if(timeThread != null) {
			timeThread.interrupt();
			timeThread = null;
			time = 60;
			timeLabel.setText(Integer.toString(time));
			score = 0;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}
	
	class TimeThread extends Thread {
		private int time;
		private JLabel timeLabel = null;
		
		public TimeThread(int time, JLabel timeLabel) {
			this.time=time;
			this.timeLabel=timeLabel;
		}
		
		public void run() {
			while(time > 0) {
				time--;
				timeLabel.setText(Integer.toString(time));
				scoreLabel.setText(Integer.toString(score));
				
				if(GamePanel.timePlus == true) {
					time+=15;
					GamePanel.timePlus = false;
				}
				
				if(GamePanel.timeMinus > 0) {
					time-=(5*GamePanel.timeMinus);
					GamePanel.timeMinus = 0;
				}
				
				try {
					Thread.sleep(1000);
					if(time <= 0) {
						time = 0;
						timeLabel.setText(Integer.toString(time));
						scoreLabel.setText(Integer.toString(score));
						ending = true;
						timeThread = null;
						break;
					}
				} catch (InterruptedException e) {
					if(time <= 0) {
						System.out.println(score);
						time = 0;
						timeLabel.setText(Integer.toString(time));
						scoreLabel.setText(Integer.toString(score));
						ending = true;
						timeThread = null;
						break;
					}
					return;
				}	
			}
		}
		
		public void reduceTime() {
			time-=5;
		}
	}
}
