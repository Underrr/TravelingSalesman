/*
* Quicksort.java
* 
* Author: Derrick Lee|dtl4734
*
* Contains the methods required to utilize quicksort.
* 
*/

import java.util.List;
import java.util.ArrayList;

public class Quicksort {

	/* Return arrays of size <= 1 (sorted). Else, pivot at the middle of the
	   list. Copy smaller elements in less; larger elements into greater.
	   Recursive sort left and right halves and merge results into a single
	   list.
	*/

    /*
    * Quicksort algorithm to sort a list of edges
    *
    * Arguments:
    *   list: the list to be sorted
    *
    * Returns:
    *   the sorted list
    * 
    */
	public static ArrayList<Edge> quicksort(ArrayList<Edge> list) {

		if (list.size() <= 1) {
			return list;
		}

		int middle = (int) Math.ceil((double) list.size() / 2);
    	Edge pivot = list.remove(middle);

		ArrayList<Edge> less = new ArrayList<Edge>();
		ArrayList<Edge> greater = new ArrayList<Edge>();

		for (Edge e : list) {
			if (e.compare(pivot) <= 0) {
				less.add(e);
			} else {
				greater.add(e);
			}
		}

		return concatenate(quicksort(less), pivot, quicksort(greater));
	}

	/*
    * Combines the two separated lists and the pivot back into a single list
    * 
    * Arguments:
    *   list1: the first list to be merged
    *   pivot: the pivot value from quicksort. Concatenated second
    *   list2: the second list to be merged. Concatenated third
    *
    * Returns:
    *   The concatenated list
    */
	public static ArrayList<Edge> concatenate(ArrayList<Edge> list1, Edge pivot, ArrayList<Edge> list2) {
        ArrayList<Edge> list = new ArrayList();
		list.addAll(list1);
		list.add(pivot);
		list.addAll(list2);
		return list;
	}


}