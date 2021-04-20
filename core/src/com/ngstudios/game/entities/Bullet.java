package com.ngstudios.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    private static final int SPEED = 500;
    private static Texture texture;

    private float x;
    private float y;

    public boolean remove = false;

    public Bullet(float x, float y){
        this.x = x;
        this.y = y;

        if (texture == null){
            texture = new Texture("bullet.png");
        }
    }

    public void update(float deltaTime){
        y += SPEED * deltaTime;
        if(y > Gdx.graphics.getHeight()){
            remove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

}
