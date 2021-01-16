package com.mesutemre.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "/testet" , method = RequestMethod.GET)
	public ResponseEntity<String> testEt(){
		return new ResponseEntity<String>("Merhaba...",HttpStatus.OK);
	}
	 
}
