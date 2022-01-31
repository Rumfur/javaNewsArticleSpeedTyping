package inputClasses;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import newsArticleSpeedTyping.*;

public class ProgramButton extends JLabel {
	private static final long serialVersionUID = 1L;

	public static String routeButtons = MainMenu.route + "//Buttons//";
	static int fWidth , fHeight;
	
	String name;
	boolean inZone, selected;
	ImageIcon[] buttonIcon = new ImageIcon[3];
	
	public static void setSizing(int frameWidth, int frameHeight) {
		fWidth = frameWidth;
		fHeight = frameHeight;
	}

	public ProgramButton(String name, JLayeredPane location, double x, double y, int width, int height,
			HashMap<String, ProgramButton> list) {
		super(name);
		this.buttonIcon[0] = new ImageIcon(new ImageIcon(routeButtons + "button.png").getImage()
				.getScaledInstance(fWidth * width / 1000, fWidth * height / 1000, Image.SCALE_SMOOTH));
		this.buttonIcon[1] = new ImageIcon(new ImageIcon(routeButtons + "buttonOn.png").getImage()
				.getScaledInstance(fWidth * width / 1000, fWidth * height / 1000, Image.SCALE_SMOOTH));
		this.buttonIcon[2] = new ImageIcon(new ImageIcon(routeButtons + "buttonPressed.png").getImage()
				.getScaledInstance(fWidth * width / 1000, fWidth * height / 1000, Image.SCALE_SMOOTH));
		this.name = name;
		this.setForeground(Color.white);
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setFont(new Font("Verdana", Font.BOLD, fWidth * 20 / 1000));
		this.setIcon(buttonIcon[0]);
		this.inZone = false;
		this.selected = false;
		this.setBounds((int)(fWidth * x / 1000), (int)(fHeight * y / 500), (int)(fWidth * width / 1000), (int)(fWidth * height / 1000));
		location.add(this, (Integer) 20);
		new Mouse(this);
		list.put(name, this);
	}

	public void buttonIconSwitch(int iconNumber, boolean inZone) {
		this.setIcon(buttonIcon[iconNumber]);
		this.inZone = inZone;
	}

}
