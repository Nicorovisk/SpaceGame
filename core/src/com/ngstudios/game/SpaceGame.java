package com.ngstudios.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ngstudios.game.screens.MainMenuScreen;

public class SpaceGame extends Game {

	public static final int WIDTH = 410;
	public static final int HEIGHT = 650;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
