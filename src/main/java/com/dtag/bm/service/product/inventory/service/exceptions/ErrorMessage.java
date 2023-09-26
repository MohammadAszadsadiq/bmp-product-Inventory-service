package com.dtag.bm.service.product.inventory.service.exceptions;

public class ErrorMessage {
	//  private HttpStatus httpStatus;
	//  private Date timestamp;
	  private String message;
	// private String details;



	  
	  public ErrorMessage(String message) {
	 // public ErrorMessage(Date timestamp, String message, String details,HttpStatus httpStatus) {
	    //this.timestamp = timestamp;
	    this.message = message;
	   // this.details = details;
	    //this.httpStatus = httpStatus;
	  }
	 
//	    public Date getTimestamp() {
//	        return timestamp;
//	    }

	 
//	    public void setTimestamp(Date timestamp) {
//	        this.timestamp = timestamp;
//	    }
	 
	    public String getMessage() {
	        return message;
	    }

	 
	    public void setMessage(String message) {
	        this.message = message;
	    }

	 
//	    public String getDetails() {
//	        return details;
//	    }
//
//	 
//	    public void setDetails(String details) {
//	        this.details = details;
//	    }
//	    public HttpStatus getHttpStatus() {
//			return httpStatus;
//		}
//
//		public void setHttpStatus(HttpStatus httpStatus) {
//			this.httpStatus = httpStatus;
//		}



	}
