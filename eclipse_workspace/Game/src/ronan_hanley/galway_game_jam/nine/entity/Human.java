package ronan_hanley.galway_game_jam.nine.entity;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ronan_hanley.galway_game_jam.nine.entity.furniture.Furniture;
import ronan_hanley.galway_game_jam.nine.level.Level;

public class Human extends MovingEntity {
	private static final double DEFAULT_SPEED = 2;
	private static final int DEFAULT_SIGHT_DISTANCE = 250;
	
	private static Animation anim;
	
	private MovementPattern movementPattern;
	private int sightDistance;
	private double fov;
	
	static {
		Random random = new Random();
		
		anim = new Animation();
		try {
			Image img = new Image("res/human.png");
			img.setImageColor((float) random.nextDouble(), (float) random.nextDouble(), (float) random.nextDouble());
			anim.addFrame(img, 1);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Human(int initialX, int initialY, double maxSpeed, MovementPattern movementPattern, int sightDistance) {
		super(initialX, initialY, maxSpeed, anim);
		this.movementPattern = movementPattern;
		this.sightDistance = sightDistance;
		fov = Math.toRadians(114);
		
		this.movementPattern.setHuman(this);
	}
	
	@Override
	public void tick(Level level) {
		movementPattern.tick();
		
		super.tick(level);
	}
	
	public Human(int initialX, int initialY, MovementPattern movementPattern) {
		this(initialX, initialY, DEFAULT_SPEED, movementPattern, DEFAULT_SIGHT_DISTANCE);
	}
	
	public boolean canSeeFurniture(Furniture f) {
		double dist = distanceTo(f);
		
		double angleTo = Math.atan2(f.getY() - getY(), f.getX() - getX());
		
		if (angleTo < 0) {
			angleTo = (2 * Math.PI) + angleTo;
		}
		
		double angleDiff = Math.abs(angleTo - getAngle()) % (2 * Math.PI);
		
		System.out.printf("angleTo: %.2f - angleDiff: %.2f%n", angleTo, angleDiff);
		
		boolean inRange = angleDiff < (fov /2) && dist < sightDistance;
		
		return inRange;
	}
	
}
