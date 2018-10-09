package it.unical.encodingObject;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("muro")
public class Muro {
	
	@Param(0)
	private int riga;

	@Param(1)
	private int colonna;
	
	public Muro() {
	
	}
	
	public Muro(int r, int c) {
		this.riga = r;
		this.colonna = c;
	}

	public int getColonna() {
		return colonna;
	}

	public int getRiga() {
		return riga;
	}

	public void setColonna(int colonna) {
		this.colonna = colonna;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

}
