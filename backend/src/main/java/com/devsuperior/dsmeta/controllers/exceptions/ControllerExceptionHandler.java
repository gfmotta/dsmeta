package com.devsuperior.dsmeta.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dsmeta.services.exceptions.DatabaseException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler({ DatabaseException.class, RuntimeException.class })
	public ResponseEntity<StandardError> database(DatabaseException exception, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError error = new StandardError();
		error.setTimespan(Instant.now());
		error.setStatus(status.value());
		error.setError("Registro não encontrado");
		error.setMessage(exception.getMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(error);
	}
}