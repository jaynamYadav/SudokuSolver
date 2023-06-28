package sudokuproject;

public class SudokSolver {

	private int[][] board;

	public SudokuSolver() {
		board = new int[9][9];
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public boolean solve(int[][] board, int row, int col) {
		if (row == 9) {
			return true;
		}
		int nextRow = row, nextCol = col + 1;
		if (nextCol == 9) {
			nextRow = row + 1;
			nextCol = 0;
		}
		if (board[row][col] != 0) {
			if (!isSafe(board, row, col, board[row][col])) {
				return false;
			}
			return solve(board, nextRow, nextCol);
		}
		for (int digit = 1; digit <= 9; digit++) {
			if (isSafe(board, row, col, digit)) {
				board[row][col] = digit;
				if (solve(board, nextRow, nextCol))
					return true;
				board[row][col] = 0;
			}
		}
		return false;
	}

	private boolean isSafe(int[][] board, int row, int col, int digit) {
		for (int i = 0; i < 9; i++) {
			if (board[row][i] == digit)
				return false;
			if (board[i][col] == digit)
				return false;
		}
		int sr = (row / 3) * 3, sc = (col / 3) * 3;
		for (int i = sr; i < sr + 3; i++)
			for (int j = sc; j < sc + 3; j++)
				if (board[i][j] == digit)
					return false;
		return true;
	}
}
