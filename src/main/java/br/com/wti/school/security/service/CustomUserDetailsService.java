package br.com.wti.school.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.wti.school.persistence.model.ApplicationUser;

/**
 * @author Washington Antunes for wTI on 19/03/23.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final ApplicationUserRepository applicationUserRepository;

	public CustomUserDetailsService(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser applicationUser = loadApplicationUserByUsername(username);
		return new CustomUserDetails(applicationUser);
	}

	public ApplicationUser loadApplicationUserByUsername(String username) {
		return Optional.ofNullable(applicationUserRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("ApplicationUser not found"));
	}

	private final static class CustomUserDetails extends ApplicationUser implements UserDetails {

		private static final long serialVersionUID = -5591965327356771919L;

		private CustomUserDetails(ApplicationUser applicationUser) {
			super(applicationUser);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> authorityListProfessor = AuthorityUtils.createAuthorityList("ROLE_PROFESSOR");
			List<GrantedAuthority> authorityListStudent = AuthorityUtils.createAuthorityList("ROLE_STUDENT");
			return this.getProfessor() != null ? authorityListProfessor : authorityListStudent;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
	}
}
