package com.ade.algorithms;

import com.ade.models.Input;
import com.ade.models.Result;

public class DPAlgorithm implements Algorithm {

    @Override
    public String getName() {
        return "Dynamic Programming";
    }

    @Override
    public Result solve(Input input) {

        long start = System.nanoTime();

        int[] data = input.getData();
        int capacity = input.getCapacity();

        int n = data.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (data[i - 1] <= w) {
                    dp[i][w] = Math.max(
                            data[i - 1] + dp[i - 1][w - data[i - 1]],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        long end = System.nanoTime();

        return new Result(getName(), dp[n][capacity], (end - start));
    }
}