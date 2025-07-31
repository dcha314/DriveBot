package com.drivebot.utils;

import com.drivebot.model.Node;

public class EuclideanHeuristic implements Heuristic {

    @Override
    public double estimate(Node current, Node goal) {
        double dx = current.getX() - goal.getX();
        double dy = current.getY() - goal.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
