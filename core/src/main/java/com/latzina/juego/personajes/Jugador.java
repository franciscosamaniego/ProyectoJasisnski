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
import com.latzina.juego.items.Item;
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
	private int vida = 100;
	private int vidaTotal;
	private int puntaje = 0;
	
	
	
	public Jugador(World mundo, PantallaJuego juego) {
		super(juego.getAtlas().findRegion("jugador"));
		this.mundo = mundo;
		this.vidaTotal = vida;
		
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
		//se acutaliza la posición del jugador y su cuerpo en base a los movimientos que realiza
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
		//se crea el cuerpo del jugador, junto a su forma para poder realizar las colisiones
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
	
	public void dibujar(int opc) {
		//cuando se abre el inventario, el jugador podrá identificar en que item
		//esta parado ya que va a estar más grande que los demás
		float escala = 0.8f;
		int nuevoAncho = 0;
		int nuevoAlto = 0;
		int anchoOriginal = 0;
		int altoOriginal = 0;
		for (int i = 0; i < this.cantidadItems; i++) {
			anchoOriginal = inventario[i].getImagen().getWidth();
			altoOriginal = inventario[i].getImagen().getHeight();
			nuevoAncho = (int) (anchoOriginal * escala);
			nuevoAlto = (int) (altoOriginal * escala);
			 if (i == opc) {
		            inventario[i].getImagen().setSize(nuevoAncho, nuevoAlto);
		        } else {
		            inventario[i].getImagen().setSize(anchoOriginal, altoOriginal);
		        }
			inventario[i].getImagen().dibujar();
		}
	}
	
	public void acomodarInventario(float x, float y) {
		//acomoda el inventario para posicionarlo en la esquina inferior izquierda
		for (int i = 0; i < this.cantidadItems; i++) {
			inventario[i].getImagen().setPosition(x, y + (100*i));
		}
	}
	
	public int getCantidadItems() {
		return cantidadItems;
	}
	
	public int getVida() {
		return this.vida;
	}
	
	public void modificarVida(int valor) {
		this.vida += valor;
		if(this.vida <= 0) {
			this.vida = 0;
		}else if(this.vida >= this.vidaTotal) {
			this.vida = this.vidaTotal;
		}
	}
	
	public int getVidaTotal() {
		return this.vidaTotal;
	}
	
	public void sacarItem(int opc) {
		for (int i = opc; i < this.cantidadItems; i++) {
			if(i == this.cantidadItems-1) {
				inventario[i] = null;
			}else {
				inventario[i] = inventario[i+1];
				
			}
			
		}
		this.cantidadItems--;
	}
	
	public int getPuntaje() {
		return puntaje;
	}
	
	public void sumarPuntos(int valor) {
		this.puntaje += valor;
	}
}
