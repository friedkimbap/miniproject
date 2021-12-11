package miniProject;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class TextSource {
	private Vector<String> words = new Vector<String>();
	
	public TextSource() { // 파일에서 읽기
		try {
			Scanner fscanner = new Scanner(new FileReader("words.txt"));
			
			while(fscanner.hasNext()) {
				String word = fscanner.nextLine();
				words.add(word.trim());
			}
			fscanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public String get() {
		int index = (int)(Math.random()*words.size());
		
		return words.get(index);
	}

}
