package com.compass.portalcompass.controllers.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


import com.compass.portalcompass.exception.BancoDeDadosExcecao;
import com.compass.portalcompass.exception.NaoEncontradoExcecao;

public class ResourceExceptionHandler {
	@ExceptionHandler(NaoEncontradoExcecao.class)
	public ResponseEntity<StandardError> resourceNoutFound(NaoEncontradoExcecao e, HttpServletRequest request) {
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(BancoDeDadosExcecao.class)
	public ResponseEntity<StandardError> database(BancoDeDadosExcecao e, HttpServletRequest request) {
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
