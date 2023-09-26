package com.dtag.bm.service.product.inventory.service.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ProductCustomeInventoryExceptionHandler {
	@ExceptionHandler(ProductInventoryValidatorException.class)
	public final ResponseEntity<ErrorCustomeMessage> handleFieldsValidation(ProductInventoryValidatorException ex,
			WebRequest request) {

		ErrorCustomeMessage errorObj = new ErrorCustomeMessage(new Date(), ex.getMessage(),
				request.getDescription(false), HttpStatus.BAD_REQUEST);
	

		return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	
	}
}
