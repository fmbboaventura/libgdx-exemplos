package zelda.game;

import zelda.game.ZeldaGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args){
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "TLZ";
		cfg.width = 640;
	    cfg.height = 640;
	    new LwjglApplication(new ZeldaGame(), cfg);
	}
}
