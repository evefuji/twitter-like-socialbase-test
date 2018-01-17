package br.everton.socialbase.controller.handler;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlRootElement;

import br.everton.socialbase.controller.exception.RestException;

@Provider
public class ExceptionHandler implements ExceptionMapper<RestException> {

	public Response toResponse(RestException ex) {
		return Response.status(
			ex.getHttpStatusCode()).entity(
				new ExceptionResponse(ex.getMessage(), ex.getErrorCode())
			).build();
	}

	@XmlRootElement 
	public static class ExceptionResponse {
		String message;
		int errorCode;

		public ExceptionResponse() {
			super();
		}
		
		public ExceptionResponse(String message, int errorCode) {
			super();
			this.message = message;
			this.errorCode = errorCode;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}
		
		public String toString(){
			return message;
			
		}
	}
}
