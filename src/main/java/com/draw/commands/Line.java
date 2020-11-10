package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.*;
import com.draw.util.ApplicationUtil;
import lombok.Getter;
import lombok.Setter;

import static com.draw.util.ApplicationUtil.CMD_LINE;
import static com.draw.util.ApplicationUtil.isCanvasExits;

/**
 * Represents the command for drawing a line in the draw.
 * A line is represented by two points connected together. Line from (x1,y1) to (x2,y2).
 */
@Getter
@Setter
public class Line extends Command {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Canvas canvas;

    public Line(Canvas canvas) {
        name = CMD_LINE;
        this.canvas = canvas;
    }

    public Line(String[] parameters, Canvas canvas) throws CanvasException {
        this(canvas);
        validate(parameters);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() throws CanvasException {
        if (canvas == null) {
            throw new CanvasNotYetCreatedException("First create a new Canvas using 'C' command. Insert '" + ApplicationUtil.CMD_HELP + "' for Help.\n");
        }
        canvas.getCommands().add(this);
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void validate(String[] params) throws CanvasException {

        isCanvasExits(canvas);

        if (params.length != 5) {
            throw new CanvasCommandNotFoundException("Wrong Parameters! For drawing a line try 'L x1 y1 x2 y2',  example: 'L 1 2 6 2'");
        } else {
            try {
                setX1(Integer.parseInt(params[1]));
                setX2(Integer.parseInt(params[3]));
                setY1(Integer.parseInt(params[2]));
                setY2(Integer.parseInt(params[4]));
            } catch (Exception e) {
                throw new WrongParamsException("Coordinates must be numbers! Example: 'L 1 1 9 1'");
            }

			if (getX1() > getX2() || getY1() > getY2()) {
				System.out.println("InsertLine - Wrong coordinates: (" + getX1() + ", " + getY1() + ") (" + getX2() + ", " + getY2() + ")");
				throw new WrongParamsException("The coordinates must be congruent: (x1 < x2 && y1 < y2)");
			}

			if (!((getX1() == getX2() && getY1() != getY2()) || (getY1() == getY2() && getX1() != getX2()))
					&& !(getX1() == getX2() && getY1() == getY2())) {
				System.out.println("InsertLine - Wrong coordinates: (" + getX1() + ", " + getY1() + ") (" + getX2() + ", " + getY2() + ")");
				throw new CanvasCommandNotYetImplementedException("You can draw only horizontal lines: (x1==x2 || y1 == y2)");
			}

			if (getX1() <= 0 || getX2() > canvas.getWidth() || getY1() <= 0 || getY2() > canvas.getHeight()) {
				System.out.println("InsertLine - Wrong coordinates: (" + getX1() + ", " + getY1() + ") (" + getX2() + ", " + getY2() + ")");
				throw new WrongParamsException("The coordinates must be inside the draw: (x > 0 && x <= " + canvas.getWidth() + ") && (y > 0 && y <= " + canvas.getHeight() + ")");
			}
        }
    }

}