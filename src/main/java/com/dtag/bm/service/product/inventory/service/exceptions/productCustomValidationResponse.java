package com.dtag.bm.service.product.inventory.service.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


	@RestController
	@ControllerAdvice
	public class productCustomValidationResponse extends ResponseEntityExceptionHandler {
		
		
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ExceptionHandler(ProductInventorycustomValidatorException.class)
		public final ResponseEntity<String> handleException(ProductInventorycustomValidatorException ex, WebRequest request) {
			
			String errorMessge = ex.getMessage();
					
					return new ResponseEntity<String>(errorMessge,HttpStatus.BAD_REQUEST);
		}
			

	}



