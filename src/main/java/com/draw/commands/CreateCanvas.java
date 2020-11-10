package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import com.draw.exception.WrongParamsException;
import com.draw.util.ApplicationUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Command used by the draw for creating a new Canvas
 */
@Getter
@Setter
public class CreateCanvas extends Command {

	private int weight;
	private int height;
	private Canvas canvas;

	public CreateCanvas(Canvas canvas) {
		name = ApplicationUtil.CMD_CREATE_CANVAS;
		this.canvas = canvas;
	}

	public CreateCanvas(String[] parameters, Canvas canvas) throws CanvasException {
		this(canvas);
		validate(parameters);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void execute() throws CanvasException {
		canvas = new Canvas(weight, height);
	}

	@Override
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	protected void validate(String[] params) throws CanvasException {
		if (params.length != 3) {
			throw new WrongParamsException("Wrong Parameters! For creating new draw try 'C w h',  example: 'C 40 20'");
		} else {
			try {
				setHeight(Integer.parseInt(params[2]));
				setWeight(Integer.parseInt(params[1]));
			} catch (Exception e) {
				throw new WrongParamsException("Weight and Height must be numbers! Example: 'C 40 20'");
			}
		}

	}
}
