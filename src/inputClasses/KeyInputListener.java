package inputClasses;

import java.awt.event.*;

import newsArticleSpeedTyping.*;

public class KeyInputListener implements KeyListener {

	public KeyInputListener() {
		GameScreen.gameScreen.addKeyListener(this);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); // 48 - 57 = numbers; 65 - 90 = letters; 10 = ENTER; 8 = BACKSPACE; 32 = SPACE;
		//System.out.println(key);
		if (key == 10 || key == 8) {
			GameScreen.actionKeyInput(key);
		} else if (key != 16 && key != 20) { // 16 = Shift; 20 = CAPS LOCK
			GameScreen.keyInput(e.getKeyChar());
		}
		
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}