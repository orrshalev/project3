package com.p3;

import java.util.LinkedList;
import java.awt.Color;

public class Test {
    public static void main(String[] args){
        LinkedList<Line> list = new LinkedList<Line>();
        list.add(new Line(5, 10, 5, 15,Color.BLACK));
        list.add(new Line(5, 20, 10, 25,Color.BLACK));
        LinkedList<Line> newList = Matrix.applyTransformation(Matrix.basicTranslate(100, 100), list);
        for (Line line : newList) {
            System.out.println(line.toString());
        }

    }
    
}
