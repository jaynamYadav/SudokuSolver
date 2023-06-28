package sudokuproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SudokuSolverApp extends JFrame implements ActionListener {

	private JTextField[][] cells;
	private JButton solveButton, resetButton, clearButton, closeButton;
	private SudokuSolver solver;
	int reset[][] = new int[9][9];
	
	public SudokuSolverApp() {
		solver = new SudokuSolver();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new GridLayout(9, 9));
		JPanel buttonPanel = new JPanel();
		cells = new JTextField[9][9];
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				cells[row][col] = new JTextField();
				cells[row][col].setHorizontalAlignment(JTextField.CENTER);
				panel.add(cells[row][col]);
			}
		}
		
		solveButton = new JButton("Solve");
		resetButton = new JButton("Reset");
		clearButton = new JButton("Clear");
		closeButton = new JButton("Close");
		
		buttonPanel.add(solveButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(closeButton);
		solveButton.addActionListener(this);
		resetButton.addActionListener(this);
		clearButton.addActionListener(this);
		closeButton.addActionListener(this);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		setTitle("Sudoku Solver");
		setApperance();
		pack();
	}
	
	private void setApperance() {
		Color white = Color.white;
		Color black = Color.black;
		Color gray = Color.gray;
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if ((row < 3 || row > 5) && (col < 3 || col > 5)) {
					cells[row][col].setBackground(white);
				} else if ((row >= 3 && row <= 5) && (col >= 3 && col <= 5)) {
					cells[row][col].setBackground(white);
				} else {
					cells[row][col].setBackground(gray);
				}
				cells[row][col].setHorizontalAlignment(JTextField.CENTER);
				cells[row][col].setFont(new Font("Arial", Font.ITALIC, 20));
				cells[row][col].setBorder(BorderFactory.createLineBorder(black, 1, true));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == solveButton) {
			solveSudoku();
		}
		if (e.getSource() == resetButton) {
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					cells[row][col].setText(Integer.toString(reset[row][col]));
				}
			}
		}
		if (e.getSource() == clearButton) {
			clearBoard();
		}
		if (e.getSource() == closeButton) {
			setVisible(false);
			dispose();
		}
	}

	private void solveSudoku() {
		int[][] board = new int[9][9];
		int valueI, flag = 1;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				String valueS = cells[row][col].getText();
				if (!valueS.isEmpty()) {
					try {
						valueI = Integer.parseInt(valueS);
					} catch (NumberFormatException e) {
						flag = 0;
						valueI = 0;
					}
				}
				else
					valueI = 0;
				if (valueS.isEmpty()) {
					board[row][col] = 0;
					reset[row][col] = 0;
				} else if (valueI < 0 || valueI > 9) {
					flag = 0;
				} else {
					board[row][col] = valueI;
					reset[row][col] = valueI;
				}
			}
		}
		if (flag == 0) {
			JOptionPane.showMessageDialog(this, "Please enter numbers between 1 to 9 only", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return;
		}
		flag = 1;
		solver.setBoard(board);
		if (solver.check(board)) {
			int[][] solution = solver.getBoard();
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					cells[row][col].setText(Integer.toString(solution[row][col]));
				}
			}
		} else {
			System.out.println("No Solution");
			JOptionPane.showMessageDialog(this, "No solution exists for the given puzzle", "No Solution", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void clearBoard() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				cells[i][j].setText("");
			}
		}
	}

	public static void main(String[] args) {
		SudokuSolverApp app = new SudokuSolverApp();
		app.setVisible(true);
		app.setSize(400, 400);
	}

}
