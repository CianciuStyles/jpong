package it.cianciustyles.jpong.entities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("serial")
public class JPongUserPaddle extends Rectangle implements KeyListener, MouseMotionListener {
	static int paddleWidth = 20, paddleHeight = 100;
	int yVelocity = 8;
	private int score;
	boolean upPressed = false, downPressed = false;
	
	public JPongUserPaddle(Dimension windowSize, Component c, boolean playWithKeyboard) {
		super(40, (windowSize.height - paddleHeight)/2, paddleWidth, paddleHeight);
		this.setScore(0);
		
		if (playWithKeyboard == true)
			c.addKeyListener(this);
		else
			c.addMouseMotionListener(this);
	}
	
	public void update(Dimension windowSize) {
		if (upPressed) {
			this.y -= yVelocity;
		} else if (downPressed) {
			this.y += yVelocity;
		}
		
		if (this.y < 0) {
			this.y = 0;
		} else if ((this.y + this.height) > windowSize.height) {
			this.y = windowSize.height - this.height;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		upPressed = false;
		downPressed = false;
		
		if (e.getY() < this.y) {
			upPressed = true;
		} else if (e.getY() > this.y) {
			downPressed = true;
		}
	}
}
