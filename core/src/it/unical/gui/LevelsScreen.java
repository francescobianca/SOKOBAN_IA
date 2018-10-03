package it.unical.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class LevelsScreen implements Screen {

	private Sokoban sokoban;

	public LevelsScreen(Sokoban sokoban) {
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
		sokoban.batch.draw(SplashScreen.loader.loadLevelsImage(), 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		sokoban.batch.end();

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 15 && Gdx.input.getX() < 115
				&& Gdx.input.getY() > 487 && Gdx.input.getY() < 587)
			sokoban.swap(2); // Tasto UNDO

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 144 && Gdx.input.getX() < 294
				&& Gdx.input.getY() > 49 && Gdx.input.getY() < 199)
			; // Livello1

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 326 && Gdx.input.getX() < 476
				&& Gdx.input.getY() > 49 && Gdx.input.getY() < 199)
			; // Livello2

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 506 && Gdx.input.getX() < 656
				&& Gdx.input.getY() > 49 && Gdx.input.getY() < 199)
			; // Livello3

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 144 && Gdx.input.getX() < 294
				&& Gdx.input.getY() > 225 && Gdx.input.getY() < 375)
			; // Livello4

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 326 && Gdx.input.getX() < 476
				&& Gdx.input.getY() > 225 && Gdx.input.getY() < 375)
			; // Livello5

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 506 && Gdx.input.getX() < 656
				&& Gdx.input.getY() > 225 && Gdx.input.getY() < 375)
			; // Livello6

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 144 && Gdx.input.getX() < 294
				&& Gdx.input.getY() > 401 && Gdx.input.getY() < 551)
			; // Livello7

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 326 && Gdx.input.getX() < 476
				&& Gdx.input.getY() > 401 && Gdx.input.getY() < 551)
			; // Livello8

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.getX() > 506 && Gdx.input.getX() < 656
				&& Gdx.input.getY() > 401 && Gdx.input.getY() < 551)
			; // Livello9

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