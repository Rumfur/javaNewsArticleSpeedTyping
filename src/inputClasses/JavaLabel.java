package inputClasses;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.*;

public class JavaLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	static int fWidth, fHeight;
	public static Font font;
	
	public static void setSizing(int frameWidth, int frameHeight) {
		fWidth = frameWidth;
		fHeight = frameHeight;
		font = new Font("Verdana", Font.BOLD, (int)(fWidth * 0.02));
	}

	public JavaLabel(String pngName, Object location, double x, double y, int width, int height, HashMap<String, JLabel> list,
			Integer layer, String route) {// CONSTRUCTS JLABEL
		super(new ImageIcon(new ImageIcon(route + pngName + ".png").getImage().getScaledInstance(width * fWidth / 1000,
				height * fHeight / 700, Image.SCALE_SMOOTH)), JLabel.CENTER);
		this.setBounds((int)(fWidth * x / 1000), (int)(fHeight * y / 700), fWidth * width / 1000, fHeight * height / 700);
		try {
			((JavaLayeredPane) location).add(this, layer);
		} catch (Exception e) {
			((JLabel) location).add(this);
		}
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setForeground(Color.white);
		this.setFont(new Font("Verdana", Font.BOLD, fWidth * 20 / 1000));
		list.put(pngName, this);
	}

}