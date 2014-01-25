package com.netflix.service.exception;

import java.io.FileNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FileNotFoundMapper implements
		ExceptionMapper<FileNotFoundException> {

	@Override
	public Response toResponse(FileNotFoundException ex) {
		return Response.status(404).entity(ex.getMessage())
				.type("application/json").build();
	}
}
