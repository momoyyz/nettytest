package com.netty.test;

public class BitSort {
    private static boolean isPowerOfTwo(int val) {
        System.out.println(val & -val);
        return (val & -val) == val;
    }
    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(16));
    }
}
