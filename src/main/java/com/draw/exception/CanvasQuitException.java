package com.draw.exception;

/**
 * General Canvas Exception. All application exceptions extends this class.
 */
public class CanvasQuitException extends CanvasException {

	private static final long serialVersionUID = 6056719373037617209L;

	public CanvasQuitException(String message){
		super(message);
	}
	
	public CanvasQuitException(String message, Throwable e){
		super(message,e);
	}
}
