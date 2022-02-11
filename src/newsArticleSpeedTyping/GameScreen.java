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
import objects.fallingWord;

public class GameScreen implements ActionListener {
	public static String routeGame = MainMenu.route + "//GameAssets//";
	public static DecimalFormat df = new DecimalFormat("0.00");
	public static Timer timer;
	public static JFrame gameScreen;
	public static JTextArea textField;
	public static int fWidth, fHeight, timerCycleTime;
	public static int speed, wordIndex, wordsWritten, charsWritten, mistakes, spawnTimer, spawnInterval;
	public static long startTime, EndTime, time;
	public static ArrayList<String> words = new ArrayList<String>();
	public static HashMap<String, ProgramButton> gameButtons = new HashMap<String, ProgramButton>();
	public static HashMap<String, JavaLayeredPane> layers = new HashMap<String, JavaLayeredPane>();
	public static HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	public static ArrayList<fallingWord> fallingWords = new ArrayList<fallingWord>();

	public GameScreen(String difficulty, ArrayList<String> data) {
		setupParameters(data);
		setupScreen();
		setupParameters2(difficulty);
	}

	public void actionPerformed(ActionEvent e) {
		time = Calendar.getInstance().getTimeInMillis() - startTime;
		if (textField.getLineCount() == 2) { // basically : when user presses enter
			checkInput(textField.getText());
			textField.setText("");
		}
		if (time / 10 < 0) {
			labels.get("timer").setText((int) (-time / 1000) + "");
			if (time / 100 == 0) {
				labels.get("timer").setLocation(0, (int) (fHeight * 0.7));
				spawnWord();
				startTime = Calendar.getInstance().getTimeInMillis();
			}
		} else {
			if (spawnTimer >= spawnInterval && wordIndex < words.size()) {
				spawnWord();
			}
			spawnTimer++;
			for (int i = 0; i < fallingWords.size(); i++) {
				fallingWords.get(i).setPosition(speed);
				if (fallingWords.get(i).checkForDespawn()) { // CHECKS IF WORD HAS FALLEN OUT OF SCREEN
					i -= fallingWords.get(i).despawn(fallingWords);
					mistakes++;
					labels.get("mistakesBox").setText("Mistakes = " + mistakes);
				}
			}
			if (wordIndex == words.size() && fallingWords.size() == 0) { // END OF GAME
				timer.stop();
				String[] speed = { df.format((double) wordsWritten / ((double) time)),
						df.format((double) wordsWritten / ((double) (time / 1000)) * 60),
						df.format((double) charsWritten / ((double) time)),
						df.format((double) charsWritten / ((double) (time / 1000)) * 60) }; // array of typing speeds
				new JavaLabel("wordSpeed", layers.get("leaderboard"), 0, fHeight * 0.3, 1000, 100, labels, 10,
						routeGame);
				labels.get("wordSpeed").setText(speed[0] + " words per second / " + speed[1] + " words per minute");
				new JavaLabel("charSpeed", layers.get("leaderboard"), 0, fHeight * 0.4, 1000, 100, labels, 10,
						routeGame);
				labels.get("charSpeed").setText(speed[2] + " letters per second / " + speed[3] + " letters per minute");
				MainMenu.updateLeaderboard(speed[1] + " |" + speed[3] + "|Vasja " + "\n",
						new File(MainMenu.leaderboardRoute));
				layers.get("leaderboard").setLocation(0, 0);
			}
			labels.get("timer").setText(df.format((double)time / 1000) + "");
		}
	}

	public static void spawnWord() {
		if (wordIndex < words.size()) {
			new fallingWord(0, 0, (int) (fWidth * 0.3), 72, words.get(wordIndex), fallingWords, 24, routeGame);
			wordIndex++;
			spawnTimer = 0;
		}
	}

	public static void checkInput(String word) {
		word = word.substring(0, word.length() - 1); // removes the 2nd line from String
		for (int i = 0; i < fallingWords.size(); i++) { // goes through all spawned words
			if (fallingWords.get(i).getWord().equals(word)) { // checks if player input matches word
				i -= fallingWords.get(i).despawn(fallingWords);
				wordsWritten++;
				charsWritten += word.length();
				labels.get("writtenWords").setText("Written = " + wordsWritten);
				if (fallingWords.size() == 0) { // if no words are on screen, then spawns one
					spawnWord();
				}
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
		new ProgramButton("RESET", layers.get("game"), 0, fHeight * 0.6, 200, 70, gameButtons);
		new ProgramButton("BACK TO MENU", layers.get("game"), fWidth * 0.78, fHeight * 0.6, 200, 70, gameButtons);
		new JavaLabel("bottomLabel", layers.get("game"), 0, fHeight * 0.84, 1000, 70, labels, 10, routeGame);
		new JavaLabel("writtenWords", labels.get("bottomLabel"), fWidth * 0.2, 0, 200, 35, labels, 10, routeGame);
		new JavaLabel("mistakesBox", labels.get("bottomLabel"), fWidth * 0.2, fHeight * 0.05, 200, 35, labels, 10, routeGame);
		new JavaLabel("timer", layers.get("game"), fWidth * 0.4, fHeight * 0.4, 160, 70, labels, 10, routeGame);

		labels.get("timer").setFont(new Font("Verdana", Font.BOLD, (int) (fWidth * 0.04)));
		labels.get("writtenWords").setText("Written = " + wordsWritten);
		labels.get("mistakesBox").setText("Mistakes = " + mistakes);

		textField = new JTextArea();
		textField.setBounds((int) (fWidth * 0.44), (int) (fHeight * 0.02), 400, 70);
		textField.setForeground(Color.white);
		textField.setOpaque(false);
		textField.setFont(new Font("Verdana", Font.BOLD, 20));
		labels.get("bottomLabel").add(textField, (Integer) 2);

		new JavaLayeredPane("leaderboard", layers.get("game"), 0, -fHeight, fWidth, fHeight, layers, 0); // fHeight - labels.get("bottomLabel").getHeight()
		new JavaLabel("scoreBackground", layers.get("leaderboard"), 0, 0, fWidth, fHeight, labels, 0, routeGame);
	}

	void setupParameters2(String difficulty) {
		speed = 4;
		spawnInterval = 200;
		if (difficulty.equals("MEDIUM")) {
			speed = 8;
			spawnInterval = 150;
		} else if (difficulty.equals("HARD")) {
			speed = 12;
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
	}
}
