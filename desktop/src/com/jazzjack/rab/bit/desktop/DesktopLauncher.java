package com.jazzjack.rab.bit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jazzjack.rab.bit.MyFirstGame;

import org.lwjgl.opengl.GL11;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyFirstGame(), config);
	}
}
