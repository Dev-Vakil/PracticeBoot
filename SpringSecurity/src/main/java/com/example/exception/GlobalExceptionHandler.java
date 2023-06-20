package com.example.exception;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    	
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })    
    public ResponseEntity<Object> handleConflict(
      RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific";
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
		
	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<Object> NullPointer(NullPointerException exception){
		return new ResponseEntity<>("Null pointer exception has occured",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {SQLException.class,DataAccessException.class})
	public ResponseEntity<Object> databaseError() {	  		
		return new ResponseEntity<>("SQL Exception occured",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleError404(Exception e)   {	
		e.printStackTrace();
		return new ResponseEntity<>("An Unknown Exception has occured",HttpStatus.NOT_FOUND);
	}
	 
}
