package com.ngstudios.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ngstudios.game.SpaceGame;
import com.ngstudios.game.entities.Asteroid;
import com.ngstudios.game.entities.Bullet;
import com.ngstudios.game.entities.Explosion;
import com.ngstudios.game.tools.CollisionRect;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainGameScreen implements Screen {
    public static final float SPEED = 300;
    public static final float SHIP_ANIMATION_SPEED = 0.5f;

    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL*3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL*3;

    public static final float ROLL_TIMER_SWITCH_TIME = 0.15f;
    public static final float SHOOT_TIMER = 0.3f;

    public static final float MIN_ASTEROID_SPAWN_TIME = 0.3f;
    public static final float MAX_ASTEROID_SPAWN_TIME = 0.6f;

    Animation[] rolls;

    float x;
    float y;
    int roll;
    float rollTimer;
    float shootTimer;
    float stateTime;
    float asteroidSpawnTimer;

    SpaceGame game;
    Random random;

    ArrayList<Bullet> bullets;
    ArrayList<Asteroid> asteroids;
    ArrayList<Explosion> explosions;

    BitmapFont scoreFont;
    int score;

    CollisionRect playerRect;

    float health = 1; // 1 full life, 0 dead
    Texture blank;

    public MainGameScreen(SpaceGame game){
        this.game = game;
        y = 15;
        x = SpaceGame.WIDTH/2 - SHIP_WIDTH /2;
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/myfont.fnt"));

        score = 0;
        shootTimer = 0;

        random = new Random();

        asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;

        roll = 2;
        rolls = new Animation[5];

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL,
                SHIP_HEIGHT_PIXEL);

        rolls[0] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[2]); // full left
        rolls[1] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[1]); // half left
        rolls[2] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[0]); // center
        rolls[3] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[3]); // half right
        rolls[4] = new Animation(SHIP_ANIMATION_SPEED,rollSpriteSheet[4]); // full right

        playerRect = new CollisionRect(0,0, SHIP_WIDTH, SHIP_HEIGHT);

        blank = new Texture("blank.png");

        bullets = new ArrayList<>();
        asteroids = new ArrayList<>();
        explosions = new ArrayList<>();
    }

    public void show() {

    }

    public void render(float delta) {

        //Shooting code
        shootTimer += delta;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_TIMER){
            shootTimer = 0;
            int offset = 4;
            if(roll == 1 || roll == 3){
                offset = 8;
            }
            if(roll == 0 || roll == 4){
                offset = 16;
            }
            bullets.add(new Bullet(x + offset, y + SHIP_HEIGHT/2 ));
            bullets.add(new Bullet(x + SHIP_WIDTH - offset, y + SHIP_HEIGHT/2));
        }

        // Asteroid Spawn Code
        asteroidSpawnTimer -= delta;
        if(asteroidSpawnTimer <= 0){
            asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
            asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH)));
        }

        //Update Asteroids
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
        for(Asteroid asteroid : asteroids){
            asteroid.update(delta);
            if(asteroid.remove){
                asteroidsToRemove.add(asteroid);
            }
        }

        // Update bullets
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        for(Bullet bullet : bullets){
            bullet.update(delta);
            if(bullet.remove){
                bulletsToRemove.add(bullet);
            }
        }

        //Update Explosions
        ArrayList<Explosion> explosionsToRemove = new ArrayList<>();
        for(Explosion explosion : explosions){
            explosion.update(delta);
            if(explosion.remove){
                explosionsToRemove.add(explosion);
            }
        }
        explosions.removeAll(explosionsToRemove);


        // Movement code
        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            y += SPEED * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
            y-= SPEED * Gdx.graphics.getDeltaTime();

            if(y < -20){
                y = -20;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            x += SPEED * Gdx.graphics.getDeltaTime();

            //Setting border limit
            if(x +SHIP_WIDTH > SpaceGame.WIDTH){
                x = SpaceGame.WIDTH - SHIP_WIDTH;
            }

            // Update roll if button just clicked
            if(Gdx.input.isButtonJustPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)
                    && !Gdx.input.isKeyPressed(Input.Keys.A) && roll < 4){
                rollTimer = 0;
                roll ++;

            }

            // Update roll
            rollTimer -= Gdx.graphics.getDeltaTime();
            if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4){
                rollTimer -= ROLL_TIMER_SWITCH_TIME;
                roll++;
            }
        }else{
            if(roll > 2){
                // Update roll to make it go back to center
                rollTimer -= Gdx.graphics.getDeltaTime();
                if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                    rollTimer -= ROLL_TIMER_SWITCH_TIME;
                    roll--;

                }
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();

            if (x < 0) {
                x = 0;
            }

            // Update roll if button just clicked
            if(Gdx.input.isButtonJustPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)
                    && !Gdx.input.isKeyPressed(Input.Keys.D) && roll > 0) {
                rollTimer = 0;
                roll++;
            }

                // Update roll
            rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll > 0) {
                rollTimer -= ROLL_TIMER_SWITCH_TIME;
                roll--;

            }
        }else{
            if(roll < 2){
                //Update roll to make it go back to center
                rollTimer -= Gdx.graphics.getDeltaTime();
                if(Math.abs(rollTimer) > ROLL_TIMER_SWITCH_TIME && roll < 4){
                    rollTimer -= ROLL_TIMER_SWITCH_TIME;
                    roll++;

                }
            }
        }

        //After player moves update collisionRect
        playerRect.move(x, y);

        // After all updates, check for collisions
        for(Bullet bullet:bullets){
            for(Asteroid asteroid: asteroids){
                if(bullet.getRect().collidesWith(asteroid.getRect())){
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);
                    explosions.add(new Explosion(asteroid.getX(),asteroid.getY()));
                    score += 100;
                }
            }
        }

        bullets.removeAll(bulletsToRemove);

        for(Asteroid asteroid: asteroids){
            if(asteroid.getRect().collidesWith(playerRect)){
                asteroidsToRemove.add(asteroid);
                health -= 0.1f;
            }
        }
        asteroids.removeAll(asteroidsToRemove);

        stateTime += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont,""+score);
        scoreFont.draw(game.batch,scoreLayout,Gdx.graphics.getWidth()/2 - scoreLayout.width/2,
                Gdx.graphics.getHeight() - scoreLayout.height -10);

        for(Bullet bullet : bullets){
            bullet.render(game.batch);
        }

        for(Asteroid asteroid : asteroids){
            asteroid.render(game.batch);
        }

        for(Explosion explosion : explosions){
            explosion.render(game.batch);
        }
        //Draw Health
        if(health > 0.6f){
            game.batch.setColor(Color.GREEN);
        }
        else if(health > 0.2f){
            game.batch.setColor(Color.ORANGE);
        }
        else{
            game.batch.setColor(Color.RED);
        }

        game.batch.draw(blank,0,0, Gdx.graphics.getWidth()*health,5);
        game.batch.setColor(Color.WHITE);

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
