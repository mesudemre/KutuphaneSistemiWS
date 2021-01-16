package com.mesutemre.exceptions;

public class SavePointErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SavePointErrorException() {
		super();
	}
	
	public SavePointErrorException(String message){
        super("Kayıt esnasında hata meydana geldi!");
    }
	
    public SavePointErrorException(Exception e){
        super(e);
    }

}
