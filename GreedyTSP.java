/*
* GreedyTSP.java
* 
* Author: Derrick Lee|dtl4734
*
* Generates an approximately optimal tour by continually choosing an
* that has the lowest cost of those edges that remain. If the number 
* of cities is less than or equal to 10, a list of the x, y coordinates,
* both adjacency matrices, as well as a list of the edges from the greedy
* graph that were used to compute the solution.
* 
*/

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.text.DecimalFormat;

public class GreedyTSP {

    private static int nodes;
    private static int seed;
    static DecimalFormat df = new DecimalFormat("0.00");


    /*
    * Generates an approximately optimal tour by continually choosing an edge that has the
    * lowest cost of those edges that remain/
    * 
    */
    public static void greedy(Graph greed, Graph original){

        ArrayList<Edge> edges = Quicksort.quicksort(original.getEdges());
        PathCompression p = new PathCompression(nodes);

        for (int i = 0; i < edges.size(); i++){
            Edge e = edges.get(i);
            // End loop if enough edges have been added
            if (greed.getEdgeCount() >= nodes) {
                break;
            } else {
                int r1 = p.find(e.row);
                int r2 = p.find(e.column);
                // Check for invalid edges (too many edges per vertex) or
                // check if the edge creates a cycle, but is NOT the final edge
                if ((greed.invalidEdge(e.row) || greed.invalidEdge(e.column)) ||
                            !greed.lastEdge() && PathCompression.createsCycle(r1, r2)) {
                    continue;
                } else {
                    //Union subtrees and update greedy graph.
                    p.union(r1, r2);
                    greed.addEdge(e);
                    greed.addEdgeWeight(e.row, e.column, original.getWeight(e.row, e.column));
                }
            }
        }
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
            System.out.println("Usage: java GreedyTSPnodes seed");
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

        Graph original = new Graph(nodes);
        original.generateCoords((long)seed);
        original.generateAM();
        original.generateEdges(original);
        Graph greed = new Graph(nodes);
        greed.resetAM();
        //Begin timer and algorithm
        long time = System.currentTimeMillis();
        greedy(greed, original);
        long runtime = System.currentTimeMillis() - time;

        //End timer

        //Check for situational result outputs
        if (nodes <= 10) {

            original.printVertices();
            original.printAM();
            System.out.printf("Greedy graph:\n");
            greed.printAM();
            ArrayList<Edge> edgeTour = greed.getEdges();
            System.out.println("Edges of tour from greedy graph:");
            for(int i = 0; i < edgeTour.size(); i++){
                Edge e = edgeTour.get(i);
                System.out.printf("%d %d weight = %s\n", e.column, e.row, df.format(e.getWeight()));
            }
        }

        double distanceTraveled = greed.edgeDistance();
        ArrayList<Integer> solution = greed.dfsInit();

        //Output results
        System.out.printf("\nDistance using greedy: %s for path ", df.format(distanceTraveled));
        for (Integer i : solution){
            System.out.printf("%d ", i);
        }
        System.out.println("");
        System.out.printf("Runtime for greedy TSP   : %d milliseconds\n", runtime);
    
    }
}