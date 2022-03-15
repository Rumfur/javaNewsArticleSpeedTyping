package newsArticleSpeedTyping;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

import inputClasses.JavaLabel;
import inputClasses.JavaLayeredPane;
import inputClasses.ProgramButton;

public class MainMenu implements ActionListener {
	public static File route = new File(System.getProperty("user.dir"));
	public static String routeMenu = route + "//MenuAssets//", leaderboardRoute = route + "//leaderboard.txt";
	public static String difficulty = "EASY", leaderboardText = "";
	public static JFrame menuScreen;
	public static Timer menuTimer;
	public static int fWidth, fHeight;
	public static ArrayList<String> words = new ArrayList<String>();
	public static HashMap<String, ProgramButton> menuButtons = new HashMap<String, ProgramButton>();
	public static HashMap<String, ProgramButton> difficultyButtons = new HashMap<String, ProgramButton>();
	public static HashMap<String, JavaLayeredPane> layers = new HashMap<String, JavaLayeredPane>();
	public static HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	public static JTextArea highscoreField;

	public MainMenu(int frameWidth, int frameHeight) {
		fWidth = frameWidth;
		fHeight = frameHeight;
		menuScreen = setupScreen(menuScreen, frameWidth, frameHeight);
		setupLayers();
		layers.get("menu").selected = true;
		setupLabels();
		difficultyButtons.get(difficulty).selected = true;
		difficultyButtons.get(difficulty).buttonIconSwitch(2, true);
		readFile(new File(route + "//tempText.txt"));
		setupLeaderboard();
		readLeaderboardFile(new File(leaderboardRoute));
		menuTimer = new Timer(10, this);
		menuTimer.start();
	}

	public void actionPerformed(ActionEvent e) {
		for (String layer : layers.keySet()) {
			layers.get(layer).Move(); // moves background if user switches screens
		}
	}

	public static void readFile(File file) {
		if (file.exists() != true) {
			System.out.println("DATA FILE NOT FOUND!");
			System.exit(0);
		}
		String data[] = new String[214748364];
		try {
			Scanner fileReader = new Scanner(file, "UTF-8");
			while (fileReader.hasNextLine()) {
				data = fileReader.nextLine().split(" "); // splits line from file into array of strings
				for (int i = 0; i < data.length; i++) {
					if (!data[i].isEmpty()) {
						words.add(data[i]); // adds individual words to String array
					}
				}
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readLeaderboardFile(File file) {
		if (file.exists() != true) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		leaderboardText = "";
		try {
			Scanner fileReader = new Scanner(file, "UTF-8");
			while (fileReader.hasNextLine()) {
				leaderboardText += fileReader.nextLine() + "\n";
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		highscoreField.setText("WPM|CPM|Username\n" + leaderboardText);
	}

	static void setupLeaderboard() {
		highscoreField = new JTextArea();
		highscoreField.setBounds(5, 5, labels.get("leaderBoard").getWidth(), labels.get("leaderBoard").getHeight() * 5);
		highscoreField.setForeground(Color.white);
		highscoreField.setOpaque(false);
		highscoreField.setEditable(false);
		highscoreField.setFont(new Font("Verdana", Font.BOLD, 14));
		labels.get("leaderBoard").add(highscoreField);
	}

	static void updateLeaderboard(String leaderboardEntry, File file) {
		leaderboardText += leaderboardEntry;
		try {
			file.delete();
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			boolean append = true;
			FileWriter wrt = new FileWriter(file, append);
			wrt.write(leaderboardText);
			wrt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		highscoreField.setText("WPM|CPM|Username\n" + leaderboardText);
	}

	static void setupLabels() {
		new JavaLabel("menuBackground", layers.get("menu"), 0, 0, 1000, 700, labels, 0, routeMenu);
		new ProgramButton("READ ARTICLES", layers.get("menu"), fWidth * 0.3, fHeight * 0.2, 400, 100, menuButtons);
		new ProgramButton("INFO PAGE", layers.get("menu"), fWidth * 0.3, fHeight * 0.4, 400, 100, menuButtons);
		new ProgramButton("EXIT", layers.get("menu"), fWidth * 0.3, fHeight * 0.6, 400, 100, menuButtons);
		new JavaLabel("infoBackground", layers.get("info"), 0, 0, 1000, 700, labels, 0, routeMenu);
		new ProgramButton("BACK", layers.get("info"), fWidth * 0.3, fHeight * 0.6, 400, 50, menuButtons);
		new JavaLabel("playBackground", layers.get("play"), 0, 0, 1000, 700, labels, 1, routeMenu);
		new ProgramButton("EASY", layers.get("play"), fWidth * 0.125, fHeight * 0.15, 150, 50, difficultyButtons);
		new ProgramButton("MEDIUM", layers.get("play"), fWidth * 0.125, fHeight * 0.25, 150, 50, difficultyButtons);
		new ProgramButton("HARD", layers.get("play"), fWidth * 0.125, fHeight * 0.35, 150, 50, difficultyButtons);
		new ProgramButton("PLAY", layers.get("play"), fWidth * 0.1, fHeight * 0.65, 200, 50, menuButtons);
		new ProgramButton("BACK", layers.get("play"), fWidth * 0.1, fHeight * 0.72, 200, 50, menuButtons);
		new JavaLabel("leaderBoard", layers.get("play"), fWidth * 0.4, fHeight * 0.1, 500, 520, labels, 2, routeMenu);
		new ProgramButton("/\\", layers.get("play"), fWidth * 0.4, fHeight * 0.06, 500, 30, menuButtons);
		new ProgramButton("\\/", layers.get("play"), fWidth * 0.4, fHeight * 0.84, 500, 30, menuButtons);
		
	}

	static void setupLayers() {
		new JavaLayeredPane("menu", menuScreen, 0, -fHeight, fWidth, fHeight, layers, 0);
		new JavaLayeredPane("play", menuScreen, fWidth, 0, fWidth, fHeight, layers, 0);
		new JavaLayeredPane("info", menuScreen, -fWidth, 0, fWidth, fHeight, layers, 0);
	}

	static JFrame setupScreen(JFrame frame, int frameWidth, int frameHeight) {
		frame = new JFrame("News Article Speed Typing");
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.black);
		frame.setIconImage(new ImageIcon("logo.png").getImage());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return frame;
	}
}
