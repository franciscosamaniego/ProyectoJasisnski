package com.latzina.juego.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.latzina.juego.Juego;
import com.latzina.juego.pantallas.PantallaJuego;

public class Render {
	
	public static SpriteBatch batch;
	public static Juego app;
	public static PantallaJuego juego;
	
	public static void limpiarPantalla(float r, float g, float b) {
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
