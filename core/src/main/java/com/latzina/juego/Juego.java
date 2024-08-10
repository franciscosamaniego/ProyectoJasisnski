package com.latzina.juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.latzina.juego.pantallas.PantallaMenu;
import com.latzina.juego.utiles.Render;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Juego extends Game {
    

    @Override
    public void create() {
        Render.batch = new SpriteBatch();
        Render.app = this;
        this.setScreen(new PantallaMenu());
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
