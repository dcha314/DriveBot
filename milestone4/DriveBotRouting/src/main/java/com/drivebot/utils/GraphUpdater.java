package com.drivebot.utils;

import com.drivebot.model.Edge;
import com.drivebot.model.Graph;

public class GraphUpdater {

    public static void updateGraphWeights(Graph graph) {
        for (Edge edge : graph.getAllEdges()) {
            if (edge.getWeight() < 0) {
                continue;
            }

            double baseCost = edge.getBaseCost();
            double trafficFactor = edge.getTrafficFactor();
            double availabilityFactor = edge.getAvailabilityFactor();

            double adjustedWeight = baseCost * trafficFactor / availabilityFactor;
            edge.setWeight(adjustedWeight);
        }
    }
}
