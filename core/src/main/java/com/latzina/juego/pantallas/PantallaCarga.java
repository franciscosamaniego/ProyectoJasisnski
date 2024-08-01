package com.latzina.juego.pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.latzina.juego.diseños.Imagen;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class PantallaCarga implements Screen{
	
	private Imagen fondo;
	private SpriteBatch b;
	private float a = 0;
	private boolean fadeTerminado = false; 
	private float cont = 0, tiempoEspera = 5;
	private boolean cambioFondo = false;
	
	@Override
	public void show() {
		fondo = new Imagen(Recurso.CARGA);
		b = Render.batch;
	}

	@Override
	public void render(float delta) {
		Render.limpiarPantalla(0, 0, 0);
		procesarFade();
		b.begin();
			fondo.dibujar();
		b.end();
	}

	private void procesarFade() {
		fondo.setTransparencia(a);
		if(!fadeTerminado) {
			a += 0.01f;
			if(a > 1) {
				a = 1;
				fadeTerminado = true;
			}
		}else {
			cont += 0.05f;
			if(cont >= tiempoEspera) {
				a -= 0.01f;
				if(a < 0) {
					a = 0;
					cambioFondo = true;
				}
			}
		}
		if(cambioFondo) {
			Render.app.setScreen(new PantallaMenu());
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
