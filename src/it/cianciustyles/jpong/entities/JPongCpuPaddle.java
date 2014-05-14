package it.cianciustyles.jpong.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class JPongCpuPaddle extends Rectangle {
	static int paddleWidth = 20, paddleHeight = 100;
	int yVelocity = 8, idleMovementRange = 10;
	private int score;
	
	public JPongCpuPaddle(Dimension windowSize) {
		super((windowSize.width - paddleWidth - 40), (windowSize.height - paddleHeight)/2, paddleWidth, paddleHeight);
		this.setScore(0);
	}
	
	public void update(JPongBall ball, Dimension windowSize) {
		if (ball.xVelocity > 0) {
			if (ball.y <= this.y + this.height/2) {
				this.y -= yVelocity;
			} else if (ball.y + ball.height > this.y + this.height/2) {
				this.y += yVelocity;
			}
		} else if (ball.xVelocity < 0) {
			if (windowSize.height/2 < this.y + this.height/2 - idleMovementRange) {
				this.y -= yVelocity;
			} else if (windowSize.height/2 > this.y + this.height/2 + idleMovementRange) {
				this.y += yVelocity;
			}
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
