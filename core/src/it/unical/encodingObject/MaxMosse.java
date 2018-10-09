package it.unical.encodingObject;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("maxmosse")
public class MaxMosse {

	@Param(0)
	private int numero;
	
	public MaxMosse() {
	
	}

	public MaxMosse(int numero) {
		this.numero=numero;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
}
