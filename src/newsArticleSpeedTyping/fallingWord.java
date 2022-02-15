package newsArticleSpeedTyping;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import inputClasses.JavaLabel;

public class fallingWord extends JLabel{

	private static final long serialVersionUID = 1L;
	static Random random = new Random();
	static int fWidth , fHeight;

	public static void setSizing(int frameWidth, int frameHeight) {
		fWidth = frameWidth;
		fHeight = frameHeight;
	}
	
	String word;
	double y;
	Font font = JavaLabel.font;
	
	public fallingWord(int x, int y, int width, int height, String name, ArrayList<fallingWord> list, int layer,
			String route) {
		super(new ImageIcon(new ImageIcon(route + name + ".png").getImage().getScaledInstance(fWidth * width / 1000,
				fHeight * width / 1000, Image.SCALE_SMOOTH)), JLabel.CENTER);
		GameScreen.layers.get("game").add(this, (Integer) layer);
		this.y = - fHeight * height / 2000;
		this.word = name;
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setForeground(Color.white);
		this.setFont(font);
		this.setText(name);
		this.setBounds((random.nextInt(fWidth - fWidth * width / 1000) + 1), y, fWidth * width / 1000, fHeight * height / 1000);
		list.add(this);
	}
	
	public String getWord() {
		return this.word;
	}

	public boolean checkForDespawn() {
		if (this.getY() > fHeight) { // if word has fallen out of the screen, it is despawned
			GameScreen.layers.get("game").remove(this);
			return true;
		}
		return false;
	}

	public int despawn(ArrayList<fallingWord> list) {
		this.setVisible(false);
		GameScreen.layers.get("game").remove(this);
		list.remove(this);
		return 1;
	}

	public void setPosition(double moveY) {
		this.y += moveY/this.word.length();
		this.setLocation(this.getX(), (int)this.y);
	}
}