/*
* PathCompression.java
* 
* Author: Derrick Lee|dtl4734
*
* Contains the methods for union-find and path compression.
* 
*/
public class PathCompression{
	
	private int x[];

	/*
    * Constructor for PathCompression object
    *
    * Arguments:
    *	n: the number of nodes
    * 
    */
	public PathCompression(int i){
		x = new int[i];
		for(int j = 0; j < i; j++){
			x[j] = -1;
		}
	}
	/*
    * 
    * Finds the root and does path compression
    *
    *
    * Returns:
    * 	parent value
    * 
    */
	public int find(int i){
		if(x[i] < 0)
			return i;
		else{
			x[i] = find(x[i]);
			return x[i];
		}
	}
	/*
    * Adds two arrays together
    *
    * Arguments:
    *	i: index of the first array
    * 	j: index of the second array
    *
    */
	public void union(int i, int j){
		if(x[i] > x[j])
			x[i] = j;
		else{
			if(x[i] == x[j])
				x[i]--;
			x[j] = i;
		}
	}

	/*
	* Checks to see if the union creates a cycle by checking if i and j 
	* are both valid and equal.
	* 
	* Arguments:
	* 	i: 	first node
	*	j: 	second node
	*
	* Returns:
	* 	true if the union has created a cycle. false if it has not
	*/
	public static boolean createsCycle(int i, int j){
		return i > 0 && j > 0 && i == j;
	}
}