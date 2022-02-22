package com.p3;

import java.util.LinkedList;
import java.awt.Color;

public class Test {
    public static void main(String[] args){
        LinkedList<Line> list = new LinkedList<Line>();
        list.add(new Line(5, 10, 5, 15,Color.BLACK));
        list.add(new Line(5, 20, 10, 25,Color.BLACK));
        list.forEach(e -> {e.x0 += 5; e.x1 += 10;});
        list.forEach(e -> System.out.println(e.toString()));

    }
    
}
