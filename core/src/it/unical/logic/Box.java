package it.unical.logic;

public class Box extends AbstractObject {

	World world;
	
	private int id;
	private static int idCounter=0; //Id ad autoincremento.

	public Box(int i, int j, World world) {
		super(i, j);
		this.world = world;
		this.id=this.idCounter++;
	}

	public World getWorld() {
		return world;
	}

	public boolean iAmMovable(int i, int j) {
		if (world.isThereBox(i, j) || world.isThereWall(i, j) || i < 0
				|| i >= world.getNumberRow() || j < 0 || j >= world.getNumberColumn())
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "Id: "+this.id+" "+this.getI()+" "+this.getJ();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public static void resetIdCounter() {
		idCounter=0;
	}
}
