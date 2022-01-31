package objects;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;

import newsArticleSpeedTyping.GameScreen;

public class fallingWord extends JavaObject {

	private static final long serialVersionUID = 1L;
	
	String word;
	double y;

	public fallingWord(int x, int y, int width, int height, String name, ArrayList<fallingWord> list, int layer,
			String route) {
		super(x, y, width, height, name, layer, route);
		this.y = y;
		this.word = name;
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setForeground(Color.white);
		this.setFont(new Font("Verdana", Font.BOLD, fWidth * 20 / 1000));
		this.setText(name);
		this.setBounds((random.nextInt(fWidth - fWidth * width / 1000) + 1), y, fWidth * width / 1000, fHeight * height / 1000);
		list.add(this);
	}
	
	public String getWord() {
		return this.word;
	}
	
	public double getTheY() {
		return this.y;
	}

	public boolean checkForDespawn() {
		if (this.getY() > fHeight) {
			GameScreen.layers.get("gameLayer").remove(this);
			return true;
		}
		return false;
	}

	public int despawn(ArrayList<fallingWord> list) {
		this.setVisible(false);
		GameScreen.layers.get("gameLayer").remove(this);
		list.remove(this);
		return 1;
	}

	public void setPosition(double moveY) {
		this.y += moveY/this.word.length();
		this.setLocation(this.getX(), (int)this.y);
	}
}