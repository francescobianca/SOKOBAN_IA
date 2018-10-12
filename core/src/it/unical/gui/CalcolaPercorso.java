package it.unical.gui;

import java.util.ArrayList;
import java.util.LinkedList;

import it.unical.logic.Player;
import it.unical.logic.World;

public class CalcolaPercorso {

	private static CalcolaPercorso istance = null; // riferimento all' istanza

	int rowNum[] = {-1, 0, 0, 1}; 
	int colNum[] = {0, -1, 1, 0};
	String direzione[]= {"sopra","sinistra","destra","sotto"};
	
	private CalcolaPercorso() {}

	public static CalcolaPercorso getIstance() {
		if (istance == null)
			istance = new CalcolaPercorso();
		return istance;
	}
	
	//ritorna 0 se la destinazione non può essere raggiunta
	//ritorna 1 se la destinazione viene raggiunta
	public int restituisciPercorso(LinkedList<String> movimenti,World world,Player player,int destI,int destJ) {
		Point dest=new Point(destI,destJ);
		Point source=new Point(player.getI(),player.getJ());
		boolean visitati[][]=new boolean[world.getNumberRow()][world.getNumberColumn()];
		for(int i=0;i<world.getNumberRow();i++)
			for(int j=0;j<world.getNumberColumn();j++)
				visitati[i][j]=false;
		if(trovaCammino(movimenti, world, source, dest, visitati))
			return 1;
		return 0;
	}

	public boolean isValid(int i,int j,World world) {
		return (i >= 0) && (i <= world.getNumberRow()) && (j >= 0) && (j <= world.getNumberColumn() 
				&& (!world.isThereBox(i, j) && !world.isThereWall(i, j)));
	}
	
	
	class Point{
		int i;
		int j;
		public Point(int i,int j) {
			this.i=i;
			this.j=j;
		}
	}
	
	public boolean trovaCammino(LinkedList<String> movimenti,World world,Point player,Point dest,boolean visitati[][]) {
		if(player.i == dest.i && player.j == dest.j) {
			visitati[dest.i][dest.j]=true;
			return true;
		}else {
			visitati[player.i][player.j]=true;
			
			boolean trovato=false;
			for(int j=0;j<4 && !trovato;j++) {
				int row=player.i+rowNum[j];
				int col=player.j+colNum[j];
				if(!visitati[row][col] && isValid(row, col, world)) {
					movimenti.add(direzione[j]);
					trovato=trovaCammino(movimenti, world, new Point(row,col), dest, visitati);
					if(!trovato)
						movimenti.removeLast();
				}
			}
			return trovato;
		}
	}
}