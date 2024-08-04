package com.latzina.juego.itemsColision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.latzina.juego.diseños.Texto;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class Hoja extends ObjetoInteractivo{

	public Hoja(World mundo, TiledMap mapa, Rectangle objeto) {
		super(mundo, mapa, objeto);
		super.fixtureColision.setUserData(this);
	}

	@Override
	public void golpearCabeza() {
		Texto texto = new Texto(Recurso.FUENTEMENU,20,Color.BLACK,true);
		texto.setTexto("E");
		texto.setPosition(this.getPosition().x-5, this.getPosition().y+60);
		Render.juego.modificarLetra(texto);
		Render.juego.realizarAccion("Hoja", super.textosInteraccion[MathUtils.random(textosInteraccion.length-1)]);

	}

	@Override
	public void eliminarLetra() {
		Render.juego.modificarLetra(null);
		Render.juego.realizarAccion("","");
	}
}
