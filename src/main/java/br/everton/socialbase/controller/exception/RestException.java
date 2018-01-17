package br.everton.socialbase.controller.exception;

public class RestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int httpStatusCode = 500;
	private int errorCode;

	public RestException() {
		super();
	}

	public RestException(int httpStatusCode) {
		super();
		this.httpStatusCode = httpStatusCode;
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(int httpStatusCode, String message) {
		super(message);
		this.httpStatusCode = httpStatusCode;
	}

	public RestException(int httpStatusCode, String message, int errorCode) {
		super(message);
		this.httpStatusCode = httpStatusCode;
		this.errorCode = errorCode;
	}
	
	public RestException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
