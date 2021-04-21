package com.ngstudios.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ngstudios.game.tools.CollisionRect;

public class Bullet {

    private static final int SPEED = 500;
    private static final int WIDTH = 3;
    private static final int HEIGHT = 12;
    private static Texture texture;

    private float x;
    private float y;

    private CollisionRect rect;

    public boolean remove = false;

    public Bullet(float x, float y){
        this.x = x;
        this.y = y;

        this.rect = new CollisionRect(x ,y, WIDTH, HEIGHT);

        if (texture == null){
            texture = new Texture("bullet.png");
        }
    }

    public void update(float deltaTime){
        y += SPEED * deltaTime;
        if(y > Gdx.graphics.getHeight()){
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
