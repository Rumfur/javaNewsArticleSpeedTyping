package newsArticleSpeedTyping;

import javax.swing.*;

import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import inputClasses.*;
import objects.fallingWord;

public class GameScreen implements ActionListener {
	public static String routeGame = MainMenu.route + "//GameAssets//";
	public static DecimalFormat df = new DecimalFormat("0.00");
	public static Timer timer;
	public static JFrame gameScreen;
	public static int fWidth, fHeight, time, timerCycleTime;
	public static int speed, wordIndex, wordsWritten, charsWritten, mistakes, spawnTimer, spawnInterval;
	public static String textBoxText = "";
	public static ArrayList<String> words = new ArrayList<String>();
	public static HashMap<String, ProgramButton> gameButtons = new HashMap<String, ProgramButton>();
	public static HashMap<String, JLayeredPane> layers = new HashMap<String, JLayeredPane>();
	public static HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	public static ArrayList<fallingWord> fallingWords = new ArrayList<fallingWord>();

	public GameScreen(String difficulty, ArrayList<String> data) {
		setupParameters(data);
		setupScreen();
		setupLayers();
		setupLabels();
		setupParameters2(difficulty);
		spawnWord();
	}

	public void actionPerformed(ActionEvent e) {
		if (spawnTimer >= spawnInterval) {
			spawnWord();
		}
		spawnTimer++;
		for (int i = 0; i < fallingWords.size(); i++) {
			fallingWords.get(i).setPosition(speed);
			if (fallingWords.get(i).checkForDespawn()) {
				i -= fallingWords.get(i).despawn(fallingWords);
			}
		}
		if (wordIndex == words.size() && fallingWords.size() == 0) {
			timer.stop();
			new JavaLabel("wordSpeed", layers.get("gameLayer"), 0, fHeight * 0.3, 1000, 100, labels, 10, routeGame);
			double wordSpeed = (double) wordsWritten / ((double) time / 50);
			labels.get("wordSpeed")
					.setText(df.format(wordSpeed) + " words per second / " + df.format(wordSpeed * 60) + " words per minute");
			
			double charSpeed;
			new JavaLabel("charSpeed", layers.get("gameLayer"), 0, fHeight * 0.5, 1000, 100, labels, 10, routeGame);
			charSpeed = (double) charsWritten / ((double) time / 50);
			labels.get("charSpeed").setText(
					df.format(charSpeed) + " characters per second / " + df.format(charSpeed * 60) + " characters per minute");
		}
		time++;
		labels.get("timer").setText("" + df.format((double)time / 50));
	}

	public static void spawnWord() {
		if (wordIndex < words.size()) {
			new fallingWord(0, 0, (int) (fWidth * 0.3), 72, words.get(wordIndex), fallingWords, 20, routeGame);
			wordIndex++;
			spawnTimer = 0;
		}
	}

	public static void keyInput(char key) {
		textBoxText += key;
		labels.get("textBox").setText(textBoxText);
	}

	public static void actionKeyInput(int key) {
		if (key == 10) {
			checkInput(textBoxText);
			textBoxText = "";
		} else if (key == 8 && textBoxText.length() > 0) {
			textBoxText = textBoxText.substring(0, textBoxText.length() - 1);
		}
		labels.get("textBox").setText(textBoxText);
	}

	public static void checkInput(String word) {
		for (int i = 0; i < fallingWords.size(); i++) {
			if (fallingWords.get(i).getWord().equals(word)) {
				i -= fallingWords.get(i).despawn(fallingWords);
				wordsWritten++;
				charsWritten += word.length();
				labels.get("writtenWords").setText("Written = " + wordsWritten);
				spawnWord();
				return;
			}
		}
		mistakes++;
		labels.get("mistakesBox").setText("Mistakes = " + mistakes);
	}

	static void setupScreen() {
		gameScreen = MainMenu.setupScreen(gameScreen, fWidth, fHeight);
	}

	static void setupLabels() {
		new JavaLabel("gameBackground", layers.get("gameLayer"), 0, 0, fWidth, fHeight, labels, 0, routeGame);
		new ProgramButton("RESET", layers.get("gameLayer"), 0, fHeight * 0.6, 200, 70, gameButtons);
		new ProgramButton("BACK TO MENU", layers.get("gameLayer"), fWidth * 0.78, fHeight * 0.6, 200, 70, gameButtons);
		new JavaLabel("textBox", layers.get("gameLayer"), fWidth * 0.4, fHeight * 0.84, 400, 70, labels, 10, routeGame);
		new JavaLabel("writtenWords", layers.get("gameLayer"), fWidth * 0.2, fHeight * 0.84, 200, 35, labels, 10,
				routeGame);
		new JavaLabel("mistakesBox", layers.get("gameLayer"), fWidth * 0.2, fHeight * 0.89, 200, 35, labels, 10,
				routeGame);
		new JavaLabel("timer", layers.get("gameLayer"), 0, fHeight * 0.7, 100, 70, labels, 10, routeGame);

		labels.get("writtenWords").setText("Written = " + wordsWritten);
		labels.get("mistakesBox").setText("Mistakes = " + wordsWritten);
	}

	static void setupLayers() {
		new JavaLayeredPane("gameLayer", gameScreen, 0, 0, fWidth, fHeight, layers, 0);
	}

	void setupParameters2(String difficulty) {
		speed = 5;
		spawnInterval = 200;
		if (difficulty.equals("medium")) {
			speed = 10;
			spawnInterval = 150;
		} else if (difficulty.equals("hard")) {
			speed = 15;
			spawnInterval = 100;
		}
		new KeyInputListener();
		timer = new Timer(timerCycleTime, this);
		timer.start();
	}

	void setupParameters(ArrayList<String> data) {
		textBoxText = "";
		words = data;
		wordIndex = 0;
		spawnTimer = 0;
		wordsWritten = 0;
		charsWritten = 0;
		mistakes = 0;
		spawnTimer = 0;
		time = 0;
		timerCycleTime = 10;
		fWidth = MainMenu.fWidth;
		fHeight = MainMenu.fHeight;
	}
}
