package br.com.wti.school.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import br.com.wti.school.security.filter.JWTAuthenticationFilter;
import br.com.wti.school.security.filter.JWTAuthorizationFilter;
import br.com.wti.school.security.service.CustomUserDetailsService;

/**
 * @author Washington Antunes for wTI on 19/03/23.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailsService customUserDetailsService;

	@Autowired
	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
				.and().csrf().disable()
				.authorizeRequests()
				.antMatchers("/*/professor/**").hasRole("PROFESSOR")
				.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailsService));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
