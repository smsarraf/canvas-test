package com.draw.exception;

/**
 * @unused actually
 * Thrown when you try to do an operation without first creating a draw.
 */
public class NoCanvasException extends CanvasException {

	private static final long serialVersionUID = -8629000422659650854L;

	public NoCanvasException(String message){
		super(message);
	}
	
	public NoCanvasException(String message, Throwable e){
		super(message,e);
	}
}
