package ronan_hanley.galway_game_jam.nine.entity.furniture;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Couch extends Furniture {
	private static Animation couchAnim;
	
	static {
		couchAnim = new Animation();
		try {
			couchAnim.addFrame(new Image("res/couch.png"), 0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public Couch(int initialX, int initialY) {
		super(initialX, initialY, couchAnim);
	}
	
}
