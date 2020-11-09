package com.draw.exception;

/**
 * Thrown when parameters inserted from command line are not congruent. 
 * Example: coordinates outside the draw.
 */
public class WrongParamsException extends CanvasException {

	private static final long serialVersionUID = -8629000422659650854L;

	public WrongParamsException(String message){
		super(message);
	}
	
	public WrongParamsException(String message, Throwable e){
		super(message,e);
	}
}
