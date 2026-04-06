package com.ade.models;

public class Result {

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