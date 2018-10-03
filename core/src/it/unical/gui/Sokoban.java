package it.unical.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sokoban extends Game {

	public static Sokoban sokoban;
	public SpriteBatch batch;

	public SplashScreen splashScreen;
	public GameManager gameManager;
	public MenuScreen menuScreen;
	public LevelsScreen levelsScreen;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		sokoban = this;

		batch = new SpriteBatch();

		splashScreen = new SplashScreen(this);
		gameManager = null;
		menuScreen = null;
		levelsScreen = null;

		this.setScreen(splashScreen);

	}

	public void swap(int state) {
		switch (state) {
		case 0:
			setScreen(splashScreen);
			break;
		case 1:
			if (gameManager == null)
				gameManager = new GameManager(this);
			setScreen(gameManager);
			break;
		case 2:
			if (menuScreen == null)
				menuScreen = new MenuScreen(this);
			setScreen(menuScreen);
			break;
		case 3:
			if (levelsScreen == null)
				levelsScreen = new LevelsScreen(this);
			setScreen(levelsScreen);
			break;
		default:
			break;
		}
	}

}