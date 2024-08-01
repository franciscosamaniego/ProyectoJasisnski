package com.latzina.juego.alimentos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.latzina.juego.diseños.Texto;
import com.latzina.juego.personajes.Direccion;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class Fulbito extends Item{

	public Fulbito(World mundo, TextureAtlas atlas) {
		super("Fulbito",30,10,mundo,atlas,"Fulbito", Recurso.FULBITO);
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
