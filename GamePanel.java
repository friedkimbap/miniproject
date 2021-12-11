package miniProject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	private JTextField input = new JTextField(20);
	private JLabel explain = new JLabel("단어를 빠르게 타이핑 하시오");
	private Word[] words = new Word[3];
	private Word superWord1;
	private Word superWord2;
	private TextSource textSource = new TextSource();
		
	public GameGroundPanel ggp = new GameGroundPanel();
	
	static public boolean timePlus = false;
	static public int timeMinus = 0;
	
	public GamePanel() {	
		setLayout(new BorderLayout());
		add(ggp, BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
		
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField)e.getSource();
				String inWord = t.getText();
				
				// 단어 맞출 경우 단어 삭제
				if(inWord.equals(words[2].la.getText()) && words[2].delete != 1) {
					words[2].la.setVisible(false);
					words[2].la = new JLabel(textSource.get());
					words[2].delete = 1;
				}
				else if(inWord.equals(words[1].la.getText()) && words[1].delete != 1){
					words[1].la.setVisible(false);
					words[1].la = new JLabel(textSource.get());
					words[1].delete = 1;
				}
				else if(inWord.equals(words[0].la.getText()) && words[0].delete != 1) {
					words[0].la.setVisible(false);
					words[0].la = new JLabel(textSource.get());
					words[0].delete = 1;
				}
				else if(inWord.equals(superWord1.la.getText()) && superWord1.delete != 1 && superWord1.la.isVisible() == true) {
					superWord1.la.setVisible(false);
					superWord1.la = new JLabel(textSource.get());
					superWord1.delete = 1;
					// 단어를 모두 없애줌
					for(int i=0;i<3;i++) {
						words[i].la.setVisible(false);
						words[i].la = new JLabel(textSource.get());
						words[i].delete = 1;
					}
				}
				else if(inWord.equals(superWord2.la.getText()) && superWord2.delete != 1 && superWord2.la.isVisible() == true) {
					superWord2.la.setVisible(false);
					superWord2.la = new JLabel(textSource.get());
					superWord2.delete = 1;
					// 시간을 10초 추가해줌
					timePlus = true;
				}
				// 단어 틀릴 경우 시간 감소
				else {
					timeMinus++;
				}			
					
				t.setText("");
			}
		});
	}
	
	
	class GameGroundPanel extends JPanel {
		
		private GameThread gameThread;

		public GameGroundPanel() {
			setLayout(null);
			setBackground(Color.white);
			
			explain.setSize(450, 40);
			explain.setHorizontalAlignment(JLabel.CENTER);
			explain.setFont(new Font("돋움체", Font.BOLD, 20));
			explain.setLocation(50, 250);
			add(explain);
		}
		
		public void gameStart() {
			setBackground(Color.WHITE);
			explain.setVisible(false);
			explain.setForeground(Color.BLACK);
			for(int i=0;i<3;i++) {
				words[i] = new Word();
			}
			superWord1 = new Word();
			superWord2 = new Word();
			gameThread = new GameThread();
			gameThread.start();
		}
		
		public void gameSet() {
			gameThread.interrupt();
		}
		
		class GameThread extends Thread {
			
			private int time;
			
			public GameThread() {
				time = 0;
			}
			
			public void run() {
				while(true) {
					if((time%5) == 0) {
						for(int i=0;i<3;i++) {
							if(words[i].delete == 1) {
								words[i].la.setVisible(true);
								words[i].delete = 0;
							}
							// 시간 안에 못 맞추면 시간 감소
							else if(words[i].delete == 0)
								timeMinus++;
							words[i].la.setSize(100,40);
							words[i].la.setBackground(Color.BLACK);
							words[i].la.setForeground(Color.YELLOW);
							words[i].la.setHorizontalAlignment(JLabel.CENTER);
							words[i].la.setOpaque(true);
							words[i].la.setLocation((int)(Math.random()*(getWidth()-100)),(int)(Math.random()*(getHeight()-30)));
							add(words[i].la);
							
							// 특별 단어 5초 후에 제거
							superWord1.la.setVisible(false);
							superWord1.delete = 1;
							superWord2.la.setVisible(false);
							superWord2.delete = 1;
						}
					}
					// 랜덤한 시간에 특별 단어 생성
					if((time%(20+(5*(int)(Math.random()*3)))) == 0) {
						if(superWord1.delete == 1) {
							superWord1.delete = 0;
						}
						superWord1.la.setVisible(true);
						superWord1.la.setSize(100,40);
						superWord1.la.setBackground(Color.LIGHT_GRAY);
						superWord1.la.setForeground(Color.YELLOW);
						superWord1.la.setHorizontalAlignment(JLabel.CENTER);
						superWord1.la.setOpaque(true);
						superWord1.la.setLocation((int)(Math.random()*(getWidth()-100)),(int)(Math.random()*(getHeight()-30)));
						add(superWord1.la);
					}
					if((time%(10+(5*(int)(Math.random()*3)))) == 0){
						if(superWord2.delete == 1) {
							superWord2.delete = 0;
						}
						superWord2.la.setVisible(true);
						superWord2.la.setSize(100,40);
						superWord2.la.setBackground(Color.DARK_GRAY);
						superWord2.la.setForeground(Color.YELLOW);
						superWord2.la.setHorizontalAlignment(JLabel.CENTER);
						superWord2.la.setOpaque(true);
						superWord2.la.setLocation((int)(Math.random()*(getWidth()-100)),(int)(Math.random()*(getHeight()-30)));
						add(superWord2.la);
					}
					repaint();
					try {
						sleep(1000);
						time++;
						// 시간이 끝나면 게임 종료
						if(ScorePanel.ending == true) {
							for(int i=0;i<3;i++) {
								words[i].la.setVisible(false);
								words[i].la = new JLabel(textSource.get());
								words[i].delete = -1;
							}
							superWord1.la.setVisible(false);
							superWord1.la = new JLabel(textSource.get());
							superWord1.delete = -1;
							
							superWord2.la.setVisible(false);
							superWord2.la = new JLabel(textSource.get());
							superWord2.delete = -1;
							
							GameGroundPanel.this.setBackground(Color.BLACK);
							explain.setForeground(Color.YELLOW);
							explain.setText(Integer.toString(ScorePanel.score) + "초 동안 버텼습니다.");
							explain.setVisible(true);							
							
							break;
						}
					} catch (InterruptedException e) {
						for(int i=0;i<3;i++) {
							words[i].la.setVisible(false);
							words[i].la = new JLabel(textSource.get());
							words[i].delete = -1;
						}
						superWord1.la.setVisible(false);
						superWord1.la = new JLabel(textSource.get());
						superWord1.delete = -1;
						
						superWord2.la.setVisible(false);
						superWord2.la = new JLabel(textSource.get());
						superWord2.delete = -1;
						
						GameGroundPanel.this.setBackground(Color.BLACK);
						explain.setForeground(Color.YELLOW);
						explain.setText("게임을 리셋시켰습니다. 다시 시작해주세요.");
						explain.setVisible(true);							
						
						return;
					}
				}
			}
	}
		
	}
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setLayout(new FlowLayout());
			setBackground(Color.black);
			
			add(input);
		}
	}
	
	class Word {
		int delete = -1;
		JLabel la = new JLabel(textSource.get());
	}
}
