package com.latzina.juego.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class ManagerSonido {
	//música de fondo para el juego
	public static Music fondo = Gdx.audio.newMusic(Gdx.files.internal(Recurso.MUSICA_MENU));
	
	//efectos de sonido para usar en el juego
	public static Sound opcionMenu = Gdx.audio.newSound(Gdx.files.internal(Recurso.EFECTO_MENU));	
	public static Sound sonidoEnter = Gdx.audio.newSound(Gdx.files.internal(Recurso.EFECTO_ENTER));
	public static Sound pasosPersonaje = Gdx.audio.newSound(Gdx.files.internal(Recurso.PASOS));
	public static Sound sonidoLogo = Gdx.audio.newSound(Gdx.files.internal(Recurso.SONIDO_LOGO));
	
	public static void dispose() {
		fondo.dispose();
		opcionMenu.dispose();
	}
}
