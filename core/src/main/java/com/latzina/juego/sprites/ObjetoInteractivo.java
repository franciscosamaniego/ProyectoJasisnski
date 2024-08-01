package com.latzina.juego.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.latzina.juego.io.Entrada;

public abstract class ObjetoInteractivo {
	private World mundo;
	private TiledMap mapa;
	private TiledMapTile tile;
	private Rectangle objeto;
	private Body cuerpo;
	protected Fixture fixtureColision;
	protected String[] textosInteraccion = {"Busca objetos curativos, mientras mejor la calidad, mayor la cantidad de vida recuperada",
			"Encuentra las herramientas necesarias para escapar de este maligno lugar y descubrir el oscuro secreto que oculta",
			"En el camino te encontrarás con alidos, que te ayudarán en tu aventura, y con enimgos, los cuales te querran detener"
	};
	
	public ObjetoInteractivo(World mundo, TiledMap mapa, Rectangle objeto) {
		this.mundo = mundo;
		this.mapa = mapa;
		this.objeto = objeto;
		
		BodyDef defCuerpo = new BodyDef();
		FixtureDef fixture = new FixtureDef();
		PolygonShape poligono = new PolygonShape();
		
		defCuerpo.type = BodyDef.BodyType.StaticBody;
		defCuerpo.position.set((objeto.getX() + objeto.getWidth()/2) , (objeto.getY() + objeto.getHeight() / 2) );
		
		cuerpo = mundo.createBody(defCuerpo);
		
		poligono.setAsBox(objeto.getWidth()/2 , objeto.getHeight()/2 );
		fixture.shape = poligono;
		fixtureColision = cuerpo.createFixture(fixture);
	}
	
	public abstract void golpearCabeza();
	
	public abstract void eliminarLetra();
	
	public Vector2 getPosition() {
		return this.cuerpo.getPosition();
	}
	
}
