package com.p3;

import java.util.LinkedList;
import java.awt.Color;

public class Matrix {
    // not implemented
    public static LinkedList<Line> applyTransformation(double[][] matrix, LinkedList<Line> lines) {
        LinkedList<Line> newlines = new LinkedList<Line>();
        lines.forEach(line -> {
            int x0 = line.x0;
            int y0 = line.y0;
            int x1 = line.x1;
            int y1 = line.x1;
            Color color = line.color;
            double[][] x0y0 = matrixMultiplication(coordinateMatrix(x0, y0), matrix);
            double[][] x1y1 = matrixMultiplication(coordinateMatrix(x1, y1), matrix);
            x0 = (int)x0y0[0][0];
            y0 = (int)x0y0[0][1];
            x1 = (int)x1y1[0][0];
            y1 = (int)x1y1[0][1];
            newlines.add(new Line(x0,y0,x1,y1,color));
        });
        return lines;
    }

    public static double[][] matrixMultiplication(double[][] A, double[][] B) {
        double[][] C = new double[A.length][B[0].length];
        double dot;
        for (int row = 0; row < C.length; row++) {
            for (int col = 0; col < C[row].length; col++) {
                dot = 0;
                for (int i = 0; i < B.length; i++) {
                    dot += (A[row][i] * B[i][col]);
                }
                C[row][col] = dot;
            }
        }
        return C;
    }

    // change int to Line
    public static double[][] basicTranslate(double Tx, double Ty) {
        double[][] tMatrix = new double[][] {
                new double[] { 1d, 0d, 0d },
                new double[] { 0d, 1d, 0d },
                new double[] { Tx, Ty, 1d }
        };
        return tMatrix;
    }

    public static double[][] basicScale(double Sx, double Sy) {
        double[][] sMatrix = new double[][] {
                new double[] { Sx, 0d, 0d },
                new double[] { 0d, Sy, 0d },
                new double[] { 0d, 0d, 1d }
        };
        return sMatrix;
    }

    // in degrees
    public static double[][] basicRotate(double angle) {
        angle = Math.toRadians(angle);
        double[][] rMatrix = new double[][] {
                new double[] { Math.cos(angle), -1 * Math.sin(angle), 0d },
                new double[] { Math.sin(angle), Math.cos(angle), 0d },
                new double[] { 0d, 0d, 1d }
        };
        return rMatrix;
    }

    public static double[][] coordinateMatrix(int x, int y) {
        double[][] mat = new double[][] {
                new double[] { x, y, 1 }
        };
        return mat;
    }

    // not implemented
    public static double[][] scale(double Sx, double Sy, double Cx, double Cy) {
        return new double[5][];
    }

    // not implemented
    // in degrees
    public static double[][] rotate(double angle, double Cx, double Cy) {
        angle = Math.toRadians(angle);
        return new double[5][];
    }

    public static void main(String[] args) {
        double[][] A = new double[][] {
                new double[] { 20, 20, 1 }
        };
        double[][] B = basicRotate(180d);
        double[][] C = matrixMultiplication(A, B);
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                System.out.print(C[i][j] + " ");
            }
            System.out.println();
        }
    }
}
