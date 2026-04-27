package com.ade.graph;

import java.util.*;

public class Graph {

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private Map<Integer, List<Edge>> adj = new HashMap<>();

    public void addEdge(int u, int v, int w) {
        adj.computeIfAbsent(u, k -> new ArrayList<>()).add(new Edge(v, w));
        adj.computeIfAbsent(v, k -> new ArrayList<>()).add(new Edge(u, w));
    }

    public int shortestPath(int src, int dest) {

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        Map<Integer, Integer> dist = new HashMap<>();

        pq.add(new int[]{src, 0});
        dist.put(src, 0);

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int d = curr[1];

            if (node == dest) return d;

            for (Edge e : adj.getOrDefault(node, new ArrayList<>())) {
                int newDist = d + e.weight;

                if (!dist.containsKey(e.to) || newDist < dist.get(e.to)) {
                    dist.put(e.to, newDist);
                    pq.add(new int[]{e.to, newDist});
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}