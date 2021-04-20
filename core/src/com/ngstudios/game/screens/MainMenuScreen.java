package com.ngstudios.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ngstudios.game.SpaceGame;

public class MainMenuScreen implements Screen {

    private static final int EXIT_BUTTON_WIDTH = 230;
    private static final int EXIT_BUTTON_HEIGTH = 80;

    private static final int PLAY_BUTTON_WIDTH = 280;
    private static final int PLAY_BUTTON_HEIGTH = 130;

    private static final int EXIT_BUTTON_Y = 100;
    private static final int EXIT_BUTTON_X = SpaceGame.WIDTH/2 - EXIT_BUTTON_WIDTH/2;

    private static final int PLAY_BUTTON_Y = 300;
    private static final int PLAY_BUTTON_X = SpaceGame.WIDTH/2 - PLAY_BUTTON_WIDTH/2;

    SpaceGame game;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;

    public MainMenuScreen(SpaceGame game){
        this.game = game;
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
    }
    public void show() {

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //Exit Button
        if(Gdx.input.getX() < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH && Gdx.input.getX() > EXIT_BUTTON_X
                && SpaceGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGTH
                && SpaceGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y){

            game.batch.draw(exitButtonActive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGTH);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }else{
            game.batch.draw(exitButtonInactive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGTH);
        }

        //Play button
        if(Gdx.input.getX() < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH && Gdx.input.getX() > PLAY_BUTTON_X
                && SpaceGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGTH
                && SpaceGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y){

            game.batch.draw(playButtonActive, EXIT_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGTH);
            if(Gdx.input.isTouched()){
                game.setScreen(new MainGameScreen(game));
            }
        }else{
            game.batch.draw(playButtonInactive, EXIT_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGTH);
        }

        game.batch.end();
    }

    public void resize(int width, int height) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }

    public void dispose() {
    }
}
