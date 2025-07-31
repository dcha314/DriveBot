package com.drivebot.model;

public class Edge {
    private final Node from;
    private final Node to;
    private double weight;
    private double baseCost;
    private double travelTime;
    private double demandFactor;
    private double trafficFactor;       // <-- Added back so GraphUpdater compiles
    private double availabilityFactor;
    private double CO2Emission;
    private double socialSubsidy;
    private double operationalCost;

    public Edge(Node from, Node to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.baseCost = weight;
        this.travelTime = weight;
        this.demandFactor = 1.0;
        this.trafficFactor = 1.0; // default for dynamic weight adjustment
        this.availabilityFactor = 1.0;
        this.CO2Emission = 0.0;
        this.socialSubsidy = 0.0;
        this.operationalCost = 0.0;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(double travelTime) {
        this.travelTime = travelTime;
    }

    public double getDemandFactor() {
        return demandFactor;
    }

    public void setDemandFactor(double demandFactor) {
        this.demandFactor = demandFactor;
    }

    public double getTrafficFactor() {
        return trafficFactor;
    }

    public void setTrafficFactor(double trafficFactor) {
        this.trafficFactor = trafficFactor;
    }

    public double getAvailabilityFactor() {
        return availabilityFactor;
    }

    public void setAvailabilityFactor(double availabilityFactor) {
        this.availabilityFactor = availabilityFactor;
    }

    public double getCO2Emission() {
        return CO2Emission;
    }

    public void setCO2Emission(double CO2Emission) {
        this.CO2Emission = CO2Emission;
    }

    public double getSocialSubsidy() {
        return socialSubsidy;
    }

    public void setSocialSubsidy(double socialSubsidy) {
        this.socialSubsidy = socialSubsidy;
    }

    public double getOperationalCost() {
        return operationalCost;
    }

    public void setOperationalCost(double operationalCost) {
        this.operationalCost = operationalCost;
    }
}
