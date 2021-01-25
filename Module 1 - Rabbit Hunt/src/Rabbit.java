public class Rabbit extends Animal {
	private int turnsToWait = 0;

	public Rabbit(Model model, int row, int column) {
		super(model, row, column);
	}

	// Map is 20x20 tiles
	int decideMove() {
		int[] movementArray = new int[]{
				0, // North
				0, // North East
				0, // East
				0, // South East
				0, // South
				0, // South West
				0, // West
				0, // North West
		};

		if (foxDistance() != -1) { // If we can see the fox, time to move!
			int foxDistance = foxDistance();
			int foxDirection = foxDirection();
			turnsToWait = 5;

			///System.out.println("== " + foxDirection());

			// Set up initial weights
			for (int i = 0; i < movementArray.length; i++)
				movementArray[i] = (distance(i) > 3) ? 2 : 0;

			// Weights that do not check the fox's distance have no risk of dying next turn.
			switch (foxDirection) {
				case Model.N -> {
					movementArray[Model.SW] += 5;
					movementArray[Model.SE] += 5;
					movementArray[Model.W] += (foxDistance > 1) ? 4 : 0;
					movementArray[Model.E] += (foxDistance > 1) ? 4 : 0;
					movementArray[Model.NW] += (foxDistance > 2) ? 3 : 0;
					movementArray[Model.NE] += (foxDistance > 2) ? 3 : 0;
				}
				case Model.NE -> {
					movementArray[Model.W] = movementArray[Model.S] = 5;
					movementArray[Model.N] = movementArray[Model.E] = (foxDistance > 2) ? 4 : 2;
					movementArray[Model.SW] = movementArray[Model.NW] = movementArray[Model.SE] = 3;
				}
				case Model.E -> {
					movementArray[Model.NW] = movementArray[Model.SW] = 5;
					movementArray[Model.N] = movementArray[Model.S] = (foxDistance > 1) ? 4 : 0;
					movementArray[Model.NE] = movementArray[Model.SE] = (foxDistance > 2) ? 3 : 0;
				}
				case Model.SE -> {
					movementArray[Model.W] = movementArray[Model.N] = 5;
					movementArray[Model.S] = movementArray[Model.E] = (foxDistance > 2) ? 5 : 3;
					movementArray[Model.NE] = movementArray[Model.SW] = 4;
					movementArray[Model.NW] = 2;
					movementArray[Model.SE] = 1;
				}
				case Model.S -> {
					movementArray[Model.NW] = movementArray[Model.NE] = 5;
					movementArray[Model.W] = movementArray[Model.E] = (foxDistance > 1) ? 4 : 0;
					movementArray[Model.SW] = movementArray[Model.SE] = (foxDistance > 2) ? 3 : 0;
				}
				case Model.SW -> {
					movementArray[Model.N] = movementArray[Model.E] = 5;
					movementArray[Model.NW] = movementArray[Model.SE]
							= movementArray[Model.W] = movementArray[Model.S] = (foxDistance > 2) ? 4 : 2;
					movementArray[Model.NE] = 1;
				}
				case Model.W -> {
					movementArray[Model.NE] = movementArray[Model.SE] = 5;
					movementArray[Model.N] = movementArray[Model.S] = (foxDistance > 1) ? 4 : 0;
					movementArray[Model.NW] = movementArray[Model.SW] = (foxDistance > 2) ? 3 : 0;
				}
				case Model.NW -> {
					movementArray[Model.E] = movementArray[Model.S] = 5;
					movementArray[Model.SW] = movementArray[Model.NE] = (foxDistance > 2) ? 4 : 2;
					movementArray[Model.W] = movementArray[Model.N] = (foxDistance > 2) ? 4 : 1;
					movementArray[Model.SE] = 2;
				}
			}
		} else {
			if (turnsToWait <= 0) {
				return roamDirection();
			}
			turnsToWait--;
		}

		for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++) {
			if (distance(i) <= 2 && movementArray[i] > 0)
				movementArray[i] -= 1;
		}

		return movementDecision(movementArray);
	}

	private int roamDirection() {
		int direction = Model.STAY;
		int previousDistance = 0;

		for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++) {
			int distance = distance(i);

			// If we're hitting this far of a distance, we're close to an edge. We NEED to move this direction!
			if (distance > 17 && distance > previousDistance) {
				direction = i;
				previousDistance = distance;
			}
		}

		return direction;
	}

	/**
	 * @param movementWeights expected to be 8 elements unless you really want unexpected behavior
	 */
	private int movementDecision(int[] movementWeights) {
		int decision = Model.STAY;
		int decisionWeight = 0;

		for (int i = 0; i < movementWeights.length; i++)
			if (canMove(i) && movementWeights[i] > decisionWeight) {
				decision = i;
				decisionWeight = movementWeights[i];
			}

		return decision;
	}

	private int foxDistance() {
		for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++)
			if (look(i) == Model.FOX)
				return distance(i);

		return -1;
	}

	private int foxDirection() {
		for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++)
			if (look(i) == Model.FOX)
				return i;

		return -1;
	}

	private int modelIsTilesAway(int tileCount) {
		for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++)
			if (distance(i) <= tileCount)
				return i;

		return -1;
	}
}