package it.unical.encodingObject;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("riga")
public class Riga {
	
	@Param(0)
	private int nriga;
	
	public Riga() {
	
	}

	public Riga(int n) {
		this.nriga=n;
	}
	
	public int getNriga() {
		return nriga;
	}
	
	public void setNriga(int nriga) {
		this.nriga = nriga;
	}
	
}
