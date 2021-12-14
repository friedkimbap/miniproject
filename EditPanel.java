package miniProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class EditPanel extends JPanel {
	private ImageIcon saveNormalIcon = new ImageIcon("saveBtn.gif");
	private ImageIcon savePressedIcon = new ImageIcon("savePressedBtn.gif");
	private ImageIcon saveOverIcon = new ImageIcon("saveOverBtn.gif");
	
	private JTextField edit = new JTextField(10);
	
	private JButton saveBtn = new JButton(saveNormalIcon);
	
	private JLabel editLabel = new JLabel("플레이어 이름을 입력하시오.");
	
	public EditPanel() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new FlowLayout());
		
		edit.addActionListener(new SaveAction());
		
		saveBtn.setPressedIcon(savePressedIcon);
		saveBtn.setRolloverIcon(saveOverIcon);
		saveBtn.addActionListener(new SaveAction());
		saveBtn.setBorderPainted(false); 
		saveBtn.setFocusPainted(false); 
		saveBtn.setContentAreaFilled(false);
		saveBtn.setPreferredSize(new Dimension(89,33));
				
		editLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		editLabel.setForeground(Color.YELLOW);
		editLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(edit);
		add(saveBtn);
		add(editLabel);
	}
	
	private class SaveAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				String word = edit.getText().trim();
				
				FileWriter rankFile = new FileWriter("rank.txt", true);
				
				if(edit.getText().equals("")) {
					editLabel.setForeground(Color.RED);
					editLabel.setText("플레이어 이름을 입력하시오");
				}
				else if(edit.getText().length() > 5) {
					editLabel.setForeground(Color.RED);
					editLabel.setText("5글자 이하로 입력하시오.");
				}
				else {
					rankFile.write('\n');
					rankFile.write(word,0,word.length());
					rankFile.write(" ");
					rankFile.write(Integer.toString(ScorePanel.score));
					rankFile.close();
					editLabel.setForeground(Color.YELLOW);
					editLabel.setText("\"" + word  + "\"의 점수가 기록되었습니다.");
					edit.setText("");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
