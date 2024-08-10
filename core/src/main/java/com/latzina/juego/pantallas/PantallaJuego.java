package com.latzina.juego.pantallas;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.latzina.juego.diseños.Texto;
import com.latzina.juego.escenas.Hud;
import com.latzina.juego.io.Entrada;
import com.latzina.juego.items.Alfajor;
import com.latzina.juego.items.Hamburguesa;
import com.latzina.juego.items.Item;
import com.latzina.juego.items.Llave;
import com.latzina.juego.listeners.MundoListener;
import com.latzina.juego.personajes.Jugador;
import com.latzina.juego.utiles.Config;
import com.latzina.juego.utiles.ManagerSonido;
import com.latzina.juego.utiles.MundoB2;
import com.latzina.juego.utiles.Recurso;
import com.latzina.juego.utiles.Render;

public class PantallaJuego implements Screen{
	private SpriteBatch b;
	private Entrada entradas;
	private float timer = 0;
	private float timerFlechas = 0.09f;
	private float timerPasos = 1f;
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
	
	private Jugador jugador;
	
	private TextureAtlas atlas;
	private TextureAtlas atlasItems;
	
	private float tiempo = 0;
	private int opc = 1;
	
	private static Texto letra;
	private static String objeto;
	private Texto cuadro;
	private Texto aviso;
	private static String texto;
	private static Item item;
	private static boolean itemAgregado = false;
	
	private int prob = 0;
	private ArrayList<Item> itemsEncontrados = new ArrayList<>();
	
	private Texto[] menu = new Texto[3];
	private String[] opciones = {"Continuar", "Opciones", "Salir"};
	private Hud hud;
	
	private Item itemEncontrado;
	private Item itemSoltado;
	
	@Override
	public void show() {
		ManagerSonido.opcionMenu.setVolume(0, 0.1f);
		ManagerSonido.sonidoEnter.setVolume(0, 0.1f);
		ManagerSonido.pasosPersonaje.setVolume(0, 0.1f);
		ManagerSonido.fondo.setLooping(true);
		ManagerSonido.fondo.setVolume(0.1f);
		ManagerSonido.fondo.play();
		int espacio = 30;
		b2dr = new Box2DDebugRenderer();
		atlas = new TextureAtlas(Recurso.SPRITE_PERSONAJE);
		atlasItems = new TextureAtlas(Recurso.SPRITE_ITEMS);
		
		
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
		
		hud = new Hud(jugador,b);
		
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
        float textX = camara.position.x - (viewPortWidth / 2) + 100;
        float textY = camara.position.y - (viewportHeight / 2) + 200;
        
        
        hud.escenario.act(delta);
        
        hud.modificarDatos(jugador);
        
        b.setProjectionMatrix(hud.escenario.getCamera().combined);
        
		
		Render.limpiarPantalla(0, 0, 0);
		
		b.begin();
		render.render();
		jugador.draw(b);
		
		
		if(estado == Estado.PROGRESO) {
			jugar(textX,textY,delta);
		}else if(estado == Estado.PAUSA) {
			elegirOpcionMenu(delta);
		}else if(estado == Estado.INVENTARIO) {
			verInventario(delta,textX,textY);
		}
		
		
		
		b.end();
		
		
		b2dr.render(mundo, camara.combined);
		
		hud.escenario.draw();
	}

	private void verInventario(float delta, float x, float y) {
		if(jugador.getCantidadItems() != 0) {
			tiempo += delta;
			
			if(entradas.isAbajo()) {
				if(tiempo > timerFlechas) {
					tiempo = 0;
					opc--;
					ManagerSonido.opcionMenu.play();
					if(opc < 1) {
						opc = jugador.getCantidadItems();
					}	
				}	
			}
			if(entradas.isArriba()) {
				if(tiempo > timerFlechas) {
					tiempo = 0;
					opc++;
					ManagerSonido.opcionMenu.play();
					if(opc > jugador.getCantidadItems()) {
						opc = 1;
					}
				}
			}
			cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			cuadro.setTexto("Aprete E para usar el item o Q para soltarlo");
			cuadro.dibujar();
			jugador.dibujar(opc-1);
			
			if(entradas.isE()) {
				if(tiempo > timerFlechas) {
					tiempo = 0;
					ManagerSonido.sonidoEnter.play();
					usarItem(opc-1,x,y,delta);
				}
			}else if(entradas.isQ()) {
				if(tiempo > timerFlechas) {
					tiempo = 0;
					ManagerSonido.sonidoEnter.play();
					soltarItem(opc-1,x,y);
				}
			}
			
			if(entradas.isEscape() || entradas.isI()) {
				if(tiempo > timerFlechas) {
					tiempo = 0;
					ManagerSonido.sonidoEnter.play();
					estado = Estado.PROGRESO;
				}
			}
		}else {
			aviso = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			aviso.setTexto("No tenés items para soltar o consumir");
			aviso.dibujar();
			tiempo += delta;
			if(tiempo > timerPasos) {
				tiempo = 0;
				estado = Estado.PROGRESO;
			}
		}
	}

