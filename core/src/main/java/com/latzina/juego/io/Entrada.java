package com.latzina.juego.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Entrada implements InputProcessor{
	
	private boolean abajo = false, arriba = false, enter = false, izq = false, der = false;;
	private boolean e = false, i = false, escape = false;
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
			case Keys.DOWN:
				abajo = true;
				break;
			case Keys.UP:
				arriba = true;
				break;
			case Keys.ENTER:
				enter = true;
				break;
			case Keys.LEFT:
				izq = true;
				break;
			case Keys.RIGHT:
				der = true;
				break;
			case Keys.E:
				e = true;
				break;
			case Keys.I:
				i = true;
				break;
			case Keys.ESCAPE:
				escape = true;
				break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.DOWN:
			abajo = false;
			break;
		case Keys.UP:
			arriba = false;
			break;
		case Keys.ENTER:
			enter = false;
			break;
		case Keys.LEFT:
			izq = false;
			break;
		case Keys.RIGHT:
			der = false;
			break;
		case Keys.E:
			e = false;
			break;
		case Keys.I:
			i = false;
			break;
		case Keys.ESCAPE:
			escape = false;
			break;
	}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		
		return false;
	}
	
	public boolean isAbajo() {
		return abajo;
	}
	
	public boolean isArriba() {
		return arriba;
	}
	
	public boolean isEnter() {
		return enter;
	}
	
	public boolean isDer() {
		return der;
	}
	
	public boolean isIzq() {
		return izq;
	}
	
	public boolean isE() {
		return e;
	}
	
	public boolean isI() {
		return i;
	}
	
	public boolean isEscape() {
		return escape;
	}
}
