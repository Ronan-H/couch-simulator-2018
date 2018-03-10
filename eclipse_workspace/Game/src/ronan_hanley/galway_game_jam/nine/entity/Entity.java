package ronan_hanley.galway_game_jam.nine.entity;

public abstract class Entity {
	private double x, y;
	
	public int getX() {
		return (int) Math.round(x);
	}
	
	public int getY() {
		return (int) Math.round(y);
	}
	
	public double getXExact() {
		return x;
	}
	
	public double getYExact() {
		return y;
	}
	
	public void setX(double newX) {
		x = newX;
	}
	
	public void setY(double newY) {
		y = newY;
	}
	
	public void changeX(double change) {
		setX(getXExact() + change);
	}
	
	public void changeY(double change) {
		setY(getYExact() + change);
	}
	
}