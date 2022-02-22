package com.p3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.SimpleAttributeSet;

import org.ejml.simple.SimpleMatrix;

public class Transform2D extends JComponent {
	static final int WINDOW_X = 500; // # of pixels horizontally
	static final int WINDOW_Y = 500; // # of pixels vertically

	private final LinkedList<Line> lines = new LinkedList<Line>(); // all lines

	public void addLine(int x0, int x1, int x2, int x3) {
		addLine(x0, x1, x2, x3, Color.black);
	}

	public void addLine(int x0, int x1, int x2, int x3, Color color) {
		lines.add(new Line(x0, x1, x2, x3, color));
		repaint();
	}

	public void clearLines() {
		lines.clear();
		repaint();
	}

	/**
	 * Implementation of bresenham line generation algorithm given two
	 * points and a graphics object. This method fills pixels based on the
	 * drawRect(x, y, width, height) function of the graphics object
	 * which draws a single pixel at (x,y) when width = 0 and height = 0.
	 * 
	 * @param x0 x coordinate of first point
	 * @param y0 y coordinate of first point
	 * @param x1 x coordinate of second point
	 * @param y1 y coordinate of second point
	 * @param g  graphics object for pixel to be placed on
	 */
	private void drawLine(int x0, int y0, int x1, int y1, Graphics g) {
		int dx = Math.abs(x1 - x0); // change in x
		int dy = Math.abs(y1 - y0); // change in y
		double m = (double) (y1 - y0) / (x1 - x0); // slope
		double rm; // recipricol of slope
		int px; // x coordinate of pixel
		int py; // y coordinate of pixel
		// CASES:
		// 1. x coordinates of the points are the same
		// 2. y coordinates of the points are the same
		// 3. equal change in x direction and y direction
		// 4. greater change in x direction than change in y direction
		// 5. greater change in y direction than change in x direction

		// CASE 1
		if (x0 == x1) {
			px = x0; // x position of pixel is constant
			// if first y coordinate is greater, start from second and
			// increment for each pixel
			if (y0 > y1) {
				for (int i = 0; i < dy + 1; i++) {
					py = y1 + i;
					g.drawRect(px, py, 0, 0);
				}
			}
			// if second y coordinate is greater, start from first and
			// increment for each pixel
			else if (y0 < y1) {
				for (int i = 0; i < dy + 1; i++) {
					py = y0 + i;
					g.drawRect(px, py, 0, 0);
				}

			}
			// otherwise, points are the same so draw a single pixel
			else {
				py = y0;
				g.drawRect(px, py, 0, 0);
			}

		}
		// CASE 2
		else if (y0 == y1) {
			py = y0; // y position of pixel is constant
			// if first x coordinate is greater, start from second and
			// increment for each pixel
			if (x0 > x1) {
				for (int i = 0; i < dx + 1; i++) {
					px = x1 + i;
					g.drawRect(px, py, 0, 0);
				}
			}
			// if second x coordinate is greater, start from first and
			// increment for each pixel
			else if (x0 < x1) {
				for (int i = 0; i < dx + 1; i++) {
					px = x0 + i;
					g.drawRect(px, py, 0, 0);
				}
			}

		}
		// CASE 3
		else if (dx == dy) {
			// Check relative positions of x and y cordinates, determines where to start and
			// whether to increment or decrement based on this
			if (x0 < x1 && y0 < y1) {
				for (int i = 0; i < dx + 1; i++) {
					px = x0 + i;
					py = y0 + i;
					g.drawRect(px, py, 0, 0);
				}
			} else if (x0 < x1 && y0 > y1) {
				for (int i = 0; i < dx + 1; i++) {
					px = x0 + i;
					py = y0 - i;
					g.drawRect(px, py, 0, 0);
				}
			} else if (x0 > x1 && y0 > y1) {
				for (int i = 0; i < dx + 1; i++) {
					px = x0 - i;
					py = y0 - i;
					g.drawRect(px, py, 0, 0);
				}
			} else if (x0 > x1 && y0 < y1) {
				for (int i = 0; i < dx + 1; i++) {
					px = x0 - i;
					py = y0 + i;
					g.drawRect(px, py, 0, 0);
				}
			}

		}
		// CASE 4
		else if (dx > dy) {
			// Check relative positions of x and y cordinates, determines where to start and
			// whether to increment or decrement based on this
			if (x0 < x1) {
				for (int i = 0; i < dx + 1; i++) {
					px = x0 + i;
					py = (int) (y0 + m * i);
					g.drawRect(px, py, 0, 0);
				}
			} else {
				for (int i = 0; i < dx + 1; i++) {
					px = x1 + i;
					py = (int) (y1 + m * i);
					g.drawRect(px, py, 0, 0);
				}
			}
		}
		// CASE 5
		else if (dx < dy) {
			rm = 1 / m; // get recipricol of slope to use
			// Check relative positions of x and y cordinates, determines where to start and
			// whether to increment or decrement based on this
			if (y0 < y1) {
				for (int i = 0; i < dy + 1; i++) {
					py = y0 + i;
					px = (int) (x0 + rm * i);
					g.drawRect(px, py, 0, 0);
				}
			} else {
				for (int i = 0; i < dy + 1; i++) {
					py = y1 + i;
					px = (int) (x1 + rm * i);
					g.drawRect(px, py, 0, 0);
				}
			}
		}
	}

	/**
	 * Drawing lines based on Bresenham algorithm
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Line line : lines) {
			g.setColor(line.color);
			drawLine(line.x0, line.y0, line.x1, line.y1, g);
		}
	}

	/**
	 * Screen logic
	 * Added text box for user to input N, button to draw lines,
	 * button to draw every type of line for showcasing purpose,
	 * and button to clear lines. Lines are generated randomly
	 * using Math package.
	 */
	public static void main(String[] args) {
		JFrame testFrame = new JFrame("Bresenham's Algorithm");
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final Transform2D comp = new Transform2D();
		comp.setPreferredSize(new Dimension(WINDOW_X, WINDOW_Y));
		testFrame.getContentPane().add(comp, BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		JButton everyButton = new JButton("Every Line Type");
		JButton clearButton = new JButton("Clear");

		comp.addLine(100, 100, 100, 150);
		comp.addLine(100, 150, 150, 150);
		comp.addLine(150, 150, 150, 100);
		comp.addLine(100, 100, 150, 100);
		SimpleMatrix mat = new SimpleMatrix(new double[][] {
				new double[] { 1d, 5d },
				new double[] { 2d, 3d }
		});
		System.out.println(mat.toString());

		buttonsPanel.add(everyButton);
		buttonsPanel.add(clearButton);
		testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		everyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comp.clearLines();
				comp.addLine(50, 50, 50, 50, Color.BLACK); // point
				comp.addLine(75, 150, 75, 100, Color.BLACK); // vertical
				comp.addLine(125, 100, 100, 100, Color.BLUE); // horizontal
				comp.addLine(200, 200, 250, 230, Color.ORANGE); // positive slope, dx > dy
				comp.addLine(250, 250, 300, 330, Color.MAGENTA); // positive slope, dy > dx
				comp.addLine(400, 100, 350, 300, Color.PINK); // negative slope, dy > dx
				comp.addLine(250, 100, 200, 130, Color.RED); // negative slope, dx > dy
				comp.addLine(200, 400, 250, 450, Color.GREEN); // positive slope, dx = dy
				comp.addLine(300, 400, 350, 350, Color.GRAY); // negative slope, dx = dy
			}
		});
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comp.clearLines();
			}
		});
		testFrame.pack();
		testFrame.setVisible(true);
	}
}