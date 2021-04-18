package com.ngstudios.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ngstudios.game.SpaceGame;

public class MainGameScreen implements Screen {
    public static final float SPEED = 120;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;

    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL*3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL*3;

    Animation[] rolls;

    float x;
    float y;
    int roll;
    float stateTime;

    SpaceGame game;

    public MainGameScreen(SpaceGame game){
        this.game = game;
        y = 15;
        x = SpaceGame.WIDTH/2 - SHIP_WIDTH /2;

        roll = 2;
        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL,
                SHIP_HEIGHT_PIXEL);

        rolls[roll] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[roll]);
    }

    public void show() {

    }

    public void render(float delta) {

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            y += SPEED * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y-= SPEED * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x += SPEED * Gdx.graphics.getDeltaTime();

        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x -= SPEED * Gdx.graphics.getDeltaTime();
        }

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);
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
