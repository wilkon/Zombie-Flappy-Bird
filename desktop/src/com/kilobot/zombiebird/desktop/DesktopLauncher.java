package com.kilobot.zombiebird.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.kilobot.zombiebird.ZBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Zombie Bird");
		config.setWindowSizeLimits(272, 408, 272, 408);
		new Lwjgl3Application(new ZBGame(), config);
	}
}
