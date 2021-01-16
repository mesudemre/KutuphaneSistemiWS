package com.mesutemre.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message){
        super(message);
    }
	
    public UserNotFoundException(Exception e){
        super(e);
    }
	
}
