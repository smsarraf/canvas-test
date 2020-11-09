package com.draw.exception;

/**
 * Thrown when cannot build a command starting from the input string inserted by the user.
 */
public class CanvasCommandNotFoundException extends CanvasException {

	private static final long serialVersionUID = -8629000422659650854L;

	public CanvasCommandNotFoundException(String message){
		super(message);
	}
	
	public CanvasCommandNotFoundException(String message, Throwable e){
		super(message,e);
	}
}
