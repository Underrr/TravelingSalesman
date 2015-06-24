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

    private LinkedHashMap<Integer,Integer> coords;
    private double adjacencyMatrix[][];
    private static int vertices;
    DecimalFormat df = new DecimalFormat("0.00");
    private static boolean visited[];
    private ArrayList<Edge> edges;
    private static int edgeCount;
    private boolean[][] mst;
    private LinkedHashMap<Integer, Integer> traversal;

    /*
    * Constructs a new graph
    *
    * Arguments:
    *   n: the number of nodes in the graph
    *
    */
    public Graph(int n){
        adjacencyMatrix = new double[n][n];
        coords = new LinkedHashMap<Integer,Integer>(n);
        vertices = n;
        edges = new ArrayList<Edge>();
        edgeCount = 0;
        traversal = new LinkedHashMap<Integer,Integer>();
        mst = new boolean[n][n];
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
    * Get the weight of the edge between two given vertices
    *
    * Arguments:
    *   x: the row corresponding to the first vertex
    *   y: the column corresponding to the second vertex
    *
    * Returns:
    *   the weight between vertex x and vertex y
    * 
    */
    public double getWeight(int row, int col) {
        return adjacencyMatrix[row][col];
    }

    /*
    * Generates coordinates for the graph using the given seed
    *
    * Arguments:
    *   seed: the given seed for the Random()
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

    //Generates the edges for the graph
    public void generateEdges(Graph g) {
        for (int row = 0; row <= vertices-1; row++) {
            for (int column = 0; column <= row-1; column++) {
                addEdge(row, column, g);
            }
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
    private static double distance(Map.Entry<Integer,Integer> node1, Map.Entry<Integer,Integer> node2) {
    
        int x1 = node1.getKey();
        int x2 = node2.getKey();
        int y1 = node1.getValue();
        int y2 = node2.getValue();
        double xdif = (double) x1 - x2;
        double ydif = (double) y1 - y2;
        return Math.sqrt(xdif * xdif + ydif * ydif);
    }

    /*
    * Edits the edge weight in the adjacency matrix
    *
    * Arguments:
    *   x: the first vertex's corresponding row
    *   y: the second vertex's corresponding column
    *   weight: the weight of the edge
    * 
    */
    public void addEdgeWeight(int x, int y, double weight) {
        adjacencyMatrix[x][y] = weight;
        adjacencyMatrix[y][x] = weight;
    }

    /*
    * Adds a new edge to the list of edges. Increments counter by 1
    *
    * Arguments:
    *   x: the first vertex's corresponding row
    *   y: the second vertex's corresponding column
    *
    */
    public void addEdge(int x, int y, Graph g) {
        edges.add(new Edge(x, y, g));
        edgeCount++;
    }

    /*
    * Adds a new edge to the list of edges. Increments counter by 1
    *
    * Arguments:
    *   e: the edge object that we want to add to the list
    * 
    */
    public void addEdge(Edge e) {
        edges.add(e);
        edgeCount++;
    }

    //Returns the number of edges in the graph
    public int getEdgeCount() {
        return edgeCount;
    }

    //Returns the list of edges in the graph
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    //Sets all entries of the adjacency matrix to 0
    public void resetAM() {
        for (int row = 0; row < adjacencyMatrix.length; row++) {
            for (int col = 0; col < adjacencyMatrix.length; col++) {
                adjacencyMatrix[row][col] = 0.0;
            }
        }
    }

    /*
    * Checks to see if a vertex can have another edge. In the final solution,
    * each vertex can only have 2 edges. More than 2 edges would result in a
    * non-optimal path
    *
    * Arguments:
    *   vertex: the vertex that we want to check
    *
    *
    * Returns:
    *   true if the edge cannot have another edge
    *   false if the edge is still open
    * 
    */
    public boolean invalidEdge(int vertex) {
        int count = 0;
        for (int i = 0; i < adjacencyMatrix[vertex].length; i++){
            double edge = adjacencyMatrix[vertex][i];
            if (edge > 0)
                count++;
        }
        return count >= 2;
    }

    /*
    * Check if the last edge has been added to the graph.
    *
    * Returns:
    *   true if the solution is now complete
    *   false if we still have nodes to visit and edges to add
    * 
    */
    public boolean lastEdge() {
        return getEdgeCount() + 1 >= adjacencyMatrix.length;
    }

    /*
    * Initializes the variables needed for dfs then calls dfs starting
    * and ending at node 0
    *
    */
    public ArrayList<Integer> dfsInit() {
        visited = new boolean[adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++)
            visited[i] = false;
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(0);
        dfs(0, path);
        path.add(0);
        return path;
    }

    /*
    * Simple depth first search
    *
    * Arguments:
    *   node: the starting node
    *   path: the current path
    *
    * Returns:
    *   the path traversed
    * 
    */
    private ArrayList<Integer> dfs(int node, ArrayList<Integer> path) {
        for (int i = 1; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[node][i] > 0 && !visited[i]) {
                path.add(i);
                visited[i] = true;
                dfs(i, path);
            }
        }
        return path;
    }

    /*
    * Finds the total distance traveled for the problem
    *
    * Returns:
    *   total distance traveled by the salesman   
    * 
    */
    public double edgeDistance() {
        double distance = 0;
        for (int i = 0; i < edges.size(); i++){
            Edge e = edges.get(i);
            distance += e.getWeight();
        }
        return distance;
    }

    //Returns a list of the current neighbors
    public ArrayList<Edge> getNeighbors(int k, Graph g){
        ArrayList<Edge> e = new ArrayList<Edge>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[k][i] > 0) {
                e.add(new Edge(i, k, g));
            }
        }
        return e;
    }

    //Add an edge to the minimum spanning tree
    public void addMstEdge(Edge e){
        mst[e.row][e.column] = true;
        mst[e.column][e.row] = true;
    }

    /*
    * Checks if the given node is already in the MST
    *
    * Arguments:
    *   n: the node to look for
    *
    * Returns:
    *   true if the node is in the MST
    *   false if the node isn't in the MST
    * 
    */
    public boolean validMstNode(int n){
        for(int i = 0; i < mst.length; i++){
            if(mst[n][i]){
                return true;
            }
        }
        return false;
    }

    public LinkedHashMap<Integer, Integer> preorder(){
        preorder(0, -1);
        return traversal;
    }

    //Recursively processes the given node and all it's children in sorted order
    public void preorder(int n, int i){
        traversal.put(n, i);
        for(Integer neighbor : getMstNeighbors(n)){
            preorder(neighbor, n);
        }
    }

    //Get all the neighbors of the current MST node
    private ArrayList<Integer> getMstNeighbors(int n){
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        for (int i = 1; i < adjacencyMatrix.length; i++) {
            if (mst[n][i] && !traversal.containsKey(i)) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    //Get the traversal
    public LinkedHashMap<Integer, Integer> getTraversal(){
        return traversal;
    }

    //Returns total cost of MST
    public double cost(){
        double c = 0;
        for(int i = 0; i < adjacencyMatrix.length; i++){
            for(int j = 0; j < i; j++){
                //check if edge is in MST
                if(mst[i][j]){
                    c += adjacencyMatrix[i][j];
                }
            }
        }
        return c;
    }

    //Returns the MST
    public boolean[][] getMst(){
        return mst;
    }

    /*
    * Finds the total distance traveled for the problem (MST only)
    *
    * Returns:
    *   total distance traveled by the salesman   
    * 
    */
    public double traversalDistance() {
        double distance = 0;
        ArrayList<Integer> list = new ArrayList(traversal.keySet());
        for (int i = 0; i < list.size()-1; i++) {
            distance += adjacencyMatrix[list.get(i)][list.get(i+1)];
        }
        distance += adjacencyMatrix[list.get(0)][list.get(list.size()-1)];
        return distance;
    }
    public static void main(String args[]){}
}