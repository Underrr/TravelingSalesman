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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Graph{

	private static LinkedHashMap<Integer,Integer> coords;
	private double adjacencyMatrix[][];
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
        coords = new LinkedHashMap<Integer,Integer>(n);
		vertices = n;
	}

	//returns the number of nodes in the graph
	public int numberOfNodes(){
		return vertices;
	}

    //returns the hashmap containing the coordinates
    public LinkedHashMap<Integer,Integer> getCoords(){
        return coords;
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

		while(coords.size() < vertices){
			
			int x = xGen.nextInt(vertices);
			int y = yGen.nextInt(vertices);

			if(coords.containsKey(x))
				continue;
			else
				coords.put(x,y);
		}
	}

	//generates the adjacency matrix
	public void generateAM(){
        ArrayList<Map.Entry<Integer, Integer>> entries =
        new ArrayList<Map.Entry<Integer, Integer>>(coords.entrySet());
		
        for (int x = 0; x < entries.size(); x++) {
            for (int y = 0; y < entries.size(); y++) {
                double weight = distance(entries.get(x), entries.get(y));
                adjacencyMatrix[x][y] = weight;
                adjacencyMatrix[y][x] = weight;
            }
        }
	}

	//prints out all the vertices
	public void printVertices(){
        System.out.println("X-Y Coordinates:");

        int index = 0;
        for (Map.Entry<Integer, Integer> entry : coords.entrySet()) {
            System.out.format("v%d: (%d,%d) ", index, entry.getKey(), entry.getValue());
            index++;
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

    /*
    * Gets the distance between two vertices
    *
    * Arguments:
    *   node1: the first vertex
    *   node2: the second vertex
    *
    * Returns:
    *   the distance between node1 and node2
    * 
    */
    private static Double distance(Map.Entry<Integer,Integer> node1, Map.Entry<Integer,Integer> node2) {
    
        int x1 = node1.getKey();
        int x2 = node2.getKey();
        int y1 = node1.getValue();
        int y2 = node2.getValue();
        double xdif = (double) x1 - x2;
        double ydif = (double) y1 - y2;
        return Math.sqrt(xdif * xdif + ydif * ydif);
    }

	public static void main(String args[]){}
}