package com.latzina.juego.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.latzina.juego.diseños.Texto;
import com.latzina.juego.personajes.Jugador;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class Llave extends Item{

	public Llave(World mundo, TextureAtlas atlas, float x, float y) {
		super("Llave",30,0,mundo,atlas,"Llave", Recurso.LLAVE,Tipos.OBJETO, x, y);
		super.fixtureColision.setUserData(this);
	}

	
	@Override
	public void recogerItem() {
		Texto texto = new Texto(Recurso.FUENTEMENU,20,Color.BLACK,true);
		texto.setTexto("E");
		texto.setPosition(this.getPosicion().x-5, this.getPosicion().y+60);
		Render.juego.modificarLetra(texto);

		Render.juego.agregarItem("Item", this);
	}
	
	@Override
	public void terminarInteraccion() {
		Render.juego.modificarLetra(null);

		Render.juego.agregarItem("Item", null);
	}
}
