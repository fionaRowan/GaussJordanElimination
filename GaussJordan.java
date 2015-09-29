/*
Fiona Rowan
Implementation of Gauss Jordan Elimination algorithm
Use "example" in commandline argument to see this in action with a given example.
Use any other argument to pass in your own augmented matrix representing a system
of linear equations.




*/

import java.util.Scanner;
import java.io.*;

public class GaussJordan{

public static void main(String args[] ){
	Scanner user_input = new Scanner(System.in);
	double[][] augmented; 
	int columns;
	int rows;
	if(args[0].equals("example")){
		System.out.println("This program simulates example from p.14.");
		augmented = new double[][]{
			{2,4,-2,2,4,2},
			{1,2,-1,2,0,4},
			{3,6,-2,1,9,1},
			{5,10,-4,5,9,9}
		};
		// double array represents augmented matrix, from question 1.2.5

		rows = augmented.length; 
		columns = augmented[0].length; 
		input(rows, columns, augmented);
		gaussJordan(rows, columns, augmented); 
	}else{
		System.out.println("How many columns?");
		columns = user_input.nextInt(); 
		System.out.println("How many rows?");
		rows = user_input.nextInt(); 
		augmented = new double[rows][columns];
		for(int i =0; i<rows; i++){
			System.out.println("Type all numbers in row " +i +" separated by space");
			for(int j = 0; j<columns; j++){
				augmented[i][j] = user_input.nextInt();
			}
		}
		input(rows, columns, augmented);
		gaussJordan(rows, columns, augmented); 
	
	}

}

public static void input(int rows, int columns, double[][] augmented){
	System.out.println("Input augmented matrix is:" ); 
	for (int i = 0; i< rows; i++){
		for (int j=0; j<columns; j++){
			System.out.print(augmented[i][j]+", ");
		}
		System.out.println();
	} 
	System.out.println();

}

public static void gaussJordan(int rows, int columns, double[][] augmented){
	//implement Gauss-Jordan 
	for(int i = 0; i<rows; i++){
		boolean empty = true;
		for(int k =0; k<columns; k++){
			if(augmented[i][k] !=0)
				empty=false;
		}
		if(!empty){
			double lead=0;
			//col of lead var
			int col = -1; 
			while(lead == 0){
				lead = augmented[i][++col];
			}
			//divide every value by lead coefficient
			for( int j=0; j < columns; j++){
				augmented[i][j] = augmented[i][j]/lead;
			}
			//cancel all other instances of that variable in other rows
			for(int row = 0; row < rows; row++){
				if (row!= i){
					double val = augmented[row][col];
					if(val !=0){
						for(int j = 0; j < columns; j++){
							augmented[row][j] = augmented[row][j] - val*augmented[i][j];
						}
					}
				}
			}
			
		}
		else{
			if(augmented[i][columns-1] ==0){
				System.out.println(i+"th row ignored");
			}
			else{
				System.out.println(i+ "th row inconsistent"); 
			}
		}	
		
	}

	//swap rows (unfinished)


	//print out matrix
	System.out.println("The augmented matrix is therefore: ");
	for (int i = 0; i< rows; i++){
		for (int j=0; j<columns; j++){
			System.out.print(augmented[i][j]+", ");
		}
		System.out.println();
	} 


}
}