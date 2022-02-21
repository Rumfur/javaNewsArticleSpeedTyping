package inputClasses;

import java.awt.event.*;
import javax.swing.JLabel;
import newsArticleSpeedTyping.*;

public class Mouse implements MouseListener {

	public Mouse(JLabel button) {
		button.addMouseListener(this); // adds mouse listener to label
	}

	public void mousePressed(MouseEvent e) {
		((ProgramButton) e.getSource()).buttonIconSwitch(2, true); // buttonIcon_Pressed.png
	}

	public void mouseReleased(MouseEvent e) {
		ProgramButton button = (ProgramButton) e.getSource();
		if (MainMenu.menuScreen.isVisible()) {// FOR MENU BUTTONS

			if (button.name == "READ ARTICLES" && button.inZone) { // OPENS RACE PANE
				MainMenu.layers.get("menu").setSlidePoint(-MainMenu.layers.get("play").xStart,
						-MainMenu.layers.get("play").yStart);
				menuSelect("play");

			} else if (button.name == "PLAY" && button.inZone) {// ACTIVATES THE GAME
				new GameScreen(MainMenu.difficulty, MainMenu.words);
				MainMenu.menuScreen.setVisible(false);
				MainMenu.menuTimer.stop();

			} else if (button.name == "INFO PAGE" && button.inZone) { // OPENS INFO
				MainMenu.layers.get("menu").setSlidePoint(-MainMenu.layers.get("info").xStart,
						-MainMenu.layers.get("info").yStart);
				menuSelect("info");

			} else if (button.name == "BACK" && button.inZone) { // RETURNS TO MAIN MENU
				menuSelect("menu");

			} else if (button.name == "EXIT" && button.inZone) {
				System.exit(0);

			} else {
				int i = 0;
				for (String difficultyChoice : MainMenu.difficultyButtons.keySet()) { // CHECKS FOR CHOSEN DIFFICULTY
					if (button.name.equals(difficultyChoice)) {
						((ProgramButton) MainMenu.difficultyButtons.get(difficultyChoice)).selected = true;
						MainMenu.difficulty = button.name;
						break;
					} else {
						((ProgramButton) MainMenu.difficultyButtons.get(difficultyChoice)).selected = false;
						((ProgramButton) MainMenu.difficultyButtons.get(difficultyChoice)).buttonIconSwitch(0, false);
					}
					i++;
				}
				if (i == MainMenu.difficultyButtons.keySet().size()) {
					((ProgramButton) MainMenu.difficultyButtons.get(MainMenu.difficulty)).selected = true;
				}
			}
		} else {// FOR GAME BUTTONS
			if (button.name == "RESET" && button.inZone) { // RESTARTS THE GAME
				gameClose();
				new GameScreen(MainMenu.difficulty, MainMenu.words);
			} else if (button.name == "BACK TO MENU" && button.inZone) { // CLOSES GAME, OPENS MENU
				gameClose();
				MainMenu.menuScreen.setVisible(true);
				MainMenu.menuTimer.start();
			}
		}
		if (button.inZone && !button.selected) {
			button.buttonIconSwitch(1, true); // buttonIcon_ON.png
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
