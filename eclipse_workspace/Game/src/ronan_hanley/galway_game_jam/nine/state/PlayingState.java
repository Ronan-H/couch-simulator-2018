package ronan_hanley.galway_game_jam.nine.state;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ronan_hanley.galway_game_jam.movement_pattern.SpinAroundPattern;
import ronan_hanley.galway_game_jam.movement_pattern.UpDownPattern;
import ronan_hanley.galway_game_jam.nine.Game;
import ronan_hanley.galway_game_jam.nine.Input;
import ronan_hanley.galway_game_jam.nine.entity.Camera;
import ronan_hanley.galway_game_jam.nine.entity.Human;
import ronan_hanley.galway_game_jam.nine.entity.furniture.Couch;
import ronan_hanley.galway_game_jam.nine.entity.furniture.Furniture;
import ronan_hanley.galway_game_jam.nine.level.Level;

public class PlayingState extends TransferableState {
	private List<Furniture> furniture;
	private List<Human> humans;
	private Furniture player;
	private Level currentLevel;
	private Camera camera;
	
	private static final int spawnX = 100, spawnY = 1450;
	
	private Music playerMovingMusic;
	private boolean movingMusicPlaying;
	
	public PlayingState(StateBasedGame sbg, Input input) {
		super(sbg, input);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		super.init(gc, sbg);
		
		currentLevel = new Level(1);
		
		furniture = new ArrayList<Furniture>();
		humans = new ArrayList<Human>();
		player = new Couch(100, 1450);
		camera = new Camera(0, currentLevel.getHeight() - Game.screenHeight, player);
		
		humans.add(new Human(445, 1095, new UpDownPattern(445, 835)));
		humans.add(new Human(950, 1460, new UpDownPattern(950, 457)));
		humans.add(new Human(567, 494, new SpinAroundPattern()));
		
		playerMovingMusic = new Music("res/music/PlayerMoving.ogg");
		playerMovingMusic.play();
		playerMovingMusic.pause();
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
			
			if (player.isMoving()) {
				if (!movingMusicPlaying) {
					playerMovingMusic.resume();
					movingMusicPlaying = true;
				}
			}
			else {
				if (movingMusicPlaying) {
					playerMovingMusic.pause();
					movingMusicPlaying = false;
				}
			}
		}
		
		for (Furniture f : furniture) {
			f.tick(currentLevel);
		}
		
		for (Human h : humans) {
			h.tick(currentLevel);
			
			if (h.canSeeFurniture(player, currentLevel)) {
				onPlayerSpotted();
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
	}
	
	public void onPlayerSpotted() {
		resetPlayerPosition();
	}
	
	public void resetPlayerPosition() {
		if (player != null) {
			player.setX(spawnX);
			player.setY(spawnY);
			
			player.resetRotation(currentLevel);
		}
	}
	
	@Override
	public int getID() {
		return 0;
	}

}
