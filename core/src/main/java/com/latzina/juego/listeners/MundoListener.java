package com.latzina.juego.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.latzina.juego.items.Item;
import com.latzina.juego.itemsColision.ObjetoInteractivo;

public class MundoListener implements ContactListener{
	
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		Fixture cuerpo = null;
		Fixture objeto = null;
		//se busca cual de los dos contactos es el jugador para guiarse
		if(fixA.getUserData().equals("Cabeza")) {
			cuerpo = fixA;
			objeto = fixB;
		}else {
			cuerpo = fixB;
			objeto = fixA;
		}
		//según la clase del objeto que realiza el contacto, se activa su respectivo método
		if(objeto.getUserData() != null) {
			if(ObjetoInteractivo.class.isAssignableFrom(objeto.getUserData().getClass())) {
				((ObjetoInteractivo) objeto.getUserData()).golpearCabeza();
			}else if(Item.class.isAssignableFrom(objeto.getUserData().getClass())) {
				
				((Item) objeto.getUserData()).recogerItem();
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		Fixture cuerpo = null;
		Fixture objeto = null;
		//se busca cual de los dos contactos es el jugador para guiarse
		if(fixA.getUserData().equals("Cabeza")) {
			cuerpo = fixA;
			objeto = fixB;
		}else {
			cuerpo = fixB;
			objeto = fixA;
		}
		
		//según la clase del objeto que realiza el contacto, se activa su respectivo método
		if(objeto.getUserData() != null) {
			if(ObjetoInteractivo.class.isAssignableFrom(objeto.getUserData().getClass())) {
				((ObjetoInteractivo) objeto.getUserData()).eliminarLetra();
			}else if(Item.class.isAssignableFrom(objeto.getUserData().getClass())) {
				((Item) objeto.getUserData()).terminarInteraccion();
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
	
}
