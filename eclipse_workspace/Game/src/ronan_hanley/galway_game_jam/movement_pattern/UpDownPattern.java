package ronan_hanley.galway_game_jam.movement_pattern;

import ronan_hanley.galway_game_jam.nine.entity.Human;

public class UpDownPattern extends BackAndForthPattern {
	
	public UpDownPattern(int destX, int destY) {
		super(destX, destY);
	}

	@Override
	public void tick() {
		Human h = getHuman();
		
		h.goMaxSpeed();
		
		if (upPhase) {
			h.goUp();
			
			if (h.getY() < destY) {
				upPhase = false;
			}
		}
		else {
			h.goDown();
			
			if (h.getY() > srcY) {
				upPhase = true;
			}
		}
	}
	
}
