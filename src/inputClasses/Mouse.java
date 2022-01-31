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
				MainMenu.layers.get("menuLayer").setSlidePoint(-MainMenu.layers.get("playScreen").xStart,
						-MainMenu.layers.get("playScreen").yStart);
				menuLayerSelect("playScreen");
				
			} else if (button.name == "PLAY") {// ACTIVATES THE GAME
				new GameScreen(MainMenu.difficulty, MainMenu.words);
				MainMenu.menuScreen.setVisible(false);
				MainMenu.menuTimer.stop();
				
			} else if (button.name == "INFO PAGE" && button.inZone) { // OPENS INFO
				MainMenu.layers.get("menuLayer").setSlidePoint(-MainMenu.layers.get("infoLayer").xStart,
						-MainMenu.layers.get("infoLayer").yStart);
				menuLayerSelect("infoLayer");
				
			} else if (button.name == "BACK" && button.inZone) { // RETURNS TO MAIN MENU
				menuLayerSelect("menuLayer");
				
			} else if (button.name == "EXIT" && button.inZone) {
				System.exit(0);
				
			} else {
				for (String difficultyChoice : MainMenu.menuButtons.keySet()) { // CHECKS FOR CHOSEN DIFFICULTY
					if (button.name.equals(difficultyChoice)) {
						((ProgramButton) MainMenu.menuButtons.get(difficultyChoice)).selected = true;
						MainMenu.difficulty = button.name;
						
					} else {
						((ProgramButton) MainMenu.menuButtons.get(difficultyChoice)).selected = false;
						((ProgramButton) MainMenu.menuButtons.get(difficultyChoice)).buttonIconSwitch(0, false);
					}
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

	public void menuLayerSelect(String name) {
		MainMenu.layers.get(name).selected = true;
		for (String layer : MainMenu.layers.keySet()) {
			if (layer != name) {
				MainMenu.layers.get(layer).selected = false;
			}
		}
	}

	public static void gameClose() {
		GameScreen.timer.stop();
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
// 1 konstruktors; 7 metodes; 112 rindas