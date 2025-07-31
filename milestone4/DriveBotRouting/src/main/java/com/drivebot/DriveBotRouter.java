package com.drivebot;

import com.drivebot.algorithms.AStar;
import com.drivebot.algorithms.BellmanFord;
import com.drivebot.algorithms.Dijkstra;
import com.drivebot.model.Edge;
import com.drivebot.model.Graph;
import com.drivebot.model.Node;
import com.drivebot.utils.GraphUpdater;
import com.drivebot.utils.Heuristic;

import java.util.*;


public class DriveBotRouter {

    private final Graph graph;
    private final Heuristic heuristic;
    private final boolean useAStar;

    public DriveBotRouter(Graph graph, Heuristic heuristic, boolean useAStar) {
        this.graph = graph;
        this.heuristic = heuristic;
        this.useAStar = useAStar;
    }


    public List<Node> computeRoute(Node start, Node goal) {
        GraphUpdater.updateGraphWeights(graph);
        applyFreewayExclusion();

        if (hasDeadEndPath(start, goal)) {
            System.out.println("[DriveBotRouter] No viable route — dead end detected.");
            return Collections.emptyList();
        }

        if (graph.hasNegativeEdge()) {
            System.out.println("Using Bellman–Ford Search");
            return BellmanFord.findPath(graph, start, goal);
        }

        if (useAStar && heuristic != null) {
            System.out.println("Using A* Search");
            return AStar.findPath(graph, start, goal, heuristic);
        }

        System.out.println("Using Dijkstra Search");
        return Dijkstra.findPath(graph, start, goal);
    }

    private void applyFreewayExclusion() {
        for (Edge edge : graph.getAllEdges()) {
            if (isFreeway(edge)) {
                edge.setWeight(999999);
            }
        }
    }

    private boolean isFreeway(Edge edge) {
        String from = edge.getFrom().getId().toLowerCase();
        String to = edge.getTo().getId().toLowerCase();
        return from.contains("i-") || to.contains("i-") || from.contains("freeway") || to.contains("freeway");
    }

    private boolean hasDeadEndPath(Node start, Node goal) {
        Set<Node> visited = new HashSet<>();
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            if (!visited.add(current)) continue;

            List<Edge> outgoing = graph.getEdges(current);
            if (current != goal && (outgoing == null || outgoing.isEmpty())) {
                return true;
            }

            for (Edge e : outgoing) {
                stack.push(e.getTo());
            }
        }
        return false;
    }
}
