package it.unical.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SplashScreen implements Screen {

	private Sokoban sokoban;
	private long startTime;
	public static imageLoader loader;

	private Skin skin;
	private ProgressBar progressBar;
	private Stage stage;
	
	private float value= 0.01f;

	public SplashScreen(Sokoban sokoban) {
		// TODO Auto-generated constructor stub
		this.sokoban = sokoban;
		startTime = TimeUtils.millis();
		loader = new imageLoader();

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal(GameConfig.SKIN));
		progressBar = new ProgressBar(0f, 1f, 0.1f, false, skin);

		progressBar.setSize(250f, 200f);
		progressBar.setPosition(Gdx.graphics.getWidth() / 2 - 125f, -45f);

		progressBar.setValue(0.0f);

		stage.addActor(progressBar);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sokoban.batch.begin();
		sokoban.batch.draw(loader.loadSplashImage(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sokoban.batch.end();
		
		if (value<1.0)
			value=value+0.01f;
		
		progressBar.setValue(value); //DA MODIFICARE
		
		stage.act();
		stage.draw();

		if (TimeUtils.millis() > (startTime + 5000))
			sokoban.swap(2);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		skin.dispose();
		stage.clear();
		stage.dispose();
	}

}
