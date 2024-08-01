package com.latzina.juego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.latzina.juego.Juego;
import com.latzina.juego.alimentos.Fulbito;
import com.latzina.juego.alimentos.Item;
import com.latzina.juego.alimentos.Llave;
import com.latzina.juego.alimentos.Paty;
import com.latzina.juego.diseños.Imagen;
import com.latzina.juego.diseños.Texto;
import com.latzina.juego.io.Entrada;
import com.latzina.juego.listeners.MundoListener;
import com.latzina.juego.personajes.Jugador;
import com.latzina.juego.utiles.Config;
import com.latzina.juego.utiles.MundoB2;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class PantallaJuego implements Screen{
	private SpriteBatch b;
	private Imagen fondo;
	private Imagen j;
	private Entrada entradas;
	private Juego juego;
	private Sprite texture;
	private float timer = 0;
	private Estado estado = Estado.PROGRESO;
	
	//Variables del mapa Tiled
	private TmxMapLoader cargadorMapa;
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer render;
	
	private OrthographicCamera camara;
	private FitViewport puerto;
	
	//Variables del Box2d
	private World mundo;
	private Box2DDebugRenderer b2dr;
	private BodyDef defCuerpo;
	private PolygonShape poligono;
	private FixtureDef fixture;
	private Body cuerpo;
	
	private Jugador jugador;
	
	private TextureAtlas atlas;
	private TextureAtlas atlasItems;
	
	private boolean derecha = false, izquierda = false, abajo = false, arriba = false;
	private float tiempo = 0;
	private int opc = 1;
	
	private static Texto letra;
	private static String objeto;
	private Texto cuadro;
	private static String texto;
	private static Item item;
	private static boolean itemAgregado = false;
	
	private Texto[] menu = new Texto[3];
	private String[] opciones = {"Continuar", "Opciones", "Salir"};
	
	private Item itemEncontrado;
	@Override
	public void show() {
		int espacio = 30;
		b2dr = new Box2DDebugRenderer();
		atlas = new TextureAtlas(Recurso.SPRITE_PERSONAJE);
		atlasItems = new TextureAtlas(Recurso.SPRITE_ITEMS);
		
		texture = new Sprite(atlas.findRegion("jugador"));
		
		entradas = new Entrada();
		b = Render.batch;
		
		
		Gdx.input.setInputProcessor(entradas);
		
		camara = new OrthographicCamera();
		
		puerto = new FitViewport(Config.ANCHO,Config.ALTO,camara);
		
		cargadorMapa = new TmxMapLoader();
		mapa = cargadorMapa.load(Recurso.AULA);
		
		
		render = new OrthogonalTiledMapRenderer(mapa);
		camara.position.set(Config.ANCHO/2,Config.ALTO/2,0);
		
		mundo = new World(new Vector2(0,0), true);
		
	
		
		
		new MundoB2(mundo, mapa,entradas);
		
		jugador = new Jugador(mundo,this);
		
		for (int i = 0; i < menu.length; i++) {
			menu[i] = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			menu[i].setPosition((Config.ANCHO/2)-(menu[i].getAncho()/2),((Config.ALTO/2)+(menu[0].getAlto()/2))-((menu[i].getAlto()*i) + (espacio*i)));
			menu[i].setTexto(opciones[i]);
		}
		
		mundo.setContactListener(new MundoListener());
	}
	
	@Override
	public void render(float delta) {
        float viewPortWidth = puerto.getWorldWidth();
        float viewportHeight = puerto.getWorldHeight();
        float textX = camara.position.x - (viewPortWidth / 2);
        float textY = camara.position.y - (viewportHeight / 2) + 200;
		int prob = 0;
		
		
		
		Render.limpiarPantalla(0, 0, 0);
		
		b.begin();
		render.render();
		jugador.draw(b);
		
		if(estado == Estado.PROGRESO) {
			actualizar(delta);
			b.setProjectionMatrix(camara.combined);
			
			
			if(letra != null) {
				letra.dibujar();
			}
			if(entradas.isE() && objeto != null) {
				switch(objeto) {
				case "Puerta":
					System.out.println("Accion puerta");
					break;
				case "Hoja":
							cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			                cuadro.setTexto(texto);
			                cuadro.setPosition(textX, textY);
							break;
				case "Escritorio":
					cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
					prob = MathUtils.random(99)+1;
					if(prob <= 30) {
						prob = MathUtils.random(2)+1;
						switch(prob) {
							case 1:
								itemEncontrado = new Fulbito(mundo,atlasItems);
								break;
							case 2:
								itemEncontrado = new Paty(mundo, atlasItems);
								break;
							case 3:
								itemEncontrado = new Llave(mundo,atlasItems);
								break;
						}
						texto += itemEncontrado.getNombre();
		                cuadro.setTexto(texto);
		                cuadro.setPosition(textX, textY);
					}else {
						cuadro.setTexto("No se encontró nada en el escritorio");
					}
					break;
				case "Biblioteca":
					break;
				case "Item":
					if(itemAgregado) {
						if(jugador.getCantidadItems() < jugador.getInventario().length) {
							jugador.agregarItem(item);
							cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			                cuadro.setTexto("Item agregado al inventario");
			                cuadro.setPosition(textX, textY);
							System.out.println(item);
							item.eliminar();
							item = null;
							itemAgregado = false;
						}else {
							cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			                cuadro.setTexto("Ya no podés llevar más items, debes soltar alguno");
			                cuadro.setPosition(textX, textY);
						}
						break;
					}
				}
			}
			
			if(cuadro != null) {
				if(timer < 0.5f) {
					cuadro.dibujar();
				}else {
					timer = 0;
					cuadro = null;
				}
				timer += 0.005f;
			}
			
			
			

			if(entradas.isI()) {
				if(jugador.getCantidadItems() != 0) {
					jugador.dibujar();
				}
			}
			if(item != null) {
				item.draw(b);
			}
			
			if(itemEncontrado != null) {
				itemEncontrado.draw(b);
			}
			
			jugador.acomodarInventario(textX, textY);
			
			if(entradas.isEscape()) {
				estado = Estado.PAUSA;
			}
		
		}else if(estado == Estado.PAUSA) {
			for (int i = 0; i < menu.length; i++) {
				menu[i].dibujar();
			}
			
			tiempo += delta;
			
			if(entradas.isAbajo()) {
				if(tiempo > 0.09f) {
					tiempo = 0;
					opc++;
					if(opc > 3) {
						opc = 1;
					}	
				}
			}
			if(entradas.isArriba()) {
				if(tiempo > 0.09f) {
					tiempo = 0;
					opc--;
					if(opc < 1) {
						opc = 3;
					}
				}
			}
			
			if(entradas.isEnter()) {
				switch(opc) {
					case 1:
						estado = Estado.PROGRESO;
						break;
					case 2:
						Render.app.setScreen(new PantallaOpciones());
						break;
					case 3:
						Render.app.setScreen(new PantallaMenu());
						break;
				}
			}
			
			
			for (int i = 0; i < opciones.length; i++) {
				if(i == opc-1) {
					menu[i].setColor(Color.GOLD);
				}else {
					menu[i].setColor(Color.BLUE);
				}
			}
		}
		
		b.end();
		
		
		b2dr.render(mundo, camara.combined);
	}

	@Override
	public void resize(int width, int height) {
		camara.viewportHeight = height;
		camara.viewportWidth = width;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		mapa.dispose();
		render.dispose();
		mundo.dispose();
		b2dr.dispose();
	}
	
	public void input(float dl) {
	    Vector2 velocidad = jugador.getCuerpo().getLinearVelocity();
	    float velocidadMaxima = 100f;
	    float aceleracion = 50000f;
	    
	    // Nueva velocidad en función de las entradas
	    Vector2 nuevaVelocidad = new Vector2(0, 0);
	    
	    if (entradas.isDer()) {
	        nuevaVelocidad.x = Math.min(velocidadMaxima, velocidad.x + aceleracion * dl);
	    } else if (entradas.isIzq()) {
	        nuevaVelocidad.x = Math.max(-velocidadMaxima, velocidad.x - aceleracion * dl);
	    }
	    
	    if (entradas.isArriba()) {
	        nuevaVelocidad.y = Math.min(velocidadMaxima, velocidad.y + aceleracion * dl);
	    } else if (entradas.isAbajo()) {
	        nuevaVelocidad.y = Math.max(-velocidadMaxima, velocidad.y - aceleracion * dl);
	    }
	    
	    jugador.getCuerpo().setLinearVelocity(nuevaVelocidad);
	}



	
	public void actualizar(float dl) {
		input(dl);
		
		mundo.step(1/60f, 10, 10);
		
		jugador.actualizar(dl);	
		if(item != null) {
			item.actualizar(dl);
		}
		
		camara.position.x = jugador.getCuerpo().getPosition().x;
		camara.position.y = jugador.getCuerpo().getPosition().y;
		
		camara.update();
		render.setView(camara);
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	public static void modificarLetra(Texto texto) {
		letra = texto;
	}
	
	public static void realizarAccion(String objetoAccion, String msg) {
		objeto = objetoAccion;
		texto = msg;
	}
	
	public static void agregarItem(String objetoAccion, Item itemInventario) {
		objeto = objetoAccion;
		item = itemInventario;
		itemAgregado = true;
	}
	
}
