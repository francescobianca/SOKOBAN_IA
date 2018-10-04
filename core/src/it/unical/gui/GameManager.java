package it.unical.gui;

import java.io.File;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import it.unical.encodingObject.Scatola;
import it.unical.logic.Box;
import it.unical.logic.Goal;
import it.unical.logic.ObjectGame;
import it.unical.logic.Player;
import it.unical.logic.Wall;
import it.unical.logic.World;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.DLV2AnswerSets;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class GameManager implements Screen {

	private Sokoban sokoban;

	private World world;
	private Player player;
	private boolean winner;
	private boolean startDLV;
	private int step = 0;

	private Sprite sprite;

	// EmbASP integration
	private static Handler handler;
	private static String encondingPath = "encodings/";
	private static String encondingName = "sokoban";
	private InputProgram facts;
	private InputProgram encoding;

	public GameManager(final Sokoban sokoban) {
		// TODO Auto-generated constructor stub
		this.sokoban = sokoban;

		world = new World(sokoban.getLivelloScelto());
		winner = false;
		startDLV = false;
		loadLevel();

		sprite = new Sprite(new TextureRegion(SplashScreen.loader.loadPlayerImage(), 0, 0, 64, 64));

		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
	}

	public void loadLevel() {
		world.loadMatrix();
		player = world.getPlayer();
	}

	public void removeLevel() {

	}

	public void movePlayer(int direction) {

		if (!winner) {

			player.move(direction);

			step++;
			if (world.win()) {
				winner = true;
				return;
			}
		}
	}

	public boolean getWin() {
		return winner;
	}

	public int getStep() {
		return step;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sokoban.batch.begin();

		for (int i = 0; i < 800; i += 64)
			for (int j = 0; j < 600; j += 64)
				sokoban.batch.draw(SplashScreen.loader.loadGrassImage(), i, j, 64, 64);

		int maxI = (world.getNumberRow() - 1) * 64;

		for (Goal g : world.getGoals()) {
			sokoban.batch.draw(SplashScreen.loader.loadGoalImage(), g.getJ() * 64, maxI - (g.getI() * 64), 64, 64);
		}

		for (int i = 0; i < world.getNumberRow(); i++) {
			for (int j = 0; j < world.getNumberColumn(); j++) {

				ObjectGame tmp = world.getObject(i, j);
				if (tmp instanceof Player) {
					// sokoban.batch.draw(SplashScreen.loader.loadPlayerImage(),j*64,maxI,64,64);
					sprite.setPosition(j * 64, maxI);
					// sprite.draw(sokoban.batch);
					// sprite.setRegion(0, 0, 64, 64);
					sprite.draw(sokoban.batch);
				}
				/*
				 * if (tmp instanceof Goal)
				 * sokoban.batch.draw(SplashScreen.loader.loadGoalImage(),j*64,maxI,64,64);
				 */
				if (tmp instanceof Box)
					sokoban.batch.draw(SplashScreen.loader.loadBoxImage(), j * 64, maxI, 64, 64);
				if (tmp instanceof Wall)
					sokoban.batch.draw(SplashScreen.loader.loadWallImage(), j * 64, maxI, 64, 64);

			}

			maxI -= 64;
		}

		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			movePlayer(player.UP);
			sprite.setRegion(128, 0, 64, 64);
			sprite.draw(sokoban.batch);
		} else if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			movePlayer(player.DOWN);
			sprite.setRegion(0, 0, 64, 64);
			sprite.draw(sokoban.batch);
		} else if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			movePlayer(player.LEFT);
			sprite.setRegion(64, 0, 64, 64);
			sprite.draw(sokoban.batch);
		} else if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			movePlayer(player.RIGHT);
			sprite.setRegion(192, 0, 64, 64);
			sprite.draw(sokoban.batch);
		}

		sokoban.batch.end();

		if (!startDLV) {
			startDLV = true;

			facts = world.loadDLVFacts();
			handler.addProgram(facts);
			encoding = new ASPInputProgram();
			encoding.addFilesPath(encondingPath + encondingName);
			handler.addProgram(encoding);
			Output output = handler.startSync();
			AnswerSets answerSets = (AnswerSets) output;

			for (AnswerSet answerSet : answerSets.getAnswersets()) {
				try {
					List<String> list = answerSet.getAnswerSet();
					System.out.println(list.toString());
					/*for (Object obj : answerSet.getAtoms())
						if (obj instanceof Scatola) {
							Scatola s=(Scatola)obj;
							System.out.println(s.getStep()+" "+s.getRiga()+" "+s.getColonna()+" "+s.getId());
						}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}