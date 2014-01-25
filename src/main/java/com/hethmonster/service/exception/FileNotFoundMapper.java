package com.hethmonster.service.exception;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FileNotFoundMapper implements
		ExceptionMapper<FileNotFoundException> {

	@Override
	public Response toResponse(FileNotFoundException ex) {
		Map<String, String> error = new HashMap<String, String>();
		error.put("error", "Resource Not Found: " + ex.getMessage());
		return Response.status(404).entity(error).type("application/json")
				.build();
	}
}
