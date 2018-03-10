package ronan_hanley.galway_game_jam.nine.entity;

public abstract class BackAndForthMovementPattern extends MovementPattern {
	protected int srcX, srcY;
	protected int destX, destY;
	protected boolean upPhase;
	
	public BackAndForthMovementPattern(int destX, int destY) {
		this.destX = destX;
		this.destY = destY;
		
		upPhase = true;
	}
	
	@Override
	public abstract void tick();
	
	public void setHuman(Human human) {
		super.setHuman(human);
		
		srcX = human.getX();
		srcY = human.getY();
	}
	
}
