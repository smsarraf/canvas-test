package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import com.draw.exception.CanvasQuitException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static com.draw.util.ApplicationUtil.CMD_QUIT;

/**
 * Command used for quite the draw.
 */
public class Quit extends Command{
	
	private Canvas canvas;
	
	private Quit() {
		name = CMD_QUIT;
	}
	
	public Quit(Canvas canvas) {
		this();
		this.canvas = canvas;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void execute() throws CanvasException {
		throw new CanvasQuitException("Quiting the game!");
	}
	
	@Override
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void validate(String[] params)  {
		throw new NotImplementedException();
	}

}