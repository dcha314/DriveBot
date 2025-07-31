package com.drivebot.model;

import java.util.*;

public class Graph {
    private final Map<Node, List<Edge>> adjacencyList = new HashMap<>();

    public void addNode(Node node) {
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node from, Node to, double weight) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.putIfAbsent(to, new ArrayList<>());
        adjacencyList.get(from).add(new Edge(from, to, weight));
    }

    public void addEdge(Edge edge) {
        Node from = edge.getFrom();
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.putIfAbsent(edge.getTo(), new ArrayList<>());
        adjacencyList.get(from).add(edge);
    }

    public void removeEdge(Edge edge) {
        if (edge == null) return;
        List<Edge> edgesFromNode = adjacencyList.get(edge.getFrom());
        if (edgesFromNode != null) {
            edgesFromNode.remove(edge);
        }
    }

    public List<Edge> getEdges(Node node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    public Set<Node> getAllNodes() {
        return adjacencyList.keySet();
    }

    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();
        for (List<Edge> eList : adjacencyList.values()) {
            edges.addAll(eList);
        }
        return edges;
    }

    public Node getNodeById(String id) {
        for (Node node : adjacencyList.keySet()) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    public Edge getEdge(String fromId, String toId) {
        Node from = getNodeById(fromId);
        Node to = getNodeById(toId);
        if (from == null || to == null) return null;

        for (Edge e : getEdges(from)) {
            if (e.getTo().equals(to)) {
                return e;
            }
        }
        return null;
    }

    public boolean hasNegativeEdge() {
        for (Edge edge : getAllEdges()) {
            if (edge.getWeight() < 0) {
                return true;
            }
        }
        return false;
    }


}

