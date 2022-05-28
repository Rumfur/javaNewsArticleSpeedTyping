package newsArticleSpeedTyping;

import javax.swing.*;

import java.io.File;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;

import inputClasses.*;
import rss.RssData;

public class GameScreen implements ActionListener {
	public static String routeGame = MainMenu.route + "//GameAssets//";
	public static DecimalFormat df = new DecimalFormat("0.00");
	public static Timer timer;
	public static JFrame gameScreen;
	public static JTextArea textField;
	public static boolean end;
	public static int fWidth, fHeight, timerCycleTime;
	public static int fallSpeed, wordIndex, wordsWritten, charsWritten, mistakes, spawnTimer, spawnInterval;
	public static long startTime, EndTime, time;
	public static String[] speed = { "", "", "", "" };
	public static ArrayList<String> words = new ArrayList<String>();
	public static HashMap<String, ProgramButton> gameButtons = new HashMap<String, ProgramButton>();
	public static HashMap<String, JavaLayeredPane> layers = new HashMap<String, JavaLayeredPane>();
	public static HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	public static ArrayList<FallingWord> fallingWords = new ArrayList<FallingWord>();

	public GameScreen() {
		if (MainMenu.useFile) {
			setupParameters(DataGetter.getData(new File(MainMenu.route + MainMenu.textFile))); // gets file data
		} else {
			setupParameters(DataGetter.getData(MainMenu.articleIndex)); // gets rss feed data
		}
		setupScreen();
		setupParameters2(MainMenu.difficulty);
	}

	public void actionPerformed(ActionEvent e) { // method that timer uses
		if (textField.getLineCount() == 2) { // is true when user presses enter
			checkInput();
		}
		if (!end) { // if game is running
			time = Calendar.getInstance().getTimeInMillis() - startTime;
			if (time / 10 > 0) { // main game
				if (spawnTimer >= spawnInterval || fallingWords.size() == 0) {
					spawnWord();
				}
				spawnTimer++;
				for (int i = 0; i < fallingWords.size(); i++) {
					fallingWords.get(i).setPosition(fallSpeed);
					if (fallingWords.get(i).checkForDespawn()) { // CHECKS IF WORD HAS FALLEN OUT OF SCREEN
						i -= fallingWords.get(i).despawn(fallingWords);
						mistakes++;
						labels.get("mistakesBox").setText("Mistakes = " + mistakes);
					}
				}
				if (wordIndex == words.size() && fallingWords.size() == 0) { // END OF GAME
					endGame();
				}
				labels.get("timer").setText(df.format((double) time / 1000) + "");
			} else { // start of game countdown
				labels.get("timer").setText((int) (-time / 1000 + 1) + "");
				if (time / 100 == 0) { // end of countdown
					labels.get("timer").setLocation(0, (int) (fHeight * 0.7));
					startTime = Calendar.getInstance().getTimeInMillis();
				}
			}
		}
	}

	public static void checkInput() {
		String word = textField.getText().replace("\n", ""); // removes \n from String
		if (!end) {
			matchWord(word); // checks if input matches any falling word
			textField.setText(""); // resets text field
		} else { // when the game has ended
			if (checkNameValidity(word)) {
				MainMenu.updateLeaderboard(speed[1] + " | " + speed[3] + " | " + word + "\n",
						new File(MainMenu.leaderboardRoute));
				textField.setEditable(false);
				if (!MainMenu.useFile) {
					labels.get("writtenWords").setVisible(false);
					labels.get("mistakesBox").setVisible(false);
					new ProgramButton("PREVIOUS ARTICLE", labels.get("bottomLabel"), 0, 0, 300, 70, gameButtons);
					new ProgramButton("NEXT ARTICLE", labels.get("bottomLabel"), fWidth * 0.3, 0, 300, 70, gameButtons);
				}
				labels.get("nameInfo").setVisible(false);
				textField.setVisible(false);
				labels.get("textBox").setVisible(false);
			}
		}
	}

	public static boolean checkNameValidity(String name) {
		textField.setText(textField.getText().replace("\n", ""));
		if (name.isEmpty()) {
			labels.get("nameInfo").setText("Name cannot be empty!");
			return false;
		}
		if (name.length() > 20) {
			labels.get("nameInfo").setText("Name limited to 20 charecters!");
			return false;
		}
		return true;
	}

