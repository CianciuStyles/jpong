package it.cianciustyles.jpong.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class JPongBall extends Rectangle {
	static int ballHeight = 20, ballWidth = 20;
	int xVelocity = 10, yVelocity = 10;
	
	public JPongBall(Dimension windowSize) {
		super((windowSize.width  - ballWidth)/2, (windowSize.height - ballHeight)/2, ballWidth, ballHeight);
	}
	
	public void update(Dimension windowSize, JPongUserPaddle userPaddle, JPongCpuPaddle cpuPaddle) {
		this.x += xVelocity;
		this.y += yVelocity;
		
		if (this.bouncesOffUserPaddle(userPaddle)) {
			this.xVelocity = -this.xVelocity;
		} else if (this.bouncesOffCpuPaddle(cpuPaddle)) {
			this.xVelocity = -this.xVelocity;
		}
		
		if (this.x < 0) {
			cpuPaddle.setScore(cpuPaddle.getScore() + 1);
			resetPosition(windowSize);
			this.xVelocity = -this.xVelocity;
		} else if (this.x + this.width > windowSize.width) {
			userPaddle.setScore(userPaddle.getScore() + 1);
			resetPosition(windowSize);
			this.xVelocity = -this.xVelocity;
		}

		if (this.y + yVelocity < 0 || this.y + yVelocity > windowSize.height - this.height) {
			this.yVelocity = -this.yVelocity;
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}
	
	void resetPosition(Dimension windowSize) {
		this.x = (windowSize.width  - width)/2;
		this.y = (windowSize.height - height)/2;
	}
	
	boolean bouncesOffUserPaddle(JPongUserPaddle paddle) {
		return this.x == paddle.x + paddle.width && 
			   this.y + this.height > paddle.y && 
			   this.y < paddle.y + paddle.height;
	}
	
	boolean bouncesOffCpuPaddle(JPongCpuPaddle paddle) {
		return this.x + this.width == paddle.x &&
			   this.y + this.height > paddle.y && 
			   this.y < paddle.y + paddle.height;
	}
}
