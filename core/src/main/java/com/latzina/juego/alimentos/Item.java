package com.latzina.juego.alimentos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.latzina.juego.diseños.Imagen;
import com.latzina.juego.personajes.Direccion;

public abstract class Item extends Sprite{
	private String nombre;
	private int probE, vidaRec;
	private World mundo;
	private TextureRegion estadoAlimento;
	private Body cuerpo;
	private Imagen imagen;
	protected Fixture fixtureColision;
	
	public Item(String nombre, int probE, int vidaRec, World mundo, TextureAtlas atlas, String rutaSprite, String rutaImagen) {
		super(atlas.findRegion(rutaSprite));
		this.nombre = nombre;
		this.probE = probE;
		this.vidaRec = vidaRec;
		this.mundo = mundo;
		this.imagen = new Imagen(rutaImagen);
		
		switch(rutaSprite) {
			case "Fulbito":
				estadoAlimento = new TextureRegion(getTexture(), 8,62,64,64);
				break;
			case "Paty":
				estadoAlimento = new TextureRegion(getTexture(), 6,24,64,34);
				break;
			case "Llave":
				estadoAlimento = new TextureRegion(getTexture(), 5,4,64,20);
				break;
		}
		
		definir();
		setBounds(0,0,50,50);
		setRegion(estadoAlimento);
	}
	
	public Imagen getImagen() {
		return imagen;
	}
	
	public void definir() {
		BodyDef defCuerpo = new BodyDef();
		defCuerpo.position.set(100,32);
		defCuerpo.type = BodyDef.BodyType.StaticBody;
		
		cuerpo = mundo.createBody(defCuerpo);
		
		FixtureDef fix = new FixtureDef();
		CircleShape forma = new CircleShape();	
		forma.setRadius(10);
		
		fix.shape = forma;
		fixtureColision = cuerpo.createFixture(fix);
		
//		EdgeShape cabeza = new EdgeShape();
//		cabeza.set(new Vector2(-2/Config.ANCHO,10/Config.ALTO), new Vector2(2/Config.ANCHO,10/Config.ALTO));
//		fix.shape = cabeza;
//		fix.isSensor = true;
//		
//		cuerpo.createFixture(fix).setUserData("Fulbito");
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public int getProbE() {
		return probE;
	}
	
	public int getVidaRec() {
		return vidaRec;
	}
	
	public abstract void recogerItem();
	
	public Vector2 getPosicion() {
		return this.cuerpo.getPosition();
	}
	
	public abstract void terminarInteraccion();
	
	public void actualizar(float dl) {
		setPosition(cuerpo.getPosition().x - getWidth() / 2, cuerpo.getPosition().y - getHeight() / 2);
	}
	
	public void eliminar() {
		cuerpo.destroyFixture(fixtureColision);
		mundo.destroyBody(cuerpo);
	}
}
