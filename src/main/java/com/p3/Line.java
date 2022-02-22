package com.p3;

import java.awt.Color;

public class Line {

    final int x0;
    final int y0;
    final int x1;
    final int y1;
    final Color color;

    public Line(int x0, int y0, int x1, int y1, Color color) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
    }

    @Override
    public String toString(){
        String line = "Line:\n";
        line += "x0: " + x0 + "\t" + "y0: " + y0 + "\n";
        line += "x1: " + x1 + "\t" + "y1: " + y1 + "\n";
        return line;
    }
}
