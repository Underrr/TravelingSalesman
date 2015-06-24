/*
* BinaryHeap.java
* 
* Author: Derrick Lee|dtl4734
*
* Contains all the basic methods needed for the creating and accessing
* a BinaryHeap as well as several get methods. 
* 
*/

import java.util.ArrayList;
import java.util.Arrays;

public class BinaryHeap{
	
	private ArrayList<Edge> heap;
	private int[] location;

	/*
    * Constructor for the BinaryHeap object
    *
    * Arguments:
    *	n: the number of nodes/vertices for the graph
    * 
    */
	public BinaryHeap(int n, Graph g){
		heap = new ArrayList<Edge>(n);
		heap.add(new Edge(-1, -1, g));
		location = new int[n];
		for(int i = 0; i < n; i++){
			location[i] = -1;
		}
	}

	//Returns the array containing each node's location in the heap
	public int[] getLocation(){
		return location;
	}

	//Swaps 2 nodes within the heap
	public void swap(int i, int j){
		Edge temp = heap.get(i);

		heap.set(i, heap.get(j));
		heap.set(j, temp);

		//swap edges
		int iIndex = heap.get(i).row;
		int jIndex = heap.get(j).row;

		//swap indices
		int t = location[iIndex];
		location[iIndex] = location[jIndex];
		location[jIndex] = t;
	}

	//Moves the given node up
	public void swim(int i){
		while((i > 1) && (heap.get(i).compare(heap.get(i/2)) > 0)){
			swap(i, i/2);
			i = i/2;
		}
	}

	//Sinks the given node down
	public void sink(int i){
		int size = heap.size() - 1;
		while(size > i * 2){
			int j = i * 2;
			if((j < size) && (heap.get(j).compare(heap.get(j + 1)) < 0)){
				j++;
			}
			if(heap.get(i).compare(heap.get(j)) >= 0){
				break;
			}
			swap(i, j);
			i = j;
		}
	}

	//Adds the given edge into the heap
	public void add(Edge input){
		heap.add(input);
		int end = heap.size() - 1;
		location[input.row] = end;
		swim(end);
	}

	//Removes and returns the root
	public Edge remove(){
		Edge edge = heap.get(1);
		int end = heap.size() - 1;
		swap(1, end);
		heap.remove(end);
		location[edge.row] = -1;
		sink(1);
		return edge;
	}

	//Checks if the heap is empty
	public boolean isEmpty(){
		return heap.size() == 1;
	}

	//Checks node priority and updates accordingly
	public void update(Edge edge){
		if(edge.compare(heap.get(location[edge.row])) > 0){
			heap.set(location[edge.row], edge);
			swim(location[edge.row]);
		}
	}

}