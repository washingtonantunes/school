package br.com.wti.school.persistence.respository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.wti.school.persistence.model.Professor;

/**
 * @author Washington Antunes for wTI on 22/03/23.
 */
public interface ProfessorRepository extends PagingAndSortingRepository<Professor, Long> {
	
    Professor findByEmail(String email);
}
