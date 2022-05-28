package com.movsisyan.model;

import com.movsisyan.exceptions.InsufficientSizeMatrixException;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Sale {
    private int[][] A;
    private double[][] B;

    /**
     * 2. Конструктор с параметрами размеров этих матриц, производящий инициализацию матриц
     */
    public Sale(int m, int n) {
        this.A = new int[m][n];
        this.B = new double[n][2];
    }

    /**
     * 3. Методы, принимающие на вход двумерные массивы, которые должны будут скопированы в поля,
     * тем самым произведя заполнение матриц. Если размеры переданных двумерных массивов
     * окажутся меньше размеров матриц в полях, то необходимо выбросить исключение InsufficientSizeMatrix
     */
    public void add(int[][] a, double[][] b) throws InsufficientSizeMatrixException {
        if (a.length < this.A.length & b.length < this.B.length) {
            throw new InsufficientSizeMatrixException("a, b smaller");
        }
        System.arraycopy(a, 0, A, 0, a.length);
        System.arraycopy(b, 0, B, 0, b.length);
    }

    /**
     * 4. Метод, производящий перемножение таблицы А на таблицу B алгебраически.
     * Смотреть определение алгебраического умножения матриц.
     */
    public double[][] addition() {
        double[][] f = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[i].length; j++) {
                for (int r = 0; r < B.length; r++) {
                    f[i][j] += A[i][r] * B[r][j];
                }
            }
        }
        return f;
    }

    /**
     * 5. Метод, определяющий какой продавец выручил больше всего денег с продажи, какой - меньше
     */
    public double[] max() {
        double[][] addition = addition();
        double[] maxMin = new double[addition.length];
        for (int i = 0; i < addition.length; i++) {
            for (int j = 0; j < addition[i].length; j++) {
                maxMin[i] += addition[i][j];
            }
        }
        double min = maxMin[0];
        int indexMin = 0;
        double max = maxMin[maxMin.length - 1];
        int indexMax = 0;
        for (int i = 0; i < maxMin.length; i++) {
            if (maxMin[i] < min) {
                min = maxMin[i];
                indexMin = i;
            }
            if (maxMin[i] > max) {
                max = maxMin[i];
                indexMax = i;
            }
        }
        return new double[]{indexMax, max, indexMin, min};
    }

    /**
     * 6. Метод, определяющий какой продавец получил наибольшие комиссионные, какой - меньшие
     */
    public double[] minMaxComissions() {
        double[] comissions = new double[B.length];
        for (int i = 0; i < B.length; i++) {
            comissions[i] = B[i][1];
        }
        double minComission = comissions[0];
        double maxComission = comissions[comissions.length - 1];
        int minIndex = 0;
        int maxIndex = 0;
        for (int i = 0; i < comissions.length; i++) {
            if (comissions[i] > maxComission) {
                maxComission = comissions[i];
                maxIndex = i;
            }
            if (comissions[i] < minComission) {
                minComission = comissions[i];
                minIndex = i;
            }
        }
        return new double[]{maxIndex, maxComission, minIndex, minComission};
    }

    /**
     * 7. Метод, определяющий чему равна общая сумма денег, вырученных за проданные товары
     */
    public double maxSum() {
        double[][] addition = addition();
        double sum = 0;
        for (double[] doubles : addition) {
            for (double aDouble : doubles) {
                sum += aDouble;
            }
        }
        return sum;
    }

    /**
     * 8. Метод, определяющий сколько всего комиссионных получили продавцы
     */
    public double allComissions() {
        double comissions = 0;
        for (double[] doubles : B) {
            comissions += doubles[1];
        }
        return comissions;
    }

    /**
     * 9. Метод, определяющий чему равна общая сумма денег, прошедших через руки продавцов
     */
    public double allSum() {
        double v = maxSum();
        double v1 = allComissions();
        return v + v1;
    }
    /**
     * 10. Метод toString, возвращающий строковое представление объекта Sale в формате:
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                for (int k = 0; k < B.length; k++) {
                    s += "Продавец " + i + " продает товар " + j + " в количестве " + A[i][j]
                            + " ед по стоимости " + B[k][0] + " с комиссионными " + B[k][1] + "\n";
                }
            }
        }
        return s;
    }

    /**
     * 11. Метод, возвращающий номера продуктов которых нет ни у одного продавца
     */
    public ArrayList<Integer> zeroProductsNumber() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int j = 0; j < A[0].length; j++) {
            boolean has = false;
            for (int i = 0; i < A.length; i++) {
                if (A[i][j] != 0) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                list.add(j);
            }
        }
        return list;
    }

    /**
     * 12. Метод, возвращающий номера продуктов по самой низкой цене
     */
    public ArrayList<Integer> minSumProducts() {
        int f = Integer.MAX_VALUE;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] < f) {
                    f = A[i][j];
                }
            }
        }
        ArrayList<Integer> minSums = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] == f) {
                    minSums.add(A[i][j]);
                }
            }
        }
        return minSums;
    }

    /**
     * 13. Метод, возвращающий номера продавцов, которые продают больше всего товаров (по количеству)
     */
    public ArrayList<Integer> sellers() {
        int coolestSellers = Integer.MIN_VALUE;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] > coolestSellers) {
                    coolestSellers = A[i][j];
                }
            }
        }
        ArrayList<Integer> sellers = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] == coolestSellers) {
                    sellers.add(A[i][j]);
                }
            }
        }
        return sellers;
    }
}