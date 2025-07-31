package com.drivebot.utils;

import com.drivebot.model.Node;

public interface Heuristic {
    /**
     * Estimates the cost from the current node to the goal node.
     * @param current The current node.
     * @param goal The target node.
     * @return Estimated cost to reach the goal.
     */
    double estimate(Node current, Node goal);
}
