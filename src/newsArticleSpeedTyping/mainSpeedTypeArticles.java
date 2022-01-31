package newsArticleSpeedTyping;

import inputClasses.JavaLabel;
import inputClasses.ProgramButton;
import objects.JavaObject;

public class mainSpeedTypeArticles {

	public static void main(String[] args) {
		int frameWidth = 986, frameHeight = 660;
		if (System.getProperty("os.name").startsWith("Windows")) {
			frameWidth += 16;
			frameHeight += 40;
		}
		JavaObject.setSizing(frameWidth, frameHeight);
		JavaLabel.setSizing(frameWidth, frameHeight);
		ProgramButton.setSizing(frameWidth, frameHeight);
		new MainMenu(frameWidth, frameHeight);
	}
}
