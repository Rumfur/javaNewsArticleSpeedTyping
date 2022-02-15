package inputClasses;

import java.util.HashMap;

import javax.swing.*;

public class JavaLayeredPane extends JLayeredPane {

	private static final long serialVersionUID = 1L;

	public String name;
	int xStart, yStart;
	public boolean selected;

	public JavaLayeredPane(String name, Object location, int xStart, int yStart, int width, int height, HashMap<String, JavaLayeredPane> list,
			Integer layer) {
		super();
		this.name = name;
		this.xStart = xStart;
		this.yStart = yStart;
		this.setBounds(xStart, yStart, width, height);
		this.selected = false;
		try {
			((JFrame) location).add(this);
		} catch (Exception e) {
			((JavaLayeredPane) location).add(this, layer);
		}
		list.put(name, this);
	}

	public void setSlidePoint(int x, int y) { // SETS LOCATION TO WHERE THE LAYER HAS TO SLIDE TO
		this.xStart = x;
		this.yStart = y;
	}

	public void Move() {
		if (this.selected) {
			this.setLocation(moveScreens(this.getX(), 0), moveScreens(this.getY(), 0));
		} else {
			this.setLocation(moveScreens(this.getX(), this.xStart), moveScreens(this.getY(), this.yStart));
		}
	}

	public int moveScreens(int currentPos, int destinationPos) { // MOVES LAYER
		if (currentPos != destinationPos) {
			int direction = -1;
			if (currentPos - destinationPos < 0) {
				direction = 1;
			}
			if ((currentPos > destinationPos) == (direction == -1)) {
				currentPos += direction * (Math.abs((currentPos - destinationPos) / 10) + 1);
			}
		}
		return currentPos;
	}
}