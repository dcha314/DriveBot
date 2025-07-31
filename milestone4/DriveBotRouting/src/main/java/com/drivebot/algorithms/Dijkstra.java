package com.drivebot.algorithms;

import com.drivebot.model.Edge;
import com.drivebot.model.Graph;
import com.drivebot.model.Node;

import java.util.*;

public class Dijkstra {

    public static List<Node> findPath(Graph graph, Node start, Node goal) {
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (Node node : graph.getAllNodes()) {
            dist.put(node, Double.POSITIVE_INFINITY);
        }
        dist.put(start, 0.0);

        pq.add(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.equals(goal)) {
                return reconstructPath(prev, goal);
            }

            for (Edge edge : graph.getEdges(current)) {
                Node neighbor = edge.getTo();
                double newDist = dist.get(current) + edge.getWeight();

                if (newDist < dist.get(neighbor)) {
                    dist.put(neighbor, newDist);
                    prev.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }

        return Collections.emptyList();
    }

    private static List<Node> reconstructPath(Map<Node, Node> prev, Node goal) {
        List<Node> path = new LinkedList<>();
        Node current = goal;

        while (current != null) {
            path.add(0, current);
            current = prev.get(current);
        }
        return path;
    }
}
