package br.com.wti.school.util;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.wti.school.persistence.model.ApplicationUser;
import br.com.wti.school.persistence.model.Professor;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
@Service
public class EndpointUtil implements Serializable {

	private static final long serialVersionUID = -4938229304470520703L;

	public ResponseEntity<?> returnObjectOrNotFound(Object object) {
		return object == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(object, HttpStatus.OK);
	}

	public ResponseEntity<?> returnObjectOrNotFound(List<?> list) {
		return list == null || list.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(list, HttpStatus.OK);
	}

	public Professor extractProfessorFromToken(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((ApplicationUser) authentication.getPrincipal()).getProfessor();
	}
}
