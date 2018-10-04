package it.unical.encodingObject;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("raggiunto")
public class Raggiunto {
	
	@Param(0)
	private int idScatola;
	
	public Raggiunto(int id) {
		this.idScatola=id;
	}
	
	public int getIdScatola() {
		return idScatola;
	}
	
	public void setIdScatola(int idScatola) {
		this.idScatola = idScatola;
	}
}
