package com.latzina.juego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.latzina.juego.diseños.Imagen;
import com.latzina.juego.diseños.Texto;
import com.latzina.juego.io.Entrada;
import com.latzina.juego.utiles.Config;
import com.latzina.juego.utiles.ManagerSonido;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class PantallaMenu implements Screen{
	private Imagen fondo;
	private SpriteBatch b;
	private Texto[] opciones = new Texto[4];
	private String[] textos = {"Nueva Partida","Online", "Opciones", "Salir"};
	private int opc = 1;
	public float tiempo = 0;
	private Entrada entradas;

	@Override
	public void show() {
		ManagerSonido.opcionMenu.setVolume(0, 0.1f);
		ManagerSonido.sonidoEnter.setVolume(0, 0.1f);
		ManagerSonido.fondo.setLooping(true);
		ManagerSonido.fondo.setVolume(0.1f);
		ManagerSonido.fondo.play();
		entradas = new Entrada();
		fondo = new Imagen(Recurso.MENU);
		b = Render.batch;
		int espacio = 30;
		Gdx.input.setInputProcessor(entradas);
		//se crea las opciones que puede elegir el jugador
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recurso.FUENTEMENU,60,Color.WHITE,true);
			opciones[i].setTexto(textos[i]);
			opciones[i].setPosition((Config.ANCHO/2)-(opciones[i].getAncho()/2),((Config.ALTO/2)+(opciones[0].getAlto()/2))-((opciones[i].getAlto()*i) + (espacio*i)));
		}
	}

	@Override
	public void render(float delta) {
		Render.limpiarPantalla(0, 0, 0);
		b.begin();
			fondo.dibujar();
			for (int i = 0; i < opciones.length; i++) {
				opciones[i].dibujar();
			}
		b.end();
		
		tiempo += delta;
		
		if(entradas.isAbajo()) {
			if(tiempo > 0.09f) {
				tiempo = 0;
				opc++;
				ManagerSonido.opcionMenu.play();
				if(opc > 4) {
					opc = 1;
				}	
			}
			
		}
		if(entradas.isArriba()) {
			if(tiempo > 0.09f) {
				tiempo = 0;
				opc--;
				ManagerSonido.opcionMenu.play();
				if(opc < 1) {
					opc = 4;
				}
			}
			
		}
		if(entradas.isEnter()) {
			switch(opc) {
				case 1:
					Render.app.setScreen(new PantallaJuego());
					break;
				case 2:
//					Render.app.setScreen(null);
					break;
				case 3:
					Render.app.setScreen(new PantallaOpciones(false));
					break;
				case 4:
					Gdx.app.exit();
					break;
			}
			ManagerSonido.sonidoEnter.play();
		}
		//según la opción en la que este el jugador, se marca en pantalla de un color
		//distinto
		for (int i = 0; i < opciones.length; i++) {
			if(i == opc-1) {
				opciones[i].setColor(Color.GOLD);
			}else {
				opciones[i].setColor(Color.BLUE);
			}
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
		b.dispose();
		ManagerSonido.dispose();
	}

}
