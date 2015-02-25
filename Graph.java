/*
* Graph.java
* 
* Author: Derrick Lee|dtl4734
*
* Contains all the basic methods needed for the graph generation
* as well as several get methods.
* 
*/

import java.util.Random;
import java.lang.Math;
import java.text.*;
import java.text.DecimalFormat;


public class Graph{

	private double adjacencyMatrix[][];
	private int xCoords[];
	private int yCoords[];
	private int openValues[];
	private int vertices;
	DecimalFormat df = new DecimalFormat("0.00");

	/*
	* Constructs a new graph
	*
	* Arguments:
	*	n: the number of nodes in the graph
	*
	*/
	public Graph(int n){
		adjacencyMatrix = new double[n][n];
		xCoords = new int[n];
		yCoords = new int[n];
		openValues = new int[n];
		for(int i = 0; i < n; i++){
			openValues[i] = -1;
		}
		vertices = n;
	}

	//returns the number of nodes in the graph
	public int numberOfNodes(){
		return vertices;
	}

	//returns the array containing the x coordinates
	public int[] getXCoords(){
		return xCoords;
	}

	//returns the array containing the y coordinates
	public int[] getYCoords(){
		return yCoords;
	}

	//returns the adjacency matrix
	public double[][] getAM(){
		return adjacencyMatrix;
	}

	/*
	* Generates coordinates for the graph using the given seed
	*
	* Arguments:
	* 	seed: the given seed for the Random()
	* 
	*/
	public void generateCoords(long seed){
		
		Random xGen = new Random(seed);
		Random yGen = new Random(2 * seed);
		int x, y;
		int pointsGenerated = 0;

		while(pointsGenerated < vertices){
			
			x = xGen.nextInt(vertices);
			y = yGen.nextInt(vertices);

			if(openValues[x] == -1){
				yCoords[pointsGenerated] = y;
				xCoords[pointsGenerated] = x;
				openValues[x] = 1;
				pointsGenerated++;
			}
		}
	}

	//generates the adjacency matrix
	public void generateAM(){
		double distance, xdif, ydif;
		double x1, y1, x2, y2;
		for(int i = 0; i < vertices; i++){
			x1 = xCoords[i];
			y1 = yCoords[i];
			for(int j = 0; j < vertices; j++){
				x2 = xCoords[j];
				y2 = yCoords[j];
				xdif = (double) (x1 - x2);
				ydif = (double) (y1 - y2);
				distance = Math.sqrt(xdif * xdif + ydif * ydif);
				adjacencyMatrix[i][j] = distance;
			}
		}
	}

	//prints out all the vertices
	public void printVertices(){
		System.out.println("X-Y Coordinates:");
		for(int i = 0; i < vertices; i++){
			System.out.printf("v%d: (", i);
			System.out.printf("%d,", (int) xCoords[i]);
			System.out.printf("%d) ", (int) yCoords[i]);
		}
		System.out.println("\n");
	}

	//prints out the adjacency matrix
	public void printAM(){
		System.out.println("Adjacency matrix of graph weights:\n");
		for(int i = 0; i < vertices; i++){
			System.out.printf("      %d", i);
		}
		System.out.println("\n");
		for(int x = 0; x < vertices; x++){
			System.out.printf("%d   ", x);
			for(int y = 0; y < vertices; y++){
				System.out.printf(df.format(adjacencyMatrix[x][y]) + "   ");
			}
			System.out.println("\n");
		}
	}

	public static void main(String args[]){}
}