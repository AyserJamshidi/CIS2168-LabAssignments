import java.util.Arrays;

public class SudokuProblem {

	private static final int[][] sudokuBoard = {
			{8, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 3, 6, 0, 0, 0, 0, 0},
			{0, 7, 0, 0, 9, 0, 2, 0, 0},
			{0, 5, 0, 0, 0, 7, 0, 0, 0},
			{0, 0, 0, 0, 4, 5, 7, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 3, 0},
			{0, 0, 1, 0, 0, 0, 0, 6, 8},
			{0, 0, 8, 5, 0, 0, 0, 1, 0},
			{0, 9, 0, 0, 0, 0, 4, 0, 0}

//			{0, 0, 0, 2, 6, 0, 7, 0, 1}, // sudoku puzzle 2D array
//			{6, 8, 0, 0, 7, 0, 0, 9, 0},
//			{1, 9, 0, 0, 0, 4, 5, 0, 0},
//			{8, 2, 0, 1, 0, 0, 0, 4, 0},
//			{0, 0, 4, 6, 0, 2, 9, 0, 0},
//			{0, 5, 0, 0, 0, 3, 0, 2, 8},
//			{0, 0, 9, 3, 0, 0, 0, 7, 4},
//			{0, 4, 0, 0, 5, 0, 0, 3, 6},
//			{7, 0, 3, 0, 1, 8, 0, 0, 0}
	};

	public static void main(String[] args) {
		printBoard();
		solve(0, 0);
	}

	public static boolean isUnique(int row, int col, int num) {
		for (int i = 0; i < sudokuBoard.length; i++)
			if (sudokuBoard[row][i] == num || sudokuBoard[i][col] == num)
				return false;

		return true;
	}

	public static boolean squareCheck(int row, int col, int num) {
		for (int i = (col - (col % 3)); i < (col - (col % 3) + 3); i++)
			for (int j = (row - (row % 3)); j < (row - (row % 3) + 3); j++)
				if (num == sudokuBoard[j][i])
					return false;

		return true;
	}

	public static boolean solve(int row, int col) {
		if (col >= sudokuBoard.length) {
			row++;
			col = 0;
		}

		if (row >= sudokuBoard.length) {
			System.out.println("Done!");
			printBoard();
			return true;
		}

		if (sudokuBoard[row][col] != 0)
			return solve(row, col + 1);

		for (int i = 1; i < 10; i++) {
			if (isUnique(row, col, i) && squareCheck(row, col, i)) {
				sudokuBoard[row][col] = i;

				if (solve(row, col + 1))
					return true;

				sudokuBoard[row][col] = 0;
			}
		}

		return false;
	}

	public static void printBoard() {
		for (int[] ints : sudokuBoard)
			System.out.println(Arrays.toString(ints).replaceAll("[\\[\\]]", ""));

		System.out.println();
	}
}