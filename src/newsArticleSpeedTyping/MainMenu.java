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
import rss.RssData;

public class MainMenu implements ActionListener {
	public static File route = new File(System.getProperty("user.dir"));
	public static String routeMenu = route + "//MenuAssets//", leaderboardRoute = route + "//leaderboard.txt",
			textFile = "//tempText.txt";
	public static String difficulty = "EASY", leaderboardText = "", selectedNewsSiteName;
	public static JFrame menuScreen;
	public static Timer menuTimer;
	public static int fWidth, fHeight, articleIndex, articleCount, boardMovementDir = 0, boardHeight = 0;
	public static boolean useFile;
	public static JTextArea inputFieldArticleNR, articleLink;
	public static HashMap<String, ProgramButton> menuButtons = new HashMap<String, ProgramButton>();
	public static HashMap<String, ProgramButton> difficultyButtons = new HashMap<String, ProgramButton>();
	public static HashMap<String, JavaLayeredPane> layers = new HashMap<String, JavaLayeredPane>();
	public static HashMap<String, JLabel> labels = new HashMap<String, JLabel>();
	public static JTextArea leaderboardField;

	public MainMenu(int frameWidth, int frameHeight) {
		articleIndex = 0;
		articleCount = 0;
		useFile = false;
		readLeaderboardFile(new File(leaderboardRoute));
		fWidth = frameWidth;
		fHeight = frameHeight;
		menuScreen = setupScreen(menuScreen, frameWidth, frameHeight);
		setupLayers();
		layers.get("menu").selected = true;
		setupLabels();
		difficultyButtons.get(difficulty).selected = true;
		difficultyButtons.get(difficulty).buttonIconSwitch(2, true);
		switchNews("https://www.delfi.lv/rss/?channel=sabiedriba", "Delfi(sabiedriba)"); // default site is delfi
		menuTimer = new Timer(10, this);
		menuTimer.start();
	}

