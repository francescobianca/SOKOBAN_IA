package it.unical.gui;

import com.badlogic.gdx.graphics.Texture;

public class imageLoader {

	private Texture splashImage;

	private Texture boxImage;
	private Texture boxColorImage;
	private Texture goalImage;
	private Texture grassImage;
	private Texture groundImage;
	private Texture playerImage;
	private Texture skyImage;
	private Texture wallImage;
	private Texture wall2Image;
	private Texture wallBigImage;
	private Texture backGroundImage;
	private Texture menuImage;
	private Texture levelImage;
	
	public imageLoader() {
		splashImage = new Texture(GameConfig.splashImage);
		boxImage = new Texture(GameConfig.boxImage);
		boxColorImage = new Texture(GameConfig.boxColorImage);
		goalImage = new Texture(GameConfig.goalImage);
		grassImage = new Texture(GameConfig.grassImage);
		groundImage = new Texture(GameConfig.groundImage);
		playerImage = new Texture(GameConfig.playerImage);
		skyImage = new Texture(GameConfig.skyImage);
		wallImage = new Texture(GameConfig.wallImage);
		wall2Image = new Texture(GameConfig.wall2Image);
		wallBigImage = new Texture(GameConfig.wallBigImage);
		backGroundImage = new Texture(GameConfig.backGroundImage);
		menuImage = new Texture(GameConfig.menuImage);
		levelImage = new Texture(GameConfig.levelsImage);
	}

	public Texture loadSplashImage() {
		return splashImage;
	}

	public Texture loadBoxImage() {
		return boxImage;
	}

	public Texture loadBoxColorImage() {
		return boxColorImage;
	}

	public Texture loadGoalImage() {
		return goalImage;
	}

	public Texture loadGrassImage() {
		return grassImage;
	}

	public Texture loadGroundImage() {
		return groundImage;
	}

	public Texture loadPlayerImage() {
		return playerImage;
	}

	public Texture loadSkyImage() {
		return skyImage;
	}

	public Texture loadWallImage() {
		return wallImage;
	}

	public Texture loadWall2Image() {
		return wall2Image;
	}

	public Texture loadWallBigImage() {
		return wallBigImage;
	}
	
	public Texture loadBackGroundImage() {
		return backGroundImage;
	}
	
	public Texture loadMenuImage() {
		return menuImage;
	}

	public Texture loadLevelsImage() {
		return levelImage;
	}
	
}
