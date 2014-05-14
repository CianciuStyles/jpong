package it.cianciustyles.jpong;

import it.cianciustyles.jpong.entities.JPongBall;
import it.cianciustyles.jpong.entities.JPongCpuPaddle;
import it.cianciustyles.jpong.entities.JPongUserPaddle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

@SuppressWarnings("serial")
class JPongFrame extends JFrame {
	static Dimension windowSize = new Dimension(640, 480);
	boolean isRunning = true;
	int fps = 30;
	
	Insets insets;
	BufferedImage backBuffer;
	
	GamePhase phase;
	JPongMainMenu menu;
	JPongBall ball;
	JPongUserPaddle userPaddle;
	JPongCpuPaddle cpuPaddle;
	
	
	public void run(){
		initialize();
		
		while (isRunning) {
			long time = System.currentTimeMillis();
			
			update();
			draw();
			
			time = (1000 / fps) - (System.currentTimeMillis() - time);
			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
				}
			}
		}
		
		setVisible(false);
	}
	
	void initialize() {
		setTitle("JPong");
		setSize(windowSize.width, windowSize.height);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		insets = this.getInsets();
		setSize(insets.left + windowSize.width  + insets.right, 
				insets.top  + windowSize.height + insets.bottom);
		backBuffer = new BufferedImage(windowSize.width, windowSize.height, BufferedImage.TYPE_INT_RGB);
		
		phase = GamePhase.MAIN_MENU;
		menu = new JPongMainMenu(this);
	}
	
	void update() {
		switch (phase) {
			case MAIN_MENU:
				int result = menu.update();
				if (result != -1) {
					if (result == 2) {
						isRunning = false;
					} else {
						ball = new JPongBall(windowSize);
						userPaddle = new JPongUserPaddle(windowSize, this, result == 0);
						cpuPaddle  = new JPongCpuPaddle(windowSize);
						phase = GamePhase.ACTIVE;
					}
				}
				break;
			case ACTIVE:
				ball.update(windowSize, userPaddle, cpuPaddle);
				userPaddle.update(windowSize);
				cpuPaddle.update(ball, windowSize);
				
				if (userPaddle.getScore() == 5 || cpuPaddle.getScore() == 5) {
					phase = GamePhase.END_GAME;
				}
				break;
			case END_GAME:
				break;
		}
	}
	
	void draw() {
		Graphics2D g   = (Graphics2D) this.getGraphics();
		Graphics2D bbg = (Graphics2D) this.backBuffer.getGraphics();		

		bbg.setColor(Color.black);
		bbg.fillRect(0, 0, windowSize.width, windowSize.height);
		
		switch (phase) {
			case MAIN_MENU:
				menu.draw(bbg, windowSize);
				break;
				
			case ACTIVE:
				ball.draw(bbg);
				userPaddle.draw(bbg);
				cpuPaddle.draw(bbg);
				
				bbg.setFont(new Font("Arial", Font.BOLD, 18));
				bbg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				bbg.drawString(String.valueOf(userPaddle.getScore()), windowSize.width/4,   20);
				bbg.drawString(String.valueOf(cpuPaddle.getScore()),  windowSize.width*3/4, 20);
				break;
			
			case END_GAME:
				bbg.setFont(new Font("Arial", Font.BOLD, 40));
				bbg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				bbg.setColor(Color.white);
				drawCenteredString(bbg, userPaddle.getScore() == 5 ? "YOU WON!" : "YOU LOST!");
				g.drawImage(backBuffer, insets.left, insets.top, this);
				
				try {
					Thread.sleep(3000);
				} catch (Exception e) {
				}
				
				isRunning = false;
				break;		
		}
		
		g.drawImage(backBuffer, insets.left, insets.top, this);
	}
	
	void drawCenteredString(Graphics graphics, String message) {
		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle2D stringBounds = fontMetrics.getStringBounds(message, graphics);
		int messageWidth  = (int) stringBounds.getWidth();
		int messageHeight = (int) stringBounds.getHeight();
		
		graphics.drawString(message,  (windowSize.width - messageWidth)/2, (windowSize.height - messageHeight)/2 + fontMetrics.getAscent());
	}
}

enum GamePhase {
	MAIN_MENU, ACTIVE, END_GAME
}