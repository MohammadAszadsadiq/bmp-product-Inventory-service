package com.dtag.bm.service.product.inventory.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ProductInventoryExceptionHandler {
	 @ExceptionHandler(ProductInventoryValidatorException.class)
	  public final ResponseEntity<String> handleFieldsValidation(ProductInventoryValidatorException ex, WebRequest request) {
	    
//	    ErrorMessage errorObj = new ErrorMessage(new Date(), ex.getMessage(),
//	        request.getDescription(false), HttpStatus.BAD_REQUEST);
		 String errorMsg ="Fields Can't Be NUll";
	    
	   // return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	    return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
	  }
}
