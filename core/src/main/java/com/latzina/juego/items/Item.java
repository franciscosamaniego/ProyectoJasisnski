package com.latzina.juego.items;

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
import com.latzina.juego.personajes.Jugador;

public abstract class Item extends Sprite{
	private String nombre;
	private int probE, vidaRec;
	private World mundo;
	private TextureRegion estadoAlimento;
	private Body cuerpo;
	private Imagen imagen;
	protected Fixture fixtureColision;
	private Tipos tipo;
	private String rutaSprite;
	
	public Item(String nombre, int probE, int vidaRec, World mundo, TextureAtlas atlas, String rutaSprite, String rutaImagen, Tipos tipo, float x, float y) {
		super(atlas.findRegion(rutaSprite));
		this.nombre = nombre;
		this.probE = probE;
		this.vidaRec = vidaRec;
		this.mundo = mundo;
		this.imagen = new Imagen(rutaImagen);
		
		this.tipo = tipo;
		this.rutaSprite = rutaSprite;
		
		setBody(rutaSprite,x,y);
	}
	
	public void setBody(String rutaSprite, float x, float y) {
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
		this.imagen.setPosition(x - getWidth() / 2, y - getHeight() / 2);
		definir(x,y);
		setBounds(x - getWidth() / 2, y - getHeight() / 2, getWidth(), getHeight());
		setRegion(estadoAlimento);
	}
	
	
	public Imagen getImagen() {
		return imagen;
	}
	
	public void definir(float x, float y) {
		BodyDef defCuerpo = new BodyDef();
		defCuerpo.position.set(x,y);
		defCuerpo.type = BodyDef.BodyType.StaticBody;
		
		cuerpo = mundo.createBody(defCuerpo);
		
		FixtureDef fix = new FixtureDef();
		CircleShape forma = new CircleShape();	
		forma.setRadius(5);
		
		fix.shape = forma;
		fixtureColision = cuerpo.createFixture(fix);

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
	//cuando el jugador colisiona con el objeto, le aparece encima una letra para que pueda interactuar
	public abstract void recogerItem();
	
	public Vector2 getPosicion() {
		return this.cuerpo.getPosition();
	}
	
	//cuando el jugador se aleja, la e desaparece
	public abstract void terminarInteraccion();
	
	public void actualizar(float dl) {
		setPosition(cuerpo.getPosition().x - getWidth() / 2, cuerpo.getPosition().y - getHeight() / 2);
	}
	
	public void eliminar() {
		cuerpo.destroyFixture(fixtureColision);
		mundo.destroyBody(cuerpo);
	}
	
	public Tipos getTipo() {
		return tipo;
	}
	
	public String getRutaSprite() {
		return rutaSprite;
	}
	
	public void setFixtureColision(Item item) {
		this.fixtureColision.setUserData(item);
	}
}
