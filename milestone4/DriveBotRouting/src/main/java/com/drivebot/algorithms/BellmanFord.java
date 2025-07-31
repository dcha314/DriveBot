package com.drivebot.algorithms;

import com.drivebot.model.Edge;
import com.drivebot.model.Graph;
import com.drivebot.model.Node;

import java.util.*;

public class BellmanFord {

    public static List<Node> findPath(Graph graph, Node start, Node goal) {
        Map<Node, Double> distance = new HashMap<>();
        Map<Node, Node> predecessor = new HashMap<>();

        for (Node node : graph.getAllNodes()) {
            distance.put(node, Double.POSITIVE_INFINITY);
        }
        distance.put(start, 0.0);

        int V = graph.getAllNodes().size();

        for (int i = 1; i < V; i++) {
            for (Node u : graph.getAllNodes()) {
                for (Edge edge : graph.getEdges(u)) {
                    Node v = edge.getTo();
                    double weight = edge.getWeight();
                    if (distance.get(u) + weight < distance.get(v)) {
                        distance.put(v, distance.get(u) + weight);
                        predecessor.put(v, u);
                    }
                }
            }
        }

        for (Node u : graph.getAllNodes()) {
            for (Edge edge : graph.getEdges(u)) {
                Node v = edge.getTo();
                if (distance.get(u) + edge.getWeight() < distance.get(v)) {
                    throw new RuntimeException("Graph contains a negative weight cycle");
                }
            }
        }

        return reconstructPath(predecessor, start, goal);
    }

    private static List<Node> reconstructPath(Map<Node, Node> predecessor, Node start, Node goal) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = goal;
        while (current != null && !current.equals(start)) {
            path.addFirst(current);
            current = predecessor.get(current);
        }
        if (current == null) {
            return Collections.emptyList();
        }
        path.addFirst(start);
        return path;
    }
}
