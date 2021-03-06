## Short Project SP12: Breadth First Search and Enumeration

1. Implementation of an Algorithm to find Diameter of a Tree (represented as 
   a Graph) using BFS.
2. Implementation of an Algorithm to find Odd-Length Cycle in a Tree. 
3. Implementation of Enumeration of all Paths in a connected Graph. 
4. Enumeration of all permutation with alternate parities in lexicographic 
   order. 

#### Author
* [Rahul Nalawade](https://github.com/rahul1947)

#### Date
* November 25, 2018

_______________________________________________________________________________
### Problems:

#### A. Team Task - Breadth First Search: 

**Problem 1.** 
   Implement the algorithm to find the diameter of a tree using the algorithm 
   discussed in class, that runs BFS twice. Code this algorithm without 
   modifying Graph.java and BFSOO.java, using them from package rbk.
```
   // assume that g is an acyclic, connected graph (tree).
   int diameter(Graph g) { ... }  
```
**Solution:** [FindDiameter.java](https://github.com/rahul1947/SP12-Breadth-First-Search-and-Enumeration/blob/master/FindDiameter.java) 
```
Sample Output: 
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
```

#### B. Optional tasks (individual) - Breadth First Search: 

**Problem 2.**
   Implement a BFS based algorithm to output an odd-length cycle of a graph. 
   If the graph is bipartite, it returns null. 
   The problem can be solved as follows.
   1. Run BFS on the graph.  If it is not connected, you should run BFS on 
   each component.
   2. If there is any edge e = (u,v) with u.distance = v.distance, then the 
   graph has an odd-length cycle. 
   Otherwise, it is bipartite, and has no odd-length cycles.
   3. Once you find such an edge, an odd-length cycle can be found by going 
   up the BFS tree using parent links, from u and v in tandem, until reaching 
   their least common ancestor in the BFS tree containing u and v. 
   
   Code this algorithm without modifying Graph.java and BFSOO.java, using them
   from package rbk. 
   Make sure that the returned list has the vertices in order along the cycle.
```
   // do not assume that g is connected
   List<Vertex> oddCycle(Graph g) { ... }  
```
**Solution:** -

#### C. Optional tasks (individual) - Enumeration: 

The following methods are related to Enumerate.java. Changing the signature 
of select from the following:
```
   public boolean select(T item) { return true; }
```

to the following, makes it easier to write approvers:
```
   // arr[0..index-1] is frozen, and item is being considered for arr[index].
   public boolean select(T[] arr, int index, T item) { return true; }	
```

**Problem 3.** 
   Implement the algorithm to enumerate all paths from s (source) to t (sink) 
   in a DAG. Code this algorithm by using the permute method of Enumerate.java,
   and writing an Approver in a new class, that extends Enumerate.Approver.

**Solution:** [EnumeratePath.java](https://github.com/rahul1947/SP12-Breadth-First-Search-and-Enumeration/blob/master/EnumeratePath.java) 
```
Sample Output: 
____________________________________________________________
Graph: n: 6, m: 7, directed: true, Edge weights: false
1 :  (1,2) (1,3)
2 :  (2,4)
3 :  (3,4) (3,5)
4 :  (4,6)
5 :  (5,6)
6 : 
____________________________________________________________
1 2 4 6 
1 3 4 6 
1 3 5 6 

Number of Paths: 3
Time: 2 msec.
Memory: 1 MB / 117 MB.
```

**Problem 4.**
   Enumerate all permutations of 1..n, in which no two consecutive integers in
   the chosen permutations are both odd, or, are both even.
   If n = 4, the permutations visited are:
```
   1 2 3 4
   1 4 3 2
   2 1 4 3
   2 3 4 1
   3 2 1 4
   3 4 1 2
   4 1 2 3
   4 3 2 1
```
   All other permutations have 2 consecutive odd numbers or 2 consecutive even 
   numbers. Avoid exploring any permutation that starts with 1 3 right at the 
   time when 3 is considered for the second position, instead of waiting till 
   visit() is reached.
   
   This problem can be solved by writing a suitable Approver, or writing the 
   enumerate code from scratch.

**Solution:** - 

_______________________________________________________________________________
   