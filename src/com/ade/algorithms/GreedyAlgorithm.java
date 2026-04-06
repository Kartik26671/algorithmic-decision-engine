package com.ade.algorithms;

import com.ade.models.Input;
import com.ade.models.Result;

import java.util.Arrays;

public class GreedyAlgorithm implements Algorithm {

    @Override
    public String getName() {
        return "Greedy Algorithm";
    }

    @Override
    public Result solve(Input input) {

        long start = System.nanoTime();

        int[] data = input.getData();
        int capacity = input.getCapacity();

        // Sort descending
        Arrays.sort(data);

        int sum = 0;
        for (int i = data.length - 1; i >= 0; i--) {
            if (sum + data[i] <= capacity) {
                sum += data[i];
            }
        }

        long end = System.nanoTime();

        return new Result(getName(), sum, (end - start));
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2));
    }
}