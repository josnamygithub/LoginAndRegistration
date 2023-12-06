package com.example.demo.common;

public class AccessDeniedException extends RuntimeException{
	
	public AccessDeniedException(String message){
		super(message);
	}

}
