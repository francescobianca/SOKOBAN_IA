package it.unical.logic;

public abstract class AbstractObject implements ObjectGame {

	int positionI;
	int positionJ;

	public AbstractObject(int i, int j) {
		// TODO Auto-generated constructor stub
		setCell(i, j);
	}

	@Override
	public int getI() {
		// TODO Auto-generated method stub
		return positionI;
	}

	@Override
	public int getJ() {
		// TODO Auto-generated method stub
		return positionJ;
	}

	@Override
	public void setI(int i) {
		// TODO Auto-generated method stub
		this.positionI = i;
	}

	@Override
	public void setJ(int j) {
		// TODO Auto-generated method stub
		this.positionJ = j;
	}
	
	@Override
	public void setCell(int i, int j) {
		setI(i);
		setJ(j);
	}

}