	public static void endGame() {
		end = true;
		speed[0] = df.format((double) wordsWritten / ((double) (time / 1000)));
		speed[1] = df.format((double) wordsWritten / ((double) (time / 1000)) * 60);
		speed[2] = df.format((double) charsWritten / ((double) (time / 1000)));
		speed[3] = df.format((double) charsWritten / ((double) (time / 1000)) * 60);
		// info labels
		new JavaLabel("nameInfo", layers.get("leaderboard"), fWidth * 0.2, fHeight * 0.77, 380, 50, labels, 10,
				routeGame);
		labels.get("nameInfo").setText("Please, enter your name!");
		new JavaLabel("wordSpeed", layers.get("leaderboard"), 0, fHeight * 0.3, 1000, 100, labels, 10, routeGame);
		labels.get("wordSpeed")
				.setText("Your word speed is " + speed[0] + " words per second / " + speed[1] + " words per minute!");
		new JavaLabel("charSpeed", layers.get("leaderboard"), 0, fHeight * 0.4, 1000, 100, labels, 10, routeGame);
		labels.get("charSpeed").setText("Your charecter speed is " + speed[2] + " charecters per second / " + speed[3]
				+ " charecters per minute!");
		new JavaLabel("word count", layers.get("leaderboard"), 0, fHeight * 0.5, 1000, 100, labels, 10, routeGame);
		labels.get("word count")
				.setText("Your mistake count is " + mistakes + " and written word count = " + wordsWritten + "!");
		if (!MainMenu.useFile) { // if a news article is used
			new JavaLabel("linkField", layers.get("leaderboard"), 0, fHeight * 0.1, fWidth * 0.98, 80, labels, 2,
					routeGame);
			JTextArea articleLink = new JTextArea();
			articleLink.setBounds(10, 2, (int) (fWidth * 0.96), 80);
			articleLink.setForeground(Color.white);
			articleLink.setFont(new Font("Verdana", Font.BOLD, 20));
			articleLink.setOpaque(false);
			articleLink.setEditable(false);
			articleLink.setLineWrap(true);
			labels.get("linkField").add(articleLink);
			articleLink.setText(RssData.linkData.get(MainMenu.articleIndex));
		}
		layers.get("leaderboard").setLocation(0, 0);
	}

	public static void spawnWord() {
		if (wordIndex < words.size()) {
			new FallingWord(0, 0, (int) (fWidth * 0.3), 72, words.get(wordIndex), fallingWords, 24, routeGame);
			wordIndex++;
			spawnTimer = 0;
		}
	}

	public static void matchWord(String word) {
		for (int i = 0; i < fallingWords.size(); i++) { // goes through all spawned words
			if (fallingWords.get(i).getWord().equals(word)) { // checks if player input matches word
				i -= fallingWords.get(i).despawn(fallingWords);
				wordsWritten++;
				charsWritten += word.length();
				labels.get("writtenWords").setText("Written = " + wordsWritten);
				return;
			}
		}
		mistakes++; // if input is incorrect, increases number of mistakes
		labels.get("mistakesBox").setText("Mistakes = " + mistakes);
	}

	static void setupScreen() {
		gameScreen = MainMenu.setupScreen(gameScreen, fWidth, fHeight);

		new JavaLayeredPane("game", gameScreen, 0, 0, fWidth, fHeight, layers, 0);
		new JavaLabel("gameBackground", layers.get("game"), 0, 0, fWidth, fHeight, labels, 0, routeGame);
		new ProgramButton("RESET", layers.get("game"), fWidth * 0.58, fHeight * 0.844, 200, 70, gameButtons);
		new ProgramButton("BACK TO MENU", layers.get("game"), fWidth * 0.78, fHeight * 0.844, 200, 70, gameButtons);
		new JavaLabel("bottomLabel", layers.get("game"), 0, fHeight * 0.844, 1000, 72, labels, 10, routeGame);
		new JavaLabel("writtenWords", labels.get("bottomLabel"), 0, 0, 200, 35, labels, 1, routeGame);
		new JavaLabel("mistakesBox", labels.get("bottomLabel"), 0, fHeight * 0.05, 200, 36, labels, 1, routeGame);
		new JavaLabel("textBox", labels.get("bottomLabel"), fWidth * 0.2, 0, 380, 70, labels, 1, routeGame);
		new JavaLabel("timer", layers.get("game"), fWidth * 0.4, fHeight * 0.4, 160, 70, labels, 1, routeGame);

		labels.get("timer").setFont(new Font("Verdana", Font.BOLD, (int) (fWidth * 0.04)));
		labels.get("writtenWords").setText("Written = " + wordsWritten);
		labels.get("mistakesBox").setText("Mistakes = " + mistakes);

		textField = new JTextArea();
		textField.setBounds((int) (fWidth * 0.04), (int) (fHeight * 0.03), 400, 70);
		textField.setForeground(Color.white);
		textField.setFont(new Font("Verdana", Font.BOLD, 20));
		textField.setOpaque(false);
		labels.get("textBox").add(textField);

		new JavaLayeredPane("leaderboard", layers.get("game"), 0, -fHeight, fWidth, fHeight, layers, 1);
		new JavaLabel("scoreBackground", layers.get("leaderboard"), 0, 0, fWidth, fHeight, labels, 0, routeGame);
	}

	void setupParameters2(String difficulty) {
		fallSpeed = 4;
		spawnInterval = 200;
		if (difficulty.equals("MEDIUM")) {
			fallSpeed = 8;
			spawnInterval = 150;
		} else if (difficulty.equals("HARD")) {
			fallSpeed = 12;
			spawnInterval = 100;
		}
		timer = new Timer(timerCycleTime, this);
		timer.start();
	}

	void setupParameters(ArrayList<String> data) {
		words = data;
		wordIndex = 0;
		spawnTimer = 0;
		wordsWritten = 0;
		charsWritten = 0;
		mistakes = 0;
		spawnTimer = 0;
		timerCycleTime = 10;
		fWidth = MainMenu.fWidth;
		fHeight = MainMenu.fHeight;
		startTime = Calendar.getInstance().getTimeInMillis() + 3000;
		end = false;
	}
}
//226 lines, 1 constructor, 9 methods