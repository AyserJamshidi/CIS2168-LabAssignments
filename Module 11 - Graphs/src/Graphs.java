import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

import java.util.*;

public class Graphs {
	public static void main(String[] args) {
		Graph<Integer, String> breadth = new SparseGraph<>();
		breadth.addEdge("A", 0, 1);
		breadth.addEdge("B", 0, 3);
		breadth.addEdge("C", 1, 2);
		breadth.addEdge("D", 3, 2);
		breadth.addEdge("E", 2, 9);
		breadth.addEdge("F", 2, 8);
		breadth.addEdge("G", 1, 4);
		breadth.addEdge("H", 1, 6);
		breadth.addEdge("I", 1, 7);
		breadth.addEdge("J", 4, 5);
		breadth.addEdge("K", 4, 6);
		breadth.addEdge("L", 4, 7);
		breadth.addEdge("M", 7, 6);

		System.out.println("Breadth: " + breadthFirstSearch(breadth, 0));

		Graph<Integer, String> depth = new SparseGraph<>();
		depth.addEdge("A", 0, 1);
		depth.addEdge("B", 0, 3);
		depth.addEdge("C", 0, 4);
		depth.addEdge("D", 0, 2);
		depth.addEdge("E", 1, 3);
		depth.addEdge("F", 1, 4);
		depth.addEdge("G", 3, 4);
		depth.addEdge("H", 2, 6);
		depth.addEdge("I", 2, 5);
		depth.addEdge("J", 5, 6);

		System.out.println("Depth: " + depthFirstSearch(depth, 0));
	}

	public static List<Integer> breadthFirstSearch(Graph<Integer, String> graph, int startingVertex) {
		Set<Integer> identified = new HashSet<>();
		List<Integer> visited = new ArrayList<>();
		Queue<Integer> queue = new LinkedList<>();

		queue.add(startingVertex);

		while (!queue.isEmpty()) {
			int curVertex = queue.poll();

			visited.add(curVertex);
			for (int i : graph.getNeighbors(curVertex))
				if (!identified.contains(i) && !visited.contains(i)) {
					identified.add(i);
					queue.add(i);
				}
		}

		return visited;
	}

	public static List<Integer> depthFirstSearch(Graph<Integer, String> graph, int startingVertex) {
		Stack<Integer> discovery = new Stack<>();
		List<Integer> order = new LinkedList<>();

		discovery.push(depthFirstSearch(graph, startingVertex, discovery, order));

		System.out.println("discovery: " + discovery);

		return order;
	}

	private static int depthFirstSearch(Graph<Integer, String> graph, int startingVertex,
	                                    Stack<Integer> discovery, List<Integer> order) {
		discovery.push(startingVertex); // Starting point

		for (int vertex : graph.getNeighbors(startingVertex)) // For every neighboring vertex
			if (!discovery.contains(vertex)) // if we haven't discovered it yet
				depthFirstSearch(graph, vertex, discovery, order);

		order.add(startingVertex); // Add the vertex to the order as we've found everything else

		return startingVertex; // We're back at the starting vertex, return it as the last discovery
	}
}
