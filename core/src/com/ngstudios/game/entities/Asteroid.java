package com.ngstudios.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Asteroid {

    private static final int SPEED = 250;
    public static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static Texture texture;

    private float x;
    private float y;

    public boolean remove = false;

    public Asteroid(float x){
        this.x = x;
        this.y = Gdx.graphics.getHeight();

        if (texture == null){
            texture = new Texture("asteroid.png");
        }
    }

    public void update(float deltaTime){
        y -= SPEED * deltaTime;
        if(y < -HEIGHT){
            remove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y);
    }
}
