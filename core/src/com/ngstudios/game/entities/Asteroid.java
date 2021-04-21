package com.ngstudios.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ngstudios.game.tools.CollisionRect;

public class Asteroid {

    private static final int SPEED = 250;
    public static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static Texture texture;

    private float x;
    private float y;

    private CollisionRect rect;

    public boolean remove = false;

    public Asteroid(float x){
        this.x = x;
        this.y = Gdx.graphics.getHeight();

        if (texture == null){
            texture = new Texture("asteroid.png");
        }

        this.rect = new CollisionRect(x ,y, WIDTH, HEIGHT);
    }

    public void update(float deltaTime){
        y -= SPEED * deltaTime;
        if(y < -HEIGHT){
            remove = true;
        }
        rect.move(x, y);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    public CollisionRect getRect(){
        return rect;
    }
}
