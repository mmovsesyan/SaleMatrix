package com.movsisyan.program;

import com.movsisyan.exceptions.InsufficientSizeMatrixException;
import com.movsisyan.model.Sale;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] twoDimArray1 = {{41, 24, 38}, {2, 20, 35}, {3, 15, 20}};
        double[][] twoDimArray2 = {{5.20, 6.50}, {2.80, 0.40}, {5.00, 1.00}};
        Sale s = new Sale(3, 3);
        try {
            s.add(twoDimArray1, twoDimArray2);
        } catch (InsufficientSizeMatrixException e) {
            throw new RuntimeException(e);
        }
        double[][] addition = s.addition();
        System.out.println(Arrays.deepToString(addition));
//        System.out.println(Arrays.toString(s.max()));
//        System.out.println(s.maxSum());
//        System.out.println(s.allComissions());
//        System.out.println(s.zeroProductsNumber());
//        System.out.println(Arrays.toString(s.minMaxComissions()));
//        System.out.println(s);
        System.out.println(s.minSumProducts());
    }
}
