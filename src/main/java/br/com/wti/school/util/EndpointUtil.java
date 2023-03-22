package br.com.wti.school.util;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
@Service
public class EndpointUtil implements Serializable {

	public ResponseEntity<?> returnObjectOrNotFound(Object object) {
		return object == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(object, HttpStatus.OK);
	}
}