	private void soltarItem(int opc, float x, float y) {
		itemSoltado = jugador.getItemInventario(opc);
		jugador.sacarItem(opc);
		itemSoltado.setBody(itemSoltado.getRutaSprite(),jugador.getX()-10,jugador.getY()-10);
		itemSoltado.setFixtureColision(itemSoltado);
		itemSoltado.draw(b);
		aviso = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
		aviso.setTexto("Item soltado");
		aviso.setPosition(x, y);
		aviso.dibujar();
	}

	private void usarItem(int opc, float x, float y, float dl) {
		aviso = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
		switch(jugador.getItemInventario(opc).getTipo()) {
		case CURATIVO:
			if(jugador.getVida() < jugador.getVidaTotal()) {
				aviso.setTexto("Item usado: " + jugador.getItemInventario(opc).getNombre() + ", +" + jugador.getItemInventario(opc).getVidaRec() + " HP");
				jugador.modificarVida(jugador.getItemInventario(opc).getVidaRec());
				jugador.sacarItem(opc);
				jugador.sumarPuntos(100);
			}else {
				aviso.setTexto("Ya tenés el máximo de vida");
			}
			break;
		case OBJETO:
			jugador.sacarItem(opc);
			aviso.setTexto("Item usado");
			jugador.sumarPuntos(100);
			break;
		}
		
		aviso.setPosition(x, y);
		aviso.dibujar();
	}

	private void elegirOpcionMenu(float delta) {
		for (int i = 0; i < menu.length; i++) {
			//acomoda el menu según la posición del jugador para que quede centrado
	        menu[i].setPosition(
	                camara.position.x - (puerto.getWorldWidth() / 2) + (Config.ANCHO / 2) - (menu[i].getAncho() / 2),
	                camara.position.y - (puerto.getWorldHeight() / 2) + ((Config.ALTO / 2) + (menu[0].getAlto() / 2)) - ((menu[i].getAlto() * i) + (20 * i))
	            );
			menu[i].dibujar();
		}
		
		tiempo += delta;
		
		if(entradas.isAbajo()) {
			if(tiempo > timerFlechas) {
				tiempo = 0;
				opc++;
				ManagerSonido.opcionMenu.play();
				if(opc > 3) {
					opc = 1;
				}	
			}
		}
		if(entradas.isArriba()) {
			if(tiempo > timerFlechas) {
				tiempo = 0;
				opc--;
				ManagerSonido.opcionMenu.play();
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
					Render.app.setScreen(new PantallaOpciones(true));
					break;
				case 3:
					Render.app.setScreen(new PantallaMenu());
					break;
			}
			ManagerSonido.sonidoEnter.play();
		}
		
		
		for (int i = 0; i < opciones.length; i++) {
			if(i == opc-1) {
				menu[i].setColor(Color.GOLD);
			}else {
				menu[i].setColor(Color.BLUE);
			}
		}
	}

