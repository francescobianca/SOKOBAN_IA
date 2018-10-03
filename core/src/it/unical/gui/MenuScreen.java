package it.unical.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class MenuScreen implements Screen {

	private Sokoban sokoban;

	public MenuScreen(Sokoban sokoban) {
		// TODO Auto-generated constructor stub
		this.sokoban = sokoban;

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
		sokoban.batch.draw(SplashScreen.loader.loadMenuImage(), 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		sokoban.batch.end();

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 123 && Gdx.input.getX() < 315
				&& Gdx.input.getY() > 300 && Gdx.input.getY() < 364)
			sokoban.swap(1);
		
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 472 && Gdx.input.getX() < 664
				&& Gdx.input.getY() > 300 && Gdx.input.getY() < 364)
			sokoban.swap(3);

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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
	}

}
