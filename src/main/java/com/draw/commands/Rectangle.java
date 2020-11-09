package com.draw.commands;

import com.draw.core.Canvas;
import com.draw.exception.CanvasCommandNotFoundException;
import com.draw.exception.CanvasException;
import com.draw.exception.CanvasNotYetCreatedException;
import com.draw.exception.WrongParamsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.draw.util.ApplicationUtil.*;

/**
 * Represents the command for drawing a rectangle in the draw.
 * A rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2)
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Rectangle extends Command {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Canvas canvas;

    public Rectangle(Canvas canvas) {
        name = CMD_RECTANGLE;
        this.canvas = canvas;
    }

    public Rectangle(String[] parameters, Canvas canvas) throws CanvasException {
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
            throw new CanvasNotYetCreatedException("First create a new Canvas using 'C' command. Insert '" + CMD_HELP + "' for Help.\n");
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
            throw new CanvasCommandNotFoundException("Wrong Parameters! For drawing a Rectangle try 'R x1 y1 x2 y2',  example: 'R 1 1 6 3'");
        } else {
            try {
                this.x1 = Integer.parseInt(params[1]);
                this.y1 = Integer.parseInt(params[2]);
                this.x2 = Integer.parseInt(params[3]);
                this.y2 = Integer.parseInt(params[4]);
            } catch (Exception e) {
                throw new WrongParamsException("Coordinates must be numbers! Example: 'R 1 1 9 4'");
            }

            if (this.getX1() > this.getX2() || this.getY1() > this.getY2()) {
                System.out.println("Insert Rectangle - Wrong coordinates: (" + this.getX1() + ", " + this.getY1() + ") (" + this.getX2() + ", " + this.getY2() + ")");
                throw new WrongParamsException("The coordinates must be congruent: (x1 < x2 && y1 < y2)");
            }

            if (this.getX1() <= 0 || this.getX2() > canvas.getWidth() || this.getY1() <= 0 || this.getY2() > canvas.getHeight()) {
                System.out.println("Insert Rectangle - Wrong coordinates: (" + this.getX1() + ", " + this.getY1() + ") (" + this.getX2() + ", " + this.getY2() + ")");
                throw new WrongParamsException("The coordinates must be inside the draw: (x > 0 && x <=" + canvas.getWidth() + ") && (y > 0 && y <= " + canvas.getHeight() + ")");
            }
        }
    }

}