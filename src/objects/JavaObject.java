package objects;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import newsArticleSpeedTyping.*;

public class JavaObject extends JLabel {
	private static final long serialVersionUID = 1L;
	static Random random = new Random();
	static int fWidth , fHeight;

	public static void setSizing(int frameWidth, int frameHeight) {
		fWidth = frameWidth;
		fHeight = frameHeight;
	}

	public JavaObject(int x, int y, int width, int height, String pngName, int layer,
			String route) {
		super(new ImageIcon(new ImageIcon(route + pngName + ".png").getImage().getScaledInstance(fWidth * width / 1000,
				fHeight * width / 1000, Image.SCALE_SMOOTH)), JLabel.CENTER);
		GameScreen.layers.get("gameLayer").add(this, (Integer) layer);
	}

	public void setPosition(int moveX, int moveY) {
		this.setLocation(this.getX() + (int) moveX, this.getY() + (int) moveY);
	}
}