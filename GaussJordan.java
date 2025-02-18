/*
  Implementation of Gauss Jordan Elimination algorithm
*/

import java.util.Scanner;
import java.io.*;

public class GaussJordan {

	public static void main(String args[]) {
		final Scanner commandlineInput = new Scanner(System.in);
		final float[][] augmentedMatrix;
		final int columns;
		final int rows;

		try {
			final boolean invalidInput = args.length != 1 || !args[0].equals("example") && !args[0].equals("input");
			if (invalidInput) {
				System.out.println(
						"Use \"example\" in commandline argument to see the Gauss Jordan Elimination algorithm in action with a given example.");
				System.out
						.println("Use \"input\" to pass in your own augmented matrix representing a system of linear equations.");
				return;
			}

			if (args[0].equals("example")) {
				System.out.println("This program simulates example from p.14.");
				augmentedMatrix = new float[][] {
						{ 2, 4, -2, 2, 4, 2 },
						{ 1, 2, -1, 2, 0, 4 },
						{ 3, 6, -2, 1, 9, 1 },
						{ 5, 10, -4, 5, 9, 9 }
				};

				// float array represents augmented matrix, from question 1.2.5
				rows = augmentedMatrix.length;
				columns = augmentedMatrix[0].length;
			} else {
				System.out.println("How many columns?");
				columns = commandlineInput.nextInt();
				System.out.println("How many rows?");
				rows = commandlineInput.nextInt();
				augmentedMatrix = new float[rows][columns];
				for (int i = 0; i < rows; i++) {
					System.out.println("Type all numbers in row " + i + " separated by space");
					for (int j = 0; j < columns; j++) {
						augmentedMatrix[i][j] = commandlineInput.nextInt();
					}
				}
			}
		} finally {
			commandlineInput.close();
		}

		System.out.println("Input augmented matrix is:");
		printMatrix(rows, columns, augmentedMatrix);
		gaussJordan(rows, columns, augmentedMatrix);
	}

	private static void gaussJordan(final int rows, final int columns, final float[][] augmentedMatrix) {
		for (int i = 0; i < rows; i++) {
			final int leadingColumn = getLeadingColumnForRow(columns, augmentedMatrix[i]);

			if (leadingColumn != -1) {
				final float leadingValue = augmentedMatrix[i][leadingColumn];

				// divide every value in row by lead coefficient
				for (int j = 0; j < columns; j++) {
					augmentedMatrix[i][j] = augmentedMatrix[i][j] / leadingValue;
				}

				// zero out other values in this column for all other rows
				for (int row = i + 1; row < rows; row++) {
					final float val = augmentedMatrix[row][leadingColumn];
					if (val != 0) {
						for (int j = 0; j < columns; j++) {
							augmentedMatrix[row][j] = augmentedMatrix[row][j] - val * augmentedMatrix[i][j];
						}
					}
				}
			} else if (augmentedMatrix[i][columns - 1] != 0) {
				System.out.println(String
						.format("Row number %d is inconsistent. The solution is probably wrong but we'll keep going anyway.", i));
			}
		}

		// print out matrix
		System.out.println("The reduced row echelon matrix is therefore: ");
		printMatrix(rows, columns, getEchelonForm(rows, columns, augmentedMatrix));
	}

	private static float[][] getEchelonForm(final int rows, final int columns, final float[][] augmentedMatrix) {
		// swap rows
		final float[][] solutionMatrix = new float[rows][columns];
		final boolean[] rowAdded = new boolean[rows];
		int nextSolutionRow = 0;
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				if (augmentedMatrix[j][i] == 1.0 && !rowAdded[j]) {
					solutionMatrix[nextSolutionRow++] = augmentedMatrix[j];
					rowAdded[j] = true;
					break;
				}
			}
		}

		return solutionMatrix;
	}

	private static int getLeadingColumnForRow(final int columns, final float[] augmentedRow) {
		int leadingColumn = -1;

		for (int k = 0; k < columns; k++) {
			final float value = augmentedRow[k];
			if (value != 0) {
				leadingColumn = k;
				break;
			}
		}
		return leadingColumn;
	}

	private static void printMatrix(final int rows, final int columns, final float[][] augmentedMatrix) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				System.out.print(augmentedMatrix[i][j] + ", ");
			}
			System.out.println();
		}
		System.out.println();
	}
}