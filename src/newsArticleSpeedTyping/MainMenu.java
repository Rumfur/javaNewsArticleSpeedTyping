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
	public static int fWidth = 1000, fHeight = 700;
	public static ArrayList<String> words = new ArrayList<String>();
	public static HashMap<String, ProgramButton> menuButtons = new HashMap<String, ProgramButton>();
	public static HashMap<String, JavaLayeredPane> layers = new HashMap<String, JavaLayeredPane>();
	public static HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	public static JTextArea highscoreField;

	public MainMenu(int frameWidth, int frameHeight) {
		fWidth = frameWidth;
		fHeight = frameHeight;
		menuScreen = setupScreen(menuScreen, frameWidth, frameHeight);
		setupLayers();
		layers.get("menuLayer").selected = true;
		setupLabels();
		menuButtons.get(difficulty).selected = true;
		menuButtons.get(difficulty).buttonIconSwitch(2, true);
		readFile(new File(route + "//tempText.txt"));
		setupLeaderboard();
		readLeaderboardFile(new File(leaderboardRoute));
		menuTimer = new Timer(10, this);
		menuTimer.start();
	}

	public void actionPerformed(ActionEvent e) {
		for (String layer : layers.keySet()) {
			layers.get(layer).Move();
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
						words.add(data[i]);
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
			System.out.println("DATA FILE NOT FOUND!");
			System.exit(0);
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
		new JavaLabel("menuBackground", layers.get("menuLayer"), 0, 0, 1000, 700, labels, 0, routeMenu); // MENU
		new ProgramButton("READ ARTICLES", layers.get("menuLayer"), fWidth * 0.3, fHeight * 0.1, 400, 100, menuButtons);
		new ProgramButton("INFO PAGE", layers.get("menuLayer"), fWidth * 0.3, fHeight * 0.3, 400, 100, menuButtons);
		new ProgramButton("EXIT", layers.get("menuLayer"), fWidth * 0.3, fHeight * 0.5, 400, 100, menuButtons);
		new JavaLabel("infoBackground", layers.get("infoLayer"), 0, 0, 1000, 700, labels, 0, routeMenu); // INFO
		new ProgramButton("BACK", layers.get("infoLayer"), fWidth * 0.3, fHeight * 0.5, 400, 50, menuButtons);
		new JavaLabel("playBackground", layers.get("playScreen"), 0, 0, 1000, 700, labels, 1, routeMenu); // PLAY
		new ProgramButton("BACK", layers.get("playScreen"), fWidth * 0.05, fHeight * 0.5, 150, 50, menuButtons);
		new ProgramButton("PLAY", layers.get("playScreen"), fWidth * 0.2, fHeight * 0.5, 150, 50, menuButtons);
		new ProgramButton("EASY", layers.get("playScreen"), fWidth * 0.125, fHeight * 0.15, 150, 50, menuButtons);
		new ProgramButton("MEDIUM", layers.get("playScreen"), fWidth * 0.125, fHeight * 0.25, 150, 50, menuButtons);
		new ProgramButton("HARD", layers.get("playScreen"), fWidth * 0.125, fHeight * 0.35, 150, 50, menuButtons);
		new JavaLabel("leaderBoard", layers.get("playScreen"), fWidth * 0.75, fHeight * 0.1, 200, 400, labels, 2,
				routeMenu);
	}

	static void setupLayers() {
		new JavaLayeredPane("menuLayer", menuScreen, 0, -fHeight, fWidth, fHeight, layers, 0);
		new JavaLayeredPane("playScreen", menuScreen, fWidth, 0, fWidth, fHeight, layers, 0);
		new JavaLayeredPane("infoLayer", menuScreen, -fWidth, 0, fWidth, fHeight, layers, 0);
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
