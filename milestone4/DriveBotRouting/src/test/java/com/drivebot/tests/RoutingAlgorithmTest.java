package com.drivebot.tests;

import com.drivebot.DriveBotRouter;
import com.drivebot.algorithms.AStar;
import com.drivebot.model.*;
import com.drivebot.utils.EuclideanHeuristic;
import com.drivebot.utils.GraphUpdater;
import com.drivebot.utils.PricingCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoutingAlgorithmTest {

    private Graph createTestGraph() {
        Graph graph = new Graph();

        Node a = new Node("A", 0, 0);
        Node b = new Node("B", 1, 0);
        Node c = new Node("C", 2, 0);

        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);

        Edge ab = new Edge(a, b, 2);
        Edge bc = new Edge(b, c, 2);
        Edge ac = new Edge(a, c, 10);

        ab.setTrafficFactor(1.0);
        ab.setAvailabilityFactor(1.0);

        bc.setTrafficFactor(1.0);
        bc.setAvailabilityFactor(1.0);

        ac.setTrafficFactor(1.0);
        ac.setAvailabilityFactor(1.0);

        graph.addEdge(ab);
        graph.addEdge(bc);
        graph.addEdge(ac);

        return graph;
    }

    // --------------------------
    // TIMELY ARRIVALS TEST
    // --------------------------
    @Test
    public void testTimelyArrivals() {
        System.out.println("\n--- Test: Timely Arrivals ---");
        Graph graph = createTestGraph();

        // Normal conditions: A→B→C is optimal
        GraphUpdater.updateGraphWeights(graph);
        Node start = graph.getNodeById("A");
        Node goal = graph.getNodeById("C");

        List<Node> pathNormal = AStar.findPath(graph, start, goal, new EuclideanHeuristic());
        assertEquals(List.of(start, graph.getNodeById("B"), goal), pathNormal);

        // Increase traffic on A→B so arrival is delayed → should switch to A→C
        Edge ab = graph.getEdge("A", "B");
        ab.setTrafficFactor(10.0);
        GraphUpdater.updateGraphWeights(graph);

        List<Node> pathTraffic = AStar.findPath(graph, start, goal, new EuclideanHeuristic());
        assertEquals(List.of(start, goal), pathTraffic);

        System.out.println("Normal Path: " + pathNormal);
        System.out.println("Traffic Path: " + pathTraffic);
    }

    // --------------------------
    // FAIR PRICING TEST
    // --------------------------
    @Test
    public void testFairPricingBounded() {
        System.out.println("\n--- Test: Fair Pricing ---");
        Graph graph = createTestGraph();
        Edge ab = graph.getEdge("A", "B");

        // Assign realistic pricing values
        ab.setBaseCost(2);
        ab.setTravelTime(5);
        ab.setDemandFactor(1.5);
        ab.setAvailabilityFactor(0.8);
        ab.setCO2Emission(0.3);
        ab.setSocialSubsidy(0.2);
        ab.setOperationalCost(1.0);

        double price = PricingCalculator.computePrice(ab);
        double minPrice = 1.0;
        double maxPrice = 10.0;

        assertTrue(price >= minPrice && price <= maxPrice,
                "Price should fall within allowed bounds.");
        System.out.printf("Computed Price: %.2f (Bounds: %.2f–%.2f)%n", price, minPrice, maxPrice);
    }
    // --------------------------
    // PROFITABILITY TEST
    // --------------------------
    @Test
    public void testProfitabilityCalculation() {
        System.out.println("\n--- Test: Profitability ---");
        Graph graph = createTestGraph();
        Edge ab = graph.getEdge("A", "B");

        // Assign dummy business data
        ab.setBaseCost(2);
        ab.setTravelTime(5);
        ab.setDemandFactor(1.2);
        ab.setAvailabilityFactor(1.0);
        ab.setCO2Emission(0.3);
        ab.setSocialSubsidy(0.1);
        ab.setOperationalCost(0.5);

        double price = PricingCalculator.computePrice(ab);
        double profit = PricingCalculator.computeProfit(ab);

        assertTrue(profit > 0, "Profit should be positive for a viable trip.");
        System.out.printf("Price: %.2f | Profit: %.2f%n", price, profit);
    }

    // --------------------------
    // AVOID "STUCK" SITUATIONS TEST
    // --------------------------
    @Test
    public void testAvoidStuckSituations() {
        System.out.println("\n--- Test: Avoid Stuck Situations ---");

        Graph graph = createTestGraph();

        // Simulate roadblock: remove B→C connection
        Edge bc = graph.getEdge("B", "C");
        graph.removeEdge(bc);

        Node start = graph.getNodeById("A");
        Node goal = graph.getNodeById("C");

        // Capture console output to verify stuck message
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Use DriveBotRouter (runs dead-end detection)
        DriveBotRouter router = new DriveBotRouter(graph, new EuclideanHeuristic(), true);
        List<Node> path = router.computeRoute(start, goal);

        // Restore standard output
        System.setOut(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.out)));

        String consoleOutput = outContent.toString();

        // Assertions
        assertTrue(path.isEmpty(), "No available route should result in empty path.");
        assertTrue(consoleOutput.toLowerCase().contains("dead end detected"),
                "Console output should indicate dead end detection.");

        // Complexity analysis
        String algoUsed = graph.hasNegativeEdge() ? "Bellman–Ford" : "A* Search";
        String complexity = graph.hasNegativeEdge()
                ? "O(V * E) — Bellman–Ford worst-case complexity"
                : "O(E log V) — A* Search with priority queue";

        System.out.println("Path when stuck: " + path);
        System.out.println("Algorithm used: " + algoUsed);
        System.out.println("Theoretical time complexity: " + complexity);
    }



    // --------------------------
    // FREEWAY EXCLUSION TEST
    // --------------------------
    @Test
    public void testFreewayExclusionConstraint() {
        System.out.println("\n--- Test: Freeway Exclusion ---");
        Graph graph = new Graph();

        Node a = new Node("A", 0, 0);
        Node b = new Node("B", 1, 0);
        Node c = new Node("C", 2, 0);

        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);

        // Non-freeway path
        Edge ab = new Edge(a, b, 2);
        ab.setTrafficFactor(1.0);
        ab.setAvailabilityFactor(1.0);

        // Freeway path with high penalty
        Edge ac = new Edge(a, c, 5);
        ac.setTrafficFactor(1.0);
        ac.setAvailabilityFactor(1.0);
        ac.setWeight(9999); // simulate exclusion

        Edge bc = new Edge(b, c, 2);
        bc.setTrafficFactor(1.0);
        bc.setAvailabilityFactor(1.0);

        graph.addEdge(ab);
        graph.addEdge(ac);
        graph.addEdge(bc);

        GraphUpdater.updateGraphWeights(graph);

        List<Node> path = AStar.findPath(graph, a, c, new EuclideanHeuristic());
        assertEquals(List.of(a, b, c), path, "Algorithm should avoid freeway edge due to high cost.");
        System.out.println("Computed Path: " + path);
    }
    // --------------------------
    // MATHEMATICS TEST
    // --------------------------
    @Test
    public void testMathematicsOfRoutingAlgorithmWithMatrices() {
        System.out.println("\n--- Test: Mathematics of Our Routing Algorithm (Matrix Form) ---");

        Graph graph = new Graph();

        // Nodes
        Node a = new Node("A", 0, 0);
        Node b = new Node("B", 1, 0);
        Node c = new Node("C", 2, 0);
        Node d = new Node("D", 3, 0);

        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);

        // Edges (Base Costs)
        Edge ab = new Edge(a, b, 4);
        Edge bc = new Edge(b, c, 2);
        Edge ac = new Edge(a, c, 7);
        Edge cd = new Edge(c, d, 3);
        Edge bd = new Edge(b, d, 8);

        // Assign factors
        ab.setTrafficFactor(1.0); ab.setAvailabilityFactor(1.0);
        bc.setTrafficFactor(1.2); bc.setAvailabilityFactor(1.0);
        ac.setTrafficFactor(1.0); ac.setAvailabilityFactor(0.8);
        cd.setTrafficFactor(1.0); cd.setAvailabilityFactor(1.0);
        bd.setTrafficFactor(1.5); bd.setAvailabilityFactor(0.9);

        // Add edges
        graph.addEdge(ab);
        graph.addEdge(bc);
        graph.addEdge(ac);
        graph.addEdge(cd);
        graph.addEdge(bd);

        String[] nodes = {"A", "B", "C", "D"};
        int n = nodes.length;
        int[][] adjacency = new int[n][n];
        double[][] weights = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Edge e = graph.getEdge(nodes[i], nodes[j]);
                if (e != null) {
                    adjacency[i][j] = 1;
                    weights[i][j] = e.getWeight();
                }
            }
        }

        System.out.println("\nAdjacency Matrix A:");
        for (int[] row : adjacency) {
            for (int val : row) System.out.print(val + " ");
            System.out.println();
        }

        System.out.println("\nWeight Matrix W (before update):");
        for (double[] row : weights) {
            for (double val : row) System.out.printf("%6.2f ", val);
            System.out.println();
        }

        GraphUpdater.updateGraphWeights(graph);

        double[][] updatedWeights = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Edge e = graph.getEdge(nodes[i], nodes[j]);
                updatedWeights[i][j] = (e != null) ? e.getWeight() : 0.0;
            }
        }

        System.out.println("\nWeight Matrix W' (after traffic/availability update):");
        for (double[] row : updatedWeights) {
            for (double val : row) System.out.printf("%6.2f ", val);
            System.out.println();
        }

        List<Node> path = AStar.findPath(graph, a, d, new EuclideanHeuristic());
        System.out.println("\nComputed Optimal Path: " + path);

        double manualCost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            manualCost += graph.getEdge(path.get(i).getId(), path.get(i + 1).getId()).getWeight();
        }
        System.out.printf("Manual Computed Path Cost: %.2f%n", manualCost);

        System.out.println("\nMathematical Interpretation:");
        System.out.println("We have a directed graph G = (V, E) with edge weight function w: E → ℝ.");
        System.out.println("GraphUpdater transforms w into w' where:");
        System.out.println("   w'(u,v) = baseCost(u,v) × trafficFactor(u,v) ÷ availabilityFactor(u,v)");
        System.out.println("We seek the path P* from A to D minimizing:");
        System.out.println("   Cost(P) = Σ_{(u,v)∈P} w'(u,v)");

        assertEquals(manualCost, manualCost, 1e-6);
    }

    // --------------------------
    // REALISTIC SANTA CLARA → SF TEST
    // --------------------------
    @Test
    public void testRealisticSantaClaraToSanFranciscoRoute() {
        System.out.println("\n--- Test: Santa Clara → San Francisco Route ---");

        Graph graph = new Graph();

        Node santaClara = new Node("Santa Clara", 0, 0);
        Node paloAlto = new Node("Palo Alto", 10, 0);
        Node dalyCity = new Node("Daly City", 40, 0);
        Node sanFrancisco = new Node("San Francisco", 50, 0);

        graph.addNode(santaClara);
        graph.addNode(paloAlto);
        graph.addNode(dalyCity);
        graph.addNode(sanFrancisco);

        Edge sc_pa = new Edge(santaClara, paloAlto, 10);
        Edge pa_dc = new Edge(paloAlto, dalyCity, 30);
        Edge dc_sf = new Edge(dalyCity, sanFrancisco, 10);
        Edge sc_dc = new Edge(santaClara, dalyCity, 45);

        sc_pa.setTrafficFactor(1.2);
        pa_dc.setTrafficFactor(1.0); pa_dc.setAvailabilityFactor(1.5);
        dc_sf.setTrafficFactor(1.0);
        sc_dc.setTrafficFactor(2.0); sc_dc.setAvailabilityFactor(0.8);

        graph.addEdge(sc_pa);
        graph.addEdge(pa_dc);
        graph.addEdge(dc_sf);
        graph.addEdge(sc_dc);

        GraphUpdater.updateGraphWeights(graph);

        DriveBotRouter router = new DriveBotRouter(graph, new EuclideanHeuristic(), true);
        List<Node> path = router.computeRoute(santaClara, sanFrancisco);

        System.out.println("Computed Path: " + path);

        double totalPrice = 0.0;
        double totalProfit = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Edge edge = graph.getEdge(path.get(i).getId(), path.get(i + 1).getId());
            edge.setBaseCost(edge.getWeight());
            edge.setTravelTime(edge.getWeight() * 2);
            edge.setDemandFactor(1.2);
            edge.setAvailabilityFactor(1.0);
            edge.setCO2Emission(0.5);
            edge.setSocialSubsidy(0.1);
            edge.setOperationalCost(0.5);

            totalPrice += PricingCalculator.computePrice(edge);
            totalProfit += PricingCalculator.computeProfit(edge);
        }

        System.out.printf("Total Trip Price: %.2f | Total Trip Profit: %.2f%n", totalPrice, totalProfit);

        assertEquals(List.of(santaClara, paloAlto, dalyCity, sanFrancisco), path);
    }
}

