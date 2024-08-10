package com.latzina.juego.utiles;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.latzina.juego.io.Entrada;
import com.latzina.juego.itemsColision.Biblioteca;
import com.latzina.juego.itemsColision.Escritorio;
import com.latzina.juego.itemsColision.Hoja;
import com.latzina.juego.itemsColision.Puerta;

public class MundoB2 {
	
	private BodyDef defCuerpo;
	private PolygonShape poligono;
	private FixtureDef fixture;
	private Body cuerpo;
	
	public MundoB2(World mundo, TiledMap mapa, Entrada entradas) {
		defCuerpo = new BodyDef();
		poligono = new PolygonShape();
		fixture = new FixtureDef();
		
		//según el mapa del Tiled, se realiza un for each para cada objeto del tipo en el que se este trabajando
		//y se pueda mostrar en pantalla con su caja para luego realizar las colisiones
		for(MapObject objeto : mapa.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
			
			new Escritorio(mundo,mapa,rect);
		}
		for(MapObject objeto : mapa.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
			
			new Biblioteca(mundo,mapa,rect);
		}
		for(MapObject objeto : mapa.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
			
			new Puerta(mundo,mapa,rect,entradas);
		}
		for(MapObject objeto : mapa.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
//			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
//			
//			defCuerpo.type = BodyDef.BodyType.StaticBody;
//			defCuerpo.position.set((rect.getX() + rect.getWidth()/2), (rect.getY() + rect.getHeight() / 2));
//			
//			cuerpo = mundo.createBody(defCuerpo);
//			
//			poligono.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
//			fixture.shape = poligono;
//			cuerpo.createFixture(fixture);
		}
		for(MapObject objeto : mapa.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
			
			new Hoja(mundo,mapa,rect);
		}
	}
}
