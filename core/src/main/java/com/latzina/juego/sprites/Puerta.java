package com.latzina.juego.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.latzina.juego.diseños.Texto;
import com.latzina.juego.io.Entrada;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class Puerta extends ObjetoInteractivo{
	
	
	public Puerta(World mundo, TiledMap mapa, Rectangle objeto, Entrada entradas) {
		super(mundo, mapa, objeto);
		super.fixtureColision.setUserData(this);
		
	}

	@Override
	public void golpearCabeza() {
		Texto texto = new Texto(Recurso.FUENTEMENU,20,Color.BLACK,true);
		texto.setTexto("E");
		texto.setPosition(this.getPosition().x-5, this.getPosition().y+60);
		Render.juego.modificarLetra(texto);
		Render.juego.realizarAccion("Puerta", "Se abre la puerta hacia un nuevo destino");


	}

	@Override
	public void eliminarLetra() {
		Render.juego.modificarLetra(null);
		Render.juego.realizarAccion("","");
	}

	
}
