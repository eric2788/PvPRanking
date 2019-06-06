package com.ericlam.mc.rankcal.utils.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AdvMath {
    private AdvMath() {
    }

    /**
     * @param num num
     * @return number length
     */
    public static int getNumLenght(long num){
        num = num>0?num:-num;
        if (num==0) {
            return 1;
        }
        return (int) Math.log10(num)+1;

    }

    /**
     *
     * @param num num
     * @return number length
     */
    public static int getNumLenght(double num){
        return getNumLenght((long)num);
    }

    /**
     * when over 0.5. round up, else round down
     *
     * @param f round length
     * @param num value
     * @return rounded value
     */
    public static double round(int f,double num){
        BigDecimal decimal = new BigDecimal(num);
        return decimal.round(new MathContext(f, RoundingMode.HALF_EVEN)).doubleValue();
    }

    /**
     * round up
     *
     * @param f round number
     * @param num value
     * @return rounded value
     */
    public static double roundCil(int f,double num){
        BigDecimal decimal = new BigDecimal(num);
        return decimal.round(new MathContext(f, RoundingMode.CEILING)).doubleValue();
    }

    /**
     * round down
     *
     * @param f round number
     * @param num value
     * @return rounded value
     */
    public static double roundFlr(int f,double num){
        BigDecimal decimal = new BigDecimal(num);
        return decimal.round(new MathContext(f, RoundingMode.FLOOR)).doubleValue();
    }

    /**
     *
     * @param row Set
     * @param contain SubSet
     * @return Set contain subset
     */
    public static boolean contain(double[] row, double[] contain){
        var a = toDoubleList(row);
        var b = toDoubleList(contain);
        return a.containsAll(b);
    }

    /**
     *
     * @param row Set
     * @param value value
     * @return how many times that value contain in Set
     */
    public static long count(double[] row, double value){
        long count = 0;
        for (double v : row) {
            if (v == value) ++count;
        }
        return count;
    }

    /**
     *
     * @param tables Tables
     * @param set SubSet
     * @return How many tables does subset have
     */
    public static long count(double[][] tables, double[] set){
        long count = 0;
        for (double[] row : tables) {
            if (contain(row,set)) ++count;
        }
        return count;
    }

    /**
     *
     * @param arr double array
     * @return double List
     */
    public static List<Double> toDoubleList(double[] arr){
        List<Double> d = new ArrayList<>();
        for (double v : arr) {
            d.add(v);
        }
        return d;
    }

    /**
     *
     * @param arr object array
     * @return double array
     */
    public static double[] toHashDoubleArray(Object[] arr){
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].hashCode();
        }
        return result;
    }

    /**
     *
     * @param total total
     * @param a first item value
     * @param b second item value
     * @return Gini Index
     */
    public static double gini(double total, double a, double b) {
        double index = 1 - Math.pow(a / total, 2) - Math.pow(b / total, 2);
        return round(3, index);
    }

    /**
     *
     * @param total total value
     * @param a first item value
     * @param b second item value
     * @return entropy value
     */
    public static double entropy(double total, double a, double b) {
        double result = -(a / total * log2(a / total)) - (b / total * log2(b / total));
        return Double.isNaN(result) ? 0 : round(4, result);
    }

    /**
     *
     * @param total total value
     * @param a first item value
     * @param b second item value
     * @return computed error rate
     */
    public static double computeErr(double total, double a, double b) {
        return round(3, 1 - Math.max(a / total, b / total));
    }

    public static double log2(double n) {
        return Math.log(n) / Math.log(2);
    }
}
