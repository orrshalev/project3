package com.p3;

import java.util.LinkedList;
import java.awt.Color;

/**
 * Matrix transformations needed for 2D geometric transformations
 */
public class Matrix {
    /**
     * Apply a matrix transformation to all lines and return new lines with transformation applied
     * @param matrix transformation matrix
     * @param lines lines to apply transformation matrix to
     * @return transformed lines
     */
    public static LinkedList<Line> applyTransformation(double[][] matrix, LinkedList<Line> lines) {
        LinkedList<Line> newlines = new LinkedList<Line>();
        // for each line, apply transformation
        lines.forEach(line -> {
            int x0 = line.x0;
            int y0 = line.y0;
            int x1 = line.x1;
            int y1 = line.y1;
            Color color = line.color;
            // apply transformation to first endpoint
            double[][] x0y0 = matrixMultiplication(coordinateMatrix(x0, y0), matrix); 
            // apply transformation to second endpoint
            double[][] x1y1 = matrixMultiplication(coordinateMatrix(x1, y1), matrix);
            // get new points
            x0 = (int) x0y0[0][0];
            y0 = (int) x0y0[0][1];
            x1 = (int) x1y1[0][0];
            y1 = (int) x1y1[0][1];
            // add line
            newlines.add(new Line(x0, y0, x1, y1, color));
        });
        return newlines;
    }
    /**
     * Multiply two matrices A x B and return result
     * @param A First matrix
     * @param B Second matrix
     * @return resulting matrix
     */
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

    /**
     * Create a translation matrix
     * @param Tx Translation in the x direction
     * @param Ty Translation in the y direction
     * @return translation matrix
     */
    public static double[][] basicTranslate(double Tx, double Ty) {
        double[][] tMatrix = new double[][] {
                new double[] { 1d, 0d, 0d },
                new double[] { 0d, 1d, 0d },
                new double[] { Tx, Ty, 1d }
        };
        return tMatrix;
    }

    /**
     * Create a scaling matrix with center of scale at (0,0)
     * @param Sx Scaling x factor
     * @param Sy Scaling y factor
     * @return Scaling matrix
     */
    public static double[][] basicScale(double Sx, double Sy) {
        double[][] sMatrix = new double[][] {
                new double[] { Sx, 0d, 0d },
                new double[] { 0d, Sy, 0d },
                new double[] { 0d, 0d, 1d }
        };
        return sMatrix;
    }

    /**
     * Create a rotation matrix with center of rotation at (0,0)
     * @param angle Angles (in degrees) to rotate by clockwise
     * @return rotation matrix
     */
    public static double[][] basicRotate(double angle) {
        angle = Math.toRadians(angle);
        double[][] rMatrix = new double[][] {
                new double[] { Math.cos(angle), -1 * Math.sin(angle), 0d },
                new double[] { Math.sin(angle), Math.cos(angle), 0d },
                new double[] { 0d, 0d, 1d }
        };
        return rMatrix;
    }

    /**
     * Create a coordinate matrix
     * @param x x coordinate
     * @param y y coordinate
     * @return coordinate matrix
     */
    public static double[][] coordinateMatrix(int x, int y) {
        double[][] mat = new double[][] {
                new double[] { x, y, 1 }
        };
        return mat;
    }

    /**
     * Create a scaling matrix with center of scale at (Cx,Cy)
     * @param Sx Scaling x factor
     * @param Sy Scaling y factor
     * @param Cx Center of scale x part
     * @param Cy Center of scale y part
     * @return
     */
    public static double[][] scale(double Sx, double Sy, double Cx, double Cy) {
        double[][] sMatrix;
        double[][] step1 = new double[][] {
                new double[] { 1d, 0d, 0d },
                new double[] { 0d, 1d, 0d },
                new double[] { -1 * Cx, -1 * Cy, 1d }
        };
        double[][] step2 = new double[][] {
                new double[] { Sx, 0d, 0d },
                new double[] { 0d, Sy, 0d },
                new double[] { 0d, 0d, 1d }
        };
        double[][] step3 = new double[][] {
                new double[] { 1d, 0d, 0d },
                new double[] { 0d, 1d, 0d },
                new double[] { Cx, Cy, 1d }
        };
        sMatrix = Matrix.matrixMultiplication(Matrix.matrixMultiplication(step1, step2), step3);
        return sMatrix;
    }

    /**
     * Create a rotation matrix with center of rotation at (Cx,Cy)
     * @param angle Angle (in degrees) to rotate by clockwise
     * @param Cx Center of rotation x part
     * @param Cy Center of rotation y part
     * @return Rotation matrix
     */
    public static double[][] rotate(double angle, double Cx, double Cy) {
        angle = Math.toRadians(angle);
        double[][] rMatrix;
        double[][] step1 = new double[][] {
                new double[] { 1d, 0d, 0d },
                new double[] { 0d, 1d, 0d },
                new double[] { -1 * Cx, -1 * Cy, 1d }
        };
        double[][] step2 = new double[][] {
                new double[] { Math.cos(angle), -1 * Math.sin(angle), 0d },
                new double[] { Math.sin(angle), Math.cos(angle), 0d },
                new double[] { 0d, 0d, 1d }
        };
        double[][] step3 = new double[][] {
                new double[] { 1d, 0d, 0d },
                new double[] { 0d, 1d, 0d },
                new double[] { Cx, Cy, 1d }
        };
        rMatrix = Matrix.matrixMultiplication(Matrix.matrixMultiplication(step1, step2), step3);
        return rMatrix;
    }
}
