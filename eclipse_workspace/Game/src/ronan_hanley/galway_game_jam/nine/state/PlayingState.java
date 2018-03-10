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
import ronan_hanley.galway_game_jam.nine.entity.furniture.Couch;
import ronan_hanley.galway_game_jam.nine.entity.furniture.Furniture;

public class PlayingState extends TransferableState {
	private List<Furniture> furniture;
	private Furniture player;
	private Input input;
	// private List<>
	
	public PlayingState(StateBasedGame sbg, Input input) {
		super(sbg, input	);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		furniture = new ArrayList<Furniture>();
		player = new Couch(10, 10);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		for (Furniture f : furniture) {
			f.tick();
		}
		
		if (player != null) {
			// process input
			
			player.tick();
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.screenWidth, Game.screenHeight);
		
		g.setColor(Color.red);
		g.fillRect(40, 40, 100, 40);
		
		for (Furniture f : furniture) {
			f.render(g);
		}
		
		if (player != null) player.render(g);
	}

	@Override
	public int getID() {
		return 0;
	}

}
