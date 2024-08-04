package com.latzina.juego.diseños;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.latzina.juego.utiles.Render;

public class Texto {
	
	private BitmapFont fuente;
	private float x = 0, y = 0;
	private String texto = "";
	private GlyphLayout layout;
	
	public Texto(String rutaFuente, int tamaño, Color color, boolean sombra) {
		FreeTypeFontGenerator generador = new FreeTypeFontGenerator(Gdx.files.internal(rutaFuente));
		FreeTypeFontParameter parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parametros.size = tamaño;
		parametros.color = color;
		if(sombra) {
			parametros.shadowColor = Color.BLACK;
			parametros.shadowOffsetX = 1;
			parametros.shadowOffsetY = 1;
		}
		fuente = generador.generateFont(parametros);
		layout = new GlyphLayout();
	}
	
	public void dibujar() {
		fuente.draw(Render.batch, texto, x, y);
	}
	
	public void setColor(Color color) {
		fuente.setColor(color);
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
		layout.setText(fuente, texto);
	}
	
	public String getTexto() {
		return texto;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getAncho() {
		return layout.width;
	}
	
	public float getAlto() {
		return layout.height;	
	}
}
