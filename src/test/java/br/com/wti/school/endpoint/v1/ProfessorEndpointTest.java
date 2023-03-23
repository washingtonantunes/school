package br.com.wti.school.endpoint.v1;

import br.com.wti.school.persistence.model.Professor;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
public class ProfessorEndpointTest {

	public static Professor mockProfessor(){
		return Professor.Builder.newProfessor()
				.id(1L)
				.name("Will")
				.email("will@something.com")
				.build();
	}
}
