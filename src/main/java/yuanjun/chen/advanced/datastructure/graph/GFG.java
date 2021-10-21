package yuanjun.chen.advanced.datastructure.graph;
// Java program to check if there is a cycle in
// directed graph using BFS.

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

class GFG {

    // Class to represent a graph
    static class Graph {
        int V; // No. of vertices'

        // Pointer to an array containing adjacency list
        Vector<Integer>[] adj;

        @SuppressWarnings("unchecked")
        Graph(int V) {
            // ConstructorØ
            this.V = V;
            this.adj = new Vector[V];
            for (int i = 0; i < V; i++)
                adj[i] = new Vector<>();
        }

        // function to add an edge to graph
        void addEdge(int u, int v) {
            adj[u].add(v);
        }

        // Returns true if there is a cycle in the graph
        // else false.

        // This function returns true if there is a cycle
        // in directed graph, else returns false.
        boolean isCycle() {

            // Create a vector to store indegrees of all
            // vertices. Initialize all indegrees as 0.
            int[] in_degree = new int[this.V];
            Arrays.fill(in_degree, 0);

            // Traverse adjacency lists to fill indegrees of
            // vertices. This step takes O(V+E) time
            for (int u = 0; u < V; u++) {
                for (int v : adj[u])
                    in_degree[v]++;
            }

            // Create an queue and enqueue all vertices with
            // indegree 0
            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < V; i++)
                if (in_degree[i] == 0)
                    q.add(i);

            // Initialize count of visited vertices
            int cnt = 0;

            // Create a vector to store result (A topological
            // ordering of the vertices)
            Vector<Integer> top_order = new Vector<>();

            // One by one dequeue vertices from queue and enqueue
            // adjacents if indegree of adjacent becomes 0
            while (!q.isEmpty()) {
                // Extract front of queue (or perform dequeue)
                // and add it to topological order
                int u = q.poll();
                top_order.add(u);
                // Iterate through all its neighbouring nodes
                // of dequeued node u and decrease their in-degree
                // by 1
                for (int itr : adj[u]) {
                    if (--in_degree[itr] == 0) {
                        q.add(itr);
                    }
                }
                cnt++;
            }

            // Check if there was a cycle
            return cnt != this.V;
        }
    }

    // Driver Code
    public static void main(String[] args) {

        // Create a graph given in the above diagram
        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(3, 4);
        g.addEdge(4, 5);

        if (g.isCycle())
            System.out.println("Yes");
        else
            System.out.println("No");
    }
}
 