package it.unical.encodingObject;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("colonna")
public class Colonna {
	
	@Param(0)
	private int ncolonna;
	
	public Colonna() {
		
	}

	public Colonna(int ncolonna) {
		this.ncolonna=ncolonna;
	}
	
	public int getNcolonna() {
		return ncolonna;
	}
	
	public void setNcolonna(int ncolonna) {
		this.ncolonna = ncolonna;
	}
}
