package ronan_hanley.galway_game_jam.nine.entity.furniture;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ronan_hanley.galway_game_jam.nine.Game;
import ronan_hanley.galway_game_jam.nine.entity.Camera;
import ronan_hanley.galway_game_jam.nine.entity.MovingEntity;
import ronan_hanley.galway_game_jam.nine.level.Level;

public abstract class Furniture extends MovingEntity {
	private boolean isHorizontal;
	private int imgOffsetX, imgOffsetY;
	
	public Furniture(int initialX, int initialY, double maxSpeed, Animation anim) {
		super(initialX, initialY, maxSpeed, anim);
		isHorizontal = true;
	}
	
	@Override
	public void render(Graphics g, Camera cam) {
		g.drawAnimation(getAnim(), getX() - imgOffsetX - cam.getX(),
						getY() - imgOffsetY - cam.getY());
		
		if (Game.DEBUG) {
			// hitbox
			g.setColor(Color.red);
			g.drawRect(getX() - cam.getX(), getY() - cam.getY(), getWidth(), getHeight());
		}
	}
	
	public void tryRotate(Level level) {
		switchWidthAndHeight();
		
		if (level.collides(this)) {
			// undo
			switchWidthAndHeight();
			return;
		}
		
		isHorizontal = !isHorizontal;
		
		float newRotAngle = (isHorizontal ? 0 : 90);
		//getAnim().getCurrentFrame().setCenterOfRotation(0, 0);
		getAnim().getCurrentFrame().setRotation(newRotAngle);
		
		if (isHorizontal) {
			imgOffsetX = 0;
			imgOffsetY = 0;
		}
		else {
			imgOffsetX = getHalfHeight() - getHalfWidth();
			imgOffsetY = getHalfWidth() - getHalfHeight();
		}
	}
	
	public void resetRotation(Level level) {
		if (!isHorizontal) {
			tryRotate(level);
		}
	}
	
	public void switchWidthAndHeight() {
		int temp;
		
		temp = getWidth();
		setWidth(getHeight());
		setHeight(temp);
	}
	
}
