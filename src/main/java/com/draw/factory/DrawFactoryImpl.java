package com.draw.factory;

import com.draw.commands.*;
import com.draw.core.Canvas;
import com.draw.exception.CanvasCommandNotFoundException;
import com.draw.exception.CanvasException;
import com.draw.exception.WrongParamsException;

import static com.draw.util.ApplicationUtil.*;

/**
 * Factory implementation for creating Draw objects.
 */
public class DrawFactoryImpl implements DrawFactory {

    @Override
    public Command buildCommand(String line, Canvas canvas) throws CanvasException {
        Command cmd;
        line = line.trim();
        String[] parameters = line.split("\\s+");//one or more space

        if (parameters.length == 0) {
            throw new WrongParamsException("Wrong command! Try one of the following: C w h; L x1 y1 x2 y2; R x1 y1 x2 y2; B x y c or Q for quit.");
        }

        switch (parameters[0].toUpperCase()) {
            case CMD_CREATE_CANVAS:
                // C w h
				cmd = new CreateCanvas(parameters, canvas);
				break;
            case CMD_LINE:
                // L x1 y1 x2 y2
                cmd = new Line(parameters, canvas);
                break;
            case CMD_RECTANGLE:
                // R x1 y1 x2 y2
                cmd = new Rectangle(parameters, canvas);
                break;
            case CMD_FILL:
                // B x y c
                cmd = new BucketFill(parameters, canvas);
                break;
            case CMD_QUIT:
                // Q
                cmd = new Quit(canvas);
                break;
            case CMD_HELP:
                // H
                cmd = new Help(canvas);
                break;
            case CMD_CLEAR:
                // X
                cmd = new Clear(canvas);
                break;
            default:
                throw new CanvasCommandNotFoundException("Command not found! The line must start with letter: 'C', 'L', 'R, 'B' or 'Q' for exit.");

        }
        return cmd;
    }

}
