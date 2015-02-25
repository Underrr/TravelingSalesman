/*
* OptimalTSP.java
* 
* Author: Derrick Lee|dtl4734
*
* Generates an optimal tour os the TSP by considering all possible
* permutations of cities. All tours begin and end at vertex 0. If
* the number of cities is less than or equal to 10, a list of the
* x, y coordinates as well as an adjacency matrix will be printed.
* If the number of cities is less than or equal to 5, all possible
* paths will be printed out in lexicographic order.
* 
* Known issues:
* Will not print in lexicographical order
*/

import java.util.*;
import java.lang.*;
import java.io.*;
import java.lang.System;
import java.text.DecimalFormat;



public class OptimalTSP{

	static DecimalFormat df = new DecimalFormat("0.00");

	private static int minOrder[];
	private static double min;
	private static int runthroughs = 0;


	/*
	* Calculates the total distance traveled
	*
	* Arguments: 
	*	order: an int array containing the order in which the nodes are
	*          to be traversed
	*	g: the graph to be traversed
	*
	* Returns: the total distance traveled
	*	
	*/
	public static double distance(int[] order, Graph g){
		double am[][] = g.getAM();
		double distance = am[0][order[0]];
		for(int i = 0; i < order.length; i++){
			int start = order[i];
			int end;
			
			if(i == order.length - 1){
				end = 0;
			}else{
				end = order[i+1];
			}

			distance += am[start][end];

		}
		return distance;
	}

	/*
	* Calculates the optimal route by going through each permutation
	*
	* Arguments: 
	*	numbers: an array containing the nodes that can be traveled
	*	pos: the index of the next value to be swapped
	*	nodes: the number of nodes in the graph
	*	g: the graph to be traversed
	*	
	*/
	public static void optimal(int[] numbers, int pos, int nodes, Graph g){
		int size = numbers.length;
		double d;
		if(size == pos + 1){
			d = distance(numbers, g);
			if(runthroughs == 0){
				min = d;
				minOrder = numbers.clone();
				runthroughs++;
			}else if(d < min){
				min = d;
				minOrder = numbers.clone();
			}
			if(nodes <= 5){
				//print out list of all possible paths
				System.out.printf("Path: 0");
				for(int j = 0; j < nodes - 1; j++){
					System.out.printf(" %d", numbers[j]);
				}
				System.out.printf(" 0  distance = " + df.format(d));
				System.out.println("");
			}
		}else{
			for(int i = pos; i < size; i++){
				swap(numbers, pos, i);
				optimal(numbers, pos+1, nodes, g);
				swap(numbers, pos, i);
			}
		}
	}
	/*
	* Swaps 2 elements in an array
	*
	* Arguments: 
	*  	x: the array containing the items to be swapped
	*	index1, index2: the indices of the elements to be swapped
	*	
	*/
	public static void swap(int[] x, int index1, int index2){
		int temp = x[index1];
		x[index1] = x[index2];
		x[index2] = temp;
	}

	public static void main(String args[]){
		//Check input
		if(args.length != 2){
			System.out.println("Usage: java OptimalTSP n seed");
			System.exit(0);
		}

		int nodes = Integer.parseInt(args[0]);
		int seed = Integer.parseInt(args[1]);

		if(nodes <= 0 || nodes >= 14){
			System.out.println("Number of vertices must be between 1 and 13");
			System.exit(0);
		}
		if(seed != (int) seed){
			System.out.println("Command line args must be integers");
			System.exit(0);
		}

		//Generate and populate graph
		Graph g = new Graph(nodes);
		g.generateCoords((long) seed);
		g.generateAM();

		if(nodes <= 10){
			//print out list of x, y coordinates and the graph
			g.printVertices();
			g.printAM();
		}
		
		int nodeA[] = new int[nodes-1];
		minOrder = new int[nodes-1];

		//create initial order
		for(int i = 0; i < nodes-1; i++){
			nodeA[i] = i+1;
		}		
		//Begin timer and algorithm
		long time = System.currentTimeMillis();
		optimal(nodeA, 0, nodes, g);
		long runtime = System.currentTimeMillis() - time;
		//End timer

		//Output results
		System.out.printf("\nOptimal distance: " + df.format(min));

		System.out.printf(" for path 0");
		for(int j = 0; j < nodes - 1; j++){
			System.out.printf(" %d", minOrder[j]);
		}
		System.out.println(" 0");
		System.out.printf("Runtime for optimal TSP   : %d milliseconds\n", runtime);
	}
}