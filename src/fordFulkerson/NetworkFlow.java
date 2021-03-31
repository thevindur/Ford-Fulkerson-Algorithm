package fordFulkerson;

import java.util.ArrayList;
import java.util.List;

public abstract class NetworkFlow {
    final int numberOfNodes, sourceNode, targetNode;
    static final long infinity = Long.MAX_VALUE / 2;    // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;

    // These variables are used in graph sub-routines to track whether a node has been visited or not.
    protected int visitedToken = 1;
    protected int[] visited;

    protected boolean solved;
    protected long maxFlow;
    protected List<Edge>[] graph;   // The adjacency list representing the flow graph.

    public NetworkFlow(int numberOfNodes, int sourceNode, int targetNode) {
        this.numberOfNodes = numberOfNodes;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        initializeEmptyFlowGraph();
        visited = new int[numberOfNodes];
    }

    // Constructs an empty graph with number of nodes including source node and target node.
    @SuppressWarnings("unchecked")
    private void initializeEmptyFlowGraph() {
        graph = new List[numberOfNodes];
        for (int i = 0; i < numberOfNodes; i++) graph[i] = new ArrayList<>();
    }

    //Adding an edge to the graph.
    public void addEdge(int from, int to, long capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Forward edge capacity <= 0");
        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);
        e1.residual = e2;
        e2.residual = e1;
        graph[from].add(e1);
        graph[to].add(e2);
    }

    public List<Edge>[] getGraph() {
        execute();
        return graph;
    }

    // Returns the maximum flow from the source node to the target node.
    public long getMaxFlow() {
        execute();
        return maxFlow;
    }

    // This ensures that solve() is only called once because it yields the same answer everytime.
    private void execute() {
        if (solved) return;
        solved = true;
        solve();
    }

    // Method to implement which solves the network flow problem.
    public abstract void solve();
}
