package com.ericlam.mc.rankcal.utils;

import java.util.Arrays;

public class ArrayCalculation {
    private double[] ints;
    private double mean;
    private double sd;
    private double variance;

    /**
     * @param ints int array
     */
    public ArrayCalculation(double... ints) {
        this.ints = ints;
        int result = 0;
        for (double i : ints) {
            result += i;
        }
        mean = (double)result/ints.length;

        int sum = 0;
        for (double i : ints) {
            sum += Math.pow((i - mean),2);
        }
        variance = (double)sum/ints.length;
        sd = Math.sqrt(variance);
    }

    /**
     *
     * @return mean
     */
    public double getMean() {
        return mean;
    }

    /**
     *
     * @return standard deviation
     */
    public double getSd() {
        return sd;
    }

    /**
     *
     * @return variance
     */
    public double getVariance() {
        return variance;
    }

    @Override
    public String toString() {
        return "Array: "+ Arrays.toString(ints)+
                "\n"+"Mean: "+mean+
                "\n"+"Variance: "+variance+
                "\n"+"Standard Deviation: "+sd;
    }
}
