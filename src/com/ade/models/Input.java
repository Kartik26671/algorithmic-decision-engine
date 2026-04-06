package com.ade.models;

public class Input {

    private int[] data;
    private int capacity;

    public Input(int[] data, int capacity) {
        this.data = data;
        this.capacity = capacity;
    }

    public int[] getData() {
        return data;
    }

    public int getCapacity() {
        return capacity;
    }

    public static class Result {

        private String algorithmName;
        private int value;
        private long executionTime;

        public Result(String algorithmName, int value, long executionTime) {
            this.algorithmName = algorithmName;
            this.value = value;
            this.executionTime = executionTime;
        }

        public String getAlgorithmName() {
            return algorithmName;
        }

        public int getValue() {
            return value;
        }

        public long getExecutionTime() {
            return executionTime;
        }
    }
}