package inputClasses;

import java.awt.event.*;

import javax.swing.JLabel;
import newsArticleSpeedTyping.*;
import rss.RssData;

public class Mouse implements MouseListener {

	public Mouse(JLabel button) {
		button.addMouseListener(this); // adds mouse listener to label
	}

	public void mousePressed(MouseEvent e) {
		((ProgramButton) e.getSource()).buttonIconSwitch(2, true); // buttonIcon_Pressed.png
		switch (((ProgramButton) (e.getSource())).name) { // for scrolling leaderboard
		case "/\\": { // move leaderboard up
			MainMenu.boardMovementDir = 1;
			break;
		}
		case "\\/": { // move leaderboard down
			MainMenu.boardMovementDir = -1;
			break;
		}
		}
	}

	public void mouseReleased(MouseEvent e) {
		ProgramButton button = (ProgramButton) e.getSource();
		if (button.inZone) {
			switch (button.name) {
			case "READ ARTICLES": {
				MainMenu.layers.get("menu").setSlidePoint(-MainMenu.layers.get("play").xStart,
						-MainMenu.layers.get("play").yStart);
				menuSelect("play");
				break;
			}
			case "PLAY": {
				new GameScreen();
				MainMenu.menuScreen.setVisible(false);
				MainMenu.menuTimer.stop();
				break;
			}
			case "INFO PAGE": {
				MainMenu.layers.get("menu").setSlidePoint(-MainMenu.layers.get("info").xStart,
						-MainMenu.layers.get("info").yStart);
				menuSelect("info");
				break;
			}
			case "BACK": {
				menuSelect("menu");
				break;
			}
			case "NEWS SELECT": {
				MainMenu.layers.get("menu").setSlidePoint(-MainMenu.layers.get("news").xStart,
						-MainMenu.layers.get("news").yStart);
				menuSelect("news");
				break;
			}
			case "Use File": {
				MainMenu.switchToFile();
				break;
			}
			case "Delfi(sabiedriba)": {
				if (!MainMenu.selectedNewsSiteName.equals(button.name)) {
					MainMenu.switchNews("https://www.delfi.lv/rss/?channel=sabiedriba", button.name);
				}
				break;
			}
			case "Delfi(bizness)": {
				if (!MainMenu.selectedNewsSiteName.equals(button.name)) {
					MainMenu.switchNews("https://www.delfi.lv/rss/?channel=bizness", button.name);
				}
				break;
			}
			case "Delfi(visas)": {
				if (!MainMenu.selectedNewsSiteName.equals(button.name)) {
					MainMenu.switchNews("https://www.delfi.lv/rss/?channel=delfi", button.name);
				}
				break;
			}
			case "EXIT": {
				System.exit(0);
				break;
			}
			case "RESET": {
				gameClose();
				new GameScreen();
				break;
			}
			case "PREVIOUS ARTICLE": {
				if (MainMenu.articleIndex == 0) {
					MainMenu.articleIndex = MainMenu.articleCount - 1;
				} else {
					MainMenu.articleIndex -= 1;
				}
				gameClose();
				new GameScreen();
				break;
			}
			case "NEXT ARTICLE": {
				if (MainMenu.articleIndex == MainMenu.articleCount) {
					MainMenu.articleIndex = 0;
				} else {
					MainMenu.articleIndex += 1;
				}
				gameClose();
				new GameScreen();
				break;
			}
			case "BACK TO MENU": {
				gameClose();
				MainMenu.menuScreen.setVisible(true);
				MainMenu.menuTimer.start();
				break;
			}
			case "Select NR": {
				int indexNR = 0;
				try {
					indexNR = Integer.parseInt(MainMenu.inputFieldArticleNR.getText()) - 1;
					if (indexNR >= 0 && indexNR < MainMenu.articleCount) {
						MainMenu.articleIndex = indexNR;
					} else {
						MainMenu.labels.get("NRerrText")
								.setText("CHOOSE NUMBER BETWEEN 1 AND " + MainMenu.articleCount + "!");
						break;
					}
				} catch (Exception e2) {
					MainMenu.labels.get("NRerrText").setText("ENTER A VALID NUMBER!");
					break;
				}
				MainMenu.labels.get("CurrentArticleNR").setText("Currently selected article number : " + (indexNR + 1));
				MainMenu.articleLink.setText(RssData.linkData.get(indexNR));
				MainMenu.labels.get("NRerrText").setText("");
				break;
			}
			case "/\\": {
				MainMenu.boardMovementDir = 0;
				break;
			}
			case "\\/": {
				MainMenu.boardMovementDir = 0;
				break;
			}
			case "": {
				break;
			}
			default:// CHECKS FOR CHOSEN DIFFICULTY
				int i = 0;
				for (String difficultyChoice : MainMenu.difficultyButtons.keySet()) {
					if (button.name.equals(difficultyChoice)) {
						((ProgramButton) MainMenu.difficultyButtons.get(difficultyChoice)).selected = true;
						MainMenu.difficulty = button.name;
					} else {
						((ProgramButton) MainMenu.difficultyButtons.get(difficultyChoice)).selected = false;
						((ProgramButton) MainMenu.difficultyButtons.get(difficultyChoice)).buttonIconSwitch(0, false);
					}
					i++;
				}
				if (i == MainMenu.difficultyButtons.keySet().size()) {
					((ProgramButton) MainMenu.difficultyButtons.get(MainMenu.difficulty)).selected = true;
				}
				MainMenu.labels.get("difficultySetting").setText("Current difficulty : " + MainMenu.difficulty);
				break;
			}
			if (!button.selected) {
				button.buttonIconSwitch(1, true); // buttonIcon_ON.png
			}
		}
	}

	public void menuSelect(String name) {
		MainMenu.layers.get(name).selected = true;
		for (String layer : MainMenu.layers.keySet()) {
			if (layer != name) {
				MainMenu.layers.get(layer).selected = false;
			}
		}
	}

	public static void gameClose() {
		GameScreen.timer.stop();
		GameScreen.fallingWords.clear();
		GameScreen.gameScreen.removeAll();
		GameScreen.gameScreen.dispose();
	}

	public void mouseEntered(MouseEvent e) {
		if ((boolean) ((ProgramButton) e.getSource()).selected == false) {
			((ProgramButton) e.getSource()).buttonIconSwitch(1, true); // buttonIcon_ON.png
		}
	}

	public void mouseExited(MouseEvent e) {
		if ((boolean) ((ProgramButton) e.getSource()).selected == false) {
			((ProgramButton) e.getSource()).buttonIconSwitch(0, false); // buttonIcon.png
		}
	}

	public void mouseClicked(MouseEvent e) {
	}
}
//203 lines, 1 constructor, 7 methods