package ronan_hanley.galway_game_jam.nine;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ronan_hanley.galway_game_jam.nine.state.PlayingState;

public class Game extends StateBasedGame {
	public static final boolean DEBUG = true;
	
    // Application Properties
	public static final String GAME_NAME = "Couch Simulator 2018";
    public static final int FPS = 60;
    
    public static int screenWidth, screenHeight;
    
    private static Input input;
    
    // Class Constructor
    public Game(String appName) {
        super(appName);
    }

    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) throws SlickException {
    	// The first state added will be the one that is loaded first, when the application is launched
        addState(new PlayingState(this, input));
    }

    // Main Method
    public static void main(String[] args) {
        try {
        	final int updateInterval = 1000 / FPS;
        	final double windowFraction = 1;
        	
        	// calculate window size
        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        	screenWidth = (int) Math.round(screenSize.width * windowFraction);
        	screenHeight = (int) Math.round(screenSize.height * windowFraction);
        	
            AppGameContainer app = new AppGameContainer(new Game(GAME_NAME));
            app.setVSync(true);
            app.setDisplayMode(screenWidth, screenHeight, false);
            app.setMinimumLogicUpdateInterval(-1);
            app.setMaximumLogicUpdateInterval(updateInterval);
            app.setTargetFrameRate(FPS);
            app.setShowFPS(true);
            app.setFullscreen(true);
            
            input = new Input();
            
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }
}