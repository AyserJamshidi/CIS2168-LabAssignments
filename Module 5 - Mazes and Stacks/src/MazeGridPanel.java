import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JPanel;

public class MazeGridPanel extends JPanel {
	private int rows;
	private int cols;
	private Cell[][] maze;

	// Static stuff
	private final Color VISITED_COLOR = Color.DARK_GRAY;
	private final Color PATH_COLOR = Color.BLUE;

	private final boolean BLOCK_FINISH_CELL = false;

	// Maze stuff
	long startTime;
	boolean initializedMaze = false;
	Stack<Cell> stack = new Stack<>();
	Cell start, finish, current;


	// extra credit
	public void genDFSMaze() {
		boolean[][] visited;
		Stack<Cell> stack = new Stack<>();
		Cell start = maze[0][0];
		stack.push(start);
	}

	//homework
	public void solveMaze() {
		if (!initializedMaze)
			initializeMaze();

		if (!stack.empty()) { // We can keep searching
			current = stack.peek(); // Sets the current cell to the cell at the top of the stack

			// North and west movement disabled because we never need to move those directions for this assignment
			if (!current.equals(finish)) { // We're not at the "finish" cell
				/*if (!current.northWall && !visited(current.row - 1, current.col)) { // Can move up
					moveVertically(stack, current, -1);
				} else*/ if (!current.southWall && !visited(current.row + 1, current.col)) { // Can move down
					moveVertically(stack, current, 1); // Move down
				} else if (!current.eastWall && !visited(current.row, current.col + 1)) { // Can move right
					moveHorizontally(stack, current, 1); // Move right
				/* } else if (!current.westWall && !visited(current.row, current.col - 1)) { // Can move left
					moveHorizontally(stack, current, -1); // Move left
				}*/
				} else { // Can't move any further,
					ignore(current); // Mark cell as ignored
					stack.pop(); // Move back one
				}
			} else // We've hit the "finish" cell
				System.out.printf("We solved the maze in %.2f seconds", (float) (System.currentTimeMillis() - startTime) / 1000);
		} else // We've exhausted every possible cell in the stack
			System.out.println("There was either no \"finish\" cell or all paths to it were blocked.");
	}

	/**
	 * Initializes the start and finishing cell along with the stack.
	 */
	public void initializeMaze() {
		initializedMaze = true;
		startTime = System.currentTimeMillis();
		start = maze[0][0];
		finish = maze[rows - 1][cols - 1];

		start.setBackground(Color.GREEN);
		finish.setBackground(Color.RED);

		stack.push(start);
	}

	/**
	 * Moves the given cell vertically by adding the change to the current row.
	 *
	 * @param stack   stack containing current
	 * @param current current cell
	 * @param change  shift amount.  Unpredictable behavior if not within bounds [-1, 1]
	 */
	public void moveVertically(Stack<Cell> stack, Cell current, int change) {
		stack.push(maze[current.row + change][current.col]);
		maze[current.row + change][current.col].setBackground(PATH_COLOR);
		callMaze();
	}

	/**
	 * Moves the given cell horizontally by adding change to the current column.
	 *
	 * @param stack   stack containing current
	 * @param current current cell
	 * @param change  shift amount.  Unpredictable behavior if not within bounds [-1, 1]
	 */
	public void moveHorizontally(Stack<Cell> stack, Cell current, int change) {
		stack.push(maze[current.row][current.col + change]);
		maze[current.row][current.col + change].setBackground(PATH_COLOR);
		callMaze();
	}

	/**
	 * Sets the given cell as used by changing its color to VISITED_COLOR.
	 *
	 * @param current current cell
	 */
	public void ignore(Cell current) {
		maze[current.row][current.col].setBackground(VISITED_COLOR);
		callMaze();
	}

	/**
	 * Dirty thread to make the GUI move while we solve.
	 */
	public void callMaze() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1); // Quick sleep to assist in visualization
				} catch (InterruptedException e) {}

				solveMaze();
			}
		}.start();
	}

	public boolean visited(int row, int col) {
		Cell c = maze[row][col];
		Color status = c.getBackground();
		if (status.equals(Color.WHITE) || status.equals(Color.RED))
			return false;

		return true;
	}

	public void genNWMaze() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				if (row == 0 && col == 0) {
					continue;
				} else if (row == 0) {
					maze[row][col].westWall = false;
					maze[row][col - 1].eastWall = false;
				} else if (col == 0) {
					maze[row][col].northWall = false;
					maze[row - 1][col].southWall = false;
				} else {
					boolean north = Math.random() < 0.5;
					if (north) {
						maze[row][col].northWall = false;
						maze[row - 1][col].southWall = false;
					} else {  // remove west
						maze[row][col].westWall = false;
						maze[row][col - 1].eastWall = false;
					}
					maze[row][col].repaint();
				}
			}
		}

		if (BLOCK_FINISH_CELL) {
			maze[rows - 1][cols - 2].eastWall = true;
			maze[rows - 2][cols - 1].southWall = true;
			maze[rows - 1][cols - 1].eastWall = true;
			maze[rows - 1][cols - 1].northWall = true;
		}

		this.repaint();
	}

	public MazeGridPanel(int rows, int cols) {
		this.setPreferredSize(new Dimension(800, 800));
		this.rows = rows;
		this.cols = cols;

		this.setLayout(new GridLayout(rows, cols));
		this.maze = new Cell[rows][cols];

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				maze[row][col] = new Cell(row, col);
				this.add(maze[row][col]);
			}
		}

		this.genNWMaze();
		this.solveMaze();
	}
}