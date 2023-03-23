package br.com.wti.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Washington Antunes for wTI on 22/03/2023.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1749673338150456407L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
