package fordFulkerson;

import java.util.List;

import static java.lang.Math.min;

public class FordFulkerson extends NetworkFlow {

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
     * the graph.
     *
     * @param n - The number of nodes in the graph including s and t.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n and t != s
     */
    public FordFulkerson(int n, int s, int t) {
        super(n, s, t);
    }

    // Performs the Ford-Fulkerson method applying a depth first search as
    // a means of finding an augmenting path.
    @Override
    public void solve() {
        // Find max flow by adding all augmenting path flows.
        for (long f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
            visitedToken++;
            maxFlow += f;
        }
    }

    private long dfs(int node, long flow) {
        // At sink node, return augmented path flow.
        if (node == t) return flow;

        // Mark the current node as visited.
        visited[node] = visitedToken;

        List<Edge> edges = graph[node];
        for (Edge edge : edges) {
            if (edge.remainingCapacity() > 0 && visited[edge.to] != visitedToken) {
                long bottleNeck = dfs(edge.to, min(flow, edge.remainingCapacity()));

                // If we made it from s -> t (a.k.a bottleNeck > 0) then
                // augment flow with bottleneck value.
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }
}
