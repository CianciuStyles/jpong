package it.cianciustyles.jpong;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

public class JPongMainMenu implements KeyListener {
	int selectedIndex = 0, result = -1;
	String[] options = {"Play with keyboard", "Play with mouse", "Exit"};

	public JPongMainMenu(Component c) {
		c.addKeyListener(this);
	}
	
	public void draw(Graphics2D g, Dimension windowSize) {
		// Render JPong title
		g.setFont(new Font("Arial", Font.BOLD, 120));
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.yellow);
		FontMetrics fontMetrics = g.getFontMetrics();
		Rectangle2D stringBounds = fontMetrics.getStringBounds("JPong", g);
		int messageWidth  = (int) stringBounds.getWidth();
		int messageHeight = (int) stringBounds.getHeight();
		g.drawString("JPong", (windowSize.width - messageWidth)/2, 50 + fontMetrics.getAscent());
		
		// Render game options
		g.setFont(new Font("Arial", Font.PLAIN, 28));
		fontMetrics = g.getFontMetrics();
		for (int i = 0; i < options.length; i++) {
			g.setColor(i == selectedIndex ? Color.green : Color.white);
			
			stringBounds = fontMetrics.getStringBounds(options[i], g);
			messageWidth  = (int) stringBounds.getWidth();
			messageHeight = (int) stringBounds.getHeight();
			
			g.drawString(options[i], (windowSize.width - messageWidth)/2, (windowSize.height + messageHeight)/2 + fontMetrics.getAscent() + i*50);
		}
	}
	
	public int update() {
		return result;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			selectedIndex = (selectedIndex - 1);
			if (selectedIndex < 0) {
				selectedIndex = options.length - 1;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			selectedIndex = (selectedIndex + 1);
			if (selectedIndex > options.length - 1) {
				selectedIndex = 0;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			result = selectedIndex;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
