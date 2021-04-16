package com.ngstudios.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ngstudios.game.SpaceGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.foregroundFPS = 60;
		config.backgroundFPS = 30;

		config.width = SpaceGame.WIDTH;
		config.height = SpaceGame.HEIGHT;

		config.resizable = false;

		new LwjglApplication(new SpaceGame(), config);
	}
}
