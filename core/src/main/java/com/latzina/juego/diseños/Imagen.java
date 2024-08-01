package com.latzina.juego.diseños;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.latzina.juego.utiles.Render;

public class Imagen {
	private Texture t;
	private Sprite s;
	
	public Imagen(String ruta) {
		t = new Texture(ruta);
		s = new Sprite(t);
	}
	
	public Imagen(String ruta, float x, float y) {
		t = new Texture(ruta);
		s = new Sprite(t);
		s.setX(x);
		s.setY(y);
	}
	
	public float getX() {
		return s.getX();
	}
	public float getY() {
		return s.getY();
	}
	public void setX(float x) {
		s.setX(x);
	}
	public void setY(float y) {
		s.setY(y);
	}
	
	public void dibujar() {
		s.draw(Render.batch);
	}
	
	public void setPosition(float x, float y) {
		s.setPosition(x, y);
	}
	
	
	public void setTransparencia(float a) {
		s.setAlpha(a);
	}
	
	public void setSize(int width, int height) {
		s.setRegionWidth(width);
		s.setRegionHeight(height);
	}
	

	
}
