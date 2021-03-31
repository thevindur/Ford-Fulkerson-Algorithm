package fordFulkerson;

import java.util.List;

import static java.lang.Math.min;

public class FordFulkerson extends NetworkFlow {

    public FordFulkerson(int numberOfNodes, int sourceNode, int targetNode) {
        super(numberOfNodes, sourceNode, targetNode);
    }

    // Performs the Ford-Fulkerson method applying a depth first search as a means of finding an augmenting path.
    @Override
    public void solve() {
        // Find max flow by adding all augmenting path flows.
        for (long f = dfs(sourceNode, infinity); f != 0; f = dfs(sourceNode, infinity)) {
            visitedToken++;
            maxFlow += f;
        }
    }

    private long dfs(int node, long flow) {
        // At target node, return augmented path flow.
        if (node == targetNode) return flow;

        // Mark the current node as visited.
        visited[node] = visitedToken;

        List<Edge> edges = graph[node];
        for (Edge edge : edges) {
            if (edge.remainingCapacity() > 0 && visited[edge.to] != visitedToken) {
                long bottleNeck = dfs(edge.to, min(flow, edge.remainingCapacity()));

                // If we made it from s -> t (a.k.a bottleNeck > 0) then augment flow with bottleneck value.
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }
}
