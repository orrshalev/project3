package com.p3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;

/**
 * Graphical app that can do simple geometric line manipulations
 */
public class Transform2D extends JComponent {
	static final int WINDOW_X = 500; // # of pixels horizontally
	static final int WINDOW_Y = 500; // # of pixels vertically

	// text area vars
	static private JTextArea textArea = new JTextArea(15, 30);
	static private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea);

	// file chooser var
	static final JFileChooser fc = new JFileChooser();

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

	public void setLines(LinkedList<Line> newLines) {
		lines.clear();
		for (Line newLine : newLines) {
			lines.add(newLine);
		}
		repaint();
	}

	public LinkedList<Line> getLines() {
		return lines;
	}

	/**
	 * Implementation of simple line generation algorithm given two
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
	 * Drawing lines based on simple line algorithm
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
	 */
	public static void main(String[] args) {
		// screen
		JFrame testFrame = new JFrame("2D Transformations");
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final Transform2D comp = new Transform2D();
		comp.setPreferredSize(new Dimension(WINDOW_X, WINDOW_Y));
		testFrame.getContentPane().add(comp, BorderLayout.CENTER);

		// text
		testFrame.getContentPane().add(textArea, BorderLayout.EAST);
		System.setOut(new PrintStream(taOutputStream));

		// buttons
		JPanel buttonsPanel = new JPanel();
		JButton inputButton = new JButton("Input Lines");
		JButton transformButton = new JButton("Transform");
		JButton outputButton = new JButton("Output Lines");
		buttonsPanel.add(inputButton);
		buttonsPanel.add(transformButton);
		buttonsPanel.add(outputButton);
		testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		// input file button logic
		inputButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comp.clearLines(); // clear lines for new lines
				fc.showOpenDialog(testFrame);
				File file = fc.getSelectedFile();
				try {
					Scanner myReader = new Scanner(file);
					while (myReader.hasNextLine()) {
						String lineText = myReader.nextLine();
						String[] line = lineText.split(" ");
						comp.addLine(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]),
								Integer.parseInt(line[3]));
					}
					myReader.close();
				} catch (FileNotFoundException notFound) {
					System.out.println("File not found");
				}
			}
		});
		// Button to activate transformation logic
		transformButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputCommand = textArea.getText();
				String lastInput = inputCommand.substring(inputCommand.lastIndexOf("\n") + 1);
				String[] command = lastInput.split(" ");
				double[][] tMatrix;
				// System.out.println("Last command found: " + lastCommand);
				if (command[0].equals("help")) {
					System.out.println("To be prompted to enter a line file, press the 'Input Lines' button.\n" +
							"To transform visible lines, enter a valid command in the console and then press the 'Transform' button.\n" +
							"Valid commands:\n" +
							"BasicTranslate x y\n" +
							"BasicScale x y\n" +
							"BasicRotate angle\n" +
							"Scale x y Cx Cy\n" +
							"Rotate angle Cx Cy\n" +
							"To write line coordinates to a file, type the path to the file you want to write to and\n" +
							"press the 'Output Lines' button.");
				} else if (command[0].equals("BasicTranslate")) {
					tMatrix = Matrix.basicTranslate(Double.parseDouble(command[1]), Double.parseDouble(command[2]));
					comp.setLines(Matrix.applyTransformation(tMatrix, comp.getLines()));
				} else if (command[0].equals("BasicScale")) {
					tMatrix = Matrix.basicScale(Double.parseDouble(command[1]), Double.parseDouble(command[2]));
					comp.setLines(Matrix.applyTransformation(tMatrix, comp.getLines()));
				} else if (command[0].equals("BasicRotate")) {
					tMatrix = Matrix.basicRotate(Double.parseDouble(command[1]));
					comp.setLines(Matrix.applyTransformation(tMatrix, comp.getLines()));
				} else if (command[0].equals("Scale")) {
					tMatrix = Matrix.scale(Double.parseDouble(command[1]), Double.parseDouble(command[2]),
							Integer.parseInt(command[3]), Integer.parseInt(command[4]));
					comp.setLines(Matrix.applyTransformation(tMatrix, comp.getLines()));
				} else if (command[0].equals("Rotate")) {
					tMatrix = Matrix.rotate(Double.parseDouble(command[1]), Double.parseDouble(command[2]),
							Integer.parseInt(command[3]));
					comp.setLines(Matrix.applyTransformation(tMatrix, comp.getLines()));
				} else {
					System.out.println("Could not parse command");
				}

			}
		});
		// Output lines to file button
		outputButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String inputCommand = textArea.getText();
				String filePath = inputCommand.substring(inputCommand.lastIndexOf("\n") + 1);
				try {
					File file = new File(filePath);
					if (file.createNewFile()) {
						FileWriter myWriter = new FileWriter(file);
						for (Line line : comp.getLines()) {
							myWriter.write(line.toString() + "\n");
						}
						myWriter.close();
						System.out.println("Lines written to " + filePath);
					} else {
						System.out.println("File already exists");
					}
				} catch (IOException ioe) {
					System.out.println("Error");
				}
			}
		});
		testFrame.pack();
		testFrame.setVisible(true);

		// instructions
		System.out.println("### TYPE \'help\' AND PRESS 'Transform' FOR PROGRAM INSTRUCTIONS ###");
		System.out.println("COMMANDS:");
	}
}