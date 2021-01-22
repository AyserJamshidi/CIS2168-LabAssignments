import java.util.Scanner;

public class Rabbit extends Animal {

	final int MODEL_NAME = 0;
	final int MODEL_DIST = 1;

	Scanner testThing = new Scanner(System.in);
	int[][] scannedArea = null;
	int currentTurn = 0;
	int previousDistanceFromFox = -1;

	// Edge checking
	boolean isAcceptablePosition = false;

	// After we found a good bush
	boolean bushIsSetup = false;
	int bushDirection = -1;

	// Weighted movement
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

	public Rabbit(Model model, int row, int column) {
		super(model, row, column);
	}

	// Map is 20x20 tiles
	int decideMove() {

		scanArea(); // Set scannedArea var
		currentTurn++;

		if (modelIsInView(Model.FOX)) {
			previousDistanceFromFox = closestOf(Model.FOX, "DISTANCE");
			int foxDirection = closestOf(Model.FOX, "DIRECTION");

			/*
			 * If fox is 3 tiles away {
			 *      If we're 1 tile from a bush:
			 *          If we're N, S, E or W of said bush:
			 *              do nothing
			 *          Else:
			 *              move to a tile that will put us in such position
			 *              (This can be done by getting the direction and % 2...  If not 0, then turn 1 or -1 maybe?)
			 * }
			 *
			 * If fox is 1 tile away {
			 *      If we're 1 tile from a bush:
			 *          If we're N, S, E or W of said bush:
			 *              Move diagonally to LOS fox
			 *          else
			 *              ????????
			 * }
			 */

			/*if (!bushIsSetup) {
				if (modelIsInDistance(Model.FOX, 2)) { // We're in danger soon
					System.out.println("Time to dip!!");
					return testThing.nextInt();
				}

				System.out.println("Side-step time?");
				return testThing.nextInt();
			} else */

			if (canMove(Model.turn(bushDirection, 1))
					&& foxDirection != Model.turn(bushDirection, 1)
					&& foxDirection != Model.turn(bushDirection, 1 + 4)) { // Look right
				System.out.println("Turning right == " + Model.turn(bushDirection, 1));

				if (closestOf(Model.BUSH, "DISTANCE") == 1)
					return bushDirection;
				else {
					bushDirection = Model.turn(bushDirection, -2);
					return Model.turn(bushDirection, 1);
				}
			} else if (canMove(Model.turn(bushDirection, -1))
					&& foxDirection != Model.turn(bushDirection, -1)
					&& foxDirection != Model.turn(bushDirection, -1 + 4)) { // Look left
				System.out.println("Turning left == " + Model.turn(bushDirection, -1));
				bushDirection = Model.turn(bushDirection, 2);
				return Model.turn(bushDirection, -1);
			}

			System.out.println("UHHHH WHERE DO WE MOVE?!");
			return testThing.nextInt();

		}

		if (!isAcceptablePosition) {
			if (closestOf(Model.EDGE, "DISTANCE") <= 5) {
				int movementDirection = Model.turn(closestOf(Model.EDGE, "DIRECTION"), 4); // 180 degree turn
				System.out.println("INSIDE !ACCEPTABLE");
				if (canMove(movementDirection))
					return movementDirection;
				else if (canMove(movementDirection - 1))
					return movementDirection - 1;
				else if (canMove(movementDirection + 1))
					return movementDirection + 1;

				System.out.println("WHERE DO WE MOVE?!?!");
				return testThing.nextInt();
			} else
				isAcceptablePosition = true;
		}

		// Bush handling
		if (modelIsInView(Model.BUSH)) { // Walk to bush
			bushDirection = closestOf(Model.BUSH, "DIRECTION");

			if (closestOf(Model.BUSH, "DISTANCE") == 1) { // We're set up at a good bush!
				switch (bushDirection) {
					case Model.N, Model.E, Model.S, Model.W -> {
						bushIsSetup = true;
						System.out.println("We're in a good position for a bush!");
						return Model.STAY; // We're in a proper position!
					}
					default -> {
						if (canMove(bushDirection - 1))
							return bushDirection - 1;
						else if (canMove(bushDirection + 1))
							return bushDirection + 1;
						System.out.println("Where do we move now?!");
					}
				}

				System.out.println("Next to a bush!");
			} else {
				System.out.println("Found a bush but now we have to walk to it! == " + bushDirection);
				return bushDirection;
				//return testThing.nextInt();
			}
		} else
			bushDirection = -1;
		// Look for bush
		if (bushDirection != -1) { // Walk towards bush
			System.out.println("Attempting to walk to bush");
		}

		System.out.println("How did we get here??");
		return testThing.nextInt();
	}

	private boolean modelIsInView(final int givenModel) {
		return modelIsInDistance(givenModel, 20);
	}

	private boolean modelIsInDistance(final int givenModel, final int givenDistance) {
		for (int[] currentModel : scannedArea)
			if (currentModel[MODEL_NAME] == givenModel && currentModel[MODEL_DIST] <= givenDistance)
				return true;

		return false;
	}

	private int closestOf(final int givenModel, final String desiredValue) {
		int closestModelDistance = Integer.MAX_VALUE;
		int closestModelDirection = -1;

		for (int curModel = 0; curModel <= Model.MAX_DIRECTION; curModel++) {
			if (scannedArea[curModel][MODEL_NAME] == givenModel && scannedArea[curModel][MODEL_DIST] < closestModelDistance) {
				closestModelDistance = scannedArea[curModel][MODEL_DIST];
				closestModelDirection = curModel;
			}
		}

		return switch (desiredValue) {
			case "DISTANCE" -> closestModelDistance;
			case "DIRECTION" -> closestModelDirection;
			default -> throw new IllegalArgumentException("Must claim \"DISTANCE\" or \"DIRECTION\"");
		};
	}

	private void scanArea() {
		int[][] scannedArea = new int[8][2];

		for (int curDirection = Model.MIN_DIRECTION; curDirection <= Model.MAX_DIRECTION; curDirection++) {
			scannedArea[curDirection][MODEL_NAME] = look(curDirection);
			scannedArea[curDirection][MODEL_DIST] = distance(curDirection);
		}

		this.scannedArea = scannedArea;
	}

	/*private int getClosest(int givenModel) {
		return getClosest(givenModel, "DIRECTION");
	}

	private int getClosest(int givenModel, String desiredOutput) {
		int closestModelDirection = -1;
		int closestModelDistance = Integer.MAX_VALUE;

		for (int curDirection = Model.MIN_DIRECTION; curDirection <= Model.MAX_DIRECTION; curDirection++) {
			if (look(curDirection) == givenModel && distance(curDirection) < closestModelDistance) {
				closestModelDistance = distance(curDirection);
				closestModelDirection = curDirection;
			}
		}

		return switch (desiredOutput) {
			case "DISTANCE" -> closestModelDistance;
			case "DIRECTION" -> closestModelDirection;
			default -> throw new IllegalArgumentException("Must claim \"DISTANCE\" or \"DIRECTION\"");
		};
	}*/
}