package com.ade.server;

import com.ade.algorithms.*;
import com.ade.models.*;

public class MainServer {
    public static void main(String[] args) {

        int[] data = {1,3,4,5};
        int capacity = 7;

        Input input = new Input(data, capacity);

        Algorithm greedy = new GreedyAlgorithm();
        Algorithm dp = new DPAlgorithm();

        Result r1 = greedy.solve(input);
        Result r2 = dp.solve(input);

        System.out.println(r1.getAlgorithmName() + " -> " + r1.getValue() + " Time: " + r1.getExecutionTime());
        System.out.println(r2.getAlgorithmName() + " -> " + r2.getValue() + " Time: " + r2.getExecutionTime());
    }
}