	private void jugar(float textX, float textY, float delta) {
		actualizar(delta);
		b.setProjectionMatrix(camara.combined);
		
		
		if(letra != null) {
			letra.dibujar();
		}
		if(entradas.isE() && objeto != null) {
			ManagerSonido.sonidoEnter.play();
			interactuar(objeto,textX,textY,delta);
		}
		
		tiempo += delta;
		
		if(cuadro != null) {
			if(timer < 0.5f) {
				cuadro.dibujar();
			}else {
				timer = 0;
				cuadro = null;
			}
			timer += 0.005f;
		}
		
		
		//al apretar la i se abre el inventario
		if(entradas.isI()) {
			if(tiempo > 0.09f) {
				tiempo = 0;
				estado = Estado.INVENTARIO;
				ManagerSonido.sonidoEnter.play();
			}
		}
		if(item != null) {
			item.draw(b);
		}
		for (int i = 0; i < itemsEncontrados.size(); i++) {
			itemsEncontrados.get(i).draw(b);
		}
		
		jugador.acomodarInventario(textX, textY);
		
		if(entradas.isEscape()) {
			estado = Estado.PAUSA;
		}
	
	}
	//función que, según la colisión y si el jugador apreta la tecla para interactuar,
	//realiza la acción adecuada
	private void interactuar(String objeto, float textX, float textY, float dl) {
		float x = jugador.getX()-10;
		float y = jugador.getY()-10;
		int i = 0;
		boolean encontrado = false;
		switch(objeto) {
		case "Puerta": //en caso de que sea una puerta, si el jugador tiene una llave en el inventario
					  //es utilizada para abrir la puerta y poder ir a más partes del mapa
			while(i < jugador.getCantidadItems() && !encontrado) {
				if(jugador.getInventario()[i].getNombre().equals("Llave")) {
					encontrado = true;
				}
				i++;
			}
			cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			if(!encontrado) {
	            cuadro.setTexto("La puerta está cerrada, necesitas encontrar una llave");   
			}else {
				cuadro.setTexto("Se logró abrir la puerta al utilizar el siguiente item: " + jugador.getInventario()[i-1].getNombre());
				usarItem(i-1,x,y,dl);
			}
			cuadro.setPosition(textX, textY);
			break;
		case "Hoja": //las hojas le muestra textos al jugador para guiarse durante el juego
					cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
	                cuadro.setTexto(texto);
	                cuadro.setPosition(textX, textY);
					break;
		case "Escritorio": //tanto en los escritorios como en las bibliotecas, se tiene una probabilidad para
						   //encontrar items que ayudan al jugador
			cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			prob = MathUtils.random(99)+1;
			if(prob <= 30) {
				prob = MathUtils.random(2)+1;
				switch(prob) {
					case 1:
						itemEncontrado = new Alfajor(mundo,atlasItems, x, y);
						break;
					case 2:
						itemEncontrado = new Hamburguesa(mundo, atlasItems, x, y);
						break;
					case 3:
						itemEncontrado = new Llave(mundo,atlasItems, x, y);
						break;
				}
				itemsEncontrados.add(itemEncontrado);
				texto += itemEncontrado.getNombre();
                cuadro.setTexto(texto);
                cuadro.setPosition(textX, textY);
			}else if(prob <= 40){
				cuadro.setTexto("Te encontraste con una araña la cuál te mordió y te sacó 10 de vida");
				jugador.modificarVida(-10);
			}else {
				cuadro.setTexto("No se encontró nada en el escritorio");
			}
			cuadro.setPosition(textX, textY);
			break;
		case "Biblioteca":
			cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
			prob = MathUtils.random(99)+1;
			if(prob <= 30) {
				prob = MathUtils.random(2)+1;
				switch(prob) {
					case 1:
						itemEncontrado = new Alfajor(mundo,atlasItems, x, y);
						break;
					case 2:
						itemEncontrado = new Hamburguesa(mundo, atlasItems, x, y);
						break;
					case 3:
						itemEncontrado = new Llave(mundo,atlasItems, x, y);
						break;
				}
				itemsEncontrados.add(itemEncontrado);
				texto += itemEncontrado.getNombre();
                cuadro.setTexto(texto);
			}else if(prob <= 40){
				cuadro.setTexto("Te encontraste con una araña la cuál te mordió y \nte sacó 10 de vida");
				jugador.modificarVida(-10);
			}else {
				cuadro.setTexto("No se encontró nada en la biblioteca");
			}
			cuadro.setPosition(textX, textY);
			break;
		case "Item": //cuando se interactua con un item, lo guarda en el inventario
					//del jugador en caso de que pueda
			if(itemAgregado) {
				if(jugador.getCantidadItems() < jugador.getInventario().length) {
					jugador.agregarItem(item);
					cuadro = new Texto(Recurso.FUENTEMENU,30,Color.WHITE,true);
	                cuadro.setTexto("Item agregado al inventario");
	                cuadro.setPosition(textX, textY);
	                itemsEncontrados.remove(item);
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
	    tiempo += dl;
	    if (entradas.isDer()) {
	        nuevaVelocidad.x = Math.min(velocidadMaxima, velocidad.x + aceleracion * dl);
	        if(tiempo >= timerPasos) {
	        	tiempo = 0;
	        	ManagerSonido.pasosPersonaje.play();
	        }
	        
	    } else if (entradas.isIzq()) {
	        nuevaVelocidad.x = Math.max(-velocidadMaxima, velocidad.x - aceleracion * dl);
	        if(tiempo >= timerPasos) {
	        	tiempo = 0;
	        	ManagerSonido.pasosPersonaje.play();
	        }
	    }
	    
	    if (entradas.isArriba()) {
	        nuevaVelocidad.y = Math.min(velocidadMaxima, velocidad.y + aceleracion * dl);
	        if(tiempo >= timerPasos) {
	        	tiempo = 0;
	        	ManagerSonido.pasosPersonaje.play();
	        }
	    } else if (entradas.isAbajo()) {
	        nuevaVelocidad.y = Math.max(-velocidadMaxima, velocidad.y - aceleracion * dl);
	        if(tiempo >= timerPasos) {
	        	tiempo = 0;
	        	ManagerSonido.pasosPersonaje.play();
	        }
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
