package com.ngstudios.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ngstudios.game.screens.GameOverScreen;
import com.ngstudios.game.screens.MainMenuScreen;
import com.ngstudios.game.tools.ScrollingBackground;

public class SpaceGame extends Game {

	public static final int WIDTH = 410;
	public static final int HEIGHT = 650;

	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;

	private SplashWorker splashWorker;

	public void create () {
		splashWorker.closeSplashScreen();
		batch = new SpriteBatch();
		scrollingBackground = new ScrollingBackground();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render () {
		super.render();
	}

	public void dispose () {
		batch.dispose();
	}

	public SplashWorker getSplashWorker() {
		return splashWorker;
	}

	public void setSplashWorker(SplashWorker splashWorker) {
		this.splashWorker = splashWorker;
	}

	public void resize(int width, int height) {
		this.scrollingBackground.resize(width, height);
		super.resize(width, height);
	}
}
