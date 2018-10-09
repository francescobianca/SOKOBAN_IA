package it.unical.gui;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class GameManager implements Screen {

	private Sokoban sokoban;

	private World world;
	private Player player;
	private boolean winner;
	private boolean startDLV;
	private int step = 0;

	private Sprite sprite;
	private Skin skin;
	private TextButton solver;
	private TextButton undo;
	private Stage stage;

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
		skin = new Skin(Gdx.files.internal(GameConfig.SKIN));

		solver = new TextButton("SOLVE", skin);
		undo = new TextButton("UNDO", skin);

		solver.setPosition(605, 310);
		solver.setWidth(150);
		solver.setHeight(45);
		solver.setColor(Color.CORAL);

		undo.setPosition(605, 380);
		undo.setWidth(150);
		undo.setHeight(45);
		undo.setColor(Color.CORAL);

		undo.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("UNDO LISTENER");
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		
		solver.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("SOLVER LISTENER");
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		stage.addActor(solver);
		stage.addActor(undo);

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

	@SuppressWarnings("static-access")
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

		// Riquadro opzioni
		sokoban.batch.draw(SplashScreen.loader.loadLivelloSchermataGiocoImage(), 580, 520, 200, 60);

		if (sokoban.getLivelloScelto() == 1)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloUnoImage(), 665, 450, 29, 56);
		else if (sokoban.getLivelloScelto() == 2)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloDueImage(), 658, 450, 45, 57);
		else if (sokoban.getLivelloScelto() == 3)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloTreImage(), 658, 450, 45, 57);
		else if (sokoban.getLivelloScelto() == 4)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloQuattroImage(), 660, 450, 41, 47);
		else if (sokoban.getLivelloScelto() == 5)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloCinqueImage(), 658, 450, 45, 57);
		else if (sokoban.getLivelloScelto() == 6)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloSeiImage(), 658, 450, 45, 57);
		else if (sokoban.getLivelloScelto() == 7)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloSetteImage(), 661, 450, 38, 57);
		else if (sokoban.getLivelloScelto() == 8)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloOttoImage(), 658, 450, 45, 57);
		else if (sokoban.getLivelloScelto() == 9)
			sokoban.batch.draw(SplashScreen.loader.loadLivelloNoveImage(), 660, 450, 41, 57);

		sokoban.batch.end();

		stage.act();
		stage.draw();

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
					for (Object obj : answerSet.getAtoms())
						if (obj instanceof Scatola) {
							Scatola s = (Scatola) obj;
							System.out.println("Step: " + s.getStep() + " Riga: " + s.getRiga() + " Colonna: "
									+ s.getColonna() + " IdScatola: " + s.getId());
						}
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
		skin.dispose();
		stage.clear();
		stage.dispose();
	}

}