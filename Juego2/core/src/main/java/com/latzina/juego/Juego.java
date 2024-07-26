package com.latzina.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.latzina.juego.pantallas.PantallaCarga;
import com.latzina.juego.utiles.Render;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        Render.batch = new SpriteBatch();
        Render.app = this;
        this.setScreen(new PantallaCarga());
    }

    @Override
    public void render() {
    	super.render();
    }

    @Override
    public void dispose() {
    	Render.batch.dispose();
    }
}
