package br.com.wti.school.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Washington Antunes for wTI on 19/03/23.
 */
@Getter
@Setter
@Entity
public class ApplicationUser extends AbstractEntity {

	public ApplicationUser() {
	}

	public ApplicationUser(ApplicationUser applicationUser) {
		this.username = applicationUser.username;
		this.password = applicationUser.password;
		this.professor = applicationUser.professor;
	}

	private static final long serialVersionUID = -8298010984383607318L;

	@NotEmpty(message = "The field username cannot be empty")
	@Column(unique = true)
	private String username;

	@NotEmpty(message = "The field password cannot be empty")
	private String password;

	@OneToOne
	private Professor professor;
}
