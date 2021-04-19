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
    public static final float SPEED = 300;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;

    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL*3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL*3;

    public static final float ROLL_TIMER_SWITCH_TIME = 0.15f;

    Animation[] rolls;

    float x;
    float y;
    int roll;
    float rollTimer;
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

        rolls[0] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[2]); // full left
        rolls[1] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[1]); // half left
        rolls[2] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[0]); // center
        rolls[3] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[3]); // half right
        rolls[4] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[4]); // full right
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

            //Setting border limit
            if(x +SHIP_WIDTH > SpaceGame.WIDTH){
                x = SpaceGame.WIDTH - SHIP_WIDTH;
            }

            // Update roll if button just clicked
            if(Gdx.input.isButtonJustPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && roll < 4){
                rollTimer = 0;
                roll ++;

            }

            // Update roll
            rollTimer -= Gdx.graphics.getDeltaTime();
            if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4){
                rollTimer = 0;
                roll++;
            }
        }else{
            if(roll > 2){
                // Update roll to make it go back to center
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer = 0;
                    roll--;

                }
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();

            if (x < 0) {
                x = 0;
            }

            // Update roll if button just clicked
            if(Gdx.input.isButtonJustPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && roll > 0) {
                rollTimer = 0;
                roll++;
            }

                // Update roll
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                rollTimer = 0;
                roll--;

            }
        }else{
            if(roll < 2){
                //Update roll to make it go back to center
                rollTimer -= Gdx.graphics.getDeltaTime();
                if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4){
                    rollTimer = 0;
                    roll++;

                }
            }
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
