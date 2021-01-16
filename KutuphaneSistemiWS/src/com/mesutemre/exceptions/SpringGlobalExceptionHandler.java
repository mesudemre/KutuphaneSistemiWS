package com.mesutemre.exceptions;

import java.sql.BatchUpdateException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mesutemre.model.ErrorDetail;

@ControllerAdvice
public class SpringGlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    protected  ErrorDetail handleSpringBadRequestException(HttpServletRequest request, Exception exception) {
		return new ErrorDetail(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage());
	} 
	
	@ExceptionHandler(BatchUpdateException.class)
    @ResponseBody
    protected  ErrorDetail handleSavePointException(HttpServletRequest request, Exception exception) {
		return new ErrorDetail(HttpStatus.BAD_REQUEST.value(), exception.getLocalizedMessage());
	} 
	
	@ExceptionHandler(ParseException.class)
    @ResponseBody
	protected  ErrorDetail handleParseException(HttpServletRequest request, Exception exception) {
		return new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "Parse hatası meydana geldi!");
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "JSON ayrıştırma hatası !");
		return new ResponseEntity<Object>(errorDetail, headers, HttpStatus.BAD_REQUEST);
	}
	
}
