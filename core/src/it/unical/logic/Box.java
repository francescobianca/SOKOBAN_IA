package it.unical.logic;

public class Box extends AbstractObject {

	World world;

	public Box(int i, int j, World world) {
		super(i, j);
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public boolean iAmMovable(int i, int j) {
		if (world.getObject(i, j) instanceof Wall || world.getObject(i, j) instanceof Box || i < 0
				|| i >= world.getNumberRow() || j < 0 || j >= world.getNumberColumn())
			return false;

		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "BOX";
	}

}
