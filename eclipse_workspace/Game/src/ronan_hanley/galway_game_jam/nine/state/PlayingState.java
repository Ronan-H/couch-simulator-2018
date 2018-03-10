package ronan_hanley.galway_game_jam.nine.state;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ronan_hanley.galway_game_jam.nine.Game;
import ronan_hanley.galway_game_jam.nine.Input;
import ronan_hanley.galway_game_jam.nine.entity.Camera;
import ronan_hanley.galway_game_jam.nine.entity.Human;
import ronan_hanley.galway_game_jam.nine.entity.UpDownMovementPattern;
import ronan_hanley.galway_game_jam.nine.entity.furniture.Couch;
import ronan_hanley.galway_game_jam.nine.entity.furniture.Furniture;
import ronan_hanley.galway_game_jam.nine.level.Level;

public class PlayingState extends TransferableState {
	private List<Furniture> furniture;
	private List<Human> humans;
	private Furniture player;
	private Level currentLevel;
	private Camera camera;
	
	private boolean playerSpotted;
	
	public PlayingState(StateBasedGame sbg, Input input) {
		super(sbg, input);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		super.init(gc, sbg);
		
		currentLevel = new Level(1);
		
		furniture = new ArrayList<Furniture>();
		humans = new ArrayList<Human>();
		player = new Couch(0, currentLevel.getHeight() - Game.screenHeight);
		camera = new Camera(0, currentLevel.getHeight() - Game.screenHeight, player);
		
		humans.add(new Human(445, 1105, new UpDownMovementPattern(445, 835)));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		input.tick();
		
		if (player != null) {
			// process input
			int xMag = 0;
			int yMag = 0;
			
			if (input.upHeld) yMag -= 1;
			if (input.downHeld) yMag += 1;
			if (input.leftHeld) xMag -= 1;
			if (input.rightHeld) xMag += 1;
			
			if (!(xMag == 0 && yMag == 0)) {
				player.setAngle(Math.atan2(yMag, xMag));
				player.goMaxSpeed();
			}
			else {
				player.stop();
			}
			
			if (input.rotateNewlyPressed) {
				player.tryRotate(currentLevel);
			}
			
			player.tick(currentLevel);
		}
		
		for (Furniture f : furniture) {
			f.tick(currentLevel);
		}
		
		for (Human h : humans) {
			h.tick(currentLevel);
			
			if (h.canSeeFurniture(player)) {
				playerSpotted = true;
			}
		}
		
		camera.tick();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		currentLevel.render(g, camera);
		
		for (Furniture f : furniture) {
			f.render(g, camera);
		}
		
		for (Human h : humans) {
			h.render(g, camera);
		}
		
		if (player != null) player.render(g, camera);
		
		if (Game.DEBUG) {
			if (playerSpotted) {
				g.setColor(Color.red);
				g.drawString("PLAYER SPOTTED", 50, 50);
				
				playerSpotted = false;
			}
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
