package com.vpsy._2f.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/welcome")
public class WelcomeController {

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> welcome(HttpServletRequest request){
		System.out.println("WelcomeController.applicationHealth()");
		return  new ResponseEntity<String>("Welcome to _2f", HttpStatus.OK);
	}
}
