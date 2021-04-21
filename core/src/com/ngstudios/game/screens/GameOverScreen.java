package com.ngstudios.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.ngstudios.game.SpaceGame;

public class GameOverScreen implements Screen {

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    Texture gameOverBanner;
    BitmapFont scoreFont;

    SpaceGame game;
    int score, highscore;

    public GameOverScreen(SpaceGame game, int score){
        this.game = game;
        this.score = score;

        // Get highscore from save files
        Preferences pref = Gdx.app.getPreferences("spacegame");
        this.highscore = pref.getInteger("highscore", 0);

        //Check if score beats Highscore
        if(score > highscore){
            pref.putInteger("highscore", score);
            pref.flush();
        }

        // Load Textures and Fonts
        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/myfont.fnt"));
    }
    public void show() {

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(gameOverBanner,Gdx.graphics.getWidth()/2 - BANNER_WIDTH/2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - 20, BANNER_WIDTH,BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont,"Score: \n"+score, Color.WHITE,0, Align.left, false);
        GlyphLayout highscoreLayout = new GlyphLayout(scoreFont,"Highscore: \n"+score, Color.WHITE,0, Align.left, false);

        scoreFont.draw(game.batch, scoreLayout,Gdx.graphics.getWidth()/2 - scoreLayout.width/2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - 20*2);
        scoreFont.draw(game.batch, highscoreLayout,Gdx.graphics.getWidth()/2 - scoreLayout.width/2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - 20*6);

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont,"Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float tryAgainX = Gdx.graphics.getWidth()/2 - tryAgainLayout.width/2;
        float tryAgainY = Gdx.graphics.getHeight()/2 - tryAgainLayout.height/2;

        float mainMenuX = Gdx.graphics.getWidth()/2 - mainMenuLayout.width/2;
        float mainMenuY = Gdx.graphics.getHeight()/2 - mainMenuLayout.height/2 - tryAgainLayout.height -15;

        float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        //if try again and menu is being pressed
        if(Gdx.input.isTouched()){
            //Try again
            if(touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width &&
                    touchY > tryAgainY - tryAgainLayout.height && touchY  < tryAgainY){
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                return;
            }

            //Main menu
            if(touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width &&
                    touchY > mainMenuY - mainMenuLayout.height && touchY  < mainMenuY){
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
        // Draw buttons
        scoreFont.draw(game.batch,tryAgainLayout,tryAgainX,tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);

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
