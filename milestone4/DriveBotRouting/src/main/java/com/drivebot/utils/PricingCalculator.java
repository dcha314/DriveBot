package com.drivebot.utils;

import com.drivebot.model.Edge;

public class PricingCalculator {

    private static final double a1 = 0.5; // distance weight
    private static final double c1 = 1.0;
    private static final double a2 = 0.3; // travel time weight
    private static final double c2 = 1.0;
    private static final double a3 = 0.2; // demand factor weight
    private static final double c3 = 1.0;
    private static final double a4 = -0.1; // availability weight (negative for discount)
    private static final double c4 = 1.0;
    private static final double lambdaE = 0.05; // CO₂ emissions penalty
    private static final double lambdaS = 1.0;  // social subsidy bonus

    public static double computePrice(Edge edge) {
        double d = edge.getBaseCost(); // distance
        double tau = edge.getTravelTime(); // estimated time
        double delta = edge.getDemandFactor();
        double eta = edge.getAvailabilityFactor();
        double e = edge.getCO2Emission();
        double s = edge.getSocialSubsidy();

        return a1 * Math.pow(d, c1) +
                a2 * Math.pow(tau, c2) +
                a3 * Math.pow(delta, c3) +
                a4 * Math.pow(eta, c4) +
                lambdaE * e -
                lambdaS * s;
    }

    public static double computeProfit(Edge edge) {
        double price = computePrice(edge);
        double cost = edge.getOperationalCost();
        return price - cost;
    }
}
