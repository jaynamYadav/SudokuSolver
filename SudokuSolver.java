package sudokuproject;

public class SudokuSolver {

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

	public boolean check(int[][] reset) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (reset[row][col] != 0) {
					if (!isSafe(reset, row, col, reset[row][col])) {
						return false;
					}
				}
			}
		}
		return solve(reset, 0, 0);
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

	public boolean isSafe(int[][] board, int row, int col, int digit) {
		
		for (int i = 0; i < 9; i++) {
			if (i != col)
				if (board[row][i] == digit)
					return false;
			if (i != row)
				if (board[i][col] == digit)
					return false;
		}
		int sr = (row / 3) * 3, sc = (col / 3) * 3;
		for (int i = sr; i < sr + 3; i++)
			for (int j = sc; j < sc + 3; j++)
				if (i != row && j != col)
					if (board[i][j] == digit)
						return false;
		return true;
	}
}
