package it.unical.encodingObject;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("scatola")
public class Scatola {

	@Param(0)
	private int step;

	@Param(1)
	private int riga;

	@Param(2)
	private int colonna;

	@Param(3)
	private int id;

	public Scatola(int s, int r, int c, int id) {
		this.step = s;
		this.riga = r;
		this.colonna = c;
		this.id = id;
	}

	public int getColonna() {
		return colonna;
	}

	public int getRiga() {
		return riga;
	}

	public int getStep() {
		return step;
	}

	public void setColonna(int colonna) {
		this.colonna = colonna;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}