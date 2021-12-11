package miniProject;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;

public class ScorePanel extends JPanel {
	static public boolean ending = false;
	static public int score = 0;
	
	private int time = 61;
	private JLabel textLabel = new JLabel("시간");
	private JLabel timeLabel = new JLabel(Integer.toString(time));
	private TimeThread timeThread;
	
	public ScorePanel() {		
		
		this.setBackground(Color.gray);
		setLayout(null);
		
		Font font = new Font("Godic", Font.BOLD, 20);
		
		textLabel.setFont(font);
		textLabel.setForeground(Color.cyan);
		textLabel.setSize(50,20);
		textLabel.setLocation(50,30);
		add(textLabel);
		
		timeLabel.setFont(font);
		timeLabel.setForeground(Color.yellow);
		timeLabel.setSize(100,20);
		timeLabel.setLocation(160,30);
		add(timeLabel);
	}
	
	public void timeStart() {
		ending = false;
		time = 61;
		score = 0;
		timeThread = new TimeThread(time, timeLabel);
		timeThread.start();
	}
	
	public void timeSet() {
		timeThread.interrupt();
		time = 61;
		score = 0;
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
					score++;
					if(time <= 0) {
						System.out.println(score);
						time = 0;
						timeLabel.setText(Integer.toString(time));
						ending = true;
						break;
					}
				} catch (InterruptedException e) {
					if(time <= 0) {
						System.out.println(score);
						time = 0;
						timeLabel.setText(Integer.toString(time));
						ending = true;
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
