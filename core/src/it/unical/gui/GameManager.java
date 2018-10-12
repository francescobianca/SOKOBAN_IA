package it.unical.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

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

import it.unical.encodingObject.Mossa;
import it.unical.encodingObject.Scatola;
import it.unical.logic.Box;
import it.unical.logic.Goal;
import it.unical.logic.ObjectGame;
import it.unical.logic.Player;
import it.unical.logic.Wall;
import it.unical.logic.World;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
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

	private final int NUMERO_MOSSE_PARTENZA = 1;

	private int minNumeroMosse = NUMERO_MOSSE_PARTENZA;

	private Sprite sprite;
	private Skin skin;
	private TextButton solver;
	private TextButton undo;
	private TextButton reset;
	private TextButton backToMenu;
	private Stage stage;

	// EmbASP integration
	private static Handler handler;
	private static String encondingPath = "encodings/";
	private static String encondingName = "sokoban";
	private InputProgram facts;
	private InputProgram encoding;

	private ArrayList<Scatola> soluzioneScatole;
	private ArrayList<Mossa> soluzioneMosse;

	// gestione mosse
	private final float FREQUENZA_AGGIORNAMENTO = 1.0f;
	private LinkedList<String> listaStep;
	private int indiceStep;
	private int indiceSoluzioneMosse;

	public GameManager(final Sokoban sokoban) {
		// TODO Auto-generated construtor stub
		this.sokoban = sokoban;

		sprite = new Sprite(new TextureRegion(SplashScreen.loader.loadPlayerImage(), 0, 0, 64, 64));
		skin = new Skin(Gdx.files.internal(GameConfig.SKIN));

		solver = new TextButton("SOLVE", skin);
		undo = new TextButton("UNDO", skin);
		reset = new TextButton("RESET", skin);
		backToMenu = new TextButton("MENU'", skin);

		soluzioneScatole = new ArrayList<Scatola>();
		soluzioneMosse = new ArrayList<Mossa>();

		solver.setPosition(605, 310);
		solver.setWidth(150);
		solver.setHeight(45);
		solver.setColor(Color.CORAL);

		undo.setPosition(605, 380);
		undo.setWidth(150);
		undo.setHeight(45);
		undo.setColor(Color.CORAL);

		reset.setPosition(605, 240);
		reset.setWidth(150);
		reset.setHeight(45);
		reset.setColor(Color.CORAL);

		backToMenu.setPosition(605, 50);
		backToMenu.setWidth(150);
		backToMenu.setHeight(45);
		backToMenu.setColor(Color.CORAL);

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
				startDLV = true;
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		reset.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("RESET LISTENER");

				loadLevel();
				winner = false;
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		backToMenu.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				sokoban.swap(2);
				startDLV = false;
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
		stage.addActor(reset);
		stage.addActor(backToMenu);

		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		encoding = new ASPInputProgram();
		encoding.addFilesPath(encondingPath + encondingName);
		try {
			ASPMapper.getInstance().registerClass(Mossa.class);
		} catch (ObjectNotValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAnnotationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadLevel() {
		world.loadMatrix();
		player = world.getPlayer();
		world.print();
	}

	public void movePlayer(int direction) {

		if (!winner) {

			player.move(direction);

			step++;
			if (world.win()) {
				winner = true;

				if (sokoban.getLivelloScelto() < 9)
					sokoban.setLivelloScelto(sokoban.getLivelloScelto() + 1);

				sokoban.swap(1);
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
		if (listaStep == null)
			listaStep = new LinkedList<String>();
		indiceSoluzioneMosse = 0;
		listaStep.clear();
		soluzioneScatole.clear();
		soluzioneMosse.clear();
		
		minNumeroMosse = NUMERO_MOSSE_PARTENZA;
		world = new World(sokoban.getLivelloScelto());
		world.setMaxMosse(minNumeroMosse);
		loadLevel();
		world.printBox();
		winner = false;
		startDLV = false;
		handler.removeAll();
	}

	private float attesa = 0;

	@SuppressWarnings("static-access")
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sokoban.batch.begin();

		attesa += delta;
		if (attesa >= FREQUENZA_AGGIORNAMENTO) {
			if (indiceStep < listaStep.size()) {
				attesa = 0;
				spostaPlayerDLV();
				indiceStep++;
			} else {
				if (indiceSoluzioneMosse < soluzioneMosse.size())
					calcolaStep();
			}
		}

		for (int i = 0; i < 800; i += 64)
			for (int j = 0; j < 600; j += 64)
				sokoban.batch.draw(SplashScreen.loader.loadGrassImage(), i, j, 64, 64);

		int maxI = (world.getNumberRow() - 1) * 64;

		for (Goal g : world.getGoals()) {
			sokoban.batch.draw(SplashScreen.loader.loadGoalImage(), g.getJ() * 64, maxI - (g.getI() * 64), 64, 64);
		}

		for (Box b : world.getBoxs()) {
			sokoban.batch.draw(SplashScreen.loader.loadBoxImage(), b.getJ() * 64, maxI - (b.getI() * 64), 64, 64);
		}

		for (int i = 0; i < world.getNumberRow(); i++) {
			for (int j = 0; j < world.getNumberColumn(); j++) {

				ObjectGame tmp = world.getObject(i, j);
				/*
				 * if (tmp instanceof Player) { //
				 * sokoban.batch.draw(SplashScreen.loader.loadPlayerImage(),j*64,maxI,64,64);
				 * sprite.setPosition(j * 64, maxI); // sprite.draw(sokoban.batch); //
				 * sprite.setRegion(0, 0, 64, 64); sprite.draw(sokoban.batch); }
				 */
				/*
				 * if (tmp instanceof Goal)
				 * sokoban.batch.draw(SplashScreen.loader.loadGoalImage(),j*64,maxI,64,64);
				 */
				// if (tmp instanceof Ground)
				// sokoban.batch.draw(SplashScreen.loader.loadBoxImage(), j * 64, maxI, 64, 64);
				if (tmp instanceof Wall)
					sokoban.batch.draw(SplashScreen.loader.loadWallImage(), j * 64, maxI - (i * 64), 64, 64);
			}

			// maxI -= 64;
		}

		sprite.setPosition(world.getPlayer().getJ() * 64, maxI - (world.getPlayer().getI() * 64));
		sprite.draw(sokoban.batch);

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

		if (startDLV) {
			startDLV = false;
			boolean risolto = false;
			
			while (!risolto) {
				// setto al world il minimo numero di mosse del gameManager
				world.setMaxMosse(minNumeroMosse);
				facts = world.loadDLVFacts();
				handler.addProgram(facts);
				handler.addProgram(encoding);

				// handler.addOption(new OptionDescriptor("-n=0 "));
				handler.addOption(new OptionDescriptor("--filter=scatola/4,mossa/3 "));
				Output output = handler.startSync();
				AnswerSets answerSets = (AnswerSets) output;
				for (AnswerSet answerSet : answerSets.getAnswersets()) {
					try {
						for (Object obj : answerSet.getAtoms()) {
							if (obj instanceof Scatola) {
								Scatola s = (Scatola) obj;
								soluzioneScatole.add(s);
							}
							if (obj instanceof Mossa) {
								Mossa m = (Mossa) obj;
								soluzioneMosse.add(m);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					// Sono riuscito a risolvere il livello ed esco dal while
					System.out.println("Risolto con " + world.getMaxMosse() + " mosse");
					risolto = true;
				}
				// Non sono riuscito a risolvere il livello e quindi incremento il numero minimo
				// di mosse
				if (!risolto) {
					this.minNumeroMosse++;
					handler.removeAll();
				}
			}
			// Collections.sort(soluzioneScatole);
			Collections.sort(soluzioneMosse);
			for (int i = 0; i < soluzioneMosse.size(); i++) {
				Mossa m = soluzioneMosse.get(i);
				System.out.println(
						"Step: " + m.getStep() + " IdScatola: " + m.getIdBox() + " direzione: " + m.getDirezione());
			}
			indiceStep=0;
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

	public void calcolaStep() {
		System.out.println("----------------------------------------");
		Mossa m = soluzioneMosse.get(indiceSoluzioneMosse);
		Box b = null;

		for (Box b1 : world.getBoxs()) {
			if (b1.getId() == m.getIdBox())
				b = b1;
		}
		int newI = 0;
		int newJ = 0;
		if (m.getDirezione().equals("sopra")) {
			newI = b.getI() + 1;
			newJ = b.getJ();
		} else if (m.getDirezione().equals("sotto")) {
			newI = b.getI() - 1;
			newJ = b.getJ();
		} else if (m.getDirezione().equals("sinistra")) {
			newI = b.getI();
			newJ = b.getJ() + 1;
		} else if (m.getDirezione().equals("destra")) {
			newI = b.getI();
			newJ = b.getJ() - 1;
		}
		System.out.println(player.getI() + " " + player.getJ());
		System.out.println(newI);
		System.out.println(newJ);
		CalcolaPercorso calcola = CalcolaPercorso.getIstance();
		LinkedList<String> movimenti = new LinkedList<String>();
		int dist = calcola.restituisciPercorso(movimenti, world, this.player, newI, newJ);
		System.out.println(movimenti.toString() + " " + dist);
		if (dist != 0) {
			this.listaStep.addAll(movimenti);
			this.listaStep.add(m.getDirezione());
		}else {
			System.out.println("Non sono riuscito a trovare il percorso da "+player.getI()+", "+player.getJ());
			System.out.println("a "+newI+", "+newJ);
		}
		System.out.println(listaStep.toString());
		indiceSoluzioneMosse++;
	}

	public void spostaPlayerDLV() {
		if (listaStep.get(indiceStep).equals("sopra")) {
			movePlayer(player.UP);
			sprite.setRegion(128, 0, 64, 64);
		} else if (listaStep.get(indiceStep).equals("sotto")) {
			movePlayer(player.DOWN);
			sprite.setRegion(0, 0, 64, 64);
		} else if (listaStep.get(indiceStep).equals("sinistra")) {
			movePlayer(player.LEFT);
			sprite.setRegion(64, 0, 64, 64);
		} else if (listaStep.get(indiceStep).equals("destra")) {
			movePlayer(player.RIGHT);
			sprite.setRegion(192, 0, 64, 64);
		}
	}
}