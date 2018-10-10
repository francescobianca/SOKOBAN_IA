package it.unical.encodingObject;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("mossa")
public class Mossa {

	@Param(0)
	private int step;
	
	@Param(1)
	private String direzione;
	
	@Param(2)
	private int idBox;
	
	public Mossa() {
		// TODO Auto-generated constructor stub
	}
	
	public Mossa(int s,String d,int i) {
		this.step=s;
		this.direzione=d;
		this.idBox=i;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getDirezione() {
		return direzione;
	}

	public void setDirezione(String direzione) {
		this.direzione = direzione;
	}

	public int getIdBox() {
		return idBox;
	}

	public void setIdBox(int idBox) {
		this.idBox = idBox;
	}
}