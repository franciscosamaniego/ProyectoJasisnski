package com.latzina.juego.escenas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.latzina.juego.personajes.Jugador;
import com.latzina.juego.utiles.Config;

public class Hud {
	public Stage escenario;
	private Viewport puerto;
	private Integer puntaje;
	private Integer vida;
	private Integer vidaTotal;
	
	private Label labelPuntaje;
	private Label labelVida;
	private Label labelVidaTotal;
	private Label vidaT;
	private Label puntajeT;
	
	private Table tabla;
	
	public Hud(Jugador jugador, SpriteBatch b) {
		this.puntaje = 0;
		this.vida = jugador.getVida();
		this.vidaTotal = jugador.getVidaTotal();
		this.puerto = new FitViewport(Config.ANCHO, Config.ALTO, new OrthographicCamera());
		//se crea el escenario para poder mostrar los puntos y la vida del jugador
		this.escenario = new Stage(puerto, b);
		
		
		//en la tabla se guardan los datos que se mostrarán en pantalla
		tabla = new Table();
		
		tabla.top();
		tabla.setFillParent(true);
		
		labelPuntaje = new Label(String.format("%06d", puntaje), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		labelVida = new Label(String.format("%03d", vida) + "/" + String.format("%03d", vidaTotal), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		
		
	
		vidaT = new Label("Vida", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		puntajeT = new Label("Puntaje", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		labelPuntaje.setFontScale(2f);
		labelVida.setFontScale(2f);
		vidaT.setFontScale(2f);
		puntajeT.setFontScale(2f);
		
		tabla.add(vidaT);
		tabla.add(puntajeT);
		
		tabla.row();
		
		tabla.add(labelVida).expandX().padTop(4);
		tabla.add(labelPuntaje).expandX().padTop(10);
		
		
		escenario.addActor(tabla);
		
		
	}
	
	//se actualiza todo el tiempo, para mostrarle al jugador su rendimiento en el juego
	public void modificarDatos(Jugador jugador) {
		this.vida = jugador.getVida();
		if(this.vida <= 0) {
			this.vida = 0;
		}else if(this.vida >= this.vidaTotal) {
			this.vida = this.vidaTotal;
		}
		this.puntaje = jugador.getPuntaje();
		
		labelPuntaje.setText(String.format("%06d", this.puntaje));
		labelVida.setText(String.format("%03d", this.vida) + "/" + String.format("%03d", vidaTotal));
		
	}
}
