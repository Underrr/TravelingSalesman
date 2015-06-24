import static java.util.Arrays.asList;
import java.util.ArrayList;
import java.util.List;

public class Edge{
	public int row;
	public int column;
	public Graph graph;

	/*
    * Constructor for edge objects
    *
    * Arguments:
    *   x: first vertex, corresponds to the row in the adjacency matrix
    *   y: second vertex, corresponds to the column in the adjacency matrix
    *   g: the graph which the edge is a part of
    * 
    */
	public Edge (int x, int y, Graph g) {
		row = x;
		column = y;
		graph = g;
	}


    /*
    * Compares two edges. Comparison priority is weight > column > row
    *
    * Arguments:
    *   e: the edge to be compared with this edge
    *
    * Returns:
    *   a negative integer, zero, or a positive integer if e is greater than, equal to, or less than 
    *   this object
    * 
    */	/* Compare by weight, then column, then row. */
	public int compare(Edge e) {
        int value1 = Double.compare(graph.getWeight(row,column), (e.graph).getWeight(e.row,e.column));
        if (value1 == 0) {
            int value2 = this.column - e.column;
            if (value2 == 0) {
                return this.row - e.row;
            } else {
                return value2;
            }
        } else {
            return value1;
        }
    }

    //Returns weight of this edge
    public double getWeight() {
    	return graph.getWeight(row, column);
    }

}