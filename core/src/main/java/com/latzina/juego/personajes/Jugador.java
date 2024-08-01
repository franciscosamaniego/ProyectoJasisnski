package com.latzina.juego.personajes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.latzina.juego.alimentos.Item;
import com.latzina.juego.pantallas.PantallaJuego;
import com.latzina.juego.utiles.Config;

public class Jugador extends Sprite{
	
	private World mundo;
	private Body cuerpo;
	private TextureRegion estadoPersonaje;
	private Direccion direccionActual = Direccion.QUIETO;
	private Direccion direccionAnterior = Direccion.DERECHA;
	private Animation jugadorMovD;
	private Animation jugadorMovI;
	private Animation jugadorMovA;
	private Animation jugadorMovAb;
	private float timer = 0;
	private Item[] inventario = new Item[5];
	private int cantidadItems = 0;
	
	
	public Jugador(World mundo, PantallaJuego juego) {
		super(juego.getAtlas().findRegion("jugador"));
		this.mundo = mundo;
		
		//en esta parte se crea un array list para guardar los frames de la animación para
		//luego pasarselas a las distintas animaciones
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 0; i < 2; i++) {
			frames.add(new TextureRegion(getTexture(), i * 28, 6,27, 37));
		}
		jugadorMovD = new Animation(0.5f,frames);
		frames.clear();
		
		for (int i = 2; i < 4; i++) {
			frames.add(new TextureRegion(getTexture(), i * 27, 6, 27, 37));
		}
		jugadorMovI = new Animation(0.5f,frames);
		frames.clear();
		
		for (int i = 5; i < 7; i++) {
			frames.add(new TextureRegion(getTexture(), i * 28, 6, 27, 37));
		}
		jugadorMovAb = new Animation(0.5f,frames);
		frames.clear();
		
		for (int i = 8; i < 10; i++) {
			frames.add(new TextureRegion(getTexture(), i * 29, 6 ,26, 37));
		}
		jugadorMovA = new Animation(0.5f,frames);
		frames.clear();
		
		estadoPersonaje = new TextureRegion(getTexture(), 0, 6, 27, 37);
		
		definir();
		setBounds(0, 0, 50, 50);
		setRegion(estadoPersonaje);
	}
	
	
	public void actualizar(float dl) {
		setPosition(cuerpo.getPosition().x - getWidth() / 2, cuerpo.getPosition().y - getHeight() / 2);
		if(direccionActual != Direccion.QUIETO) {
			timer += dl;
		}else {
			timer = 0;
		}
		
		setRegion(getFrame(dl));
	}
	
	public TextureRegion getFrame(float dl) {
		//luego de obtener las direcciones del personaje, según las mismas se crea la animación
	    getDireccion();
	    TextureRegion region = new TextureRegion(getTexture(), 0, 6, 27, 37);

	    switch(direccionActual) {
	        case DERECHA:
	            region = (TextureRegion) jugadorMovD.getKeyFrame(timer, true);
	            break;
	        case IZQUIERDA:
	            region = (TextureRegion) jugadorMovI.getKeyFrame(timer, true);
	            break;
	        case ABAJO:
	            region = (TextureRegion) jugadorMovAb.getKeyFrame(timer, true);
	            break;
	        case ARRIBA:
	            region = (TextureRegion) jugadorMovA.getKeyFrame(timer, true);
	            break;
	        case QUIETO:
	           
	            switch(direccionAnterior) {
	                case DERECHA:
	                    region = new TextureRegion(getTexture(), 0, 6, 27, 37);
	                    break;
	                case IZQUIERDA:
	                    region = new TextureRegion(getTexture(), 54, 6, 27, 37);
	                    break;
	                case ABAJO:
	                    region = new TextureRegion(getTexture(), 108, 6, 27, 37);
	                    break;
	                case ARRIBA:
	                    region = new TextureRegion(getTexture(), 199, 6, 27, 37);
	                    break;
	            }
	            break;
	    }
	    return region;
	}

	
	public void getDireccion() {
		//segun el movimiento de la cámara, se actualiza el valor de la dirección del personaje
	    if(cuerpo.getLinearVelocity().y > 0) {
	        direccionAnterior = direccionActual;
	        direccionActual = Direccion.ARRIBA;
	    } else if(cuerpo.getLinearVelocity().y < 0) {
	        direccionAnterior = direccionActual;
	        direccionActual = Direccion.ABAJO;
	    } else if(cuerpo.getLinearVelocity().x > 0) {
	        direccionAnterior = direccionActual;
	        direccionActual = Direccion.DERECHA;
	    } else if(cuerpo.getLinearVelocity().x < 0) {
	        direccionAnterior = direccionActual;
	        direccionActual = Direccion.IZQUIERDA;
	    } else {
	        // Si el personaje está quieto, actualiza la dirección previa
	        if (direccionActual != Direccion.QUIETO) {
	            direccionAnterior = direccionActual;
	        }
	        direccionActual = Direccion.QUIETO;
	    }
	}

	
	public void definir() {
		BodyDef defCuerpo = new BodyDef();
		defCuerpo.position.set(200,32);
		defCuerpo.type = BodyDef.BodyType.DynamicBody;
		
		cuerpo = mundo.createBody(defCuerpo);
		
		FixtureDef fix = new FixtureDef();
		CircleShape forma = new CircleShape();	
		forma.setRadius(10);
		
		fix.shape = forma;
		cuerpo.createFixture(fix).setUserData("Cabeza");
		
		EdgeShape cabeza = new EdgeShape();
		cabeza.set(new Vector2(-2/Config.ANCHO,10/Config.ALTO), new Vector2(2/Config.ANCHO,10/Config.ALTO));
		fix.shape = cabeza;
		fix.isSensor = true;
		
		cuerpo.createFixture(fix).setUserData("Cabeza");
	}
	
	public World getMundo() {
		return mundo;
	}
	
	public Body getCuerpo() {
		return cuerpo;
	}
	
	public void agregarItem(Item item) {
		this.inventario[this.cantidadItems++] = item;
	}
	
	public Item[] getInventario() {
		return inventario;
	}
	
	public Item getItemInventario(int idx) {
		return inventario[idx];
	}
	
	public void dibujar() {
		for (int i = 0; i < this.cantidadItems; i++) {
			inventario[i].getImagen().dibujar();
		}
	}
	
	public void acomodarInventario(float x, float y) {
		for (int i = 0; i < this.cantidadItems; i++) {
			inventario[i].getImagen().setPosition(x, y + (60*i));
		}
	}
	
	public int getCantidadItems() {
		return cantidadItems;
	}
}
