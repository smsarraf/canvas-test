package com.draw.exception;


/**
 * Thrown when calling a command or operation not supported.
 * Example when trying to draw a line that is not horizontal, not vertical.
 */
public class CanvasCommandNotYetImplementedException extends CanvasException {

	private static final long serialVersionUID = -8629000422659650854L;

	public CanvasCommandNotYetImplementedException(String message){
		super(message);
	}
	
	public CanvasCommandNotYetImplementedException(String message, Throwable e){
		super(message,e);
	}
}
