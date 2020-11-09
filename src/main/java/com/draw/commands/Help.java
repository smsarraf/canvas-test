package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.util.ApplicationUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Help command
 */
public class Help extends Command {
	
	private Canvas canvas;

	private Help() {
		name = ApplicationUtil.CMD_HELP;
	}
	public Help(Canvas canvas) {
		this();
		this.canvas = canvas;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void execute() {
		printHelp();
	}
	
	@Override
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void validate(String[] params)  {
		throw new NotImplementedException();
	}

	private void printHelp() {
		System.out.println();
		System.out.println("************ OPTIONS *************");
		System.out.println("C w h - Create a new draw of width w and height h.");
		System.out.println("L x1 y1 x2 y2 - Create a new line from (x1,y1) to (x2,y2).");
		System.out.println("R x1 y1 x2 y2 - Create a new rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2).");
		System.out.println("B x y c - Fill the area connected to (x,y) with 'color' c.");
		System.out.println("X - Clear the draw.");
		System.out.println("H - Help.");
		System.out.println("Q - Quit the program.");
		System.out.println("**********************************");
		System.out.println();
	}

}
