import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build { 

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
     if (vertex == null) return;

    Set<Vertex<String>> visited = new HashSet<>();
    dfsShort(vertex, k, visited);
}

private static void dfsShort(Vertex<String> v, int k, Set<Vertex<String>> visited) {
    if (v == null || visited.contains(v)) return;

    visited.add(v);

    if (v.data.length() < k) {
        System.out.println(v.data);
    }

    for (Vertex<String> next : v.neighbors) {
        dfsShort(next, k, visited);
    }

  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    if (vertex == null) return "";

    Set<Vertex<String>> visited = new HashSet<>();
    return dfsLongest(vertex, visited, "");
}

private static String dfsLongest(Vertex<String> v, Set<Vertex<String>> visited, String longest) {
    if (v == null || visited.contains(v)) return longest;

    visited.add(v);

    if (v.data.length() > longest.length()) {
        longest = v.data;
    }

    for (Vertex<String> next : v.neighbors) {
        longest = dfsLongest(next, visited, longest);
    }

    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    if (vertex == null) return;

    Set<Vertex<T>> visited = new HashSet<>();
    dfsSelf(vertex, visited);
}
  private static <T> void dfsSelf(Vertex<T> v, Set<Vertex<T>> visited) {
    if (v == null || visited.contains(v)) return;

    visited.add(v);

    if (v.neighbors.contains(v)) {
        System.out.println(v.data);
    }

    for (Vertex<T> next : v.neighbors) {
        dfsSelf(next, visited);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    if (start == null || destination == null) return false;
    if (start == destination) return true;

    Set<Airport> visited = new HashSet<>();
    return dfsAirport(start, destination, visited);
}

private static boolean dfsAirport(Airport current, Airport destination, Set<Airport> visited) {
    if (current == destination) return true;
    if (visited.contains(current)) return false;

    visited.add(current);

    for (Airport next : current.getOutboundFlights()) {
        if (dfsAirport(next, destination, visited)) {
            return true;
        }
    }

    return false;

  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    // return new HashSet<>();
     Set<T> visited = new HashSet<>();

    if (starting != null && graph.containsKey(starting)) {
        dfsGraph(graph, starting, visited);
    }

    Set<T> result = new HashSet<>();

    for (T node : graph.keySet()) {
        if (!visited.contains(node)) {
            result.add(node);
        }
    }

    return result;
}

private static <T> void dfsGraph(Map<T, List<T>> graph, T current, Set<T> visited) {
    if (current == null || visited.contains(current)) return;

    visited.add(current);

    List<T> neighbors = graph.get(current);
    if (neighbors == null) return;

    for (T next : neighbors) {
        dfsGraph(graph, next, visited);
    }

  }
}
