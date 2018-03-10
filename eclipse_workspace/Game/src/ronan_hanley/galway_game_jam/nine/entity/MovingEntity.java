package ronan_hanley.galway_game_jam.nine.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

public abstract class MovingEntity extends Entity {
	public static final double FULL_RAD_ROTATION = Math.PI * 2;
	
	private double speed;
	private double angle;
	
	private int width, height;
	
	private Animation anim;
	
	public MovingEntity(int initialX, int initialY, Animation anim) {
		this(initialX, initialY, anim.getImage(0).getWidth(), anim.getImage(0).getHeight());
		this.anim = anim;
	}
	
	public MovingEntity(int initialX, int initialY, int width, int height) {
		super(initialX, initialY);
		this.width = width;
		this.height = height;
	}
	
	public void tick() {
		changeX(Math.cos(angle) * speed);
		changeX(Math.sin(angle) * speed);
	}
	
	public void render(Graphics g) {
		g.drawAnimation(anim, getX(), getY());
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setSpeed(double newSpeed) {
		this.speed = newSpeed;
	}
	
	public void setAngle(double newAngle) {
		this.angle = newAngle;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void goUp() {
		angle = FULL_RAD_ROTATION * 0.75d;
	}
	
	public void goDown() {
		angle = FULL_RAD_ROTATION * 0.25d;
	}
	
	public void goLeft() {
		angle = FULL_RAD_ROTATION * 0.5d;
	}
	
	public void goRight() {
		angle = 0;
	}
	
}
