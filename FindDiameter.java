package rsn170330.sp12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import rbk.BFSOO;
import rbk.Graph;
import rbk.Graph.Vertex;

/**
 * CS 5V81: Implementation of Data Structures and Algorithms
 * Short Project SP12: Breadth First Search and Enumeration
 * @author Rahul Nalawade (rsn170330)
 * @author Pooja Srinivasan (pxs176230)
 * 
 * Date: November 25, 2018
 */

/**
 * Implement the algorithm to find the diameter of a tree using the algorithm 
 * discussed in class, that runs BFS twice. 
 * 
 * Code this algorithm without modifying Graph.java and BFSOO.java, using them 
 * from package rbk. 
 * 
 * // assume that g is an acyclic, connected graph (tree).
 * int diameter(Graph g) { ... }
 */
public class FindDiameter {
	
	/**
	 * Finds the diameter of an undirected Graph (acyclic and connected)/ Tree.
	 * 
	 * @param g the input graph
	 * @return the diameter of the Tree
	 */
	public static int diameter(Graph g) {
		// diameter: max distance between two nodes in a Tree
		int diameter = 0;
		
		Vertex src = g.getVertex(1);
		
		// 1st run of Breadth-First-Search with first vertex as root
		BFSOO bfs = BFSOO.breadthFirstSearch(g, src);
		
		// LI: finding farthest vertex from the src
		for (Vertex u : g) {
			int d = bfs.getDistance(u);
			
			if (diameter < d) {
				diameter = d;
				src = u;
			}
		}
		
		// 2nd run of Breadth-First-Search with new root
		bfs = BFSOO.breadthFirstSearch(g, src);
		
		// LI: diameter = distance of the farthest vertex from the new src
		for (Vertex u : g) {
			diameter = Math.max(diameter, bfs.getDistance(u));
		}
		
		return diameter;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		String string = "8 7   1 2 2   1 3 3   2 4 5   2 5 4   4 6 1   5 7 7   7 8 11 0";
		Scanner in;
		
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
		
		// Read graph from input
		Graph g = Graph.readGraph(in); // NOTE: read the graph as 'undirected' graph
		g.printGraph(false);
		
		System.out.println("Diameter: " + diameter(g));
	}
}

/** 
EXPECTED OUTPUT: 
____________________________________________________________
Graph: n: 8, m: 7, directed: false, Edge weights: false
1 :  (1,2) (1,3)
2 :  (1,2) (2,4) (2,5)
3 :  (1,3)
4 :  (2,4) (4,6)
5 :  (2,5) (5,7)
6 :  (4,6)
7 :  (5,7) (7,8)
8 :  (7,8)
____________________________________________________________
Diameter: 5
 
 */

