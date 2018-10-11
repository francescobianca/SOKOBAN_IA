package it.unical.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import it.unical.encodingObject.Colonna;
import it.unical.encodingObject.MaxMosse;
import it.unical.encodingObject.Muro;
import it.unical.encodingObject.Obiettivo;
import it.unical.encodingObject.Personaggio;
import it.unical.encodingObject.Riga;
import it.unical.encodingObject.Scatola;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;

public class World {

	private int numberRow;
	private int numberColumn;

	private ObjectGame world[][];

	private ArrayList<Goal> goals;
	private ArrayList<Box> boxs;
	private ArrayList<Ground> grounds;

	private Player player;

	private int maxMosse;
	
	private final int livello;

	public World(final int livello) {
		this.livello = livello;
	}

	public ObjectGame getObject(int i, int j) {
		return world[i][j];
	}
	
	public void setMaxMosse(int maxMosse) {
		this.maxMosse = maxMosse;
	}
	
	public int getMaxMosse() {
		return maxMosse;
	}

	public void loadMatrix() {// File file) {

		try {
			File file1 = new File("../core/level/level" + this.livello);

			FileReader filein = new FileReader(file1);
			int next = 0;
			int currentRow = 0, currentColumn = 0;
			BufferedReader b = new BufferedReader(filein);
			this.numberRow = Integer.parseInt(b.readLine());
			this.numberColumn = Integer.parseInt(b.readLine());
			
			world = new ObjectGame[numberRow][numberColumn];
			goals = new ArrayList<Goal>();
			boxs = new ArrayList<Box>();

			do {
				next = b.read();

				if (next != -1 && currentRow < numberRow) {
					char nextc = (char) next;

					switch (nextc) {
					/*
					 * case '0': break;
					 */
					case '1':
						world[currentRow][currentColumn] = new Wall(currentRow, currentColumn);
						break;
					case '0':
						/*
						 * Ground groundTmp = new Ground(currentRow, currentColumn);
						 * grounds.add(groundTmp);
						 */
						world[currentRow][currentColumn] = new Ground(currentRow, currentColumn);
						break;
					case '3':
						player = new Player(currentRow, currentColumn, this);
						world[currentRow][currentColumn] = player;
						break;
					case '4':
						Box boxTmp = new Box(currentRow, currentColumn, this);
						boxs.add(boxTmp);
						world[currentRow][currentColumn] = boxTmp;
						break;
					case '5':
						goals.add(new Goal(currentRow, currentColumn));
						world[currentRow][currentColumn] = goals.get(goals.size() - 1);
						break;
					case '7':
						world[currentRow][currentColumn] = null;
						break;
					}
					if (currentColumn == numberColumn) {
						currentRow++;
						currentColumn = 0;
					} else
						currentColumn++;
				}

			} while (next != -1);
			Box.resetIdCounter();
			filein.close();
			b.close();

		} catch (IOException e) {
			System.out.println(e);
			System.out.println("File non esistente");
		}
	}

	// restituisco un InputProgram con i fatti del world
	public InputProgram loadDLVFacts() {	
		InputProgram input = new ASPInputProgram();
		try {
			
			input.addObjectInput(new MaxMosse(this.maxMosse));
			for(int i=0;i<numberRow;i++) {
				input.addObjectInput(new Riga(i));
				for(int j=0;j<numberColumn;j++) {
					input.addObjectInput(new Colonna(j));
				if (world[i][j] instanceof Player)
					input.addObjectInput(new Personaggio(0, i, j));
				if (world[i][j] instanceof Goal)
					input.addObjectInput(new Obiettivo(i, j));					
				if (world[i][j] instanceof Box) {
					Box b=(Box)world[i][j];
					input.addObjectInput(new Scatola(0, i, j,b.getId()));
				}
				if (world[i][j] instanceof Wall)
					input.addObjectInput(new Muro(i, j));
				}
			
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	public void print() {
		for (int i = 0; i < numberRow; i++) {
			String row = "";
			for (int j = 0; j < numberColumn; j++) {
				if (world[i][j] != null)
					row += world[i][j].toString() + " ";
				else
					row += "  ";
			}
			System.out.println(row);
		}
	}

	public boolean isThereGoal(int i, int j) {
		for (int z = 0; z < goals.size(); z++)
			if (goals.get(z).getI() == i && goals.get(z).getJ() == j)
				return true;
		return false;
	}

	public boolean isThereGround(int i, int j) {
		for (int z = 0; z < grounds.size(); z++)
			if (grounds.get(z).getI() == i && grounds.get(z).getJ() == j)
				return true;
		return false;
	}

	public void movePlayer(int i1, int j1, int i2, int j2) {
		Player player = (Player) world[i1][j1];
		player.setCell(i2, j2);
		world[i2][j2] = player;
		world[i1][j1] = null;
	}

	public void moveBox(int i1, int j1, int i2, int j2) {
		Box box = (Box) world[i1][j1];
		box.setCell(i2, j2);
		world[i2][j2] = box;
		world[i1][j1] = null;
	}

	public void movePlayerAndBox(int i1, int j1, int i2, int j2, int i3, int j3) {
		moveBox(i2, j2, i3, j3);
		movePlayer(i1, j1, i2, j2);
	}

	public int getNumberRow() {
		return numberRow;
	}

	public int getNumberColumn() {
		return numberColumn;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Goal> getGoals() {
		return goals;
	}

	public ArrayList<Box> getBoxs() {
		return boxs;
	}
	
	public boolean win() {
		int boxWin = 0;

		for (Box box : boxs)
			for (Goal goal : goals)
				if (box.getI() == goal.getI() && box.getJ() == goal.getJ()) {
					boxWin++;
					break;
				}

		if (boxWin == boxs.size())
			return true;
		return false;
	}
	
	public ObjectGame[][] getWorld() {
		return world;
	}

}
