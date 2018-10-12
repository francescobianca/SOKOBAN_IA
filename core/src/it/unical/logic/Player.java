package it.unical.logic;

public class Player extends AbstractObject {

	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int RIGHT = 2;
	public final static int LEFT = 3;

	private World world;

	public Player(int i, int j, World world) {
		super(i, j);
		this.world = world;
	}

	public void move(int direction) {
		switch (direction) {

		case UP:
			if (canMove(positionI - 1, positionJ)) {
				if (isThereBox(positionI - 1, positionJ)) {
				Box currentBox=world.getBox(positionI-1,positionJ);
				//	Box currentBox = (Box) world.getObject(positionI - 1, positionJ);
					if (currentBox.iAmMovable(positionI - 2, positionJ))
						world.movePlayerAndBox(positionI, positionJ, currentBox.getI(), currentBox.getJ(),
								positionI - 2, positionJ);
				} else
					world.movePlayer(positionI, positionJ, positionI - 1, positionJ);
			}
			break;

		case DOWN:
			if (canMove(positionI + 1, positionJ)) {
				if (isThereBox(positionI + 1, positionJ)) {
					Box currentBox=world.getBox(positionI+1,positionJ);
					//Box currentBox = (Box) world.getObject(positionI + 1, positionJ);
					if (currentBox.iAmMovable(positionI + 2, positionJ))
						world.movePlayerAndBox(positionI, positionJ, currentBox.getI(), currentBox.getJ(),
								positionI + 2, positionJ);
				} else
					world.movePlayer(positionI, positionJ, positionI + 1, positionJ);
			}
			break;

		case RIGHT:
			if (canMove(positionI, positionJ + 1)) {
				if (isThereBox(positionI, positionJ + 1)) {
					Box currentBox=world.getBox(positionI,positionJ+1);
					//Box currentBox = (Box) world.getObject(positionI, positionJ + 1);
					if (currentBox.iAmMovable(positionI, positionJ + 2))
						world.movePlayerAndBox(positionI, positionJ, currentBox.getI(), currentBox.getJ(), positionI,
								positionJ + 2);
				} else
					world.movePlayer(positionI, positionJ, positionI, positionJ + 1);
			}
			break;

		case LEFT:
			if (canMove(positionI, positionJ - 1)) {
				if (isThereBox(positionI, positionJ - 1)) {
					Box currentBox=world.getBox(positionI,positionJ-1);
					//Box currentBox = (Box) world.getObject(positionI, positionJ - 1);
					if (currentBox.iAmMovable(positionI, positionJ - 2))
						world.movePlayerAndBox(positionI, positionJ, currentBox.getI(), currentBox.getJ(), positionI,
								positionJ - 2);
				} else
					world.movePlayer(positionI, positionJ, positionI, positionJ - 1);
			}
			break;

		}
	}

	public boolean isThereBox(int i, int j) {
		for(Box b:world.getBoxs()) {
			if(b.getI()==i && b.getJ()==j)
				return true;
		}
		return false;
	/*	if (world.getObject(i, j) instanceof Box)
			return true;
		return false;*/
	}

	public boolean canMove(int i, int j) {
		if ((world.isThereWall(i, j)) || i < 0 || i >= world.getNumberRow() || j < 0
				|| j >= world.getNumberColumn()) 
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "PLAYER";
	}

}
