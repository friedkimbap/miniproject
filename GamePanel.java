package miniProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	private int panelChange = 0;
	
	// 단어를 입력할 입력창
	private JTextField input = new JTextField(20);
	
	// 시작하기 전에 설명해주는 텍스트
	private JLabel explain;
	
	// 두더지의 이미지를 가지고 있는 JLabel
	private ImageIcon mIcon = new ImageIcon("mole.png");
	private Image mImg = mIcon.getImage();
	private JLabel[] moleImage = new JLabel[3];
	
	// 두더지 퇴치기의 이미지를 가지고 있는 JLabel
	private ImageIcon kIcon = new ImageIcon("killMole.png");
	private Image kImg = kIcon.getImage();
	private JLabel kMoleImage = new JLabel();
	
	// 시계의 이미지를 가지고 있는 JLabel
	private ImageIcon cIcon = new ImageIcon("clock.png");
	private Image cImg = cIcon.getImage();
	private JLabel clockImage = new JLabel();
	
	// 맞춰야할 단어를 가르키는 Word 객체
	private Word[] words = new Word[3];
	private Word superWord1;
	private Word superWord2;
	
	// 단어를 불러오기 위한 객체
	private TextSource textSource = new TextSource();
	
	// 각각 랭크를 보여주는 패널, 게임 화면을 보여주는 패널
	private RankPanel rp;
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
					ScorePanel.score += inWord.length();

					words[2].la.setVisible(false);
					words[2].la = new JLabel(textSource.get());
					words[2].delete = 1;
					moleImage[2].setVisible(false);
				}
				else if(inWord.equals(words[1].la.getText()) && words[1].delete != 1){
					ScorePanel.score += inWord.length();

					words[1].la.setVisible(false);
					words[1].la = new JLabel(textSource.get());
					words[1].delete = 1;
					moleImage[1].setVisible(false);
				}
				else if(inWord.equals(words[0].la.getText()) && words[0].delete != 1) {
					ScorePanel.score += inWord.length();
					
					words[0].la.setVisible(false);
					words[0].la = new JLabel(textSource.get());
					words[0].delete = 1;
					moleImage[0].setVisible(false);
				}
				else if(inWord.equals(superWord1.la.getText()) && superWord1.delete != 1 && superWord1.la.isVisible() == true) {
					superWord1.la.setVisible(false);
					superWord1.la = new JLabel(textSource.get());
					superWord1.delete = 1;
					
					kMoleImage.setVisible(false);
					// 단어를 모두 없애줌
					for(int i=0;i<3;i++) {
						if(words[i].delete != 1) {
							ScorePanel.score += words[i].la.getText().length();
						}
						
						words[i].la.setVisible(false);
						words[i].la = new JLabel(textSource.get());
						words[i].delete = 1;
						moleImage[i].setVisible(false);
						

					}
				}
				else if(inWord.equals(superWord2.la.getText()) && superWord2.delete != 1 && superWord2.la.isVisible() == true) {
					superWord2.la.setVisible(false);
					superWord2.la = new JLabel(textSource.get());
					superWord2.delete = 1;
					
					clockImage.setVisible(false);
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
	
	public void changePanel() {
		if(panelChange == 0) {
			panelChange = 1;
			this.removeAll();
			rp = new RankPanel();
			add(rp, BorderLayout.CENTER);
			revalidate();
			repaint();
		}
		else {
			panelChange = 0;
			this.removeAll();
			ggp = new GameGroundPanel();
			add(ggp, BorderLayout.CENTER);
			add(new InputPanel(), BorderLayout.SOUTH);
			revalidate();
			repaint();
		}
	}
	
	
	class GameGroundPanel extends JPanel {
		private ImageIcon icon = new ImageIcon("gameback.png");
		private Image img = icon.getImage();
		
		private GameThread gameThread = null;
		
		private Font f = new Font("맑은 고딕", Font.PLAIN, 15);

		public GameGroundPanel() {
			setLayout(null);
			setBackground(Color.white);
			
			explain = new JLabel("단어를 빠르게 타이핑 하시오");
			
			explain.setSize(520, 40);
			explain.setHorizontalAlignment(JLabel.CENTER);
			explain.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			explain.setLocation(0, 250);
			add(explain);
		}
		
		// 게임 시작, 변수들 초기화
		public void gameStart() {
			setBackground(Color.WHITE);
			explain.setVisible(false);
			explain.setForeground(Color.BLACK);
			for(int i=0;i<3;i++) {
				words[i] = new Word();
				moleImage[i] = new JLabel();
				moleImage[i].setIcon(mIcon);
			}
			superWord1 = new Word();
			
			kMoleImage.setIcon(kIcon);

			superWord2 = new Word();
			
			clockImage.setIcon(cIcon);
			
			gameThread = new GameThread();
			gameThread.start();
		}
		
		// 게임 종료
		public void gameSet() {
			if(gameThread != null) {
				gameThread.interrupt();
				gameThread = null;
				for(int i=0;i<3;i++) {
					words[i].la.setVisible(false);
					words[i].la = new JLabel(textSource.get());
					words[i].delete = -1;
					moleImage[i].setVisible(false);
				}
				superWord1.la.setVisible(false);
				superWord1.la = new JLabel(textSource.get());
				superWord1.delete = -1;
				
				kMoleImage.setVisible(false);
				
				superWord2.la.setVisible(false);
				superWord2.la = new JLabel(textSource.get());
				superWord2.delete = -1;
				
				clockImage.setVisible(false);
				
				GameGroundPanel.this.setBackground(Color.BLACK);
				explain.setForeground(Color.YELLOW);
				explain.setText("게임이 리셋되었습니다. 다시 시작해주세요.");
				explain.setVisible(true);				
			}
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		
		class GameThread extends Thread {
			
			private int time;
			private int getTime1;
			private int getTime2;
			private boolean first1;
			private boolean first2;
			
			public GameThread() {
				time = 0;
				getTime1 = 0;
				getTime2 = 0;
				first1 = true;
				first2 = true;
			}
			
			public void run() {
				while(true) {
					if((time%5) == 0) {
						for(int i=0;i<3;i++) {
							if(words[i].delete == 1) {
								words[i].la.setVisible(true);
								words[i].delete = 0;
								moleImage[i].setVisible(true);
							}
							// 시간 안에 못 맞추면 시간 감소
							else if(words[i].delete == 0)
								timeMinus++;
							words[i].la.setFont(f);
							words[i].la.setSize(100,40);
							words[i].la.setForeground(Color.GREEN);
							words[i].la.setHorizontalAlignment(JLabel.CENTER);
							words[i].la.setLocation(16+(int)(Math.random()*(getWidth()-132)),77+(int)(Math.random()*(getHeight()-118)));
							add(words[i].la);
							
							moleImage[i].setSize(150,135);
							moleImage[i].setLocation(words[i].la.getX()-15 ,words[i].la.getY()-85);
							add(moleImage[i]);
							
							// 특별 단어 5초 후에 제거
							superWord1.la.setVisible(false);
							superWord1.delete = 1;
							
							kMoleImage.setVisible(false);
						
							superWord2.la.setVisible(false);
							superWord2.delete = 1;
							
							clockImage.setVisible(false);
							
						}
					}
					
					// 랜덤한 시간에 특별 단어 생성
					if(((time-getTime1)%(20+(5*(int)(Math.random()*2)))) == 0) {
						if(first1 == true) {
							first1 = false;
						}
						else {
							if(superWord1.delete == 1) {
								superWord1.delete = 0;
							}
							superWord1.la.setFont(f);
							superWord1.la.setVisible(true);
							superWord1.la.setSize(100,40);
							superWord1.la.setForeground(Color.BLACK);
							superWord1.la.setHorizontalAlignment(JLabel.CENTER);
							superWord1.la.setLocation(15+(int)(Math.random()*(getWidth()-131)),15+(int)(Math.random()*(getHeight()-160)));
							add(superWord1.la);
							
							kMoleImage.setVisible(true);
							kMoleImage.setSize(150,160);
							kMoleImage.setLocation(superWord1.la.getX() - 14, superWord1.la.getY() - 14);
							add(kMoleImage);
							
							getTime1=time;
						}
					}
					if(((time-getTime2)%(10+(5*(int)(Math.random()*2)))) == 0){
						if(first2 == true) {
							first2 = false;
						}
						else {
							if(superWord2.delete == 1) {
								superWord2.delete = 0;
							}
							superWord2.la.setFont(f);
							superWord2.la.setVisible(true);
							superWord2.la.setSize(100,40);
							superWord2.la.setForeground(Color.WHITE);
							superWord2.la.setHorizontalAlignment(JLabel.CENTER);
							superWord2.la.setLocation(20+(int)(Math.random()*(getWidth()-140)),11+(int)(Math.random()*(getHeight()-144)));
							add(superWord2.la);

							clockImage.setVisible(true);
							clockImage.setSize(150,150);
							clockImage.setLocation(superWord2.la.getX() - 20, superWord2.la.getY() - 14);
							add(clockImage);
												
							getTime2=time;
						}
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
								moleImage[i].setVisible(false);
							}
							superWord1.la.setVisible(false);
							superWord1.la = new JLabel(textSource.get());
							superWord1.delete = -1;
							
							kMoleImage.setVisible(false);
							
							superWord2.la.setVisible(false);
							superWord2.la = new JLabel(textSource.get());
							superWord2.delete = -1;
							
							clockImage.setVisible(false);
							
							GameGroundPanel.this.setBackground(Color.BLACK);
							explain.setForeground(Color.YELLOW);
							explain.setText(Integer.toString(ScorePanel.score) + "점을 획득하였습니다!");
							explain.setVisible(true);							
							
							break;
						}
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		}
	}
	
	class RankPanel extends JPanel {
		private ImageIcon icon = new ImageIcon("rankback.png");
		private Image img = icon.getImage();
		
		private int[] list;
		private int temp;
		private HashMap<Integer, String> h = new HashMap<Integer, String>();
		
		private ImageIcon closeIcon = new ImageIcon("closeBtn.gif");
		private ImageIcon closePressedIcon = new ImageIcon("closePressedBtn.gif");
		private ImageIcon closeOverIcon = new ImageIcon("closeOverBtn.gif");
		private JButton closeBtn = new JButton(closeIcon);
		
		private JLabel[] la = new JLabel[10];
		
		private Font f = new Font("맑은 고딕", Font.BOLD, 20);	

		public RankPanel(){
			Scanner s;
			
			setBackground(Color.CYAN);
						
			try {
				s = new Scanner(new File("rank.txt"));
				while(s.hasNextLine()) {
					String str =s.nextLine();
					h.put(Integer.parseInt(str.split(" ")[1]),str.split(" ")[0]);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			list = new int[h.size()];
			
			int k=0;
			for(Integer key : h.keySet()) {
					list[k++] = key;
				}
			sort(list.length);
			
			setLayout(null);
			
			la[0] = new JLabel(list[0] + "  " + h.get(list[0]));
			la[0].setHorizontalAlignment(JLabel.CENTER);
			la[0].setForeground(new Color(218,166,0));
			la[0].setFont(f);
			la[0].setBounds(168, 125, 200, 20);
			add(la[0]);
			
			la[1] = new JLabel(list[1] + "  " + h.get(list[1]));
			la[1].setHorizontalAlignment(JLabel.CENTER);
			la[1].setForeground(new Color(110,110,110));
			la[1].setFont(f);
			la[1].setBounds(-2, 138, 200, 20);
			add(la[1]);
			
			la[2] = new JLabel(list[2] + "  " + h.get(list[2]));
			la[2].setHorizontalAlignment(JLabel.CENTER);
			la[2].setForeground(new Color(215,98,19));
			la[2].setFont(f);
			la[2].setBounds(340, 151, 200, 20);
			add(la[2]);
			
			if(list.length < 10) {
				for(int i=3;i<list.length;i++) {
					la[i] = new JLabel((i + 1) + "등  :  " + list[i] + "   " + h.get(list[i]));
					la[i].setForeground(Color.BLACK);
					la[i].setFont(new Font("맑은 고딕", Font.PLAIN, 15));
					la[i].setBounds(215, 150 + (i*30), 400, 20);
					add(la[i]);
				}
			}
			else {
				for(int i=3;i<9;i++) {
					la[i] = new JLabel((i + 1) + "등  :  " + list[i] + "   " + h.get(list[i]));
					la[i].setForeground(Color.BLACK);
					la[i].setFont(new Font("맑은 고딕", Font.PLAIN, 15));
					la[i].setBounds(213, 150 + (i*30), 400, 20);
					add(la[i]);
				}
				la[9] = new JLabel((9 + 1) + "등  :  " + list[9] + "   " + h.get(list[9]));
				la[9].setForeground(Color.BLACK);
				la[9].setFont(new Font("맑은 고딕", Font.PLAIN, 15));
				la[9].setBounds(205, 420, 400, 20);
				add(la[9]);
			}
			
			closeBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					changePanel();
				}		
			});
			
			closeBtn.setSize(38, 24);
			closeBtn.setLocation(460, 20);
			closeBtn.setPressedIcon(closePressedIcon);
			closeBtn.setRolloverIcon(closeOverIcon);
			add(closeBtn);
		}
		
		private void sort(int n) {			
			for(int i=n-1;i>0;i--)
				for(int j=0;j<i;j++) {
					if(list[j]<list[j+1])
							swap(j, j+1);
				}
		}
		
		private void swap(int x, int y) {
			temp=list[x];list[x]=list[y];list[y]=temp;
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setLayout(new FlowLayout());
			setBackground(new Color(132,60,12));
			
			add(input);
		}
	}
	
	class Word {
		int delete = -1;
		JLabel la = new JLabel(textSource.get());
		
		public Word() {
			la.setSize(100, 40);
			la.setLocation(0, 0);
		}
	}
}
