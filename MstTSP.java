/*
* MstTSP.java
* 
* Author: Derrick Lee|dtl4734
*
* Generates an approximately optimal tour by constructing the minimum spanning
* tree of the complete graph. If the number of cities is less than or equal to
* 10, a list of the x, y coordinates, the graph as an adjacency matrix of
* Euclidean weights, the minimum spanning tree used to find the approximate
* tour, the total weight of the MST, and the parent of each vertex after a pre-
* order traversal of the MST will be printed.
* 
*/

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.text.DecimalFormat;

public class MstTSP{

    private static Graph g;
    private static int nodes;
    private static int seed;
    static DecimalFormat df = new DecimalFormat("0.00");

    /*
    * Implementation of Prim's algorithm with a priority queue implemented as a binary heap
    */
    public static void mst(Graph g){
    	BinaryHeap f = new BinaryHeap(g.getAM().length, g);
    	int[] loc = f.getLocation();
    	for(Edge e : g.getNeighbors(0, g)){
    		if(loc[e.row] == -1){
    			f.add(e);
    		}else{
    			f.update(e);
    		}
    	}
    	while(!f.isEmpty()){
    		Edge edge = f.remove();
    		g.addMstEdge(edge);
    		for(Edge n : g.getNeighbors(edge.row, g)){
    			if(!g.validMstNode(n.row)){
    				if(loc[n.row] == -1){
    					f.add(n);
    				}else{
    					f.update(n);
    				}
    			}
    		}
    	}
    	g.preorder();
    }
    
    /**
    * Method: isInt
    *
    * Checks to see if the given string is an integer
    *
    * Arguments:
    *   s: string to be checked
    *
    * Returns:
    *   If s is an int, return true.
    *   If s is not an int, return false.
    *
    */
    public static boolean isInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

    public static void main(String[] args) {
        //Check input
        if(args.length != 2){
            System.out.println("Usage: java MstTSP nodes seed");
            System.exit(0);
        }
        if(!isInt(args[0]) || !isInt(args[1])){
            System.out.println("Command line args must be integers");
            System.exit(0);
        }
        
        nodes = (int) Integer.parseInt(args[0]);
        seed = (int) Integer.parseInt(args[1]);
        
        if(nodes <= 0){
            System.out.println("Number of vertices must be greater than 0");
            System.exit(0);
        }

        g = new Graph(nodes);
        g.generateCoords((long) seed);
        g.generateAM();
        g.generateEdges(g);
        //Begin timer and algorithm
        long time = System.currentTimeMillis();
        mst(g);
        long runtime = System.currentTimeMillis() - time;

        //End timer

        //Check for situational result outputs
        if (nodes <= 10) {
            g.printVertices();
            g.printAM();
        }

        if (nodes <= 10) {
        	System.out.printf("Minimum Spanning Tree:");
            System.out.printf("Adjacency matrix of graph weights:\n\n\t");

            int i;
            for (i = 0; i < nodes; i++) {
                System.out.printf("  %d\t", i);
            }
            System.out.println("\n");

            boolean[][] minTree = g.getMst();
            double adjacencyMatrix[][] = g.getAM();
            for (i = 0; i < nodes; i++) {
                System.out.printf("%d\t", i);
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                	double d = adjacencyMatrix[i][j];
                	if(!minTree[i][j]){
                		d = 0;
                	}
                    System.out.printf("%s\t", df.format(d));
                }
                System.out.println("\n");
            }
        }

        if(nodes <= 10){
        	double weight = g.cost();
        	System.out.println("\nTotal weight of mst: " + df.format(weight));
            System.out.println("");
        }

        LinkedHashMap<Integer, Integer> solution = g.getTraversal();

        if (nodes <= 10) {
            System.out.println("Pre-order traversal: ");
            for(Map.Entry<Integer, Integer> entry : solution.entrySet()){
            	System.out.printf("Parent of %d is %d\n", entry.getKey(), entry.getValue());
            }
        }

        //Output results
        System.out.printf("\nDistance using mst: %s for path ", df.format(g.traversalDistance()));
        for (Integer i : solution.keySet()){
            System.out.printf("%d ", i);
        }
        System.out.println("0");
        System.out.printf("Runtime for Mst TSP   : %d milliseconds\n", runtime);
    
    }
}