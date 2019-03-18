import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Balkrishna Patel
 * @userid bpatel66
 * @GTID 9032023192
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex.
     *
     * When deciding which neighbors to visit next from a vertex, visit the
     * vertices in the order presented in that entry of the adjacency list.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.List}, and
     * any classes that implement the aforementioned interfaces, as long as it
     * is efficient.
     *
     * The only instance of {@code java.util.Map} that you may use is the
     * adjacency list from {@code graph}. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
                                            Graph<T> graph) {
        /*if either the starting vertex or the graph is null, throw an
        exception*/
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The starting vertex and/or "
                    + "graph that was passed in is null.");
        }
        /*create a list for storing vertices as they are visited*/
        List<Vertex<T>> verticesList = new ArrayList<>();
        /*create a set of all the visited vertices so we do not add the same
        vertex to our resultant list twice*/
        Set<Vertex<T>> visitedSet = new HashSet<>();
        /*if the graph doesn't not contain the starting graph throw an
        exception*/
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("The start vertex that was "
                    + "passed in does not exist.");
        }
        /*return the result of the recursive method call*/
        rDepthFirstSearch(start, graph, verticesList, visitedSet);
        return verticesList;
    }

    /**
     * Private recursive helper method for the depthFirstSearch method.
     *
     * @param start starting/currenct vertex to perform DFS
     * @param graph to search through
     * @param verticesList result list of all the vertices
     * @param visitedSet set of visited vertices
     * @param <T> the generic typing of the data
     * @return list of vertices in the visited order
     */
    private static <T> void rDepthFirstSearch(Vertex<T> start, Graph<T> graph,
                                              List<Vertex<T>> verticesList,
                                              Set<Vertex<T>> visitedSet) {
        /*add the current vertex to the resulting list*/
        verticesList.add(start);
        /*add the current vertex to the visited set*/
        visitedSet.add(start);
        /*for each vertex distance pair adjacent to the current vertex*/
        for (VertexDistance<T> pair : graph.getAdjList().get(start)) {
            /*extract the vertex from the vertex/distance pair*/
            Vertex<T> currentVertex = pair.getVertex();
            /*check if the extracted vertex has not been visited and if the
            vertex is not null*/
            if (!visitedSet.contains(currentVertex) && currentVertex != null) {
                /*if the vertex has not been visited and if it is not null
                make perform the recursive call on the extracted vertex*/
                rDepthFirstSearch(currentVertex, graph, verticesList,
                        visitedSet);
            }
        }
    }



    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces, as long as it's efficient.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check that not all vertices have been visited.
     * 2) Check that the PQ is not empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null, or if start
     *  doesn't exist in the graph.
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                      Graph<T> graph) {
        /*if the starting vertex or the graph is null throw exception*/
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The starting vertex and/or "
                    + "graph that was passed in is null.");
        }
        /*if the graph does not contain the starting vertex throw an exception*/
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("The starting vertex that was "
                    + "passed in does not exist in the graph.");
        }
        /*create a map that will store each vertex and the cost to reach that
        vertex from the starting vertex*/
        Map<Vertex<T>, Integer> result = new HashMap<>();
        /*for each vertex in the graph*/
        for (Vertex<T> v : graph.getAdjList().keySet()) {
            /*add the starting vertex into the map with a distance of 0*/
            if ((v.equals(start))) {
                result.put(v, 0);
            } else {
                /*add the rest of the vertices into the map with the
                distances set to max integer*/
                result.put(v, Integer.MAX_VALUE);
            }
        }
        /*create the priority queue that the algorithm will use to determine
        which vertex to go to next*/
        PriorityQueue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();
        /*add the starting vertex distance pair to the priority queue*/
        priorityQueue.add(new VertexDistance<>(start, 0));
        /*create a set to store the visted vertices in so that we can
        terminate early if all the vertices have been visited*/
        Set<Vertex<T>> vistedSet = new HashSet<>();
        /*while not all the vertices have been visited and the priority queue
        is not empty*/
        while (vistedSet.size() < graph.getAdjList().keySet().size()
                && !priorityQueue.isEmpty()) {
            /*take the vertex with the min distance cost to it */
            Vertex<T> currVertex = priorityQueue.poll().getVertex();
            /*if the vertex has not been visited*/
            if (!vistedSet.contains(currVertex)) {
                /*add the vertex to the visited set*/
                vistedSet.add(currVertex);
                /*get a list all the adjacent vertex distance pairs to the
                vertex*/
                List<VertexDistance<T>> currentVertexAdjacencyList = graph
                        .getAdjList().get(currVertex);
                /*for each of the vertex distance pairs*/
                for (VertexDistance<T> vdp : currentVertexAdjacencyList) {
                    /*check if the aggregate distance that has been
                    calculated is less than the distance that is stored for
                    that vdp in the map*/
                    if (result.get(currVertex) + vdp.getDistance()
                            < result.get(vdp.getVertex())) {
                        /*sort the vertex in a variable */
                        Vertex<T> v = vdp.getVertex();
                        /*sort the newly calculated distance in a variable*/
                        int distance = vdp.getDistance() + result.
                                get(currVertex);
                        /*add the vertex and its new distance to the result
                        map*/
                        result.put(v, distance);
                        /*if the vertex has not been visited*/
                        if (!vistedSet.contains(v)) {
                            /*add the vertex and its distance to the priority
                             queue*/
                            priorityQueue.add(new VertexDistance<>(v, distance));
                        }
                    }
                }
            }
        }
        /*return the map with each vertex and the minimum cost to get to them
         from the start*/
        return result;
    }


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the {@code DisjointSet} and {@code DisjointSetNode} classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops into the MST.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        /*if the graph is null throw exception*/
        if (graph == null) {
            throw new IllegalArgumentException("The graph that was passed in "
                    + "is null.");
        }
        /*Create a set that will hold the edges that form a minimum spanning
        tree*/
        Set<Edge<T>> result = new HashSet<>();
        /*Create a priority queue and add all the edges in the graph to it*/
        PriorityQueue<Edge<T>> priorityQueue = new
                PriorityQueue<>(graph.getEdges());
        /*Create a disjoint set that will hold the vertices that are
        connected to which edge so we can prevent a cycle*/
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>(graph
                .getAdjList().keySet());
        /*while the size of the resulting map is less than 2 time the size of
         the number of vertices in the graph (because the graph is
         undirected) and the priority queue is not empty*/
        while (result.size() < 2 * (graph.getAdjList().keySet().size() - 1)
                && !priorityQueue.isEmpty()) {
            /*take the smallest edge from the priority queue*/
            Edge<T> minEdge = priorityQueue.poll();
            /*check to see if the edge is not a cycle*/
            if (disjointSet.find(minEdge.getU()) != disjointSet.find(minEdge
                    .getV())) {
                /*add the edge to the result*/
                result.add(minEdge);
                /*and the reverse of that edge to result*/
                result.add(new Edge<>(minEdge.getV(), minEdge.getU(), minEdge
                        .getWeight()));
                /*merge the two vertices so that we don't try and add them
                again to the result*/
                disjointSet.union(minEdge.getU(), minEdge.getV());
            }
        }
        /*if the result is not 2 x the number of vertices return null*/
        if (result.size() < 2 * (graph.getAdjList().keySet().size() - 1)) {
            return null;
        }
        /*return the result*/
        return result;
    }
}