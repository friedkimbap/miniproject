package miniProject;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

public class EditPanel extends JPanel {
	private JTextField edit = new JTextField(20);
	
	private JButton addButton = new JButton("add");
	private JButton saveButton = new JButton("save");
	
	private JLabel editLabel = new JLabel("");
	
	public EditPanel() {
		this.setBackground(Color.gray);
		this.setLayout(new FlowLayout());
		
		add(edit);
		add(addButton);
		add(saveButton);
		add(editLabel);
		
		editLabel.setForeground(Color.yellow);
		
		addButton.addActionListener(new AddAction());
		saveButton.addActionListener(new SaveAction());
	}
	
	private class AddAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				String word = edit.getText().trim();
				
				FileWriter wordsFile = new FileWriter("words.txt", true);
				
				if(edit.getText().equals("")) {
					editLabel.setText("추가할 단어를 입력하시오");
				}
				else {
					wordsFile.write('\n');
					wordsFile.write(word,0,word.length());
					wordsFile.close();
					editLabel.setText("\"" + word  + "\"가 저장되었습니다.");
					edit.setText("");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class SaveAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				String word = edit.getText().trim();
				
				FileWriter lankFile = new FileWriter("rank.txt", true);
				
				if(edit.getText().equals("")) {
					editLabel.setText("플레이어 이름을 입력하시오");
				}
				else {
					lankFile.write('\n');
					lankFile.write(word,0,word.length());
					lankFile.write(" ");
					lankFile.write(Integer.toString(ScorePanel.score));
					lankFile.close();
					editLabel.setText("\"" + word  + "\"의 점수가 기록되었습니다.");
					edit.setText("");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
