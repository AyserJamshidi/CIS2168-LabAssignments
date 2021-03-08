import java.util.Arrays;

public class EightQueensProblem {

	private static int[][] chessBoard = {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
	};

	public static void main(String[] args) {
		printBoard();
		solveColumn(0);
	}

	public static boolean inBounds(int row, int col) {
		return (0 <= row && row < chessBoard.length) && (0 <= col && col < chessBoard.length);
	}

	public static boolean isSafe(int row, int col) {
		for (int i = 0; i < chessBoard.length; i++) // Vertical
			if (chessBoard[row][i] == 1)
				return false;

		for (int i = 0; i < chessBoard.length; i++) // Horizontal
			if (chessBoard[i][col] == 1)
				return false;

		for (int i = 0; inBounds(row + i, col + i); i++) // North East
			if (chessBoard[row + i][col + i] == 1)
				return false;

		for (int i = 0; inBounds(row - i, col + i); i++) // South East
			if (chessBoard[row - i][col + i] == 1)
				return false;

		for (int i = 0; inBounds(row - i, col - i); i++) // South West
			if (chessBoard[row - i][col - i] == 1)
				return false;

		for (int i = 0; inBounds(row + i, col - i); i++) // North West
			if (chessBoard[row + i][col - i] == 1)
				return false;

		return true;
	}

	public static void placeQueen(int row, int col) {
		chessBoard[row][col] = 1;
		printBoard();
	}

	public static boolean solveColumn(int col) {
		if (col >= chessBoard.length) {
			System.out.println("Done!");
			return true;
		}

		for (int curRow = 0; curRow < chessBoard.length; curRow++) {
			if (isSafe(curRow, col)) { // If no other queen can attack this position
				placeQueen(curRow, col);

				if (solveColumn(col + 1)) // Recursive call
					return true;

				chessBoard[curRow][col] = 0; // Undo our queen placement as we couldn't solve the puzzle with it placed here.
			}
		}

		return false;
	}

	public static void printBoard() {
		for (int[] ints : chessBoard)
			System.out.println(Arrays.toString(ints).replaceAll("[\\[\\]]", ""));

		System.out.println();
	}
}
