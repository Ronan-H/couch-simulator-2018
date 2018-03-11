package ronan_hanley.galway_game_jam.nine.entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ronan_hanley.galway_game_jam.movement_pattern.MovementPattern;
import ronan_hanley.galway_game_jam.nine.entity.furniture.Furniture;
import ronan_hanley.galway_game_jam.nine.level.Level;

public class Human extends MovingEntity {
	private static final double DEFAULT_SPEED = 1;
	private static final int DEFAULT_SIGHT_DISTANCE = 250;
	
	private static Animation staticAnim;
	
	private MovementPattern movementPattern;
	private int sightDistance;
	private double fov;
	
	static {
		staticAnim = new Animation();
		try {
			Image img = new Image("res/images/human.png");
			staticAnim.addFrame(img, 1);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Human(int initialX, int initialY, double maxSpeed, MovementPattern movementPattern, int sightDistance) {
		super(initialX, initialY, maxSpeed, new Animation(new Image[] {staticAnim.getCurrentFrame().copy()}, 1));
		this.movementPattern = movementPattern;
		this.sightDistance = sightDistance;
		fov = Math.toRadians(114);
		
		movementPattern.setHuman(this);
		
		getAnim().getCurrentFrame().setImageColor((float) random.nextDouble(), (float) random.nextDouble(), (float) random.nextDouble());
	}
	
	@Override
	public void tick(Level level) {
		movementPattern.tick();
		
		super.tick(level);
	}
	
	@Override
	public void render(Graphics g, Camera cam) {
		// draw fov area
		g.setColor(new Color(1f, 1f, 0f, 0.02f));
		
		double angIncrement = getFov() / 1000;
		for (double ang = -(getFov() /2); ang < getFov() / 2; ang += angIncrement) {
			double newAng = (getAngle() + ang) % FULL_RAD_ROTATION;
			float destX = getX() + getHalfWidth() + (float) (Math.cos(newAng) * getSightDistance());
			float destY = getY() + getHalfHeight() + (float) (Math.sin(newAng) * getSightDistance());
			
			g.setLineWidth(2);
			g.drawLine(getX() + getHalfWidth() - cam.getX(), getY() + getHalfHeight() - cam.getY(), destX - cam.getX(), destY - cam.getY());
		}
		
		g.setLineWidth(1);
		
		super.render(g, cam);
	}
	
	public Human(int initialX, int initialY, MovementPattern movementPattern) {
		this(initialX, initialY, DEFAULT_SPEED, movementPattern, DEFAULT_SIGHT_DISTANCE);
	}
	
	public boolean canSeeFurniture(Furniture f, Level level) {
		// first pass
		final double allowance = 1.2;
		
		double dist = distanceTo(f);
		
		double angleTo = Math.atan2(f.getY() - getY(), f.getX() - getX());
		
		if (angleTo < 0) {
			angleTo = (2 * Math.PI) + angleTo;
		}
		
		double angleDiff = Math.abs(angleTo - getAngle()) % (2 * Math.PI);
		
		boolean inRange = angleDiff < (fov /2) * allowance && dist < sightDistance * allowance;
		
		if (!inRange) {
			return false;
		}
		
		// second pass
		return level.inLineOfSight(this, f);
	}
	
	public int getSightDistance() {
		return sightDistance;
	}
	
	public double getFov() {
		return fov;
	}
	
}