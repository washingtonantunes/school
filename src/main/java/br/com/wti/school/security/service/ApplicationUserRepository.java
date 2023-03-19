package br.com.wti.school.security.service;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.wti.school.persistence.model.ApplicationUser;

/**
 * @author Washington Antunes for wTI on 19/03/23.
 */
public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

	ApplicationUser findByUsername(String username);
}
