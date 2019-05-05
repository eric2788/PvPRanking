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
     * @param num 數字
     * @return 數字長度
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
     * @param num 數字
     * @return 數次長度
     */
    public static int getNumLenght(double num){
        return getNumLenght((long)num);
    }

    /**
     *
     * @param f 取至位數
     * @param num 數值
     * @return 四捨五入後的數值
     */
    public static double round(int f,double num){
        BigDecimal decimal = new BigDecimal(num);
        return decimal.round(new MathContext(f, RoundingMode.HALF_EVEN)).doubleValue();
    }

    /**
     *
     * @param f 取至位數
     * @param num 數值
     * @return 向上取值後的數值
     */
    public static double roundCil(int f,double num){
        BigDecimal decimal = new BigDecimal(num);
        return decimal.round(new MathContext(f, RoundingMode.CEILING)).doubleValue();
    }

    /**
     *
     * @param f 取至位數
     * @param num 數值
     * @return 向下取值後的數值
     */
    public static double roundFlr(int f,double num){
        BigDecimal decimal = new BigDecimal(num);
        return decimal.round(new MathContext(f, RoundingMode.FLOOR)).doubleValue();
    }

    /**
     *
     * @param row Set  (列）
     * @param contain subSet  (子列)
     * @return 主列是否含有子列
     */
    public static boolean contain(double[] row, double[] contain){
        var a = toDoubleList(row);
        var b = toDoubleList(contain);
        return a.containsAll(b);
    }

    /**
     *
     * @param row Set  (列）
     * @param value 數值
     * @return 主列出現該數值多少次
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
     * @param tables 表
     * @param set subSet 子列
     * @return 表出現子列多少次
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
     * @param arr 數字串
     * @return 裝載 Double 的 List
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
     * @param arr 任何物件的列
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
     * @param total 總共數量
     * @param a 第一項物品的擁有數值
     * @param b 第二項物品的擁有數值
     * @return Gini Index
     */
    public static double gini(double total, double a, double b) {
        double index = 1 - Math.pow(a / total, 2) - Math.pow(b / total, 2);
        return round(3, index);
    }

    /**
     *
     * @param total 總共數量
     * @param a 第一項物品的擁有數值
     * @param b 第二項物品的擁有數值
     * @return entropy value
     */
    public static double entropy(double total, double a, double b) {
        double result = -(a / total * log2(a / total)) - (b / total * log2(b / total));
        return Double.isNaN(result) ? 0 : round(4, result);
    }

    /**
     *
     * @param total 總共數量
     * @param a 第一項物品的擁有數值
     * @param b 第二項物品的擁有數值
     * @return computed error rate
     */
    public static double computeErr(double total, double a, double b) {
        return round(3, 1 - Math.max(a / total, b / total));
    }

    public static double log2(double n) {
        return Math.log(n) / Math.log(2);
    }
}
