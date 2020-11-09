package com.draw.util;

import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import com.draw.exception.CanvasNotYetCreatedException;

/**
 * Constants used by the application
 */
public class ApplicationUtil {

	public final static String CMD_CREATE_CANVAS = "C";
	public static int CANVAS_MAX_WIDTH = 200;
	public static int CANVAS_MAX_HEIGHT = 50;
	public final static String CMD_LINE      = "L";
	public final static String CMD_RECTANGLE = "R";
	public final static String CMD_FILL      = "B";
	public final static String CMD_QUIT = "Q";
	public final static String CMD_HELP      = "H";
	
	public final static String CMD_CLEAR      = "X"; 
	
	public final static String PIXEL = "x";

	public static void isCanvasExits(Canvas canvas) throws CanvasException {
		if(canvas.getHeight() == 0 && canvas.getWidth() == 0){
			throw  new CanvasNotYetCreatedException("No Canvas found, Please a new draw first!");
		}
	}
	
}
