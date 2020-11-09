package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import com.draw.exception.CanvasNotYetCreatedException;
import com.draw.util.ApplicationUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Clear extends Command {
	
	private Canvas canvas;
	
	private Clear() {
		name = ApplicationUtil.CMD_CLEAR;
	}
	public Clear(Canvas canvas) {
		this();
		this.canvas = canvas;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void execute() throws CanvasException {
		if(canvas == null){
			throw new CanvasNotYetCreatedException("First create a new Canvas using 'C' command. Insert '"+ ApplicationUtil.CMD_HELP+"' for Help.\n");
		}
		canvas.clearCanvas();
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
