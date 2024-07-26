package com.latzina.juego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.latzina.juego.io.Entrada;
import com.latzina.juego.personajes.Imagen;
import com.latzina.juego.utiles.Config;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class PantallaJuego implements Screen{
	private SpriteBatch b;
	private Imagen fondo;
	private Imagen p;
	private Entrada entradas;
	
	private TmxMapLoader cargadorMapa;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer render;
	
	private OrthographicCamera camara;
	
	private boolean derecha = false, izquierda = false, abajo = false, arriba = false;
	private int cont = 0;
	
	@Override
	public void show() {
		entradas = new Entrada();
		b = Render.batch;
		fondo = new Imagen(Recurso.JUEGO);
		p = new Imagen(Recurso.PERSONAJE);
		
		Gdx.input.setInputProcessor(entradas);
		
		camara = new OrthographicCamera();
		cargadorMapa = new TmxMapLoader();
		mapa = cargadorMapa.load("tiles/Aula.tmx");
		
		render = new OrthogonalTiledMapRenderer(mapa);
		camara.position.set(Config.ANCHO/2,Config.ALTO/2,0);
	}

	public void input(float dl) {
		if(Gdx.input.isTouched()) {
			camara.position.x += 100 * dl;
		}
	}
	
	public void actualizar(float dl) {
		input(dl);
		
		camara.update();
		render.setView(camara);
	}
	
	@Override
	public void render(float delta) {
		actualizar(delta);
		
		
		render.render();
		
		b.begin();
			fondo.dibujar();
			p.dibujar();
		b.end();
		
		if(entradas.isAbajo()) {
			if(!abajo) {
				p = new Imagen(Recurso.PERSONAJE,p.getX(),p.getY());
				abajo = true;
				arriba = false;
				derecha = false;
				izquierda = false;
			}
			p.setY(p.getY()-3);
			p.setPosition(p.getX(), p.getY());
		}
		if(entradas.isArriba()) {
			if(!arriba) {
				p = new Imagen(Recurso.PERSONAJE4,p.getX(),p.getY());
				arriba = true;
				derecha = false;
				izquierda = false;
				abajo = false;
			}
			p.setY(p.getY()+3);
			p.setPosition(p.getX(), p.getY());
		}
		if(entradas.isDer()) {
			if(!derecha) {
				p = new Imagen(Recurso.PERSONAJE2,p.getX(),p.getY());
				derecha = true;
				izquierda = false;
				arriba = false;
				abajo = false;
			}
			p.setX(p.getX()+3);
			p.setPosition(p.getX(), p.getY());
		}
		if(entradas.isIzq()) {
			if(!izquierda) {
				p = new Imagen(Recurso.PERSONAJE3,p.getX(),p.getY());
				izquierda = true;
				derecha = false;
				arriba = false;
				abajo = false;
			}
			p.setX(p.getX()-3);
			p.setPosition(p.getX(), p.getY());
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
