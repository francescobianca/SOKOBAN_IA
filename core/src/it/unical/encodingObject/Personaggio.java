package it.unical.encodingObject;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("personaggio")
public class Personaggio {

	@Param(0)
	private int step;
	
	@Param(1)
	private int riga;
	
	@Param(2)
	private int colonna;
	
	public Personaggio(int s,int r,int c) {
		this.step=s;
		this.riga=r;
		this.colonna=c;
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
}
