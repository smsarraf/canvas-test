package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasCommandNotFoundException;
import com.draw.exception.CanvasException;
import com.draw.exception.CanvasNotYetCreatedException;
import com.draw.exception.WrongParamsException;
import com.draw.util.ApplicationUtil;
import lombok.Getter;
import lombok.Setter;

import static com.draw.util.ApplicationUtil.isCanvasExits;

/**
 * Command used by the Canvas for filling the area around a given coordinate.
 * Same as "bucket fill" tool in paint programs.
 */
@Getter
@Setter
public class BucketFill extends Command {

    private int x;
    private int y;
    private String color;
    private Canvas canvas;

    public BucketFill(Canvas canvas) {
        name = ApplicationUtil.CMD_FILL;
        this.canvas = canvas;
    }

    public BucketFill(int x, int y, String color, Canvas canvas) {
        this(canvas);
        this.x = x;
        this.y = y;
        this.color = color;
        this.canvas = canvas;
    }

    public BucketFill(String[] parameters, Canvas canvas) throws CanvasException {
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
    protected void validate(String[] params) throws CanvasException {
        isCanvasExits(canvas);

        if (params.length != 4) {
            throw new CanvasCommandNotFoundException("Wrong Parameters! For filling an area try 'B x1 y1 color',  example: 'B 2 3 c'");
        } else {
            try {
                this.setX(Integer.parseInt(params[1]));
                this.setY(Integer.parseInt(params[2]));
            } catch (Exception e) {
                throw new WrongParamsException("Coordinates must be numbers! Example: 'B 2 3 c'");
            }

            String color = params[3];
            if (color.length() > 1) {
                throw new WrongParamsException("Wrong Parameters! Color must be a single character. Example: 'B 2 3 c'");
            }
            this.setColor(params[3]);

			if (this.getX() <= 0 || this.getX() > canvas.getWidth() || this.getY() <= 0 || this.getY() > canvas.getHeight()) {
				System.out.println("FillBucket - Wrong coordinates: (" + this.getX() + ", " + this.getY() + ")");
				throw new WrongParamsException("The coordinates must be inside the draw: (x > 0 && x <= " + canvas.getWidth() + ") && (y > 0 && y <= " + canvas.getHeight() + ")");
			}
        }
    }

}