	public void actionPerformed(ActionEvent e) {
		for (String layer : layers.keySet()) {
			layers.get(layer).checkMovement(); // moves background if user switches screens
		}
		if (layers.get("news").selected) {
			String nrText = inputFieldArticleNR.getText();
			if (nrText.length() > 5) {
				inputFieldArticleNR.setText(nrText.substring(0, nrText.length() - 1));
			}
		}
		if (layers.get("play").selected) {
			if (boardMovementDir == 1 && leaderboardField.getY() + 10 * boardMovementDir < 25) {
				leaderboardField.setLocation(leaderboardField.getX(), leaderboardField.getY() + 10 * boardMovementDir);
			}
			if (boardMovementDir == -1
					&& leaderboardField.getY() + leaderboardField.getHeight() + 10 * boardMovementDir > 35) {
				leaderboardField.setLocation(leaderboardField.getX(), leaderboardField.getY() + 10 * boardMovementDir);
			}
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
				boardHeight += 20;
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void switchNews(String link, String newsSiteName) {
		inputFieldArticleNR.setText((articleIndex + 1) + "");
		labels.get("blackBar").setVisible(false);
		menuButtons.get("Select NR").setVisible(true);
		new RssData(link);
		articleLink.setText(RssData.linkData.get(articleIndex));
		selectedNewsSiteName = newsSiteName;
		articleCount = RssData.descriptionData.size();
		labels.get("currentNews").setText("Currently selected : " + selectedNewsSiteName + ".");
		labels.get("articleNR").setText("There are " + articleCount + " articles in " + selectedNewsSiteName + ".");
	}

	public static void switchToFile() {
		labels.get("NRerrText").setText("");
		selectedNewsSiteName = "";
		labels.get("blackBar").setVisible(true);
		menuButtons.get("Select NR").setVisible(false);
		useFile = true;
		labels.get("currentNews").setText("Currently selected : file " + textFile + ".");
	}

	static void updateLeaderboard(String leaderboardEntry, File file) {
		System.out.println(leaderboardField.getHeight());
		leaderboardField.setBounds(0, 0, leaderboardField.getWidth(), leaderboardField.getHeight() + 20);
		System.out.println(leaderboardField.getHeight());
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

	}

	static void setupLabels() {
		// MENU
		new JavaLabel("menuBackground", layers.get("menu"), 0, 0, 1000, 700, labels, 0, routeMenu);
		new ProgramButton("READ ARTICLES", layers.get("menu"), fWidth * 0.3, fHeight * 0.2, 400, 100, menuButtons);
		new ProgramButton("INFO PAGE", layers.get("menu"), fWidth * 0.3, fHeight * 0.4, 400, 100, menuButtons);
		new ProgramButton("EXIT", layers.get("menu"), fWidth * 0.3, fHeight * 0.6, 400, 100, menuButtons);
		// INFO
		new JavaLabel("infoBackground", layers.get("info"), 0, 0, 1000, 700, labels, 0, routeMenu);
		new ProgramButton("BACK", layers.get("info"), fWidth * 0.3, fHeight * 0.6, 400, 50, menuButtons);
		// PLAY
		new JavaLabel("playBackground", layers.get("play"), 0, 0, 1000, 700, labels, 1, routeMenu);
		new ProgramButton("NEWS SELECT", layers.get("play"), fWidth * 0.1, fHeight * 0.05, 200, 50, menuButtons);
		new ProgramButton("EASY", layers.get("play"), fWidth * 0.125, fHeight * 0.25, 150, 50, difficultyButtons);
		new ProgramButton("MEDIUM", layers.get("play"), fWidth * 0.125, fHeight * 0.35, 150, 50, difficultyButtons);
		new ProgramButton("HARD", layers.get("play"), fWidth * 0.125, fHeight * 0.45, 150, 50, difficultyButtons);
		new JavaLabel("difficultySetting", layers.get("play"), 0, fHeight * 0.55, 400, 30, labels, 2, routeMenu);
		labels.get("difficultySetting").setText("Current difficulty : " + difficulty);
		new ProgramButton("PLAY", layers.get("play"), fWidth * 0.1, fHeight * 0.65, 200, 50, menuButtons);
		new ProgramButton("BACK", layers.get("play"), fWidth * 0.1, fHeight * 0.72, 200, 50, menuButtons);
		new JavaLabel("leaderBoard", layers.get("play"), fWidth * 0.4, fHeight * 0.1, 500, 270, labels, 2, routeMenu);
		new ProgramButton("/\\", labels.get("leaderBoard"), 0, 0, labels.get("leaderBoard").getWidth(), 20,
				menuButtons);
		new ProgramButton("\\/", labels.get("leaderBoard"), 0, labels.get("leaderBoard").getHeight() - 20,
				labels.get("leaderBoard").getWidth(), 20, menuButtons);
		leaderboardField = new JTextArea();
		leaderboardField.setBounds(5, 20, labels.get("leaderBoard").getWidth(), boardHeight);
		leaderboardField.setForeground(Color.white);
		leaderboardField.setOpaque(false);
		leaderboardField.setEditable(false);
		leaderboardField.setFont(new Font("Verdana", Font.BOLD, 14));
		leaderboardField.setText("WPM|CPM|Username\n" + leaderboardText);
		labels.get("leaderBoard").add(leaderboardField);
		// NEWS
		new JavaLabel("newsBackground", layers.get("news"), 0, 0, 1000, 700, labels, 1, routeMenu);
		new ProgramButton("READ ARTICLES", layers.get("news"), fWidth * 0.1, fHeight * 0.82, 200, 50, menuButtons);
		new JavaLabel("blackBar", layers.get("news"), 0, fHeight * 0.55, fWidth, 170, labels, 2, routeMenu);
		new ProgramButton("Delfi(sabiedriba)", layers.get("news"), fWidth * 0.1, fHeight * 0.15, 200, 50, menuButtons);
		new ProgramButton("Delfi(bizness)", layers.get("news"), fWidth * 0.4, fHeight * 0.15, 200, 50, menuButtons);
		new ProgramButton("Delfi(visas)", layers.get("news"), fWidth * 0.7, fHeight * 0.15, 200, 50, menuButtons);
		new ProgramButton("Use File", layers.get("news"), fWidth * 0.4, fHeight * 0.45, 200, 50, menuButtons);
		new JavaLabel("currentNews", layers.get("news"), 0, fHeight * 0.04, fWidth, 30, labels, 2, routeMenu);
		new JavaLabel("articleNR", layers.get("news"), fWidth * 0.02, fHeight * 0.55, 500, 30, labels, 2, routeMenu);
		new JavaLabel("NRtext", layers.get("news"), 0, fHeight * 0.6, 500, 30, labels, 2, routeMenu);
		labels.get("NRtext").setText("Select which article you want to read:");
		new JavaLabel("NRField", layers.get("news"), fWidth * 0.47, fHeight * 0.6, 100, 30, labels, 2, routeMenu);
		inputFieldArticleNR = new JTextArea();
		inputFieldArticleNR.setBounds(10, 2, 400, 70);
		inputFieldArticleNR.setForeground(Color.white);
		inputFieldArticleNR.setFont(new Font("Verdana", Font.BOLD, 20));
		inputFieldArticleNR.setOpaque(false);
		inputFieldArticleNR.setText((articleIndex + 1) + "");
		labels.get("NRField").add(inputFieldArticleNR);
		new ProgramButton("Select NR", layers.get("news"), fWidth * 0.57, fHeight * 0.6, 200, 30, menuButtons);
		new JavaLabel("CurrentArticleNR", layers.get("news"), 0, fHeight * 0.65, 500, 30, labels, 2, routeMenu);
		labels.get("CurrentArticleNR").setText("Currently selected article number : " + (articleIndex + 1));
		new JavaLabel("NRerrText", layers.get("news"), fWidth * 0.4, fHeight * 0.82, 500, 50, labels, 2, routeMenu);
		labels.get("NRerrText").setForeground(Color.red);
		new JavaLabel("linkField", layers.get("news"), 0, fHeight * 0.7, fWidth * 0.98, 60, labels, 2, routeMenu);
		articleLink = new JTextArea();
		articleLink.setBounds(10, 2, (int) (fWidth * 0.96), 60);
		articleLink.setForeground(Color.white);
		articleLink.setFont(new Font("Verdana", Font.BOLD, 20));
		articleLink.setOpaque(false);
		articleLink.setEditable(false);
		articleLink.setLineWrap(true);
		labels.get("linkField").add(articleLink);
	}

	static void setupLayers() {
		new JavaLayeredPane("menu", menuScreen, 0, -fHeight, fWidth, fHeight, layers, 0);
		new JavaLayeredPane("play", menuScreen, fWidth, 0, fWidth, fHeight, layers, 0);
		new JavaLayeredPane("info", menuScreen, -fWidth, 0, fWidth, fHeight, layers, 0);
		new JavaLayeredPane("news", menuScreen, fWidth, -fHeight, fWidth, fHeight, layers, 0);
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
