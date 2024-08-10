package com.latzina.juego.itemsColision;

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
	protected String[] textosInteraccion = {"Busca objetos curativos, mientras mejor la calidad,\n mayor la cantidad de vida recuperada",
			"Encuentra las herramientas necesarias para escapar\n de este maligno lugar y descubrir el oscuro \nsecreto que oculta",
			"En el camino te encontrarás con aliados, que te ayudarán\n en tu aventura , y con enimgos, los cuales \n te querran detener"
	};
	
	//en el constructor, se crea el cuerpo del objeto que este en el mapa, para que el
	//jugador pueda realizar una colisión con el mismo
	public ObjetoInteractivo(World mundo, TiledMap mapa, Rectangle objeto) {
		this.mundo = mundo;
		this.mapa = mapa;
		this.objeto = objeto;
		
		BodyDef defCuerpo = new BodyDef();
		FixtureDef fixture = new FixtureDef();
		PolygonShape poligono = new PolygonShape();
		
		//se define el tipo de cuerpo, y luego se crea la figura que representa su colisión
		defCuerpo.type = BodyDef.BodyType.StaticBody;
		defCuerpo.position.set((objeto.getX() + objeto.getWidth()/2) , (objeto.getY() + objeto.getHeight() / 2) );
		
		cuerpo = mundo.createBody(defCuerpo);
		
		poligono.setAsBox(objeto.getWidth()/2 , objeto.getHeight()/2 );
		fixture.shape = poligono;
		fixtureColision = cuerpo.createFixture(fixture);
	}
	
	
	//cuando ocurre una colisión, aparece una letra para que se pueda realizar una interacción
	public abstract void golpearCabeza();
	
	//cuando no ocurre más la colisión, se deja de mostrar el signo para que el jugador pueda interactuar
	public abstract void eliminarLetra();
	
	public Vector2 getPosition() {
		return this.cuerpo.getPosition();
	}
	
}
