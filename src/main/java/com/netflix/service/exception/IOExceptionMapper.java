package com.netflix.service.exception;

import java.io.IOException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IOExceptionMapper implements
		ExceptionMapper<IOException> {

	@Override
	public Response toResponse(IOException ex) {
		return Response.status(400).entity(ex.getMessage())
				.type("application/json").build();
	}
}
