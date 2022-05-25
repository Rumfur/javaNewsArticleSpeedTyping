package newsArticleSpeedTyping;

import inputClasses.JavaLabel;
import inputClasses.ProgramButton;

public class mainSpeedTypeArticles {

	public static void main(String[] args) {
		int frameWidth = 986, frameHeight = 660;
		if (System.getProperty("os.name").startsWith("Windows")) {
			frameWidth += 16;
			frameHeight += 40;
			System.getProperty("os.name");
		}
		FallingWord.setSizing(frameWidth, frameHeight);
		JavaLabel.setSizing(frameWidth, frameHeight);
		ProgramButton.setSizing(frameWidth, frameHeight);
		new MainMenu(frameWidth, frameHeight);
	}
}
// 20 lines, 1 method
//
// Total for project : 13 classes, 10 constructors, 53 methods, 1205 lines of code.