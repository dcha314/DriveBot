package com.drivebot.algorithms;

import com.drivebot.model.Edge;
import com.drivebot.model.Graph;
import com.drivebot.model.Node;
import com.drivebot.utils.Heuristic;

import java.util.*;

public final class AStar {

    private AStar() {}

    public static List<Node> findPath(Graph graph,
                                      Node start,
                                      Node goal,
                                      Heuristic heuristic) {

        Map<Node, Double> gScore = new HashMap<>();
        Map<Node, Node>   cameFrom = new HashMap<>();
        Map<Node, Double> fScore = new HashMap<>();

        for (Node n : graph.getAllNodes()) {
            gScore.put(n, Double.POSITIVE_INFINITY);
            fScore.put(n, Double.POSITIVE_INFINITY);
        }
        gScore.put(start, 0.0);
        fScore.put(start, heuristic.estimate(start, goal));

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(fScore::get));
        open.add(start);
        Set<Node> closed = new HashSet<>();

        while (!open.isEmpty()) {
            Node current = open.poll();
            if (closed.contains(current)) continue; // stale entry
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }
            closed.add(current);

            for (Edge edge : graph.getEdges(current)) {
                Node neighbour = edge.getTo();
                if (closed.contains(neighbour)) continue;

                double tentativeG = gScore.get(current) + edge.getWeight();
                if (tentativeG < gScore.get(neighbour)) {
                    cameFrom.put(neighbour, current);
                    gScore.put(neighbour, tentativeG);
                    double f = tentativeG + heuristic.estimate(neighbour, goal);
                    fScore.put(neighbour, f);
                    open.add(neighbour);
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Node> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        LinkedList<Node> path = new LinkedList<>();
        path.addFirst(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(current);
        }
        return path;
    }
}