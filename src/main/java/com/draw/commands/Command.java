package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import lombok.Getter;
import lombok.Setter;

/**
 * Each command must extends this class so must provide at least the name, draw status, and execute method.
 */
@Getter
@Setter
public abstract class Command {
	
	protected String name;

	public abstract void execute() throws CanvasException;
	
	public abstract Canvas getCanvas();

	protected abstract void validate(String[] params) throws CanvasException;
	
}
