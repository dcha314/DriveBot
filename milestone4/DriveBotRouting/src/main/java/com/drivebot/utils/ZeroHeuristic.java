package com.drivebot.utils;

import com.drivebot.model.Node;

public class ZeroHeuristic implements Heuristic {
    @Override
    public double estimate(Node current, Node goal) {
        return 0;
    }
}
