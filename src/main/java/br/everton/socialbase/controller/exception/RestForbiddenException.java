package br.everton.socialbase.controller.exception;

public class RestForbiddenException extends RestException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5867752187076219606L;
	private static final int HTTP_STATUS_CODE = 403;

	public RestForbiddenException() {
		super(HTTP_STATUS_CODE);
	}

	public RestForbiddenException(String message) {
		super(HTTP_STATUS_CODE, message);
	}
	
	public RestForbiddenException(String message, int errorCode) {
		super(HTTP_STATUS_CODE, message);
		setErrorCode(errorCode);
	}

}