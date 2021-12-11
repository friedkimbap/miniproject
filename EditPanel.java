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
	}
	
	private class AddAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				String word = edit.getText().trim();
				
				FileWriter wordsFile = new FileWriter("words.txt", true);
				
				if(edit.getText().equals("")) {
					editLabel.setText("단어를 입력하시오");
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
				
				FileWriter wordsFile = new FileWriter("lank.txt", true);
				
				if(edit.getText().equals("")) {
					editLabel.setText("플레이어 이름을 입력하시오");
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
	
}
