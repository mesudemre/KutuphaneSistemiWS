package com.mesutemre.model;

import java.io.Serializable;

public class ErrorDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int statusCode;
	private String statusMessage;

	public ErrorDetail() {
		super();
	}

	public ErrorDetail(Exception e) {
		this.statusCode = -1;
		this.statusMessage = e.getMessage();
	}
	public ErrorDetail(int statusCode, String errorMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = errorMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
}
